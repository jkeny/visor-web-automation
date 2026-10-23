package pe.visorweb.test.producto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import pe.visorweb.pagina.login.PaginaLogin;
import pe.visorweb.test.base.BaseTest;

public class ActualizarProductoTest extends BaseTest {

	PaginaLogin paginaLogin;

	@BeforeEach
	void inicializarPaginas() {

		paginaLogin = new PaginaLogin(driver);
	}

	@Tag("regresion-happy-path")
	@ParameterizedTest
	@CsvFileSource(resources = "/testdata/datos-actualizar-producto.csv", numLinesToSkip = 1)
	void actualizarProducto_deberiaMostrarMensajeEsperado(String usuario, String password, String productoABuscar,
			String producto, String categoria, String precio, String mensajeEsperado) {
		String mensajeObtenido = paginaLogin.iniciarSesion(usuario, password).cargarPaginaMantenimientoProducto()
				.filtrarProducto(productoABuscar).seleccionarProductoGrilla(productoABuscar).hacerClicEnBotonEditar()
				.actualizarProducto(producto, categoria, precio).obtenerMensajeActualizarProducto();

		Assertions.assertThat(mensajeObtenido).isEqualTo(mensajeEsperado);

		System.out.println(mensajeObtenido);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/testdata/datos-actualizar-producto.csv", numLinesToSkip = 1)
	void actualizarProducto_deberiaMostrarMensajeDeError_cuandoProductoGrillaNoSeleccionado(String usuario,
			String password, String productoABuscar, String producto, String categoria, String precio,
			String mensajeEsperado, String mensajeProductoNoSeleccionado) {
		String mensajeObtenido = paginaLogin.iniciarSesion(usuario, password).cargarPaginaMantenimientoProducto()
				.filtrarProducto(productoABuscar).hacerClicEnBotonEditarSinSeleccionar()
				.obtenerMensajeProductoNoSeleccionado();

		Assertions.assertThat(mensajeObtenido).isEqualTo(mensajeProductoNoSeleccionado);
		System.out.println(mensajeObtenido);
	}

}
