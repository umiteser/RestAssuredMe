import Model.Location;
import Model.Place;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class _04_ApiTestPOJO {


    @Test
    public void extractJsonAll_POJO() {

        Location locationNesnesi=
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().body().as(Location.class);

        ;

        System.out.println("locationNesnesi = " + locationNesnesi);
        System.out.println("locationNesnesi.getPlaces() = " + locationNesnesi.getPlaces());
        System.out.println("locationNesnesi.getPostcode() = " + locationNesnesi.getPostcode());

    }

    @Test
    public void extractPOJO_Soru() {
        // http://api.zippopotam.us/tr/01000
        // endpointinden dönen verilerden "Dörtağaç Köyü" ait bilgileri yazdırınız

        Location gelenBodyNesnesi=
                given()

                        .when()
                        .get("http://api.zippopotam.us/tr/01000")

                        .then()
                        //.log().body()
                        .extract().body().as(Location.class)
                ;

        for(Place p: gelenBodyNesnesi.getPlaces())
        {
            if (p.getPlacename().equalsIgnoreCase("Camuzcu Köyü"))
                System.out.println("p = " + p);
        }


    }
}
