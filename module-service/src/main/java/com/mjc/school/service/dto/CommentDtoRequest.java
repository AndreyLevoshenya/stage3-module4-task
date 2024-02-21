package com.mjc.school.service.dto;

import com.mjc.school.service.annotations.IdField;
import com.mjc.school.service.annotations.NotNull;
import com.mjc.school.service.annotations.StringField;

public record CommentDtoRequest(
        @IdField
        Long id,

        @StringField(min = 5, max = 255)
        @NotNull
        String content,

        @IdField
        Long newsId) {
}
