package com.mjc.school.service.dto;

import com.mjc.school.service.annotations.IdField;
import com.mjc.school.service.annotations.NotNull;
import com.mjc.school.service.annotations.StringField;

import java.util.Objects;

public record CommentDtoRequest(
        @IdField
        Long id,

        @StringField(min = 5, max = 255)
        @NotNull
        String content,

        @IdField
        Long newsId) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDtoRequest that = (CommentDtoRequest) o;
        return id.equals(that.id) && content.equals(that.content) && newsId.equals(that.newsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, newsId);
    }
}
