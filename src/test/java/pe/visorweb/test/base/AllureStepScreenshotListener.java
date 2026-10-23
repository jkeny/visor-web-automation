package pe.visorweb.test.base;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import pe.visorweb.browsers.DriverFactory;

public class AllureStepScreenshotListener implements StepLifecycleListener {
	
    @Override
    public void beforeStepStop(StepResult result) {

        WebDriver driver = DriverFactory.obtenerDriver();


        if (driver != null) {
            try {
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                

                Allure.addAttachment("Evidencia de paso: " + result.getName(), 
                                     "image/png", 
                                     new ByteArrayInputStream(screenshotBytes), 
                                     ".png");
            } catch (Exception e) {
                System.err.println("No se pudo capturar la pantalla en el paso: " + e.getMessage());
            }
        }
    }
}
