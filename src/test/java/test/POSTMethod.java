package test;
import com.google.gson.Gson;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.PostBody;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;

import static io.restassured.RestAssured.given;


public class POSTMethod {

    public static void main(String[] args) {

        // RequestSpecification object
        String baseUri = "https://jsonplaceholder.typicode.com";
        RequestSpecification request = given();
        request.baseUri(baseUri);

        // Content-type => Header
        request.header(new Header("Content-type","application/json; charset=UTF-8"));

        // Form up request body
/*        String postBody = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"The request's tile\",\n" +
                "  \"body\": \"The request's body\"\n" +
                "}";*/

        // Gson
        Gson gson = new Gson();
        PostBody postBody = new PostBody();
        postBody.setUserId(1);
        postBody.setId(1);
        postBody.setTitle("The request's tile");
        postBody.setBody("The request's body");

        // Send POST request
        Response response = request.body(gson.toJson(postBody)).post("/posts");
        response.prettyPrint();

        // Verification
        response.then().statusCode(equalTo(201));
        response.then().statusLine(containsStringIgnoringCase("201 Created"));
        response.then().body("userId",equalTo(1));
        response.then().body("id",equalTo(101)); //bug
        response.then().body("title",equalTo("The request's tile"));
        response.then().body("body",equalTo("The request's body"));
    }
}
