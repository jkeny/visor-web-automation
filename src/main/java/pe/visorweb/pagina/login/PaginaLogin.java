package pe.visorweb.pagina.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import pe.visorweb.pagina.base.BasePage;
import pe.visorweb.pagina.menu.PaginaMenuPrincipal;

public class PaginaLogin extends BasePage {

	@FindBy(id = "txtUsuario")
	private WebElement txtUsuario;
	@FindBy(id = "txtClave")
	private WebElement txtClave;
	@FindBy(id = "btnIniciarSesion")
	private WebElement btnIniciarSesion;
	@FindBy(xpath = "//div[@class='title-main']")
	private WebElement lblMensajeSesionExitoso;
	@FindBy(xpath = "//span[@class='ui-messages-warn-summary']")
	private WebElement lblMensajeContrasenaIncorrecta;
	@FindBy(xpath = "//span[@class='ui-messages-error-summary']")
	private WebElement lblMensajeCamposVacios;
	@FindBy(xpath = "//div[@class='title-main'] | //span[@class='ui-messages-warn-summary'] | //span[@class='ui-messages-error-summary']")
	private WebElement lblMensajeSesionUnificado;

	public PaginaLogin(WebDriver driver) {
		super(driver);

	}
	@Step("Ingresar Usuario = '{0}'")
	public void ingresarUsuaio(String usuario) {
		limpiar(txtUsuario);
		escribir(txtUsuario, usuario);
	}

	@Step("Ingresar Contraseña = '{0}'")
	public void ingresarContrasena(String clave) {
		limpiar(txtClave);
		escribir(txtClave, clave);
	}

	@Step("clic en el boton iniciar sesión")
	public void hacerClicEnIngresar() {
		click(btnIniciarSesion);
	}

	@Attachment
	@Step("Iniciar Sesion --> Usuario = '{0}' y  Contraseña = '{1}'")
	public PaginaMenuPrincipal iniciarSesion(String usuario, String clave) {
		ingresarUsuaio(usuario);
		ingresarContrasena(clave);
		hacerClicEnIngresar();

		return new PaginaMenuPrincipal(driver);

	}

	@Step("Obteniendo el mensaje de incio de sesion")
	public String obtenerMensajeInicioDeSesionExitoso() {
		return obtenerTexto(lblMensajeSesionExitoso);

	}
	@Step("Obteniendo el mensaje de incio de sesion")
	public String obtenerMensajeContrasenaIncorrecta() {
		return obtenerTexto(lblMensajeContrasenaIncorrecta);

	}
	@Step("Obteniendo el mensaje de incio de sesion")
	public String obtenerMensajeCamposVacios() {
		String mensaje= obtenerTexto(lblMensajeCamposVacios);
		return adjuntarMensajeAlReporte(mensaje);

	}

	@Step("Obteniendo el mensaje de validación unificado de la pantalla")
	public String obtenerMensajeInicioSesionUnificado() {
		return obtenerTexto(lblMensajeSesionUnificado);

	}
	@Attachment(value = "Texto del Mensaje Capturado", type = "text/plain")
	public String adjuntarMensajeAlReporte(String texto) {
	    return texto;
	}

}
