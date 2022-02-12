package factoryRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPUT implements IRequest{
    @Override
    public Response send(RequestInfo requestInfo) {
        Response response=given()
                        .auth()
                        .preemptive()
                        .basic("eynarmojix@eynar.com","12345")
                        .log().all()
                        .body(requestInfo.getBody())
                 .when()
                        .put(requestInfo.getUrl());

        response.then()
                .log().all();

        return response;
    }
}
