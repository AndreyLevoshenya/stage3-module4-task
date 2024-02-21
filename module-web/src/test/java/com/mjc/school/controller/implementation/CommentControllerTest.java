package com.mjc.school.controller.implementation;

import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentControllerTest {
    private static final RequestSpecification SPEC = given().baseUri("http://localhost:8080/api/v1")
            .contentType(ContentType.JSON)
            .queryParam("version", 1);

    @Test
    public void readAllCommentsTest() {
        List<CommentDtoResponse> response = SPEC.basePath("comments").param("size", 40)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("", CommentDtoResponse.class);
        assertThat(response).isNotNull();
    }

    @Test
    public void readCommentByIdTest() {
        CommentDtoResponse response = SPEC.basePath("comments/1")
                .when().get()
                .then().statusCode(200)
                .extract().as(CommentDtoResponse.class);
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    public void createCommentTest() {
        CommentDtoResponse response = given()
                .baseUri("http://localhost:8080/api/v1")
                .basePath("comments")
                .contentType(ContentType.JSON)
                .queryParam("version", 1)
                .body(new CommentDtoRequest(null, "content", 1L))
                .when().post()
                .then().statusCode(201)
                .extract().as(CommentDtoResponse.class);

        assertThat(response.getContent()).isEqualTo("content");
        assertThat(response.getNewsDtoResponse().getId()).isEqualTo(1L);
    }

    @Test
    public void updateCommentTest() {
        CommentDtoResponse response = SPEC.basePath("comments")
                .body(new CommentDtoRequest(4L, "content", 1L))
                .when().put()
                .then().statusCode(200)
                .extract().as(CommentDtoResponse.class);

        assertThat(response.getId()).isEqualTo(4L);
        assertThat(response.getContent()).isEqualTo("content");
        assertThat(response.getNewsDtoResponse().getId()).isEqualTo(1L);
    }

    @Test
    public void patchCommentTest() {
        CommentDtoResponse response = SPEC.basePath("comments")
                .body(new CommentDtoRequest(3L, "content", null))
                .when().patch()
                .then().statusCode(200)
                .extract().as(CommentDtoResponse.class);

        assertThat(response.getId()).isEqualTo(3L);
        assertThat(response.getContent()).isEqualTo("content");
    }

    @Test
    public void deleteCommentTest() {
        SPEC.basePath("comments/2")
                .when().delete()
                .then().statusCode(204);
    }

    @Test
    public void getCommentsByNewsId() {
        List<CommentDtoResponse> response = SPEC.basePath("comments/get/30")
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("", CommentDtoResponse.class);
        assertThat(response).isNotNull();
    }
}
