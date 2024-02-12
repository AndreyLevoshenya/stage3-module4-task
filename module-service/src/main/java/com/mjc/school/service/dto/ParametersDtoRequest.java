package com.mjc.school.service.dto;

import java.util.List;
import java.util.Objects;

public record ParametersDtoRequest(
        String newsTitle,
        String newsContent,
        String authorName,
        List<Integer> tagIds,
        List<String> tagNames) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParametersDtoRequest that = (ParametersDtoRequest) o;
        return Objects.equals(tagNames, that.tagNames) && Objects.equals(tagIds, that.tagIds) && Objects.equals(authorName, that.authorName) && Objects.equals(newsTitle, that.newsTitle) && Objects.equals(newsContent, that.newsContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagNames, tagIds, authorName, newsTitle, newsContent);
    }
}
