package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {

    String email = "trandungnhan89@gmail.com";
    String token = "ATATT3xFfGF0RRzktH54C1w8C4RQ_ddaMmSgW6bJHxzDvUQm_gwYGPqjs7uvzY2G13SKLFGRE3l_IxQjYsmDCVoc6M9CXvLFT5Ez4AfV6ul4olIRZJTNXCy7B4nYspU0f727WHG0iurZi8WlJk7wufNpp_ZFkSUEzY6cYXt1gQ6EOBoJjxZfchI=9BC1F2A9";

    Header defaultHeader = new Header("Content-type", "application/json; charset=UTF-8");
    Header accessJSONHeader = new Header("Accept", "application/json");

    static Header getAuthenticatedHeader(String encodedCredStr) {
        if (encodedCredStr == null) {
            throw new IllegalArgumentException("[ERR] encodedCredStr can't be null!");
        }
        return new Header("Authorization", "Basic " + encodedCredStr);
    }

    Function<String, Header> getAuthenticatedHeader = encodedCredStr -> {
        if (encodedCredStr == null) {
            throw new IllegalArgumentException("[ERR] encodedCredStr can't be null!");
        }
        return new Header("Authorization", "Basic " + encodedCredStr);
    };
}

