package pe.visorweb.pagina.producto;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pe.visorweb.pagina.base.BasePage;

public class PaginaMantenimientoProducto extends BasePage {

	private String productoFiltrado;

	@FindBy(id = "btnNuevo")
	private WebElement btnNuevo;

	@FindBy(id = "btnActualizar")
	private WebElement btnActualizar;

	@FindBy(id = "txtFiltro")
	private WebElement txtFiltro;

	@FindBy(id = "btnFiltrar")
	private WebElement btnFiltrar;

	// @FindBy(xpath = "//tbody[@id='tablaProductos_data']/tr[1]/td")
	// private final By tablaProducto =
	// By.xpath("//tbody[@id='tablaProductos_data']/tr");
	private final By filasGrilla = By.xpath("//tbody[@id='tablaProductos_data']/tr");
	@FindBy(xpath = "//span[@class='ui-messages-warn-summary']")
	private WebElement lblMensajeProductoNoSeleccionado;

	@FindBy(id = "btnEliminar")
	private WebElement btnEliminar;

	@FindBy(xpath = "//span[@class='ui-messages-info-summary']")
	private WebElement lblMensajeEliminarProducto;

	@FindBy(id = "frmConfirmacion")
	private WebElement frmConfirmacion;

	@FindBy(id = "btnSi")
	private WebElement btnSi;

	public PaginaMantenimientoProducto(WebDriver driver) {
		super(driver);

	}

	public PaginaRegistrarProducto hacerClicEnBotonNuevo() {
		click(btnNuevo);
		return new PaginaRegistrarProducto(driver);
	}

	public PaginaActualizarProducto hacerClicEnBotonEditar() {
		click(btnActualizar);
		return new PaginaActualizarProducto(driver);
	}

	public void hacerClicEnBotonEliminar() {

		click(btnEliminar);
	}

	public void hacerClicEnBotonSi() {

		click(btnSi);
	}

	public PaginaMantenimientoProducto hacerClicEnBotonEditarSinSeleccionar() {
		click(btnActualizar);
		return this;
	}

	public PaginaMantenimientoProducto filtrarProducto(String producto) {

		this.productoFiltrado = producto;

		limpiar(txtFiltro);
		escribir(txtFiltro, producto);
		WebElement filasAntesDeFiltrar = encontrarElemento(filasGrilla);
		click(btnFiltrar);
		esperarReferenciaSeaObsoleta(filasAntesDeFiltrar);

		return this;
	}

	public void seleccionarProductoGrillaOld(String productoActualizar) {

		for (WebElement fila : encontrarElementos(filasGrilla)) {

			List<WebElement> producto = fila.findElements(By.tagName("td"));
			if (producto.get(1).getText().trim().equals(productoActualizar)) {
				producto.get(1).click();
				break;

			}

		}
	}

	public WebElement obtenerCeldaEnGrillaProducto(String productoActualizar) {
		List<WebElement> filasDespuesDeFiltrar = encontrarElementos(filasGrilla);
		return obtenerCeldaPorValorColumna(filasDespuesDeFiltrar, 1, productoActualizar);

	}

	public PaginaMantenimientoProducto seleccionarProductoGrilla(String productoActualizar) {

		WebElement celdaProducto = obtenerCeldaEnGrillaProducto(productoActualizar);
		click(celdaProducto);

		return this;

	}

	public String obtenerMensajeProductoNoSeleccionado() {

		return obtenerTexto(lblMensajeProductoNoSeleccionado);

	}

	public PaginaMantenimientoProducto eliminarProducto() {

		hacerClicEnBotonEliminar();
		esperarHastaQueSeaVisible(frmConfirmacion);
		hacerClicEnBotonSi();

		return this;

	}

	public String obtenerMensajeEliminarProducto() {

		return obtenerTexto(lblMensajeEliminarProducto);

	}

	public String obtenerMensajeEliminarProductoNoSeleccionado() {

		return obtenerTexto(lblMensajeProductoNoSeleccionado);

	}

	public String obtenerResultadoBusqueda() {

		List<WebElement> filas = encontrarElementos(filasGrilla);
		if (filas.isEmpty()) {
			throw new NoSuchElementException("La grilla no contiene filas.");
		}
		WebElement primeraFila = obtenerPrimeraFila(filas);

		if (obtenerCantidadCeldas(primeraFila) == 1) {

	        return obtenerTexto(
	                obtenerPrimeraCelda(primeraFila));

		} else {
		    return obtenerTexto(obtenerCelda(primeraFila, 1));
		            
		}

	}

}
