package saucedrivertest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.ios.IOSDriver;
import saucehelper.DriverType;
import saucehelper.SauceDriver;

public class AppiumTest {
	@Test
	public void canRunRealMobileDeviceTest() {
		/* WebDriver d = new SauceDriver.Builder(DriverType.SAFARI, "iOS")
//				.withDeviceName("iPhone_6_10_3_real")
//				.withDeviceOrientation("portrait")
//				.withPlatformName("iOS")
				.withPlatformVersion("10.3.1")
				  //.withVersion("10.3")
				  .usingAppiumVersion("1.6.4")
				  .build();
		
		d.get("http://google.com");
		WebElement queryBox = d.findElement(By.name("q"));
		queryBox.sendKeys("pictures of lab husky mix");
		queryBox.submit();
		List<WebElement> results = d.findElements(By.className("rc"));
		Assert.assertEquals(results.size(), 7);
		d.quit();
		*/
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // Set my TestObject API Key
 
        capabilities.setCapability("testobjectApiKey", "2F6153D6E4A9422AAFF212B58EC9E188");
         
        // Dynamic device allocation of an iPhone 7, running iOS 10.3 device
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "11.3.1");
        capabilities.setCapability("deviceName", "iPhone_6_free");
  
        // Set allocation from private device pool only
//        capabilities.setCapability("privateDevicesOnly", "true");
         
        // Set Application under test
        capabilities.setCapability("testobject_app_id", "1");
        capabilities.setCapability("name", "My Test 1!");
 
        // Set Appium version
        capabilities.setCapability("appiumVersion", "1.8.0");
             
        // Set Appium end point
        WebDriver d = null;
        try {
			d = new IOSDriver(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Assert.assertNotNull(d, "Expected webdriver to be set!");
        
		d.get("http://google.com");
		WebElement queryBox = d.findElement(By.name("q"));
		queryBox.sendKeys("pictures of lab husky mix");
		queryBox.submit();
		List<WebElement> results = d.findElements(By.className("rc"));
		Assert.assertEquals(results.size(), 7);
		d.quit();
	}
}
