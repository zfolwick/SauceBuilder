# SauceBuilder
A builder pattern for accessing SauceLabs

##running test:
```
mvn test -Dtest=SauceDriverWithGalenTest -DSAUCE_API_KEY={your sauce api key in quotes}
```

will run the saucelabs basic test with a galen testbase class.  This verifies that the image comparison library accepts the RemoteWebDriver instance.
