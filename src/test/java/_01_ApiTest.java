import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class _01_ApiTest {

    @Test
    public void Test1() {
        given()

                //hazırlık kodları buraya yazılıyor

                .when()
                //endpoint(url), metoduyla birlikte istek gönderilme aşaması

                .then()
        //assertion, test, data işlemleri
        ;
    }

    @Test
    public void statusCodeTest() {
        given()
                // gönderilecek bilgiler burada olacak
                .when()
                .get("http://api.zippopotam.us/us/90210") // duruma göre post,put,delete olacak

                .then()
                .log().body() // gelen body kısmını göster
                .statusCode(200); // test kısmı assertion status 200 mü
    }

    @Test
    public void contentTypeTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210") // duruma göre post,put,delete olacak

                .then()
                .log().body() // dönen body bilgisini göster
                .statusCode(200) // test kısmı assertion status 200 mü
                .contentType(ContentType.JSON); // dönen datanın tipi JSON mı

    }

    @Test
    public void checkCountryInResponseBody() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210") // duruma göre post,put,delete olacak

                .then()
                .log().body() // dönen body bilgisini göster
                .statusCode(200) // test kısmı assertion status 200 mü
                .body("country", equalTo("United States")); //country i dışarı almadan
        // bulunduğu yeri (path i) vererek içerde assertion hamcrest kütüphanesini yapıyor.

    }

    @Test
    public void checkCountryInResponseBody2() {

        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu doğrulayınız

        given()
                .when()

                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places[0].state", equalTo("California")); //jason pathfinder dan bulundu
    }

    @Test
    public void checkHasItem() {
        // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
        // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
        // olduğunu doğrulayınız

        given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")
                .then()
                .log().body() //sıralamayı yaptır. url nin
                .body("places.'place name'", hasItem("Dörtağaç Köyü")); //places içindeki bütün place name
        // places içinde Dervişler köyü var mı
    }

    @Test
    public void bodyArraySizeTest() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places", hasSize(1)) //places in eleman uzunluğu 1 mi? 1 elemanlı mı
        ;
    }

    @Test
    public void combiningTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                //.log().body()
                .body("places" , hasSize(1))  // places in eleman uzunluğuı 1 mi
                .body("places[0].state", equalTo("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"));

    }

    @Test
    public void pathParamTest(){

        given()  // gönderilecek hazırlıklar
                .pathParam("ulke","us")
                .pathParam("postaKodu",90210)
                .log().uri()  // request linki ni göndermeden önce görebilirsin

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKodu}")

                .then()
                .log().body()
        ;
    }

    @Test
    public void queryParamTest() {
        //https://gorest.co.in/public/v1/users?page=3

        given()
                .param("page",1)  //?page=1  param: soru işareti ile parametre göndreceğim
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users") //https://gorest.co.in/public/v1/users?page=1
                //.get("https://gorest.co.in/public/v1/users?page=1")

                .then()
                .log().body();
    }

    @Test
    public void queryParamTest2(){

        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 0; i <=10 ; i++) {


        given()
                .param("page",i)
                .log().uri() // kontrol etmek için log koyduk
                .when()
                .get("https://gorest.co.in/public/v1/users") //?page=3 ü kaldırdık, param yaptık
                .then()
                .body("meta.pagination.page", equalTo(i)); //assertion yaptık

        }

    }

}
