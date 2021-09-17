package br.com.rmv.tasks.prod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {
	
	public static String sysEnvStr;
	
	@BeforeClass
	public static void setup() {
		sysEnvStr = System.getenv("SELENIUM_DRIVER")+"/chromedriver";
		System.setProperty("webdriver.chrome.driver",sysEnvStr);
	}
	

	public WebDriver acessarAplicacao() throws MalformedURLException  {			
		DesiredCapabilities dcap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://34.125.175.57:4444/wd/hub"),dcap);
		driver.navigate().to("http://34.125.175.57:9999/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;		
	}
	
	@Test
	public void healthCheck() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		String version = driver.findElement(By.id("version")).getText();
		Assert.assertTrue(version.startsWith("build"));
		driver.quit();
	}

}
