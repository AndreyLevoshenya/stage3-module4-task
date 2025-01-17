package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.ParametersDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/v1/news")
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    @GetMapping(params = "version=1")
    @ResponseStatus(OK)
    public ResponseEntity<List<NewsDtoResponse>> readAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy) {

        return new ResponseEntity<>(newsService.readAll(page, size, sortBy), OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}", params = "version=1")
    @ResponseStatus(OK)
    public ResponseEntity<NewsDtoResponse> readById(@PathVariable Long id) {
        return new ResponseEntity<>(newsService.readById(id), OK);
    }

    @Override
    @PostMapping(params = "version=1")
    @ResponseStatus(CREATED)
    public ResponseEntity<NewsDtoResponse> create(@RequestBody NewsDtoRequest createRequest) {
        return new ResponseEntity<>(newsService.create(createRequest), CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}", params = "version=1")
    @ResponseStatus(OK)
    public ResponseEntity<NewsDtoResponse> update(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
        updateRequest.setId(id);
        return new ResponseEntity<>(newsService.update(updateRequest), OK);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}", params = "version=1")
    @ResponseStatus(OK)
    public ResponseEntity<NewsDtoResponse> patch(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
        updateRequest.setId(id);
        return new ResponseEntity<>(newsService.patch(updateRequest), OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}", params = "version=1")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @GetMapping(value = "/byParams", params = "version=1")
    @ResponseStatus(OK)
    public ResponseEntity<List<NewsDtoResponse>> readByParams(@RequestBody ParametersDtoRequest parametersDtoRequest) {
        return new ResponseEntity<>(newsService.readByParams(parametersDtoRequest), OK);
    }
}
