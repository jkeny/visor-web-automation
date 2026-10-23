package pe.visorweb.test.producto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pe.visorweb.pagina.login.PaginaLogin;
import pe.visorweb.test.base.BaseTest;

public class RegistrarProductoChainedMethodTest extends BaseTest {

	PaginaLogin paginaLogin;

	@BeforeEach
	void inicializarPaginas() {

		paginaLogin = new PaginaLogin(driver);

	}
	@Tag("regresion-happy-path")
	@CsvFileSource(resources = "/testdata/datos-registrar-producto.csv", numLinesToSkip = 1)
	@ParameterizedTest
	void testRegistrarProducto_deberiaMostrarMensajeDeRegistroProducto(String usuario, String password, String nombreProducto, String categoria, String precio,
			String mensajeEsperado) {

		String mensajeObtenido = paginaLogin.iniciarSesion(usuario, password)
				.cargarPaginaMantenimientoProducto()
				.hacerClicEnBotonNuevo()
				.registrarProducto(nombreProducto, categoria, precio)
				.obtenerMensajeRegistroProducto();

		Assertions.assertThat(mensajeObtenido).isEqualTo(mensajeEsperado);
		
		System.out.println(mensajeObtenido);
	}

}
