package pe.visorweb.pagina.menu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pe.visorweb.pagina.base.BasePage;
import pe.visorweb.pagina.producto.PaginaMantenimientoProducto;


public class PaginaMenuPrincipal extends BasePage {

	@FindBy(xpath = "//div[@class='menu']/div")
	WebElement menuHamburguesa;

	@FindBy(xpath = "//span[contains(text(),'Mod. de Almacén ')]")
	WebElement menuModAlmacen;

	@FindBy(xpath = "//a[@href='/VisorWeb/mntAdmProducto.xhtml']")
	WebElement menuMntCategoria;

	public PaginaMenuPrincipal(WebDriver driver) {
		super(driver);
	}

	public PaginaMantenimientoProducto cargarPaginaMantenimientoProducto() {

		
		click(menuHamburguesa);
		click(menuModAlmacen);
		click(menuMntCategoria);
		

		return new PaginaMantenimientoProducto(driver);

	}

}
