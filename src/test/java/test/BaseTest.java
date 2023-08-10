package test;

import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import utils.AuthenticationHandler;

import static io.restassured.RestAssured.given;

public class BaseTest implements RequestCapability {

    //Crt + Alt + L
    protected String encodedCredStr;
    protected String baseUri;
    protected String projectKey;
    protected RequestSpecification request;

    @BeforeSuite
    public void beforeSuite() {
        encodedCredStr = AuthenticationHandler.encodedCredStr(email, token);
        baseUri = "https://trandungnhan.atlassian.net";
        projectKey = "PILOT01";
    }

    @BeforeTest
    public void beforeTest() {
        request = given();
        request.baseUri(baseUri);
        request.header(RequestCapability.defaultHeader);
        request.header(RequestCapability.accessJSONHeader);
        request.header(RequestCapability.getAuthenticatedHeader.apply(encodedCredStr));
    }

}
