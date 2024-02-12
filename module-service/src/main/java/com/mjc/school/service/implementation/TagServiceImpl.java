package com.mjc.school.service.implementation;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.TagService;
import com.mjc.school.service.annotations.Valid;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.TagDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ExceptionErrorCodes.NEWS_DOES_NOT_EXIST;
import static com.mjc.school.service.exceptions.ExceptionErrorCodes.TAG_DOES_NOT_EXIST;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final NewsRepository newsRepository;

    private final TagDtoMapper tagDtoMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, NewsRepository newsRepository, TagDtoMapper tagDtoMapper) {
        this.tagRepository = tagRepository;
        this.newsRepository = newsRepository;
        this.tagDtoMapper = tagDtoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDtoResponse> readAll(int page, int size, String sortBy) {
        return tagDtoMapper.modelListToDtoList(tagRepository.readAll(page, size, sortBy));
    }

    @Override
    @Transactional(readOnly = true)
    public TagDtoResponse readById(@Valid Long id) {
        if (tagRepository.existById(id)) {
            Tag tag = tagRepository.readById(id).get();
            return tagDtoMapper.modelToDto(tag);
        } else {
            throw new NotFoundException(String.format(TAG_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }

    @Override
    @Transactional
    public TagDtoResponse create(@Valid TagDtoRequest createRequest) {
        Tag tag = tagDtoMapper.dtoToModel(createRequest);
        return tagDtoMapper.modelToDto(tagRepository.create(tag));
    }

    @Override
    @Transactional
    public TagDtoResponse update(@Valid TagDtoRequest updateRequest) {
        if (tagRepository.existById(updateRequest.id())) {
            Tag tag = tagDtoMapper.dtoToModel(updateRequest);
            return tagDtoMapper.modelToDto(tagRepository.update(tag));
        } else {
            throw new NotFoundException(String.format(TAG_DOES_NOT_EXIST.getErrorMessage(), updateRequest.id()));
        }
    }

    @Override
    @Transactional
    public TagDtoResponse patch(TagDtoRequest patchRequest) {
        Long id;
        String name;
        if (patchRequest.id() != null || tagRepository.existById(patchRequest.id())) {
            id = patchRequest.id();
        } else {
            throw new NotFoundException(String.format(TAG_DOES_NOT_EXIST.getErrorMessage(), patchRequest.id()));
        }
        Tag prevTag = tagRepository.readById(id).get();
        name = patchRequest.name() != null ? patchRequest.name() : prevTag.getName();

        TagDtoRequest tagDtoRequest = new TagDtoRequest(id, name);

        Tag tag = tagDtoMapper.dtoToModel(tagDtoRequest);
        return tagDtoMapper.modelToDto(tagRepository.update(tag));
    }

    @Override
    @Transactional
    public boolean deleteById(@Valid Long id) {
        if (tagRepository.existById(id)) {
            return tagRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(TAG_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDtoResponse> readByNewsId(@Valid Long newsId) {
        if (newsRepository.existById(newsId)) {
            return tagDtoMapper.modelListToDtoList(tagRepository.readByNewsId(newsId));
        } else {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), newsId));
        }
    }
}
