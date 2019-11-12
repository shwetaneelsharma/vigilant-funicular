package hackathon.shweta.applitools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

public class VisualAITests {

	private static BatchInfo loginBatch2;
	private static BatchInfo canvasChartBatch;
	String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss")
			.format(new Date());

	WebDriver driver;
	Utils util;
	EyesRunner runner = new ClassicRunner();
	Eyes eyes = new Eyes(runner);

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

	@BeforeClass
	public static void prepareBatch() {
		loginBatch2 = new BatchInfo("Verify Login Credentials");
		canvasChartBatch = new BatchInfo("Canvas Chart Batch");
	}

	@BeforeMethod
	public void setup() throws Exception {
		registerChromeDriver();
		System.setProperty("webdriver.chrome.driver",
				"/Users/shwetasharma/Documents/softwares/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		eyes = new Eyes();
		eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
		eyes.setForceFullPageScreenshot(true);
	}

	@Test
	public void checkLoginPage2() {
		driver = eyes.open(driver, "Demo Hackathon App",
				"Login Page UI Elements Test");
		driver.navigate().to("https://demo.applitools.com/hackathon.html");
		// This line takes the screenshot and stores it in Applitools as a
		// baseline for the first time and then later uses it for comparison for
		// subsequent runs
		eyes.checkWindow("Login Window");
	}

	@Test
	public void checkEmptyLoginCredentials() {
		eyes.setBatch(loginBatch2);
		loginBatch2.setId("login credentials 2" + timestamp);
		driver = eyes.open(driver, "Demo Hackathon App",
				"Verify Empty Credentials");
		driver.manage().window().maximize();
		driver.navigate().to("https://demo.applitools.com/hackathonV2.html");
		util.login("", "");
		eyes.checkWindow("Empty credentials");
	}

//	@Test
//	public void checkValidCredentials() {
//		eyes.setBatch(loginBatch2);
//		loginBatch2.setId("login credentials 2" + timestamp);
//		driver = eyes.open(driver, "Demo Hackathon App",
//				"Verify Valid Credentials");
//		driver.manage().window().maximize();
//		driver.navigate().to("https://demo.applitools.com/hackathonV2.html");
//		login("shweta", "shweta");
//		eyes.checkWindow("Valid credentials");
//	}
//
//	@Test
//	public void checkEmptyUsernameCredentials() {
//		eyes.setBatch(loginBatch2);
//		loginBatch2.setId("login credentials 2" + timestamp);
//		driver = eyes.open(driver, "Demo Hackathon App",
//				"Verify Empty Username Credentials");
//		driver.manage().window().maximize();
//		driver.navigate().to("https://demo.applitools.com/hackathonV2.html");
//		login("", "shweta");
//		eyes.checkWindow("Empty username credentials");
//	}
//
//	@Test
//	public void checkEmptyPasswordCredentials() {
//		eyes.setBatch(loginBatch2);
//		loginBatch2.setId("login credentials 2" + timestamp);
//		driver = eyes.open(driver, "Demo Hackathon App",
//				"Verify empty password Credentials");
//		driver.manage().window().maximize();
//		driver.navigate().to("https://demo.applitools.com/hackathonV2.html");
//		login("shweta", "");
//		eyes.checkWindow("Empty password credentials");
//	}
//
//	@Test
//	public void tableSortTest2() {
//		driver = eyes.open(driver, "Demo Hackathon App", "Table Sort Test");
//		driver.manage().window().maximize();
//		driver.navigate().to("https://demo.applitools.com/hackathonV2.html");
//		// This line takes the screenshot and stores it in Applitools as a
//		// baseline for the first time and then later uses it for comparison for
//		// subsequent runs
//		login("shweta", "shweta");
//		clickElement("amount");
//		eyes.checkWindow("Amount sorting in the Recent Transactions table");
//	}
//
//	@Test
//	public void dynamicContentTest2() {
//		driver = eyes.open(driver, "Demo Hackathon App",
//				"Dynamic Content Test");
//		driver.manage().window().maximize();
//		driver.navigate()
//				.to("https://demo.applitools.com/hackathonV2.html?showAd=true");
//		// This line takes the screenshot and stores it in Applitools as a
//		// baseline for the first time and then later uses it for comparison for
//		// subsequent runs
//		login("shweta", "shweta");
//		eyes.checkWindow("Dynamic Ads Content");
//	}
//
//	@Test
//	public void canvasChartTest2() {
//		eyes.setBatch(canvasChartBatch);
//		driver = eyes.open(driver, "Demo Hackathon App", "Canvas Chart Test");
//		driver.manage().window().maximize();
//		driver.navigate().to("https://demo.applitools.com/hackathonV2.html");
//		// This line takes the screenshot and stores it in Applitools as a
//		// baseline for the first time and then later uses it for comparison for
//		// subsequent runs
//		login("shweta", "shweta");
//		clickElement("showExpensesChart");
//		eyes.checkWindow("Canvas Chart");
//		clickElement("addDataset");
//		eyes.checkWindow("Canvas Chart");
//	}

	@AfterMethod
	public void after() {
		driver.quit();
		System.out.println(
				"Please wait... we are now: \n1. Uploading resources, \n2. Rendering in Visual Grid, and \n3. Using Applitools A.I. to validate the checkpoints. \nIt'll take about 30 secs to a minute...");
		eyes.closeAsync();
		eyes.abortIfNotClosed();
	}

	@AfterClass
	public void captureResults() {
		// Capture all test results
		TestResultsSummary allTestResults = runner.getAllTestResults();
		// Print all results
		System.out.println(allTestResults);
	}

}
