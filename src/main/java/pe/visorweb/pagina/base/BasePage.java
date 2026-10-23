package pe.visorweb.pagina.base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(WebDriver driver) {

		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);

	}

	public void click(WebElement element) {
		if (element != null) {

			wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		} else {
			throw new RuntimeException("No se puede hacer clic porque el elemento proporcionado es nulo.");
		}
	}

	public void escribir(WebElement element, String texto) {

		wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(texto);

	}

	public void limpiar(WebElement element) {

		wait.until(ExpectedConditions.visibilityOf(element)).clear();

	}

	public String obtenerTexto(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element)).getText();

	}

	public void seleccionarPorTextoVisible(WebElement elemento, String texto) {
		wait.until(ExpectedConditions.visibilityOf(elemento));
		Select select = new Select(elemento);
		select.selectByVisibleText(texto);
	}

	public List<WebElement> encontrarElementos(By locator) {

		// return
		// wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)));
		// 1. PASO DE PRESENCIA: Esperamos a que los elementos existan en la estructura
		// HTML de la página
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		// 2. PASO DE REFRESCO Y VISIBILIDAD: Devolvemos la lista estabilizándola contra
		// cambios en el DOM (Stale)
		return wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)));

	}

	public WebElement encontrarElemento(By locator) {

		return driver.findElement(locator);

	}

	public void esperarReferenciaSeaObsoleta(WebElement element) {

		wait.until(ExpectedConditions.stalenessOf(element));

	}

	public void esperarHastaQueSeaVisible(WebElement element) {

		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public WebElement obtenerCeldaPorValorColumna(List<WebElement> filas, int indiceColumna, String valorBuscado) {

		// List<WebElement> filasGrilla = encontrarElementos(filas);

		for (WebElement fila : filas) {
			List<WebElement> celdas = fila.findElements(By.tagName("td"));

			if (celdas.size() > indiceColumna) {
				WebElement celda = celdas.get(indiceColumna);
				if (celda.getText().trim().equalsIgnoreCase(valorBuscado.trim())) {
					return celda;
				}
			} else {
				return celdas.get(0);
			}
		}
		return null;
	}

	public WebElement obtenerFilaPorValorColumna(By tabla, int indiceColumna, String valorBuscado) {

		List<WebElement> filas = driver.findElement(tabla).findElements(By.tagName("tr"));

		for (WebElement fila : filas) {

			List<WebElement> celdas = fila.findElements(By.tagName("td"));

			if (celdas.size() > indiceColumna && celdas.get(indiceColumna).getText().trim().equals(valorBuscado)) {

				return fila;
			}
		}

		throw new NoSuchElementException("No se encontró el valor: " + valorBuscado);
	}

	public WebElement obtenerFilaPorTexto(By tabla, String texto) {

		List<WebElement> filas = driver.findElement(tabla).findElements(By.tagName("tr"));

		// List<WebElement> filas = tablaElement.findElements(By.tagName("tr"));

		return filas.stream().filter(f -> f.getText().contains(texto)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("No se encontró una fila con el texto: " + texto));
	}

	public WebElement obtenerCeldaPorTexto(By tabla, String texto) {

		List<WebElement> celdas = driver.findElement(tabla).findElements(By.tagName("td"));

		return celdas.stream().filter(c -> c.getText().contains(texto)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("No se encontró una celda con el texto: " + texto));
	}

	public WebElement obtenerCeldaPorTexto(By tabla, String textoBusqueda, int columnaRetorno) {

		WebElement fila = obtenerFilaPorTexto(tabla, textoBusqueda);

		List<WebElement> celdas = fila.findElements(By.tagName("td"));

		return celdas.get(columnaRetorno);
	}

	public WebElement obtenerPrimeraCelda(WebElement fila) {

		List<WebElement> celdas = fila.findElements(By.tagName("td"));

		if (celdas.isEmpty()) {
			throw new NoSuchElementException("La fila no contiene celdas.");
		}

		return celdas.get(0);
	}

	public WebElement obtenerPrimeraFila(List<WebElement> filas) {

		if (filas.isEmpty()) {
			throw new NoSuchElementException("No existen filas en la grilla.");
		}

		return filas.get(0);
	}

	public int obtenerCantidadCeldas(WebElement fila) {

		return fila.findElements(By.tagName("td")).size();
	}

	public WebElement obtenerCelda(WebElement fila, int indiceColumna) {

		return fila.findElements(By.tagName("td")).get(indiceColumna);
	}

	public void hacerClickFila(By tabla, String texto) {

		WebElement tablaElement = driver.findElement(tabla);

		List<WebElement> filas = tablaElement.findElements(By.tagName("tr"));

		for (WebElement fila : filas) {

			if (fila.getText().contains(texto)) {

				fila.click();
				return;
			}
		}

		throw new NoSuchElementException("No se encontró una fila con el texto: " + texto);
	}

}
