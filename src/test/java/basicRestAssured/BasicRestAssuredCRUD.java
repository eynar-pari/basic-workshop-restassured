package basicRestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BasicRestAssuredCRUD {
    /*
    *  given ---> configuration
    *  when ---> method
    *  then ---> responses
    * */
    @Test
    public void verifyCRUDProject(){
        // GET TOKEN
        Response response=given()
                .auth()
                .preemptive()
                .basic("eynarmojix@eynar.com","12345")
                .log().all()
        .when()
                .get("https://todo.ly/api/authentication/token.json");

        response.then()
                .log().all();

        String token= response.then().extract().path("TokenString");

        // CREATE
        response=given()
                .header("Token",token)
                .body("{\n" +
                        "   \"Content\":\"Training\",\n" +
                        "   \"Icon\":\"1\"\n" +
                        "}")
                .log().all()
        .when()
                .post("https://todo.ly/api/projects.json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("Training"))
                .body("Icon",equalTo(1))
                .log().all();

        Integer idProj= response.then().extract().path("Id");

        // READ
        response=given()
                .header("Token",token)
                .log().all()
                .when()
                .get("https://todo.ly/api/projects/"+idProj+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("Training"))
                .body("Icon",equalTo(1))
                .log().all();
        // UPDATE
        response=given()
                .header("Token",token)
                .log().all()
                .body("{\n" +
                        "   \"Content\":\"Update\",\n" +
                        "   \"Icon\":\"5\"\n" +
                        "}")
                .when()
                .put("https://todo.ly/api/projects/"+idProj+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("Update"))
                .body("Icon",equalTo(5))
                .log().all();
        // DELETE
        response=given()
                .header("Token",token)
                .log().all()
                .when()
                .delete("https://todo.ly/api/projects/"+idProj+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("Update"))
                .body("Icon",equalTo(5))
                .body("Deleted",equalTo(true))
                .log().all();


    }
}
