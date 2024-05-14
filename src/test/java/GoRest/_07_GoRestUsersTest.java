package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _07_GoRestUsersTest {
    // Token ı aldım ,
    // usersCreate için neler lazım, body(user bilgileri)
    // enpoint i aldım gidiş metodu
    // BeforeClass ın içinde yapılacaklar var mı? nelerdir ?  url set ve spec hazırlanmalı

    Faker randomUreteci = new Faker();
    RequestSpecification reqSpec;
    int userID = 0;

    @BeforeClass
    public void Setup() {
        baseURI = "https://gorest.co.in/public/v2/users";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer f92bf3f56439b631d9ed928b3540968e747c8e75309c876420fb349cbb420ed1")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void CreateUser() {
        String rndFullName = randomUreteci.name().fullName();
        String rndEmail = randomUreteci.internet().emailAddress();

        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", rndFullName);
        newUser.put("gender", "male");
        newUser.put("email", rndEmail);
        newUser.put("status", "active");

        userID =
                given()
                        .spec(reqSpec)
                        .body(newUser)

                        .when()
                        .post("")// http ile başlamıyorsa BASEURI geçerli

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");

    }

    @Test(dependsOnMethods = "CreateUser")
    public void GetUserById() {

        given()
                .spec(reqSpec)

                .when()
                .get("/" + userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID)); //id kontrolu yapildi
    }

    @Test(dependsOnMethods = "GetUserById")
    public void UpdateUser(){
        String updName = "İsmet Temur";

        Map<String, String> updUser = new HashMap<>();
        updUser.put("name", updName);

        given()
                .spec(reqSpec)
                .body(updUser)
                .when()
                .put("/" + userID)
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID))
                .body("name", equalTo(updName));
    }

    @Test(dependsOnMethods = "UpdateUser")
    public void DeleteUser(){
        given()
                .spec(reqSpec)
                .when()
                .delete("/"+userID)
                .then()
                .statusCode(204);
    }
    @Test(dependsOnMethods = "DeleteUser")
    public void DeleteUserNegative(){
        given()
                .spec(reqSpec)
                .when()
                .delete("/"+userID)
                .then()
                .statusCode(404);
    }
}
