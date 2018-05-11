package saucehelper;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class SauceDriver {
	private DesiredCapabilities caps = null;
	public static final String USERNAME = "rei-sauce";
	public static final String API_KEY = System.getProperty("SAUCE_API_KEY"); // get from command line parameter
	public static final String SAUCE_URL = "https://" + USERNAME + ":" + API_KEY + "@ondemand.saucelabs.com:443/wd/hub";

	// https://wiki.saucelabs.com/display/DOCS/Examples+of+Test+Configuration+Options+for+Website+Tests#ExamplesofTestConfigurationOptionsforWebsiteTests-PC/Windows/IE

	/*
	 * PC/Windows/IE DesiredCapabilities caps =
	 * DesiredCapabilities.internetExplorer(); caps.setCapability("platform",
	 * "Windows 8.1"); caps.setCapability("version", "11.0"); PC/Linux/Chrome
	 * DesiredCapabilities caps = DesiredCapabilities.chrome();
	 * caps.setCapability("platform", "Linux"); caps.setCapability("version",
	 * "47.0"); Mac/OSX/Safari DesiredCapabilities caps =
	 * DesiredCapabilities.safari(); caps.setCapability("platform", "OS X 10.9");
	 * caps.setCapability("version", "7.0"); Android Emulator Phone/Android 5.1
	 * DesiredCapabilities caps = DesiredCapabilities.android();
	 * caps.setCapability("platform", "Linux"); caps.setCapability("version",
	 * "5.1"); caps.setCapability("deviceName","Android Emulator");
	 * caps.setCapability("deviceType","phone");
	 * caps.setCapability("deviceOrientation", "portrait"); Samsung Galaxy S3
	 * Emulator/Android 4.4 DesiredCapabilities caps =
	 * DesiredCapabilities.android(); caps.setCapability("platform", "Linux");
	 * caps.setCapability("version", "4.4");
	 * caps.setCapability("deviceName","Samsung Galaxy S3 Emulator");
	 * caps.setCapability("deviceOrientation", "portrait");
	 */

	// These should be all that's required for Desktop stuff
	private final DriverType browserName;
	private final String platform;
	private final String version;

	private final String deviceName;
	private final String deviceType;
	private final String deviceOrientation;

	////// APPIUM STUFF
	/*
	 * Appium Mobile and Desktop Browser Test Configuration Examples iPhone
	 * DesiredCapabilities caps = DesiredCapabilities.iphone();
	 * caps.setCapability("appiumVersion", "1.5.1");
	 * caps.setCapability("deviceName","iPhone 6");
	 * caps.setCapability("deviceOrientation", "portrait");
	 * caps.setCapability("platformVersion","8.4");
	 * caps.setCapability("platformName", "iOS"); caps.setCapability("browserName",
	 * "Safari"); iPad DesiredCapabilities caps = DesiredCapabilities.iphone();
	 * caps.setCapability("appiumVersion", "1.5.1");
	 * caps.setCapability("deviceName","iPad Retina");
	 * caps.setCapability("deviceOrientation", "portrait");
	 * caps.setCapability("platformVersion","8.4");
	 * caps.setCapability("platformName", "iOS"); caps.setCapability("browserName",
	 * "Safari"); Android Phone Emulator/Android 4.4 DesiredCapabilities caps =
	 * DesiredCapabilities.android(); caps.setCapability("appiumVersion", "1.5.1");
	 * caps.setCapability("deviceName","Android Emulator");
	 * caps.setCapability("deviceType","phone");
	 * caps.setCapability("deviceOrientation", "portrait");
	 * caps.setCapability("browserName", "Browser");
	 * caps.setCapability("platformVersion", "4.4");
	 * caps.setCapability("platformName","Android");
	 */
	// All the above
	private final String appiumVersion;

	/*
	 * REAL DEVICES MAY NOT BE DEALT WITH HERE.... iPhone 6 Real Device {
	 * deviceName:'iPhone 6 Device', platformName:'iOS', platformVersion:'8.0',
	 * browserName:'Safari', "appium-version":"1.5.1" } Samsung Galaxy S5 Real
	 * Device { deviceName:'Samsung Galaxy S5 Device', platformName:'Android',
	 * platformVersion:'4.4', browserName:'Chrome', name:'S5 real device google.com'
	 * } Samsung Galaxy S4 Real Device { deviceName:'Samsung Galaxy S4 Device',
	 * platformName:'Android', platformVersion:'4.4', browserName:'Chrome', name:'S5
	 * real device google.com' }
	 */
	// private ctor
	private SauceDriver() {
		/* don't use empty ctor */
		this.browserName = null;
		this.platform = null;
		this.version = null;
		this.deviceName = null;
		this.deviceType = null;
		this.deviceOrientation = null;
		this.appiumVersion = null;
	}

	private SauceDriver(Builder builder) {
		this.browserName = builder.browserName;
		this.platform = builder.platform;
		this.version = builder.version;
		// optionals
		this.deviceName = builder.deviceName;
		this.deviceType = builder.deviceType;
		this.deviceOrientation = builder.deviceOrientation;
		this.appiumVersion = builder.appiumVersion;

		switch (builder.browserName) {
		case FIREFOX:
			caps = DesiredCapabilities.firefox();
			break;

		case EDGE:
			caps = DesiredCapabilities.edge();
			break;

		case IE:
			caps = DesiredCapabilities.internetExplorer();
			break;

		case SAFARI:
			caps = DesiredCapabilities.safari();
			break;

		case ANDROID:
			caps = DesiredCapabilities.android();
			break;

		case CHROME:
			caps = DesiredCapabilities.chrome();
		default:
			caps = DesiredCapabilities.chrome();
			break;
		}
		
		Assert.assertNotNull(caps, "Expected driver to be initialized!");

		// Required capabilities
		caps.setCapability("platform", this.platform);
		caps.setCapability("version", this.version);
		
		// Here we take care of optional fields.
		
	}

	public DesiredCapabilities getCapabilities() {
		return caps;
	}
	
	
	public static class Builder {
		// Required Parameters
		private final DriverType browserName;
		private final String platform;
		private final String version;
		// Optional Parameters
		private String deviceName;
		private String deviceType;
		private String deviceOrientation;
		private String dimension;
		// TODO: Possibly remove
		private String appiumVersion;

		/**
		 * 
		 * @param browserName - sets selenium's desiredcapabilities.
		 * @param platform - Linux, Windows, Mac, etc.
		 * @param version - appropriate version number as a string.
		 */
		public Builder(DriverType browserName, String platform, String version) {
			this.browserName = browserName;
			this.platform = platform;
			this.version = version;
		}

		public Builder withDeviceName(String value) {
			this.deviceName = value;
			return this;
		}

		public Builder withDeviceType(String value) {
			this.deviceType = value;
			return this;
		}

		public Builder withDeviceOrientation(String value) {
			this.deviceOrientation = value;
			return this;
		}

		public Builder usingAppiumVersion(String value) {
			this.appiumVersion = value;
			return this;
		}

		public WebDriver build() {
			SauceDriver sd = new SauceDriver(this);
			WebDriver d = null;
			try {
				d = new RemoteWebDriver(new URL(SAUCE_URL), sd.getCapabilities());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assert.assertNotNull(d, "Expected Webdriver to be initialized!");
			return d;
		}
	}
}
