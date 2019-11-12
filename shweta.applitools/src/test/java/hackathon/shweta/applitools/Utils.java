package hackathon.shweta.applitools;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {
	WebDriver driver;

	public Utils(WebDriver driver) {
		this.driver = driver;
	}

////////////////
// OPERATIONS //
////////////////
	void login() {
		login("shweta", "shweta");
	}

	void login(String username, String password) {
		findElementById("username").clear();
		driver.findElement(By.id("username")).sendKeys(username);
		findElementById("password").clear();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("log-in")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	void clickElement(String elementId) {

		driver.findElement(By.id(elementId)).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	WebElement findElementById(String id) {

		return driver.findElement(By.id(id));
	}

	WebElement findElementByXpath(String xpath) {

		return driver.findElement(By.xpath(xpath));
	}

	List<WebElement> findElementsByXpath(String xpath) {

		return driver.findElements(By.xpath(xpath));
	}
}
