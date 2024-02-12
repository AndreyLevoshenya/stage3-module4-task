package com.mjc.school.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentDtoResponse {
    private Long id;
    private String content;
    private Long newsId;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public CommentDtoResponse() {
    }

    public CommentDtoResponse(Long id, String content, Long newsId, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.content = content;
        this.newsId = newsId;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        return "CommentDtoResponse{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", newsId=" + newsId +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDtoResponse that = (CommentDtoResponse) o;
        return id.equals(that.id) && content.equals(that.content) && newsId.equals(that.newsId) && createDate.equals(that.createDate) && lastUpdateDate.equals(that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, newsId, createDate, lastUpdateDate);
    }
}
