package com.mjc.school.service.dto;

import com.mjc.school.service.annotations.IdField;
import com.mjc.school.service.annotations.NotNull;
import com.mjc.school.service.annotations.StringField;

import java.util.List;

public record NewsDtoRequest(
        @IdField
        Long id,

        @StringField(min = 5, max = 30)
        @NotNull
        String title,

        @StringField(min = 5, max = 225)
        @NotNull
        String content,
        @IdField
        @NotNull
        Long authorId,

        List<Long> tagIds) {
}
