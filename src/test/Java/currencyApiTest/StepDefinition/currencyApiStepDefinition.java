package currencyApiTest.StepDefinition;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import static org.hamcrest.Matchers.*;

public class currencyApiStepDefinition {
    private Response response;
    private static final String wrong_URL = "https://open.er-api.com/v6/latest/SKI";
    private static final String correct_URL = "https://open.er-api.com/v6/latest/USD";

    @Given("I make a request to the {string} USD exchange rate API with Serenity")
    public void iMakeARequestToTheUSDExchangeRateAPIWithSerenity(String isCorrectApi) {
        switch (isCorrectApi.toLowerCase()) {
            case "correct":
                 response = SerenityRest.given().when().get(correct_URL);
                break;
            case "incorrect":
                response = SerenityRest.given().when().get(wrong_URL);
        }
    }


    @Then("the status code should be {int}")
    public void theStatusCodeShouldBe(int code) {
        response.then().statusCode(code);
    }

    @Then("the response status should indicate success")
    public void theResponseStatusShouldIndicateSuccess() {
        response.then().body("result", equalTo("success"));
    }

    @Then("the USD to AED rate should be between {float} and {float}")
    public void theUSDToAEDRateShouldBeBetweenAnd(float minRate, float maxRate) {
        response.then().body("rates.AED", allOf(greaterThan(minRate), lessThan(maxRate)));
    }

    @Then("the response should include a timestamp")
    public void theResponseShouldIncludeATimestamp() {
        response.then().body("time_last_update_unix", notNullValue());
    }

    @Then("the API response time should be less than {int} seconds")
    public void theAPIResponseTimeShouldBeLessThanSeconds(int seconds) {
        response.then().time(lessThan((long)seconds * 1000));
    }

    @Then("the response should contain {int} currency pairs")
    public void theResponseShouldContainCurrencyPairs(int count) {
        response.then().body("rates.size()", equalTo(count));
    }

    @Then("the response status should indicate failure")
    public void theResponseStatusShouldIndicateFailure() {
        response.then().body("result", (equalTo("error")));
    }

    @And("the error message should be descriptive")
    public void theErrorMessageShouldBeDescriptive() {
        response.then().body("error-type", (equalTo("unsupported-code")));
    }

    @Then("the response should match the expected JSON schema")
    public void theResponseShouldMatchTheExpectedJSONSchema() {
    }
}
