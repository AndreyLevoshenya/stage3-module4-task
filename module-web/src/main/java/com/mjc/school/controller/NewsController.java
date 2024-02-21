package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.ParametersDtoRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NewsController extends BaseController<NewsDtoRequest, NewsDtoResponse, Long> {
    ResponseEntity<List<NewsDtoResponse>> readByParams(ParametersDtoRequest parametersDtoRequest);
}
