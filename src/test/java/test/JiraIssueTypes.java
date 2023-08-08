package test;

import model.RequestCapability;
import utils.ProjectInfo;

public class JiraIssueTypes implements RequestCapability {

    public static void main(String[] args) {

        String baseUri = "https://trandungnhan.atlassian.net";
        String projectKey = "PILOT01";

        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        System.out.printf("Task ID: " + projectInfo.getIssueTypeId("task"));
        System.out.printf("\nEpic: " + projectInfo.getIssueTypeId("epic"));


    }
}

