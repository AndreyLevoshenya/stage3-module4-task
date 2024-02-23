package com.mjc.school.service.implementation;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.annotations.Valid;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.AuthorDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ExceptionErrorCodes.AUTHOR_DOES_NOT_EXIST;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorDtoMapper authorDtoMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorDtoMapper authorDtoMapper) {
        this.authorRepository = authorRepository;
        this.authorDtoMapper = authorDtoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDtoResponse> readAll(int page, int size, String sortBy) {
        return authorDtoMapper.modelListToDtoList(authorRepository.readAll(page, size, sortBy));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readById(@Valid Long id) {
        if (authorRepository.existById(id)) {
            Author author = authorRepository.readById(id).get();
            return authorDtoMapper.modelToDto(author);
        } else {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }

    @Override
    @Transactional
    public AuthorDtoResponse create(@Valid AuthorDtoRequest createRequest) {
        Author model = authorDtoMapper.dtoToModel(createRequest);

        Author author = authorRepository.create(model);
        return authorDtoMapper.modelToDto(author);
    }

    @Override
    @Transactional
    public AuthorDtoResponse update(@Valid AuthorDtoRequest updateRequest) {
        if (authorRepository.existById(updateRequest.getId())) {
            Author author = authorDtoMapper.dtoToModel(updateRequest);
            return authorDtoMapper.modelToDto(authorRepository.update(author));
        } else {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), updateRequest.getId()));
        }
    }

    @Override
    @Transactional
    public AuthorDtoResponse patch(AuthorDtoRequest patchRequest) {
        Long id;
        String name;
        if (patchRequest.getId() != null && authorRepository.existById(patchRequest.getId())) {
            id = patchRequest.getId();
        } else {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), patchRequest.getId()));
        }
        Author prevAuthor = authorRepository.readById(id).get();
        name = patchRequest.getName() != null ? patchRequest.getName() : prevAuthor.getName();

        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(id, name);

        return update(authorDtoRequest);
    }

    @Override
    @Transactional
    public boolean deleteById(@Valid Long id) {
        if (authorRepository.existById(id)) {
            return authorRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readByNewsId(@Valid Long newsId) {
        if (authorRepository.readByNewsId(newsId).isPresent()) {
            return authorDtoMapper.modelToDto(authorRepository.readByNewsId(newsId).get());
        } else {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), newsId));
        }
    }
}
