package saucehelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class SauceDriver {
	private DesiredCapabilities caps = null;
	public static final String USERNAME = "rei-sauce";
	public static final String API_KEY = System.getProperty("SAUCE_API_KEY");
	public static final String SAUCE_URL = "https://" + USERNAME + ":" + API_KEY + "@ondemand.saucelabs.com:443/wd/hub";

	// https://wiki.saucelabs.com/display/DOCS/Examples+of+Test+Configuration+Options+for+Website+Tests#ExamplesofTestConfigurationOptionsforWebsiteTests-PC/Windows/IE

	// These should be all that's required for Desktop stuff
	private final DriverType browserName;
	private final String platform;
	
	private Optional<String> version = Optional.empty();
	private Optional<String> deviceName = Optional.empty();
	private Optional<String> deviceType = Optional.empty();
	private Optional<String> deviceOrientation = Optional.empty();

	////// APPIUM STUFF

	// All the above
	private Optional<String> platformVersion = Optional.empty();
	private Optional<String> appiumVersion = Optional.empty();
	private Optional<String> platformName = Optional.empty();

	// private ctor
	private SauceDriver() {
		/* don't use empty ctor */
		this.browserName = null;
		this.platform = null;
	}

	private SauceDriver(Builder builder) {
		this.browserName = builder.browserName;
		this.platform = builder.platform;

		// optionals
		this.version = Optional.ofNullable(builder.version);
		this.deviceName = Optional.ofNullable(builder.deviceName);
		this.deviceType = Optional.ofNullable(builder.deviceType);
		this.deviceOrientation = Optional.ofNullable(builder.deviceOrientation);
		this.platformVersion = Optional.ofNullable(builder.platformVersion);
		this.appiumVersion = Optional.ofNullable(builder.appiumVersion);
		this.platformName = Optional.ofNullable(builder.platformName);

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
		
		// Here we take care of optional fields.
		caps.setCapability("deviceType", this.deviceType.orElse(null));
		caps.setCapability("version", this.version.orElse(null));
		caps.setCapability("deviceName", this.deviceName.orElse(null));
		caps.setCapability("deviceOrientation", this.deviceOrientation.orElse(null));
		caps.setCapability("platformVersion", this.platformVersion.orElse(null));
		caps.setCapability("appiumVersion", this.appiumVersion.orElse(null));
		caps.setCapability("platformName", this.platformName.orElse(null));
	}

	public DesiredCapabilities getCapabilities() {
		return caps;
	}
	
	
	public static class Builder {
		// Required Parameters
		private final DriverType browserName;
		private final String platform;
		
		// Optional Parameters
		private String version; // not present with mobile! 
		private String deviceName;
		private String deviceType;
		private String deviceOrientation;
		private String dimension;
		private String platformVersion;
		private String appiumVersion;
		private String platformName;

		/**
		 * 
		 * @param browserName - sets selenium's desiredcapabilities.
		 * @param platform - Linux, Windows, Mac, etc.
		 * @param version - appropriate version number as a string.
		 */
		public Builder(DriverType browserName, String platform) {
			this.browserName = browserName;
			this.platform = platform;
		}
		
		public Builder withVersion(String value){
			this.version = value;
			return this;
		}
		
		public Builder withPlatformVersion(String value) {
			this.platformVersion = value;
			return this;
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
		
		public Builder withPlatformName(String value) {
			this.platformName = value;
			return this;
		}

		public WebDriver build() {
			SauceDriver sd = new SauceDriver(this);
			WebDriver d = null;
			try {
				d = new RemoteWebDriver(new URL(SAUCE_URL), sd.getCapabilities());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			Assert.assertNotNull(d, "Expected Webdriver to be initialized!");
			
			return d;
		}
	}
}
