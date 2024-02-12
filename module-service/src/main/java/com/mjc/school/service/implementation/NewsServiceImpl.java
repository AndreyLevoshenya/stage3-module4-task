package com.mjc.school.service.implementation;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.SearchParameters;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.annotations.Valid;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.ParametersDtoRequest;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.NewsDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ExceptionErrorCodes.*;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final NewsDtoMapper newsDtoMapper;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, AuthorRepository authorRepository, TagRepository tagRepository, NewsDtoMapper newsDtoMapper) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.newsDtoMapper = newsDtoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readAll(int page, int size, String sortBy) {
        return newsDtoMapper.modelListToDtoList(newsRepository.readAll(page, size, sortBy));
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDtoResponse readById(@Valid Long id) {
        if (newsRepository.existById(id)) {
            News news = newsRepository.readById(id).get();
            return newsDtoMapper.modelToDto(news);
        } else {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }

    @Override
    @Transactional
    public NewsDtoResponse create(@Valid NewsDtoRequest createRequest) {
        if(!authorRepository.existById(createRequest.authorId())) {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), createRequest.authorId()));
        }
        News model = newsDtoMapper.dtoToModel(createRequest);

        return newsDtoMapper.modelToDto(newsRepository.create(model));
    }

    @Override
    @Transactional
    public NewsDtoResponse update(@Valid NewsDtoRequest updateRequest) {
        if (!newsRepository.existById(updateRequest.id())) {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), updateRequest.id()));
        }
        if (!authorRepository.existById(updateRequest.authorId())) {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), updateRequest.authorId()));
        }

        for (Long id : updateRequest.tagIds()) {
            if (!tagRepository.existById(id)) {
                throw new NotFoundException(String.format(TAG_DOES_NOT_EXIST.getErrorMessage(), id));
            }
        }

        News news = newsDtoMapper.dtoToModel(updateRequest);
        return newsDtoMapper.modelToDto(newsRepository.update(news));
    }

    @Override
    @Transactional
    public NewsDtoResponse patch(NewsDtoRequest patchRequest) {
        Long id;
        String title;
        String content;
        Long authorId;
        if (patchRequest.id() != null || newsRepository.existById(patchRequest.id())) {
            id = patchRequest.id();
        } else {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), patchRequest.id()));
        }
        News prevNews = newsRepository.readById(id).get();
        title = patchRequest.title() != null ? patchRequest.title() : prevNews.getTitle();
        content = patchRequest.content() != null ? patchRequest.content() : prevNews.getContent();
        authorId = patchRequest.authorId() != null ? patchRequest.authorId() : prevNews.getAuthor().getId();

        if (!authorRepository.existById(authorId)) {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), authorId));
        }

        if (patchRequest.tagIds() != null) {
            for (Long tagId : patchRequest.tagIds()) {
                if (!tagRepository.existById(tagId)) {
                    throw new NotFoundException(String.format(TAG_DOES_NOT_EXIST.getErrorMessage(), tagId));
                }
            }
        }

        NewsDtoRequest newsDtoRequest = new NewsDtoRequest(id, title, content, authorId, patchRequest.tagIds());

        News news = newsDtoMapper.dtoToModel(newsDtoRequest);
        return newsDtoMapper.modelToDto(newsRepository.update(news));
    }

    @Override
    @Transactional
    public boolean deleteById(@Valid Long id) {
        if (newsRepository.existById(id)) {
            return newsRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readByParams(ParametersDtoRequest parametersDtoRequest) {
        SearchParameters params = new SearchParameters(
                parametersDtoRequest.newsTitle().isEmpty() ? parametersDtoRequest.newsTitle() : null,
                parametersDtoRequest.newsContent().isEmpty() ? parametersDtoRequest.newsContent() : null,
                parametersDtoRequest.authorName().isEmpty() ? parametersDtoRequest.authorName() : null,
                parametersDtoRequest.tagIds().isEmpty() ? parametersDtoRequest.tagIds() : null,
                parametersDtoRequest.tagNames().isEmpty() ? parametersDtoRequest.tagNames() : null);
        return newsDtoMapper.modelListToDtoList(newsRepository.readByParams(params));
    }
}
