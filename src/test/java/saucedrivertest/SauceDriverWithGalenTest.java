package saucedrivertest;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.galenframework.testng.GalenTestNgTestBase;

import saucehelper.DriverType;
import saucehelper.SauceDriver;

/**
 * Note, because this class extends GalenTestNgTestBase, there may be some
 * threading issues with driver creation under parallel testing.
 */
public class SauceDriverWithGalenTest extends GalenTestNgTestBase {

	// Validate that several configurations can be created and passed around.
	@DataProvider
	public Object[][] drivers() {
		return new Object[][] {
				{ new SauceDriver.Builder(DriverType.CHROME, Platform.SIERRA.toString())
								 .withVersion("66")
								 .build() },
//				{ new SauceDriver.Builder(DriverType.EDGE, Platform.WIN10.toString())
//								 .withVersion("16.16299").build() },
//				{ new SauceDriver.Builder(DriverType.SAFARI, Platform.IOS.toString())
//								 .withDeviceName("iPhone Simulator")
//								 .withPlatformVersion("11.2")
//								 .usingAppiumVersion("1.8.0")
//								 .withDeviceOrientation("portrait")
//								 .build() },
//				{ new SauceDriver.Builder(DriverType.CHROME, Platform.ANDROID.toString()).usingAppiumVersion("1.8.0")
//								 .withDeviceName("Samsung Galaxy S9 WQHD GoogleAPI Emulator")
//								 .withDeviceOrientation("portrait")
//								 .withPlatformVersion("7.1")
//								 .withPlatformName("Android")
//								 .build() 
//				}
		};
	}

	@Test
	public void canExecuteDriverInSauceLabs() {
		int width = 1024;
		int height = 768;

		load("http://www.ytmd.com", width, height);
	}
	
	// TODO: actually interact with a webpage.
	@Test(dataProvider="drivers")
	public void canRunSeveralDrivers(WebDriver d) throws InterruptedException {
		//d.get("http://www.google.com");
		d.get("http://google.com");
		WebElement queryBox = d.findElement(By.name("q"));
		queryBox.sendKeys("pictures of lab husky mix");
		queryBox.submit();
		List<WebElement> results = d.findElements(By.className("rc"));
		Assert.assertEquals(results.size(), 7);
		d.quit();
	}

	/**
	 * This webpage helps configure the correct version and platform:
	 * https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/
	 * 
	 */
	@Override
	public WebDriver createDriver(Object[] args) {
//		WebDriver d = new SauceDriver.Builder(DriverType.CHROME, Platform.SIERRA.toString())
//									 .withVersion("66.0")
//									 .build();
		
		WebDriver d = new SauceDriver.Builder(DriverType.SAFARI, "macOS 10.12")
				  .withVersion("11.0")
				  .build();

		return d;
	}
}
