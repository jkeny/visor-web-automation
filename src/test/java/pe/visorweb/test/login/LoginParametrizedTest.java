package pe.visorweb.test.login;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import pe.visorweb.pagina.login.PaginaLogin;
import pe.visorweb.test.base.BaseTest;
import pe.visorweb.test.base.WatcherErrorTest;

public class LoginParametrizedTest extends BaseTest {
	PaginaLogin paginaLogin;
	
	@BeforeEach
	void inicializarPaginas() {
		paginaLogin=new PaginaLogin(driver);
	}
	
	@RegisterExtension
	WatcherErrorTest watcher = new WatcherErrorTest(this);

	@DisplayName("Validar Inicio de  Sesion")	
	@MethodSource("pe.visorweb.data.TestDataProvider#proveerDatosLogin")
	@ParameterizedTest(name = "CP00{index}_Login[{0}]_Debe_Mostrar_{2}")
	void IniciarSesion_deberiaMostrarmensajedeInicioDeSesion(String usuario, String clave, String mensajeEsperado) {

		paginaLogin.iniciarSesion(usuario, clave);
		String resultadoObtenido = paginaLogin.obtenerMensajeInicioSesionUnificado();
		Assertions.assertThat(resultadoObtenido)
		.isEqualTo(mensajeEsperado);
		
		System.out.println("Mensaje capturado en pantalla: " + resultadoObtenido);
		
		
	}
}
