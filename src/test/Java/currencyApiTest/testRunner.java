package currencyApiTest;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/Feature File",
        glue = "currencyApiTest.StepDefinition",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class testRunner {
}