package pe.visorweb.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
	// Usamos ThreadLocal para correr tus pruebas en paralelo futuras
	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	private static ThreadLocal<String> tlNombreNavegador = new ThreadLocal<>();

	public enum Navegador {
		CHROME, FIREFOX, EDGE
	}

	public static void inicializarDriver(Navegador navegador) {

		if (navegador == null) {
			throw new IllegalArgumentException("El navegador no puede ser nulo.");
		}
		System.out.println("Iniciando el navegador: " + navegador);
		WebDriver driver = browserDriverOptions(navegador);

		tlDriver.set(driver);
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

	private static WebDriver browserDriverOptions(Navegador navegador) {

		switch (navegador) {
		case CHROME -> {
			ChromeOptions chromeOptions = new ChromeOptions();

			chromeOptions.addArguments("--remote-allow-origins=*");

			return new ChromeDriver(chromeOptions);

		}
		case FIREFOX -> {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--headless=new");
			firefoxOptions.addArguments("--remote-allow-origins=*");
			firefoxOptions.addArguments("--disable-gpu");
			// firefoxOptions.addArguments("--window-size=1920,1080");
			return new FirefoxDriver(firefoxOptions);
		}
		case EDGE -> {
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--headless=new");
			edgeOptions.addArguments("--no-sandbox");
			edgeOptions.addArguments("--disable-dev-shm-usage");
			edgeOptions.addArguments("--remote-allow-origins=*");
			edgeOptions.addArguments("--disable-gpu");
			edgeOptions.addArguments("--window-size=1920,1080");
			// edgeOptions.addArguments("--window-size=1920,1080");
			return new EdgeDriver(edgeOptions);

		}
		case null -> throw new IllegalArgumentException("El navegador no puede ser Nulo: " + navegador);

		}

	}

}
