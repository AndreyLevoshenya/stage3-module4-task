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
import com.mjc.school.service.mapper.NewsDtoMapper;
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
    private final NewsDtoMapper newsDtoMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, NewsRepository newsRepository, CommentDtoMapper commentDtoMapper, NewsDtoMapper newsDtoMapper) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.commentDtoMapper = commentDtoMapper;
        this.newsDtoMapper = newsDtoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDtoResponse> readAll(int page, int size, String sortBy) {
        return commentDtoMapper.modelListToDtoList(commentRepository.readAll(page, size, sortBy), newsDtoMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDtoResponse readById(@Valid Long id) {
        if (commentRepository.existById(id)) {
            return commentDtoMapper.modelToDto(commentRepository.readById(id).get(), newsDtoMapper);
        } else {
            throw new NotFoundException(String.format(COMMENT_DOES_NOT_EXIST.getErrorMessage(), id));

        }
    }

    @Override
    @Transactional
    public CommentDtoResponse create(@Valid CommentDtoRequest createRequest) {
        Comment model = commentDtoMapper.dtoToModel(createRequest, newsRepository);

        return commentDtoMapper.modelToDto(commentRepository.create(model), newsDtoMapper);
    }

    @Override
    @Transactional
    public CommentDtoResponse update(@Valid CommentDtoRequest updateRequest) {
        if (commentRepository.existById(updateRequest.getId())) {
            Comment comment = commentDtoMapper.dtoToModel(updateRequest, newsRepository);
            return commentDtoMapper.modelToDto(commentRepository.update(comment), newsDtoMapper);
        } else {
            throw new NotFoundException(String.format(COMMENT_DOES_NOT_EXIST.getErrorMessage(), updateRequest.getId()));
        }
    }

    @Override
    @Transactional
    public CommentDtoResponse patch(CommentDtoRequest patchRequest) {
        Long id;
        String content;
        if (patchRequest.getId() != null && commentRepository.existById(patchRequest.getId())) {
            id = patchRequest.getId();
        } else {
            throw new NotFoundException(String.format(COMMENT_DOES_NOT_EXIST.getErrorMessage(), patchRequest.getId()));
        }
        Comment prevComment = commentRepository.readById(id).get();
        content = patchRequest.getContent() != null ? patchRequest.getContent() : prevComment.getContent();

        CommentDtoRequest updateRequest = new CommentDtoRequest(id, content, prevComment.getNews().getId());

        return update(updateRequest);
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
            return commentDtoMapper.modelListToDtoList(commentRepository.readByNewsId(newsId), newsDtoMapper);
        } else {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), newsId));
        }
    }
}
