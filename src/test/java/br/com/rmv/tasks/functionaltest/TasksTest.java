package br.com.rmv.tasks.functionaltest;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TasksTest {
	
	public static String sysEnvStr;
	
	@BeforeClass
	public static void setup() {
		sysEnvStr = System.getenv("SELENIUM_DRIVER")+"/chromedriver";
		System.setProperty("webdriver.chrome.driver",sysEnvStr);
	}
	
	public WebDriver acessarAplicacao() {			
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("headless", "no-sandbox");
		WebDriver driver = new ChromeDriver(opt);
		driver.navigate().to("http://34.125.175.57:8080/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
		return driver;
	}
	

	@Test
	public void deveSalvarTarefaComSucesso() {
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
		//fechar o browser
		driver.quit();
		} finally {
			driver.quit();
		}
	}	
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
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
		//fechar o browser
		driver.quit();
		} finally {
			driver.quit();
		}
	}	
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
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
		//fechar o browser
		driver.quit();
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
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
		//fechar o browser
		driver.quit();
		} finally {
			driver.quit();
		}
	}	
	
}
