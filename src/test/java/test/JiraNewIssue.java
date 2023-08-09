package test;

import builder.IssueContentBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.IssueFields;
import model.RequestCapability;
import org.apache.commons.lang3.RandomStringUtils;
import utils.AuthenticationHandler;
import utils.ProjectInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.restassured.RestAssured.given;

// Todo: Apache Commons Lang from Maven repository

public class JiraNewIssue implements RequestCapability {

    public static void main(String[] args) {
        // DRY -> Don't repeat yourself
        // API Info
        String baseUri = "https://trandungnhan.atlassian.net";
        String path = "/rest/api/3/issue";
        String projectKey = "PILOT01";

        String email = "trandungnhan89@gmail.com";
        String apiToken = "ATATT3xFfGF0umxCY0NulrFgoEnT2b1Rd0XGMJqQ5VTZ3N8uq1Id0IFQM7-N7Jk4tDoTfe7WTLlXdUDix3YtfY1nmBaHkNJjFX6iPWCysWtL8PyWXdzH3jBURTDHbK8f4vnHWCl56rk-OYS8mokCD9RI9V1xklui_qmMmKM5kVLHTEL_ze5hSZg=F47CD302";
        String encodedCredStr = AuthenticationHandler.encodedCredStr(email, apiToken);
        // Request object
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(accessJSONHeader);
        request.header(getAuthenticatedHeader.apply(encodedCredStr));

        // Define body data
        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        String taskTypeId = projectInfo.getIssueTypeId("task");
        int desiredLength = 20;
        boolean hasLetters = true;
        boolean hasNumbers = true;
        String randomSummary = RandomStringUtils.random(desiredLength, hasLetters, hasNumbers);
        IssueContentBuilder issueContentBuilder = new IssueContentBuilder();
        String IssueFieldsContent = issueContentBuilder.build(projectKey, taskTypeId, randomSummary);

        // CREATE JIRA TASK
        Response response = request.body(IssueFieldsContent).post(path);
        response.prettyPrint();

        // Check created task details
        Map<String, String> responseBody = (Map<String, String>) JsonPath.from(response.asString()).get();
        final String CREATED_ISSUE_KEY = responseBody.get("key");
        IssueFields issueFields = issueContentBuilder.getIssueFields();
        String expectedSummary = issueFields.getFields().getSummary();
        String expectedStatus = "To Do";

        Function<String, Map<String, String>> getIssueInfo = issueKey -> {
            String getIssuePath = "/rest/api/3/issue/" + issueKey;
            // READ CREATED JIRA TASK INFO
            Response response_ = request.get(getIssuePath);

            Map<String, Object> fields = JsonPath.from(response_.getBody().asString()).get("fields");
            String actualSummary = fields.get("summary").toString();
            Map<String, Object> status = (Map<String, Object>) fields.get("status");
            Map<String, Object> statusCategory = (Map<String, Object>) status.get("statusCategory");
            String actualStatus = statusCategory.get("name").toString();

            Map<String, String> issueInfo = new HashMap<>();
            issueInfo.put("summary", actualSummary);
            issueInfo.put("status", actualStatus);
            return issueInfo;
        };
        Map<String, String> issueInfo = getIssueInfo.apply(CREATED_ISSUE_KEY);

        System.out.println("expectedSummary: " + expectedSummary);
        System.out.println("actualSummary: " + issueInfo.get("summary"));

        System.out.println("expectedStatus: " + expectedStatus);
        System.out.println("actualStatus: " + issueInfo.get("status"));

        // UPDATE CREATED JIRA TASK
        String issueTransitionPath = "/rest/api/3/issue/" + CREATED_ISSUE_KEY + "/transitions";
        String transitionBody ="{\n" +
                "  \"transition\": {\n" +
                "    \"id\": \"31\"\n" +
                "  }\n" +
                "}";

        request.body(transitionBody).post(issueTransitionPath).then().statusCode(204);
        issueInfo = getIssueInfo.apply(CREATED_ISSUE_KEY);
        String latestIssueStatus = issueInfo.get("status");
        System.out.println("latestIssueStatus: " + latestIssueStatus);

    }
}