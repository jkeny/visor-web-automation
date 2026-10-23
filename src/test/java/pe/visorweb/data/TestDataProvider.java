package pe.visorweb.data;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import pe.visorweb.utils.DataReader;

public class TestDataProvider {
	public static Stream<Arguments> proveerDatosLogin() {
		
		String ruta = "src/test/resources/testdata/usuarios.xlsx";
		String query = "SELECT usuario, contrasena, mensajeEsperado FROM Hoja1";

		List<String[]> datosExternos = DataReader.obtenerDatosExcel(ruta, query);

		// Convertimos la lista de String[] a un Stream de Arguments para JUnit 5
		
		return datosExternos.stream().map(Arguments::of);
	}

}
