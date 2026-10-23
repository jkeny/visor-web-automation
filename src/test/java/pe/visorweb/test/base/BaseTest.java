package pe.visorweb.test.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import pe.visorweb.browsers.DriverFactory;
import pe.visorweb.browsers.DriverFactory.Navegador;

public class BaseTest {
	protected WebDriver driver;
	// protected String nombreNavegadorHilo;

	@BeforeEach
	public void inicializarBrowserDriver() {
		String navegadorSystem = System.getProperty("browser");
		Navegador navegador;

		if (navegadorSystem == null || navegadorSystem.trim().isEmpty()) {
			navegadorSystem = "CHROME";
		}

		try {
			navegador = Navegador.valueOf(navegadorSystem.toUpperCase().trim());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"El navegador '" + navegadorSystem + "' no está soportado. Usa CHROME, FIREFOX o EDGE.");
		}
		DriverFactory.inicializarDriver(navegador);
		// this.driver=DriverFactory.obtenerDriver();
		this.driver = getDriver();
		// inyectarAllureHistoryId();
		// this.nombreNavegadorHilo=getNombreNavegadorActual();
		this.driver.get("http://localhost:8080/VisorWeb/");

	}

	public String getNombreNavegadorActual() {

		return DriverFactory.obtenerNombreNavegadorActual();
		// return this.driver;
	}

	public WebDriver getDriver() {

		return DriverFactory.obtenerDriver();
		// return this.driver;
	}

	@AfterEach
	public void cerrarDriver() {
		DriverFactory.cerrarDriver();
	}

}
