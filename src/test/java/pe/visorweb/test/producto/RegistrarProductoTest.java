package pe.visorweb.test.producto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pe.visorweb.pagina.login.PaginaLogin;
import pe.visorweb.pagina.menu.PaginaMenuPrincipal;
import pe.visorweb.pagina.producto.PaginaMantenimientoProducto;
import pe.visorweb.pagina.producto.PaginaRegistrarProducto;
import pe.visorweb.test.base.BaseTest;

public class RegistrarProductoTest extends BaseTest {

	PaginaLogin paginaLogin;
	PaginaMenuPrincipal paginaMenuPrincipal;
	PaginaMantenimientoProducto paginaMantenimientoProducto;
	PaginaRegistrarProducto paginaRegistrarProducto;

	@BeforeEach
	void inicializarPaginas() {

		paginaLogin = new PaginaLogin(driver);

	}

	@CsvFileSource(resources = "/testdata/datos-registrar-producto.csv", numLinesToSkip = 1, emptyValue = "")
	@ParameterizedTest
	void registrarProducto_deberiaMostrarMensajeDeRegistro(String usuario, String password, String nombreProducto, String categoria, String precio,
			String mensajeEsperado) {
		paginaMenuPrincipal = paginaLogin.iniciarSesion(usuario, password);
		paginaMantenimientoProducto = paginaMenuPrincipal.cargarPaginaMantenimientoProducto();
		paginaRegistrarProducto = paginaMantenimientoProducto.hacerClicEnBotonNuevo();
		paginaRegistrarProducto.registrarProducto(nombreProducto, categoria, precio);
		Assertions.assertThat(paginaRegistrarProducto.obtenerMensajeRegistroProducto()).isEqualTo(mensajeEsperado);
		
		System.out.println(paginaRegistrarProducto.obtenerMensajeRegistroProducto());
	}

}
