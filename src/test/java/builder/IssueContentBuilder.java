package builder;

import model.IssueFields;

public class IssueContentBuilder {

    private  IssueFields issueFields;
    public String build(String projectKey, String taskTypeId, String summary){
        IssueFields.IssueType issueType = new IssueFields.IssueType(taskTypeId);
        IssueFields.Project project = new IssueFields.Project(projectKey);
        IssueFields.Fields fields = new IssueFields.Fields(project, issueType, summary);
        issueFields = new IssueFields(fields);

        return BodyJSONBuilder.getJSONContent(issueFields);
    }

    public IssueFields getIssueFields() {
        return issueFields;
    }
}