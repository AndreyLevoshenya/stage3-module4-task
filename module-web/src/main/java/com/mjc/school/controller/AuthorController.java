package com.mjc.school.controller;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.http.ResponseEntity;

public interface AuthorController extends BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {
    ResponseEntity<AuthorDtoResponse> readByNewsId(Long newsId);
}
