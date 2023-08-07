package test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.BuildModelJSON;
import model.PostBody;
import model.RequestCapability;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PATCHMethod implements RequestCapability {

    public static void main(String[] args) {

        String baseUri = "https://jsonplaceholder.typicode.com";

        // Form up request instance, header and baseUri
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);

        // Form up body
        PostBody postBody = new PostBody();
        postBody.setTitle("New Title");
        String postBodyStr = BuildModelJSON.parseJSONString(postBody);
        final String TARGET_POST_TO = "1";
        Response response = request.body(postBodyStr).patch("/posts/".concat(TARGET_POST_TO));
        response.prettyPrint();
        response.then().body("title", equalTo(postBody.getTitle()));
    }
}
