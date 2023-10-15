import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty:target/cucumber/cucumber.txt",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"json:target/cucumber/cucumber.json",
				"utils.TestListener"
		},
		features="src/test/resources/features", 
glue ={"stepDefinition"},
tags= "@spotify"
)
public class TestRunner {

}