package hackathon.shweta.applitools;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import hackathon.shweta.data.DataService;

public class TraditionalTests {
	WebDriver driver;
	WebDriverWait wait;
	DataService dataService = new DataService();
	Utils util;

	@BeforeMethod
	public void registerChromeDriver() {

		// Create a new instance of the Chrome driver
		System.setProperty("webdriver.chrome.driver",
				"/Users/shwetasharma/Documents/softwares/chromedriver");
		driver = new ChromeDriver();
		util = new Utils(driver);
		// Put a Implicit wait, this means that any search for elements on the
		// page
		// could take the time the implicit wait is set for before throwing
		// exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Launch the Applitools hackathon Website
		driver.get("https://demo.applitools.com/hackathon.html");
		driver.manage().window().maximize();

	}

	// Method to check whether amount is sorted
	private void checkSortedAmount() {

		List<String> actualAmountList = new ArrayList<String>();
		List<WebElement> elementList = util.findElementsByXpath("//td[5]/span");
		for (WebElement we : elementList) {
			actualAmountList.add(we.getText());
		}

		assertEquals(actualAmountList, dataService.getAscendingAmounts());
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	public long compareImage(File fileA, File fileB) throws Exception {
		BufferedImage imgA = ImageIO.read(fileA);
		BufferedImage imgB = ImageIO.read(fileB);

		int width1 = imgA.getWidth();
		int width2 = imgB.getWidth();
		int height1 = imgA.getHeight();
		int height2 = imgB.getHeight();

		if ((width1 != width2) || (height1 != height2)) {
			throw new Exception("Error: Images dimensions" + " mismatch");
		} else {
			long difference = 0;
			for (int y = 0; y < height1; y++) {
				for (int x = 0; x < width1; x++) {
					int rgbA = imgA.getRGB(x, y);
					int rgbB = imgB.getRGB(x, y);
					int redA = (rgbA >> 16) & 0xff;
					int greenA = (rgbA >> 8) & 0xff;
					int blueA = (rgbA) & 0xff;
					int redB = (rgbB >> 16) & 0xff;
					int greenB = (rgbB >> 8) & 0xff;
					int blueB = (rgbB) & 0xff;
					difference += Math.abs(redA - redB);
					difference += Math.abs(greenA - greenB);
					difference += Math.abs(blueA - blueB);
				}
			}

			// Total number of red pixels = width * height
			// Total number of blue pixels = width * height
			// Total number of green pixels = width * height
			// So total number of pixels = width * height * 3
			double total_pixels = width1 * height1 * 3;

			// Normalizing the value of different pixels
			// for accuracy(average pixels per color
			// component)
			double avg_different_pixels = difference / total_pixels;

			// There are 255 values of pixels in total
			return new Double((avg_different_pixels / 255) * 100).longValue();
		}
	}

	// Verify the login form
	@Test
	public void checkLoginPage() {

		// Locate the logo on the page
		WebElement logo = driver.findElement(By.xpath("(//a/img)[1]"));
		String logoSrc = logo.getAttribute("src");

		// Locate the heading of the page
		String heading = driver.findElement(By.className("auth-header"))
				.getText();

		// Locate the Username related fields
		String usernameLabel = driver.findElement(By.xpath("(//label)[1]"))
				.getText();
		WebElement usernameIcon = driver
				.findElement(By.className("os-icon-user-male-circle"));
		WebElement usernameTextField = driver.findElement(By.id("username"));
		String placeholderText = usernameTextField.getAttribute("placeholder");

		// Locate the Password related fields
		String passwordLabel = driver.findElement(By.xpath("(//label)[2]"))
				.getText();
		WebElement passwordIcon = driver
				.findElement(By.className("os-icon-fingerprint"));
		WebElement passwordTextField = driver.findElement(By.id("password"));
		String placeholderPassword = passwordTextField
				.getAttribute("placeholder");

		// Locate the Sign in button
		WebElement signInButton = driver.findElement(By.id("log-in"));
		String signInButtonText = signInButton.getText();

		// Locate the Remember me checkbox and label
		WebElement rememberMeCheckbox = driver
				.findElement(By.className("form-check-input"));
		String rememberMeText = driver
				.findElement(By.className("form-check-label")).getText();

		// Locate the social media icons
		WebElement twitterIcon = driver.findElement(By.xpath("(//a/img)[2]"));
		String twitterSrc = twitterIcon.getAttribute("src");

		WebElement facebookIcon = driver.findElement(By.xpath("(//a/img)[3]"));
		String facebookSrc = facebookIcon.getAttribute("src");

		WebElement linkedInIcon = driver.findElement(By.xpath("(//a/img)[4]"));
		String linkedInSrc = linkedInIcon.getAttribute("src");

		// Assert that the logo is displayed
		Assert.assertTrue(logo.isDisplayed());
		Assert.assertEquals(logoSrc,
				"https://demo.applitools.com/img/logo-big.png");

		// Add assertions for all elements displayed on the Login for,
		Assert.assertEquals(heading, "Login Form");
		Assert.assertEquals(usernameLabel, "Username");
		Assert.assertTrue(usernameIcon.isDisplayed());
		Assert.assertTrue(usernameTextField.isDisplayed());
		Assert.assertEquals(placeholderText, "Enter your username");

		Assert.assertEquals(passwordLabel, "Password");
		Assert.assertTrue(passwordIcon.isDisplayed());
		Assert.assertTrue(passwordTextField.isDisplayed());
		Assert.assertEquals(placeholderPassword, "Enter your password");

		Assert.assertTrue(signInButton.isDisplayed());
		Assert.assertEquals(signInButtonText, "Log In");

		Assert.assertTrue(rememberMeCheckbox.isDisplayed());
		Assert.assertFalse(rememberMeCheckbox.isSelected());
		Assert.assertEquals(rememberMeText, "Remember Me");

		Assert.assertTrue(twitterIcon.isDisplayed());
		Assert.assertEquals(twitterSrc,
				"https://demo.applitools.com/img/social-icons/twitter.png");

		Assert.assertTrue(facebookIcon.isDisplayed());
		Assert.assertEquals(facebookSrc,
				"https://demo.applitools.com/img/social-icons/facebook.png");

		Assert.assertTrue(linkedInIcon.isDisplayed());
		Assert.assertEquals(linkedInSrc,
				"https://demo.applitools.com/img/social-icons/linkedin.png");
	}

	// Test to verify valid credentials used to login. This data is coming from
	// hackathon.shweta.data.DataService.java
	@Test(dataProvider = "validcredentials", dataProviderClass = DataService.class)
	public void checkValidCredentials(String username, String password,
			String message) {
		util.login(username, password);

		// Capture the time information displayed in the header after successful
		// login
		String loginMessage = util.findElementById("time").getText();

		// Assert the above is as expected
		Assert.assertEquals(message, loginMessage);
	}

	// Test to verify incorrect combination of credentials used to login. This
	// data is coming from hackathon.shweta.data.DataService.java
	@Test(dataProvider = "credentials", dataProviderClass = DataService.class)
	public void checkIncompleteCredentials(String username, String password,
			String message) {
		util.login(username, password);
		// Capture the alert displayed on login form
		String actualMessage = driver.findElement(By.className("alert-warning"))
				.getText();
		// Verify whether the alert message is as expected
		Assert.assertEquals(message, actualMessage);
		driver.quit();
	}

	@Test
	public void testAmountSort() {
		util.login();
		// Click on the Amount column header
		util.clickElement("amount");
		// Compare the sorting with expected
		checkSortedAmount();
	}

	@Test
	public void testCanvas() throws Exception {
		util.login();
		util.clickElement("showExpensesChart");
		WebElement canvas = util.findElementById("canvas");
		assertNotNull(canvas);

		// Get entire page screenshot
		File screenshot = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		BufferedImage canvasScreenshot = ImageIO
				.read(canvas.getScreenshotAs(OutputType.FILE));
		ImageIO.write(canvasScreenshot, "png", screenshot);

		// Copy the element screenshot to disk
		File screenshotLocation = new File("canvas.png");
		FileUtils.copyFile(screenshot, screenshotLocation);
		assertTrue(compareImage(new File("TestCanvas1.png"),
				screenshotLocation) == 0);
		Files.deleteIfExists(screenshotLocation.toPath());

		util.clickElement("addDataset");
		WebElement canvas2 = util.findElementById("canvas");
		assertNotNull(canvas2);

		// Get entire page screenshot
		File screenshot2 = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		BufferedImage canvasScreenshot2 = ImageIO
				.read(canvas2.getScreenshotAs(OutputType.FILE));
		ImageIO.write(canvasScreenshot2, "png", screenshot2);

		// Copy the element screenshot to disk
		File screenshotLocation2 = new File("canvas2.png");
		FileUtils.copyFile(screenshot2, screenshotLocation2);
		assertTrue(compareImage(new File("TestCanvas2.png"),
				screenshotLocation2) == 0);
		Files.deleteIfExists(screenshotLocation2.toPath());
	}

	@Test
	public void checkDynamicAds() {
		driver.get("https://demo.applitools.com/hackathon.html?showAd=true");
		util.login();

		// Locate the first flash sale ad
		WebElement flashsale1 = util
				.findElementByXpath("//*[@id=\"flashSale\"]/img");

		// Verify that it is displayed
		Assert.assertTrue(flashsale1.isDisplayed());
		String flashsale1src = flashsale1.getAttribute("src");

		// Verify the source
		Assert.assertEquals(flashsale1src,
				"https://demo.applitools.com/img/flashSale.gif");

		// Locate the second flash sale ad
		WebElement flashsale2 = util
				.findElementByXpath("//*[@id=\"flashSale2\"]/img");

		// Verify that it is displayed
		Assert.assertTrue(flashsale2.isDisplayed());
		String flashsale2src = flashsale2.getAttribute("src");

		// Verify the source
		Assert.assertEquals(flashsale2src,
				"https://demo.applitools.com/img/flashSale2.gif");
	}

	@AfterMethod
	public void afterClass() {
		// Close the browser
		driver.quit();
	}
}
