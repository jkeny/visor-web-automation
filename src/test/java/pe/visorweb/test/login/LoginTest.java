package pe.visorweb.test.login;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import pe.visorweb.pagina.login.PaginaLogin;
import pe.visorweb.test.base.BaseTest;
import pe.visorweb.test.base.WatcherErrorTest;


public class LoginTest extends BaseTest {

	PaginaLogin paginaLogin;

	@BeforeEach
	void incializarPaginas() {
		paginaLogin = new PaginaLogin(driver);
	}
	
	@RegisterExtension
	WatcherErrorTest watcher = new WatcherErrorTest(this);

	@Test
	@DisplayName("Iniciar sesión exitoso con credenciales válidas")
	void testIniciarSesionExitoso() {
		paginaLogin.iniciarSesion("admin", "clave");
		Assertions.assertThat(paginaLogin.obtenerMensajeInicioDeSesionExitoso())
				.isEqualTo("Bienvenido(a) al Sistema Visor de Almacén");

	}

	@Test
	@DisplayName("Mostrar Mensaje de Error cuando la contraseña es incorrecta")
	void testIniciarSesionContrasenaIncorrecta() {
		paginaLogin.iniciarSesion("admin", "1234");
		Assertions.assertThat(paginaLogin.obtenerMensajeContrasenaIncorrecta()).isEqualTo("Clave incorrectaaaaaaaa");

	}

	@Test
	@DisplayName("Mostrar Mensaje de Error cuando el campo Usuario esta vacío")
	void testIniciarSesionCampoUsuarioVacio() {
		paginaLogin.ingresarContrasena("clave");
		paginaLogin.hacerClicEnIngresar();
		Assertions.assertThat(paginaLogin.obtenerMensajeCamposVacios())
				.isEqualTo("Usuario: Error de validación: se necesita un valor.");

	}

	@Test
	@DisplayName("Mostrar Mensaje de Error cuando el campo Contraseña esta vacío")
	void testIniciarSesionCampoContrasenaVacio() throws InterruptedException {
		paginaLogin.ingresarUsuaio("admin");
		paginaLogin.hacerClicEnIngresar();
		Assertions.assertThat(paginaLogin.obtenerMensajeCamposVacios())
				.isEqualTo("Clave: Error de validación: se necesita un valor.");

	}

}
