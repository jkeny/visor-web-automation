package pe.visorweb.utils;

import java.util.ArrayList;
import java.util.List;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class DataReader {
	public static List<String[]> obtenerDatosExcel(String rutaArchivo, String query) {
		List<String[]> listaDatos = new ArrayList<>();
		Fillo fillo = new Fillo();
		Connection conexion = null;
		Recordset recordset = null;

		try {
			conexion = fillo.getConnection(rutaArchivo);
			recordset = conexion.executeQuery(query);
			List<String> columnas = recordset.getFieldNames();

			while (recordset.next()) {
				String[] fila = new String[columnas.size()];
				for (int i = 0; i < columnas.size(); i++) {
					fila[i] = recordset.getField(columnas.get(i));
				}
				listaDatos.add(fila);
			}
		} catch (FilloException e) {
			e.printStackTrace();
		} finally {
			if (recordset != null)
				recordset.close();
			if (conexion != null)
				conexion.close();
		}
		return listaDatos;
	}

}
