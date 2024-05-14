import Model.ToDo;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class _04_Tasks {

    /**
     * Task 1
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */
    @Test
    public void Task1(){
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                //.log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
                ;

    }

    /**
     * Task 2
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     *a) expect response completed status to be false(hamcrest)
     *b) extract completed field and testNG assertion(testNG)
     */

    @Test
    public void Task2(){
        // a)
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                //.log().body() //testi yaptıktan sonra satırı kapa
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed",equalTo(false)) //hemster ile assertion
                ;

        // b) //eşitleme olacak dikkat
        boolean completedData=
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("completed")
        ;
        Assert.assertFalse(completedData);

    }

    /** Task 3
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * Converting Into POJO body data and write
     */

    @Test
    public void Task3(){
        ToDo todoNesne=
    given()
            .when()
            .get("https://jsonplaceholder.typicode.com/todos/2")
            .then()
            //.log().body() // log body den sonra içindekileri TODO classına kopyaladık
            //.statusCode(200);
            .extract().body().as(ToDo.class);

        System.out.println("todoNesne = " + todoNesne);
        System.out.println("todoNesne.getTitle() = " + todoNesne.getTitle());
        System.out.println("todoNesne.getId() = " + todoNesne.getId());
        System.out.println("todoNesne.isCompleted() = " + todoNesne.isCompleted());
    }

}

