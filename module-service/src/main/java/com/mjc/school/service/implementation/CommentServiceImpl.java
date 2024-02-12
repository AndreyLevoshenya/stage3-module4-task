package com.mjc.school.service.implementation;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.annotations.Valid;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.CommentDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ExceptionErrorCodes.COMMENT_DOES_NOT_EXIST;
import static com.mjc.school.service.exceptions.ExceptionErrorCodes.NEWS_DOES_NOT_EXIST;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    private final CommentDtoMapper commentDtoMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, NewsRepository newsRepository, CommentDtoMapper commentDtoMapper) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.commentDtoMapper = commentDtoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDtoResponse> readAll(int page, int size, String sortBy) {
        return commentDtoMapper.modelListToDtoList(commentRepository.readAll(page, size, sortBy));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDtoResponse readById(@Valid Long id) {
        if (commentRepository.existById(id)) {
            return commentDtoMapper.modelToDto(commentRepository.readById(id).get());
        } else {
            throw new NotFoundException(String.format(COMMENT_DOES_NOT_EXIST.getErrorMessage(), id));

        }
    }

    @Override
    @Transactional
    public CommentDtoResponse create(@Valid CommentDtoRequest createRequest) {
        Comment model = commentDtoMapper.dtoToModel(createRequest);

        return commentDtoMapper.modelToDto(commentRepository.create(model));
    }

    @Override
    @Transactional
    public CommentDtoResponse update(@Valid CommentDtoRequest updateRequest) {
        if (commentRepository.existById(updateRequest.id())) {
            Comment comment = commentDtoMapper.dtoToModel(updateRequest);
            return commentDtoMapper.modelToDto(commentRepository.update(comment));
        } else {
            throw new NotFoundException(String.format(COMMENT_DOES_NOT_EXIST.getErrorMessage(), updateRequest.id()));
        }
    }

    @Override
    @Transactional
    public CommentDtoResponse patch(CommentDtoRequest patchRequest) {
        Long id;
        String content;
        if (patchRequest.id() != null || commentRepository.existById(patchRequest.id())) {
            id = patchRequest.id();
        } else {
            throw new NotFoundException(String.format(COMMENT_DOES_NOT_EXIST.getErrorMessage(), patchRequest.id()));
        }
        Comment prevComment = commentRepository.readById(id).get();
        content = patchRequest.content() != null ? patchRequest.content() : prevComment.getContent();

        CommentDtoRequest commentDtoRequest = new CommentDtoRequest(id, content, prevComment.getNewsId());

        Comment comment = commentDtoMapper.dtoToModel(commentDtoRequest);
        return commentDtoMapper.modelToDto(commentRepository.update(comment));
    }

    @Override
    @Transactional
    public boolean deleteById(@Valid Long id) {
        if (commentRepository.existById(id)) {
            return commentRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(COMMENT_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDtoResponse> readByNewsId(@Valid Long newsId) {
        if (newsRepository.existById(newsId)) {
            return commentDtoMapper.modelListToDtoList(commentRepository.readByNewsId(newsId));
        } else {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), newsId));
        }
    }
}
