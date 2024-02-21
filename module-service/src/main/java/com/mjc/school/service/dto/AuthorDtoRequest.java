package com.mjc.school.service.dto;

import com.mjc.school.service.annotations.IdField;
import com.mjc.school.service.annotations.NotNull;
import com.mjc.school.service.annotations.StringField;

public record AuthorDtoRequest(
        @IdField
        Long id,

        @StringField(min = 3, max = 15)
        @NotNull
        String name) {
}
