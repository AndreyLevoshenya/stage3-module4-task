package com.mjc.school.controller.implementation;

import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class TagControllerTest {
    private static final RequestSpecification SPEC = given().baseUri("http://localhost:8080/api/v1")
            .contentType(ContentType.JSON)
            .queryParam("version", 1);

    @Test
    public void readAllTagsTest() {
        List<TagDtoResponse> response = SPEC.basePath("tags").param("size", 40)
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("", TagDtoResponse.class);
        assertThat(response).isNotNull();
    }

    @Test
    public void readTagByIdTest() {
        TagDtoResponse response = SPEC.basePath("tags/1")
                .when().get()
                .then().statusCode(200)
                .extract().as(TagDtoResponse.class);
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    public void createTagTest() {
        TagDtoResponse response = given()
                .baseUri("http://localhost:8080/api/v1")
                .basePath("tags")
                .contentType(ContentType.JSON)
                .queryParam("version", 1)
                .body(new TagDtoRequest(null, "name"))
                .when().post()
                .then().statusCode(201)
                .extract().as(TagDtoResponse.class);

        assertThat(response.getName()).isEqualTo("name");
    }

    @Test
    public void updateTagTest() {
        TagDtoResponse response = SPEC.basePath("tags")
                .body(new TagDtoRequest(4L, "name"))
                .when().put()
                .then().statusCode(200)
                .extract().as(TagDtoResponse.class);

        assertThat(response.getId()).isEqualTo(4L);
        assertThat(response.getName()).isEqualTo("name");
    }

    @Test
    public void patchTagTest() {
        TagDtoResponse response = SPEC.basePath("tags")
                .body(new TagDtoRequest(3L, "name"))
                .when().patch()
                .then().statusCode(200)
                .extract().as(TagDtoResponse.class);

        assertThat(response.getId()).isEqualTo(3L);
        assertThat(response.getName()).isEqualTo("name");
    }

    @Test
    public void deleteTagTest() {
        SPEC.basePath("tags/2")
                .when().delete()
                .then().statusCode(204);
    }

    @Test
    public void getTagsByNewsId() {
        List<TagDtoResponse> response = SPEC.basePath("tags/get/30")
                .when().get()
                .then().statusCode(200)
                .extract().jsonPath().getList("", TagDtoResponse.class);
        assertThat(response).isNotNull();
    }
}
