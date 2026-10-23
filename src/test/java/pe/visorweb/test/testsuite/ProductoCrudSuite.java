package pe.visorweb.test.testsuite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import pe.visorweb.test.producto.ActualizarProductoTest;
import pe.visorweb.test.producto.MantenimientoProductoTest;
import pe.visorweb.test.producto.RegistrarProductoChainedMethodTest;

@Suite
@SuiteDisplayName("Suite Producto Regresion")
@SelectClasses({
	
	RegistrarProductoChainedMethodTest.class,
	ActualizarProductoTest.class,
	MantenimientoProductoTest.class
	
})
@IncludeTags("regresion-happy-path")

public class ProductoCrudSuite {

}
