package com.mjc.school.repository.implementation;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.SearchParameters;
import com.mjc.school.repository.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class NewsRepositoryImpl extends AbstractRepository<News, Long> implements NewsRepository {

    @Override
    public void update(News entity, News newEntity) {
        entity.setTitle(newEntity.getTitle());
        entity.setContent(newEntity.getContent());
        entity.setAuthor(newEntity.getAuthor());
        List<Tag> newTags = new ArrayList<>(newEntity.getTags());
        entity.setTags(newTags);
    }

    @Override
    public List<News> readByParams(SearchParameters params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        Root<News> root = criteriaQuery.from(News.class);

        List<Predicate> predicates = new ArrayList<>();

        if(params.newsTitle() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + params.newsTitle() + "%"));
        }

        if(params.newsContent() != null) {
            predicates.add(criteriaBuilder.like(root.get("content"), "%" + params.newsContent() + "%"));
        }

        if(params.authorName() != null) {
            Join<Author, News> authorNews = root.join("author");
            predicates.add(criteriaBuilder.equal(authorNews.get("name"), params.authorName()));
        }

        if(params.tagName() != null || params.tagId() != null) {
            Join<Tag, News> newsTags = root.join("tags");
            if(params.tagName() != null) {
                predicates.add(newsTags.get("id").in(params.tagId()));
            }
            if(params.tagId() != null) {
                predicates.add(newsTags.get("name").in(params.tagName()));
            }
        }

        return entityManager.createQuery(criteriaQuery.select(root).distinct(true)
                .where(predicates.toArray(new Predicate[0]))).getResultList();
    }
}
