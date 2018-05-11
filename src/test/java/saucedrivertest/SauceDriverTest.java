package saucedrivertest;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.galenframework.testng.GalenTestNgTestBase;

import saucehelper.DriverType;
import saucehelper.SauceDriver;


public class SauceDriverTest extends GalenTestNgTestBase {
	
  @Test
  public void canExecuteDriverInSauceLabs() {
		int width = 1024;
		int height = 768;
		
		load("http://www.ytmd.com", width, height);
  }

@Override
public WebDriver createDriver(Object[] args) {
	WebDriver d = new SauceDriver.Builder(DriverType.CHROME, Platform.SIERRA.toString(), "66.0")
				 .build();
	return d;
}
}
