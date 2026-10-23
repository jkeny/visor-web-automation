package pe.visorweb.pagina.producto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pe.visorweb.pagina.base.BasePage;

public class PaginaActualizarProducto extends BasePage {

	@FindBy(id = "txtNombre")
	private WebElement txtNombre;

	@FindBy(id = "cboCategoria")
	private WebElement cboCategoria;

	@FindBy(id = "txtPrecio")
	private WebElement txtPrecio;

	@FindBy(id = "btnGuardar")
	private WebElement btnGuardar;

	@FindBy(xpath = "//span[@class='ui-messages-info-summary'] | //span[@class='ui-messages-error-summary']")
	private WebElement lblMensajeRegistroProducto;

	public PaginaActualizarProducto(WebDriver driver) {
		super(driver);

	}

	public void actualizarNombreProducto(String nombreProducto) {
		limpiar(txtNombre);
		escribir(txtNombre, nombreProducto);
	}

	public void seleccionarCategoria(String categoria) {
		seleccionarPorTextoVisible(cboCategoria, categoria);
	}

	public void actualizarPrecioProducto(String precio) {
		limpiar(txtPrecio);
		escribir(txtPrecio, precio);
	}

	public void hacerClicEnGuardar() {
		click(btnGuardar);
	}

	public PaginaActualizarProducto actualizarProducto(String nombreProducto, String categoria, String precio) {
		actualizarNombreProducto(nombreProducto);
		seleccionarCategoria(categoria);
		actualizarPrecioProducto(precio);
		hacerClicEnGuardar();

		return this;
	}

	public String obtenerMensajeActualizarProducto() {

		return obtenerTexto(lblMensajeRegistroProducto);

	}
}
