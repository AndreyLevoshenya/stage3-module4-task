package com.mjc.school.controller;

import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagController extends BaseController<TagDtoRequest, TagDtoResponse, Long> {
    ResponseEntity<List<TagDtoResponse>> readByNewsId(Long newsId);
}
