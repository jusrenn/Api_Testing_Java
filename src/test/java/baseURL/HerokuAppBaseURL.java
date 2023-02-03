package baseURL;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class HerokuAppBaseURL {

    public static RequestSpecification SPEC;

    static {
        SPEC = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();
    }
}
