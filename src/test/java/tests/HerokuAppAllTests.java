package tests;

import baseURL.HerokuAppBaseURL;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.*;

import static io.restassured.RestAssured.given;

public class HerokuAppAllTests {

    public static String token;
    public static int bookingId;

    @Test(priority = 1)
    public void createToken() {
        HerokuAppBaseURL.SPEC.pathParams("pp1", "auth");
        HerokuAppTokenBody body = new HerokuAppTokenBody("admin", "password123");

        Response response = given().spec(HerokuAppBaseURL.SPEC).contentType(ContentType.JSON).when().body(body).post("/{pp1}");
        response.prettyPrint();

        HerokuAppExpectedTokenBody resBody = response.as(HerokuAppExpectedTokenBody.class);
        token = resBody.getToken();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void createBooking() {
        HerokuAppBaseURL.SPEC.pathParams("pp1", "booking");
        HerokuAppCreateBookingInner inner = new HerokuAppCreateBookingInner("2023-06-18", "2023-07-18");
        HerokuAppCreateBookingBody body = new HerokuAppCreateBookingBody("Yusuf", "Soyisim", 250, true, inner, "Breakfast");

        Response response = given()
                            .spec(HerokuAppBaseURL.SPEC)
                            .contentType(ContentType.JSON)
                            .when().body(body)
                            .post("/{pp1}");
        response.prettyPrint();

        HerokuAppCreateBookingExpectedData resBody = response.as(HerokuAppCreateBookingExpectedData.class);
        bookingId = resBody.getBookingid();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(resBody.getBooking().getFirstname(), body.getFirstname());
        Assert.assertEquals(resBody.getBooking().getLastname(), body.getLastname());
        Assert.assertEquals(resBody.getBooking().getTotalprice(), body.getTotalprice());
        Assert.assertEquals(resBody.getBooking().getBookingdates().getCheckin(), body.getBookingdates().getCheckin());
        Assert.assertEquals(resBody.getBooking().getBookingdates().getCheckout(), body.getBookingdates().getCheckout());
        Assert.assertEquals(resBody.getBooking().getAdditionalneeds(), body.getAdditionalneeds());
    }

    @Test(priority = 3)
    public void updateBooking() {
        HerokuAppBaseURL.SPEC.pathParams("pp1", "booking", "pp2", bookingId);
        HerokuAppCreateBookingInner inner = new HerokuAppCreateBookingInner("2023-06-18", "2023-07-18");
        HerokuAppCreateBookingBody body = new HerokuAppCreateBookingBody("uusi nimi", "uusi sukunimi", 350, false, inner, "Breakfast");

        Response response = given()
                            .spec(HerokuAppBaseURL.SPEC)
                            .contentType(ContentType.JSON)
                            .header("Accept", ContentType.JSON)
                            .header("Cookie", "token=" + token)
                            .when()
                            .body(body)
                            .put("/{pp1}/{pp2}");
        response.prettyPrint();

        HerokuAppCreateBookingBody resBody = response.as(HerokuAppCreateBookingBody.class);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(resBody.getFirstname(), body.getFirstname());
        Assert.assertEquals(resBody.getLastname(), body.getLastname());
        Assert.assertEquals(resBody.getTotalprice(), body.getTotalprice());
        Assert.assertEquals(resBody.getBookingdates().getCheckin(), body.getBookingdates().getCheckin());
        Assert.assertEquals(resBody.getBookingdates().getCheckout(), body.getBookingdates().getCheckout());
        Assert.assertEquals(resBody.getAdditionalneeds(), body.getAdditionalneeds());
    }

    @Test(priority = 4)
    public void deleteBooking() {
        HerokuAppBaseURL.SPEC.pathParams("pp1", "booking", "pp2", bookingId);

        Response response = given()
                .spec(HerokuAppBaseURL.SPEC)
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .when()
                .delete("/{pp1}/{pp2}");
        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 201);
    }
}
