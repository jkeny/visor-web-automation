package pe.visorweb.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	// Usamos ThreadLocal para correr tus pruebas en paralelo futuras
	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	private static ThreadLocal<String> tlNombreNavegador = new ThreadLocal<>();

	public enum Navegador {
		CHROME, FIREFOX, EDGE
	}

	public static void inicializarDriver(Navegador navegador) {
		System.out.println("Iniciando el navegador: " + navegador);

		switch (navegador) {
		case CHROME -> {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			tlDriver.set(new ChromeDriver(options));
		}
		case FIREFOX -> tlDriver.set(new FirefoxDriver());
		case EDGE -> tlDriver.set(new EdgeDriver());
		case null -> throw new IllegalArgumentException("El navegador no puede ser nulo.");
		}
		
		 tlNombreNavegador.set(navegador.name());

		obtenerDriver().manage().window().maximize();
	}

	public static synchronized WebDriver obtenerDriver() {
		return tlDriver.get();
	}
	
    public static synchronized String obtenerNombreNavegadorActual() {
        String nombreNavegador = tlNombreNavegador.get();
        return (nombreNavegador != null) ? nombreNavegador : "CHROME";
    }

	public static void cerrarDriver() {
		if (obtenerDriver() != null) {
			obtenerDriver().quit();
			tlDriver.remove(); // Limpia el hilo de ejecución
		}
		
		tlNombreNavegador.remove(); // 🟢 Limpia el hilo del texto
	}
}
