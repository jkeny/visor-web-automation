package pe.visorweb.test.producto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import pe.visorweb.pagina.login.PaginaLogin;
import pe.visorweb.test.base.BaseTest;

public class MantenimientoProductoTest extends BaseTest {

	PaginaLogin paginaLogin;

	@BeforeEach
	void inicializarPaginas() {
		paginaLogin = new PaginaLogin(driver);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/testdata/datos-eliminar-producto.csv", numLinesToSkip = 1)
	void EliminarProducto_DeberiaMostrarMensajeEsperado(String usuario, String password, String productoAEliminar,
			String mensajeEsperado) {
		String mensajeObtenido = paginaLogin.iniciarSesion(usuario, password).cargarPaginaMantenimientoProducto()
				.filtrarProducto(productoAEliminar).seleccionarProductoGrilla(productoAEliminar).eliminarProducto()
				.obtenerMensajeEliminarProducto();

		Assertions.assertThat(mensajeObtenido).isEqualTo(mensajeEsperado);

		System.out.println(mensajeObtenido);
	}

	@Tag("regresion-happy-path")
	@ParameterizedTest
	@CsvFileSource(resources = "/testdata/datos-eliminar-producto.csv", numLinesToSkip = 1)
	void EliminarProducto_DeberiaMostrarMensajeDeError_cuandoProductoNoSeleccionado(String usuario, String password,
			String productoAEliminar, String mensajeEsperado, String msgProductoNoSeleccionado) {
		String mensajeObtenido = paginaLogin.iniciarSesion(usuario, password).cargarPaginaMantenimientoProducto()
				.filtrarProducto(productoAEliminar).eliminarProducto().obtenerMensajeEliminarProductoNoSeleccionado();

		Assertions.assertThat(mensajeObtenido).isEqualTo(msgProductoNoSeleccionado);

		System.out.println(mensajeObtenido);
	}

	@Tag("regresion-happy-path")
	@ParameterizedTest
	@CsvFileSource(resources = "/testdata/datos-consultar-producto.csv", numLinesToSkip = 1)
	void filtrarProducto_DeberiaMostrarResultadoDeBusqueda(String usuario, String password, String productoAbuscar,
			String mensajeEsperado) {
		String mensajeObtenido = paginaLogin.iniciarSesion(usuario, password).cargarPaginaMantenimientoProducto()
				.filtrarProducto(productoAbuscar).obtenerResultadoBusqueda();

		Assertions.assertThat(mensajeObtenido).isEqualTo(mensajeEsperado);

		System.out.println(mensajeObtenido);
	}

}
