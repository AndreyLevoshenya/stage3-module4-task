package com.mjc.school.repository.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(name = "news_id")
    private Long newsId;

    @Column(name = "create_date", nullable = false)
    @CreatedDate
    private LocalDateTime createDate;
    @Column(name = "last_update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    public Comment() {
    }

    public Comment(Long id, String content, Long newsId, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) && content.equals(comment.content) && newsId.equals(comment.newsId) && createDate.equals(comment.createDate) && lastUpdateDate.equals(comment.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, newsId, createDate, lastUpdateDate);
    }
}
