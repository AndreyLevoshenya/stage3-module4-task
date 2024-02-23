package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    @GetMapping(params = "version=1")
    public ResponseEntity<List<AuthorDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy) {
        return new ResponseEntity<>(authorService.readAll(page, size, sortBy), OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}", params = "version=1")
    public ResponseEntity<AuthorDtoResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.readById(id), OK);
    }

    @Override
    @PostMapping(params = "version=1")
    public ResponseEntity<AuthorDtoResponse> create(@RequestBody AuthorDtoRequest createRequest) {
        return new ResponseEntity<>(authorService.create(createRequest), CREATED);
    }

    @Override
    @PutMapping(params = "version=1")
    public ResponseEntity<AuthorDtoResponse> update(@RequestBody AuthorDtoRequest updateRequest) {
        return new ResponseEntity<>(authorService.update(updateRequest), OK);
    }

    @Override
    @PatchMapping(params = "version=1")
    public ResponseEntity<AuthorDtoResponse> patch(@RequestBody AuthorDtoRequest updateRequest) {
        return new ResponseEntity<>(authorService.patch(updateRequest), OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}", params = "version=1")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.deleteById(id), NO_CONTENT);
    }

    @GetMapping(value = "/get/{newsId:\\d+}", params = "version=1")
    public ResponseEntity<AuthorDtoResponse> readByNewsId(@PathVariable Long newsId) {
        return new ResponseEntity<>(authorService.readByNewsId(newsId), OK);
    }
}
