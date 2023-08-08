package test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.IssueFields;
import model.RequestCapability;
import utils.AuthenticationHandler;
import utils.ProjectInfo;

import static io.restassured.RestAssured.given;

public class JiraNewIssue implements RequestCapability {

    public static void main(String[] args) {
        // DRY -> Don't repeat yourself
        // API Info
        String baseUri = "https://trandungnhan.atlassian.net";
        String path = "/rest/api/3/issue";
        String projectKey = "PILOT01";

        String email = System.getenv("email");
        String apiToken = System.getenv("token");

        String encodedCredStr = AuthenticationHandler.encodedCredStr(email, apiToken);
        // Request object
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(accessJSONHeader);
        request.header(getAuthenticatedHeader.apply(encodedCredStr));
        //request.header(RequestCapability.getAuthenticatedHeader(encodedCredStr));

        // Define body data
        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        String taskTypeId = projectInfo.getIssueTypeId("task");

        String summary = "This is my summary";
        IssueFields.IssueType issueType = new IssueFields.IssueType(taskTypeId);
        IssueFields.Project project = new IssueFields.Project(projectKey);
        IssueFields.Fields fields = new IssueFields.Fields(project, issueType, summary);
        IssueFields issueFields = new IssueFields(fields);

        // Send request
        Response response = request.body(issueFields).post(path);
        response.prettyPrint();

    }
}