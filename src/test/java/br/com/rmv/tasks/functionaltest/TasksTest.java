package br.com.rmv.tasks.functionaltest;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public static String sysEnvStr;
	
	@BeforeClass
	public static void setup() {
		sysEnvStr = System.getenv("SELENIUM_DRIVER")+"/chromedriver";
		System.setProperty("webdriver.chrome.driver",sysEnvStr);
	}
	
	public WebDriver acessarAplicacaoLocal() {			
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("headless", "no-sandbox");
		WebDriver driver = new ChromeDriver(opt);
		driver.navigate().to("http://34.125.175.57:8080/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
		return driver;
	}
	
	public WebDriver acessarAplicacao() throws MalformedURLException  {			
		DesiredCapabilities dcap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://34.125.175.57:4444/wd/hub"),dcap);
		driver.navigate().to("http://34.125.175.57:8080/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();	
		try {
			//clicar em add todo
			driver.findElement(By.id("addTodo")).click();
			//escrever descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			//escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("01/01/2100");
			//clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			//verificar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			assertEquals("Success!", message);						
		} finally {
			driver.quit();
		}
	}	
	
	@Test
	public void deveRemoverTarefasComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();	
		try {
			String xPath =  "//a[@class='btn btn-outline-danger btn-sm']";
			//clicar em remove
			driver.findElement(By.xpath(xPath)).click();		
			//verificar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			assertEquals("Success!", message);	
			driver.findElement(By.xpath(xPath)).click();
			//verificar mensagem de sucesso
			message = driver.findElement(By.id("message")).getText();
			assertEquals("Success!", message);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();	
		try {				
			//clicar em add todo
			driver.findElement(By.id("addTodo")).click();
			//escrever descricao
			driver.findElement(By.id("task"));
			//escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("01/01/2100");
			//clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			//verificar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			assertEquals("Fill the task description", message);
		} finally {
			driver.quit();
		}
	}	
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();	
		try {				
			//clicar em add todo
			driver.findElement(By.id("addTodo")).click();
			//escrever descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			//escrever a data
			driver.findElement(By.id("dueDate"));
			//clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			//verificar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			assertEquals("Fill the due date", message);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();	
		try {				
			//clicar em add todo
			driver.findElement(By.id("addTodo")).click();
			//escrever descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			//escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("01/01/2000");
			//clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			//verificar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			assertEquals("Due date must not be in past", message);
		} finally {
			driver.quit();
		}
	}	
	
}
