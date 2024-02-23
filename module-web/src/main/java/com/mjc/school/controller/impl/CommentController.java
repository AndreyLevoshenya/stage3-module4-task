package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/v1/comments")
public class CommentController implements BaseController<CommentDtoRequest, CommentDtoResponse, Long> {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    @GetMapping(params = "version=1")
    public ResponseEntity<List<CommentDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy
    ) {
        return new ResponseEntity<>(commentService.readAll(page, size, sortBy), OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}", params = "version=1")
    public ResponseEntity<CommentDtoResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.readById(id), OK);
    }

    @Override
    @PostMapping(params = "version=1")
    public ResponseEntity<CommentDtoResponse> create(@RequestBody CommentDtoRequest createRequest) {
        return new ResponseEntity<>(commentService.create(createRequest), CREATED);
    }

    @Override
    @PutMapping(params = "version=1")
    public ResponseEntity<CommentDtoResponse> update(@RequestBody CommentDtoRequest updateRequest) {
        return new ResponseEntity<>(commentService.update(updateRequest), OK);
    }

    @Override
    @PatchMapping(params = "version=1")
    public ResponseEntity<CommentDtoResponse> patch(@RequestBody CommentDtoRequest updateRequest) {
        return new ResponseEntity<>(commentService.patch(updateRequest), OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}", params = "version=1")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.deleteById(id), NO_CONTENT);
    }

    @GetMapping(value = "/get/{newsId:\\d+}", params = "version=1")
    public ResponseEntity<List<CommentDtoResponse>> readByNewsId(@PathVariable Long newsId) {
        return new ResponseEntity<>(commentService.readByNewsId(newsId), OK);
    }
}
