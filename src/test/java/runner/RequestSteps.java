package runner;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class RequestSteps {

    Map<String,String> values= new HashMap<>();
    JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
            .setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(SchemaVersion.DRAFTV4).freeze()).freeze();

    Response response;
    @Given("i have an authentication token for Todo.ly")
    public void iHaveAnAuthenticationTokenForTodoLy() {

    }

    @When("i send a {} request to {}")
    public void iSendAPOSTRequestToURL(String method,String url, String body) {
        RequestInfo requestInfo= new RequestInfo();
        requestInfo.setUrl(this.updateValues(url))
                   .setBody(body);
        response=FactoryRequest.make(method).send(requestInfo);
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int expectedResponseCode) {
        response.then().statusCode(expectedResponseCode);
    }

    @And("the schema should be {}")
    public void theSchemaShouldBe(String nameSchemaFile) {
        response.then().body(matchesJsonSchemaInClasspath(nameSchemaFile).using(jsonSchemaFactory));
    }

    @And("the {} should be equal to {}")
    public void theATTRIBUTEShouldBeEqualTo(String attribute, String expectedResult) {
        response.then().body(attribute,equalTo(expectedResult));
    }

    @And("save the attribute {} in {}")
    public void saveTheAttributeIdInID_PROJ(String attribute,String nameVariable) {
        values.put(nameVariable,response.then().extract().path(attribute)+"");
    }

    private String updateValues(String value){
        for (String attribute:values.keySet()) {
            value=value.replace(attribute,values.get(attribute));
        }
        return value;
    }
}
