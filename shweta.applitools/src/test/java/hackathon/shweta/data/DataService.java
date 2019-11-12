package hackathon.shweta.data;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;

public class DataService {

	///////////////
	// DATA SETS //
	///////////////
	public List<String> getAscendingAmounts() {
		return Arrays.asList(
				new String[] { "- 320.00 USD", "- 244.00 USD", "+ 17.99 USD",
						"+ 340.00 USD", "+ 952.23 USD", "+ 1,250.00 USD" });
	}

	@DataProvider(name = "validcredentials")
	public Object[][] validDataProviderMethod() {
		Object[][] retObjArr = { { "shweta", "shweta",
				"Your nearest branch closes in: 30m 5s" } };
		return (retObjArr);
	}

	@DataProvider(name = "credentials")
	public Object[][] dataProviderMethod() {
		Object[][] retObjArr = { { "shweta", "", "Password must be present" },
				{ "", "shweta", "Username must be present" },
				{ "", "", "Both Username and Password must be present" } };
		return (retObjArr);
	}
}
