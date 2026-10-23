package pe.visorweb.test.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.Properties;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Parameter;
import pe.visorweb.utils.EvidenciaUtils;

public class WatcherErrorTest implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

	private final BaseTest baseTest;

	public WatcherErrorTest(BaseTest baseTest) {
		this.baseTest = baseTest;
	}

	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {

		String browserActual = baseTest.getNombreNavegadorActual();
		String nombreClaseDinamico = context.getRequiredTestClass().getSimpleName();
		String nombreMetodoDinamico = context.getRequiredTestMethod().getName();
		
		registrarEntornoDinamico(browserActual);
		configurarCategoriasDefectos();

		Allure.getLifecycle().updateTestCase(testResult -> {
			// A) Forzamos la inserción del parámetro en la iteración parametrizada activa
			Parameter navegadorParam = new Parameter().setName("Navegador de Ejecución").setValue(browserActual);
			testResult.getParameters().add(navegadorParam);

			// B) Mutamos el historyId y testCaseId para separar físicamente los reportes
			String baseHistoryId = testResult.getHistoryId();
			if (baseHistoryId != null) {
				testResult.setHistoryId(generarMd5(baseHistoryId + "-" + browserActual));
			} else {
				// Si por alguna razón viene null, asignamos una firma única forzada
				testResult.setHistoryId(generarMd5(context.getUniqueId() + "-" + browserActual));
			}

			String baseTestCaseId = testResult.getTestCaseId();
			if (baseTestCaseId != null) {
				testResult.setTestCaseId(generarMd5(baseTestCaseId + "-" + browserActual));
			}

			testResult.getLabels().removeIf(label -> "suite".equals(label.getName()));
			testResult.getLabels().add(new io.qameta.allure.model.Label().setName("suite")
					.setValue(nombreClaseDinamico + " - " + browserActual));

			testResult.getLabels().removeIf(label -> "subSuite".equals(label.getName()));

			boolean esParametrizado = context.getRequiredTestMethod()
					.isAnnotationPresent(org.junit.jupiter.params.ParameterizedTest.class);
			if (esParametrizado) {
				testResult.getLabels()
						.add(new io.qameta.allure.model.Label().setName("subSuite").setValue(nombreMetodoDinamico));

			}

		});

	}
	
    private void registrarEntornoDinamico(String browserActual) {
        try {
            Properties props = new Properties();
            File envFile = new File("target/allure-results/environment.properties");
            
            // Si el archivo ya existe (de una ejecución previa como Chrome), lo cargamos
            if (envFile.exists()) {
                try (FileInputStream fis = new FileInputStream(envFile)) {
                    props.load(fis);
                }
            }
            
            // Concatenamos navegadores si ejecutamos más de uno en la misma carpeta
            String navegadoresPrevios = props.getProperty("Navegador de Ejecución");
            if (navegadoresPrevios != null && !navegadoresPrevios.contains(browserActual)) {
                props.setProperty("Navegador de Ejecución", navegadoresPrevios + ", " + browserActual);
            } else if (navegadoresPrevios == null) {
                props.setProperty("Navegador de Ejecución", browserActual);
            }

            // Propiedades de infraestructura fijas
            props.setProperty("Sistema Operativo", System.getProperty("os.name"));
            props.setProperty("Versión de Java", System.getProperty("java.version"));
            props.setProperty("Ambiente", "LOCAL-QA");

            // Guardamos de forma física en el disco
            envFile.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(envFile)) {
                props.store(fos, "Allure Dynamic Environment Properties");
            }
        } catch (Exception e) {
            System.err.println("Error al registrar información de entorno en archivo: " + e.getMessage());
        }
    }
    
    private void configurarCategoriasDefectos() {
        try {
            File categoriasOrigen = new File("target/test-classes/categories.json");
            File categoriasDestino = new File("target/allure-results/categories.json");

            // Siempre que el origen exista, forzamos la copia y sobrescritura en el destino
            if (categoriasOrigen.exists()) {
                categoriasDestino.getParentFile().mkdirs(); // Nos aseguramos que exista la carpeta target/allure-results
                
                Files.copy(
                    categoriasOrigen.toPath(), 
                    categoriasDestino.toPath(), 
                    StandardCopyOption.REPLACE_EXISTING // <--- Esto obliga a actualizar el JSON viejo
                );
            } else {
                System.err.println("Advertencia: No se encontró categories.json en target/test-classes/");
            }
        } catch (Exception e) {
            System.err.println("Error al copiar de forma dinámica el archivo de categorías: " + e.getMessage());
        }
    }

	@Override
	public void afterTestExecution(ExtensionContext context) {

		if (context.getExecutionException().isEmpty()) {
			return;
		}

		String browserActual = baseTest.getNombreNavegadorActual();
		WebDriver driverActivo = baseTest.getDriver();

		if (driverActivo == null) {
			System.err.println("No se pudo capturar: el driver es null.");
			return;
		}

		String nombreTest = context.getDisplayName();
		String estado = context.getExecutionException().isEmpty() ? "EXITOSO" : "FALLIDO";
		String nombreArchivoInicial = browserActual + " - " + estado + " - " + nombreTest;
		String nombreArchivoFinal = nombreArchivoInicial.replaceAll("[\\\\/:*?\"<>|]", "_");

		System.out.println("Prueba Fallida detectada. Registrando evidencia de (" + estado + ") en " + browserActual
				+ " para: " + nombreTest);
		// System.out.println("Registrando evidencia (" + estado + ") en " +
		// browserActual + " para: " + nombreTest);
		EvidenciaUtils.capturarPantallaTestFailed(driverActivo, nombreArchivoFinal);

	}

	private String generarMd5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashInBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte b : hashInBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			return input;
		}
	}

}
