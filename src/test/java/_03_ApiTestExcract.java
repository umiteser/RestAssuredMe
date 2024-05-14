import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class _03_ApiTestExcract {

    @Test
    public void extractingJsonPath() {
        String ulkeAdi =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().path("country"); //path i country olan değeri exctract yap

        System.out.println("ulkeAdi = " + ulkeAdi);
        Assert.assertEquals(ulkeAdi, "United States");// alınan değer buna eşit mi
    }

    @Test
    public void extractingJsonPath2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız

        String stateName =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().path("places[0].state");

        System.out.println("stateName = " + stateName);
        Assert.assertEquals(stateName, "California");
    }

    @Test
    public void extractingJsonPath3() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının place name değerinin  "Beverly Hills"
        // olduğunu testNG Assertion ile doğrulayınız

        String placeName =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().path("places[0].'place name'"); //places[0]["place name"] bu da olur

        Assert.assertEquals(placeName, "Beverly Hills");
    }

    @Test
    public void extractingJsonPath4() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // limit bilgisinin 10 olduğunu testNG ile doğrulayınız.

        int limit =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .extract().path("meta.pagination.limit");

        Assert.assertEquals(limit, 10);
    }

    @Test
    public void extractingJsonPath5() {
        List<Integer> idler =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .log().body()
                        .extract().path("data.id")  // id lerin yer aldığı bir array
                ;

        System.out.println("idler = " + idler);
    }

    @Test
    public void extractingJsonPath6() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // bütün name leri yazdırınız.

        List<String> names =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        //.log().body()
                        .extract().path("data.name")  // name lerin yer aldığı bir array
                ;

        System.out.println("names = " + names);
    }

    @Test
    public void extractingJsonPathResponsAll() {  //tüm dataları almak
        Response donenData =    // var donenData=pm.Response.Json()
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        //.log().body()
                        .extract().response()  // dönen tüm data alındı
                ;

        List<Integer> idler = donenData.path("data.id");  // var id=donenData.id;
        List<String> names = donenData.path("data.name");
        int limit = donenData.path("meta.pagination.limit"); //önceki sorulardan

        System.out.println("idler = " + idler);
        System.out.println("names = " + names);
        System.out.println("limit = " + limit);

        Assert.assertTrue(idler.contains(6892473));
        Assert.assertTrue(names.contains("Gov. Atmananda Iyer"));
        Assert.assertEquals(limit, 10);
        //Not id ler silinmiş hata alabilirsin
    }

}
