package com.mjc.school.controller.implementation;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorControllerImplV1Test {
    private static final RequestSpecification SPEC = given().baseUri("http://localhost:8080/api/v1")
            .contentType(ContentType.JSON)
            .queryParam("version", 1);

    @Test
    public void readAllAuthorsTest() {
        List<AuthorDtoResponse> response = SPEC.basePath("authors").param("size", 40)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("", AuthorDtoResponse.class);
        assertThat(response).isNotNull();
    }

    @Test
    public void readAuthorByIdTest() {
        AuthorDtoResponse response = SPEC.basePath("authors/15")
                .when().get()
                .then().statusCode(200)
                .extract().as(AuthorDtoResponse.class);
        assertThat(response.getId()).isEqualTo(15L);
    }

    @Test
    public void createAuthorTest() {
        AuthorDtoResponse response = given()
                .baseUri("http://localhost:8080/api/v1")
                .basePath("authors")
                .contentType(ContentType.JSON)
                .queryParam("version", 1)
                .body(new AuthorDtoRequest(null, "name"))
                .when().post()
                .then().statusCode(201)
                .extract().as(AuthorDtoResponse.class);

        assertThat(response.getName()).isEqualTo("name");
    }

    @Test
    public void updateAuthorTest() {
        AuthorDtoResponse response = SPEC.basePath("authors")
                .body(new AuthorDtoRequest(2L, "name"))
                .when().put()
                .then().statusCode(200)
                .extract().as(AuthorDtoResponse.class);

        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getName()).isEqualTo("name");
    }

    @Test
    public void patchAuthorTest() {
        AuthorDtoResponse response = SPEC.basePath("authors")
                .body(new AuthorDtoRequest(3L, "name"))
                .when().patch()
                .then().statusCode(200)
                .extract().as(AuthorDtoResponse.class);

        assertThat(response.getId()).isEqualTo(3L);
        assertThat(response.getName()).isEqualTo("name");
    }

    @Test
    public void deleteAuthorTest() {
        SPEC.basePath("authors/22")
                .when().delete()
                .then().statusCode(204);
    }

    @Test
    public void getAuthorByNewsId() {
        AuthorDtoResponse response = SPEC.basePath("authors/get/30")
                .when().get()
                .then().statusCode(200)
                .extract().as(AuthorDtoResponse.class);
        assertThat(response).isNotNull();
    }
}
