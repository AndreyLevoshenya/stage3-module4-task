package com.mjc.school.controller.implementation;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.ParametersDtoRequest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class NewsControllerImplV1Test {
    private static final RequestSpecification SPEC = given().baseUri("http://localhost:8080/api/v1")
            .contentType(ContentType.JSON)
            .queryParam("version", 1);

    @Test
    public void readAllNewsTest() {
        List<NewsDtoResponse> response = SPEC.basePath("news").param("size", 40)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("", NewsDtoResponse.class);
        assertThat(response).isNotNull();
    }

    @Test
    public void readNewsByIdTest() {
        NewsDtoResponse response = SPEC.basePath("news/15")
                .when().get()
                .then().statusCode(200)
                .extract().as(NewsDtoResponse.class);
        assertThat(response.getId()).isEqualTo(15L);
    }

    @Test
    public void createNewsTest() {
        NewsDtoResponse response = SPEC.basePath("news")
                .body(new NewsDtoRequest(null, "title", "content", 1L, new ArrayList<>()))
                .when().post()
                .then().statusCode(201)
                .extract().as(NewsDtoResponse.class);

        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getContent()).isEqualTo("content");
        assertThat(response.getAuthorDtoResponse().getId()).isEqualTo(1L);
    }

    @Test
    public void updateNewsTest() {
        NewsDtoResponse response = SPEC.basePath("news")
                .body(new NewsDtoRequest(21L, "title", "content", 1L, new ArrayList<>()))
                .when().put()
                .then().statusCode(200)
                .extract().as(NewsDtoResponse.class);

        assertThat(response.getId()).isEqualTo(21L);
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getContent()).isEqualTo("content");
        assertThat(response.getAuthorDtoResponse().getId()).isEqualTo(1L);
    }

    @Test
    public void patchNewsTest() {
        NewsDtoResponse response = SPEC.basePath("news")
                .body(new NewsDtoRequest(3L, "title", null, null, null))
                .when().patch()
                .then().statusCode(200)
                .extract().as(NewsDtoResponse.class);

        assertThat(response.getId()).isEqualTo(3L);
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getContent()).isNotNull();
        assertThat(response.getAuthorDtoResponse()).isNotNull();
    }

    @Test
    public void deleteNewsTest() {
        SPEC.basePath("news/22")
                .when().delete()
                .then().statusCode(204);
    }

    @Test
    public void readNewsByParamsTest() {
        List<NewsDtoResponse> response = SPEC.basePath("news/byParams")
                .body(new ParametersDtoRequest("title", "content", "William Shakespeare", new ArrayList<>(), new ArrayList<>()))
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("", NewsDtoResponse.class);
        assertThat(response).isNotNull();
    }
}