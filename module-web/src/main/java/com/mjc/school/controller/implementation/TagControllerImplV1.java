package com.mjc.school.controller.implementation;

import com.mjc.school.controller.TagController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/v1/tags")
public class TagControllerImplV1 implements TagController {
    private final TagService tagService;

    @Autowired
    public TagControllerImplV1(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    @GetMapping(params = "version=1")
    public ResponseEntity<List<TagDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy
    ) {
        return new ResponseEntity<>(tagService.readAll(page, size, sortBy), OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}", params = "version=1")
    public ResponseEntity<TagDtoResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.readById(id), OK);
    }

    @Override
    @PostMapping(params = "version=1")
    public ResponseEntity<TagDtoResponse> create(@RequestBody TagDtoRequest createRequest) {
        return new ResponseEntity<>(tagService.create(createRequest), CREATED);
    }

    @Override
    @PutMapping(params = "version=1")
    public ResponseEntity<TagDtoResponse> update(@RequestBody TagDtoRequest updateRequest) {
        return new ResponseEntity<>(tagService.update(updateRequest), OK);
    }

    @Override
    @PatchMapping(params = "version=1")
    public ResponseEntity<TagDtoResponse> patch(@RequestBody TagDtoRequest updateRequest) {
        return new ResponseEntity<>(tagService.patch(updateRequest), OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}", params = "version=1")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.deleteById(id), NO_CONTENT);
    }

    @Override
    @GetMapping(value = "/get/{newsId:\\d+}", params = "version=1")
    public ResponseEntity<List<TagDtoResponse>> readByNewsId(@PathVariable Long newsId) {
        return new ResponseEntity<>(tagService.readByNewsId(newsId), OK);
    }
}
