// see: http://www.gebish.org/manual/current/#configuration

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver

waiting {
	timeout = 2 // seconds
}

environments {

	// see: https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver
	chrome {
		driver = { new ChromeDriver() }
	}

	phantomJs {
		driver = { new PhantomJSDriver() }
	}
}

baseUrl = new File("src/test/resources/").toURI().toString()
