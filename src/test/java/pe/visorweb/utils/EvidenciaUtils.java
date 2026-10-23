package pe.visorweb.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import io.qameta.allure.Allure;

public class EvidenciaUtils {

    // Al hacerlo estático, cualquier Watcher o clase Test en 'src/test/java' lo puede invocar
    public static void capturarPantallaSurefire(WebDriver driver, String nombreTest) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File origen = ts.getScreenshotAs(OutputType.FILE);
            String rutaDestino = "./target/site/screenshots/" + nombreTest + ".png";
            File destino = new File(rutaDestino);
            destino.getParentFile().mkdirs();
            FileHandler.copy(origen, destino);
            
            System.out.println("[EVIDENCIA VISUAL]: <a href='./screenshots/" + nombreTest + ".png' target='_blank'>Ver Captura</a>");
        } catch (Exception e) {
            System.out.println("Error al capturar evidencia: " + e.getMessage());
        }
    }
    public static void capturarPantallaTestFailed(WebDriver driver, String nombreTest) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            
            // =========================================================================
            // 🟢 LA MAGIA PARA ALLURE: Le enviamos los bytes directamente por código.
            // ¡Esto no requiere configurar AspectJ ni tejedores en el pom.xml!
            // =========================================================================
            byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(nombreTest, new ByteArrayInputStream(screenshotBytes));

            // =========================================================================
            // Tu lógica anterior (Se mantiene intacta por si quieres conservar los archivos físicos)
            // =========================================================================
            File origen = ts.getScreenshotAs(OutputType.FILE);
            String rutaDestino = "./target/site/screenshots/" + nombreTest + ".png";
            File destino = new File(rutaDestino);
            destino.getParentFile().mkdirs();
            FileHandler.copy(origen, destino);
            
            System.out.println("[EVIDENCIA VISUAL COPIADA EN DISCO]: " + rutaDestino);
            
        } catch (Exception e) {
            System.out.println("Error al capturar evidencia: " + e.getMessage());
        }
    }
}
