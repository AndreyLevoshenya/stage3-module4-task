package com.mjc.school.repository.implementation;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends AbstractRepository<Comment, Long> implements CommentRepository {
    @Override
    void update(Comment entity, Comment newEntity) {
        entity.setContent(newEntity.getContent());
        entity.setNewsId(newEntity.getNewsId());
        entity.setLastUpdateDate(newEntity.getLastUpdateDate());
    }

    @Override
    public List<Comment> readByNewsId(Long newsId) {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c" +
                        " WHERE c.newsId=:newsId", Comment.class)
                .setParameter("newsId", newsId);
        return query.getResultList();
    }
}
