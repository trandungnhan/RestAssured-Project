package model;

import io.restassured.http.Header;

import java.util.function.Function;

public interface RequestCapability {

    String email = "trandungnhan89@gmail.com";
    String token = "ATATT3xFfGF0E3253pGmnf6iZZAmAAzJTDhyxjkSBbV8pwQw3DrTBymrK0B4dp7b7vVSUCGDcWxeBqbSSps4TDE2CrEfDhEU7emnh6UMY3TWdLxOZFNjdmQWqYLU2Ga3osBtwLEo4RQiMHoHQYEXNY4ZEQe_1Lh0BTsDcnKRgfTS77U9iIWVGzk=B4F2BF4D";

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

