package com.mjc.school.service.dto;

import com.mjc.school.service.annotations.IdField;
import com.mjc.school.service.annotations.NotNull;
import com.mjc.school.service.annotations.StringField;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (NewsDtoRequest) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.content, that.content) &&
                Objects.equals(this.authorId, that.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, authorId);
    }
}
