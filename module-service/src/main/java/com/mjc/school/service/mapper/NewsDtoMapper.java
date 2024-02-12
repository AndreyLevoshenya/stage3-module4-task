package com.mjc.school.service.mapper;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class NewsDtoMapper {
    @Autowired
    protected NewsRepository newsRepository;

    @Autowired
    protected AuthorRepository authorRepository;

    @Autowired
    protected TagRepository tagRepository;

    public abstract List<NewsDtoResponse> modelListToDtoList(List<News> modelList);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "author", expression = "java(authorRepository.getReference(dtoRequest.authorId()))"),
            @Mapping(target = "tags", expression =
                    "java(dtoRequest.tagIds() != null ? dtoRequest.tagIds().stream().map(tagId -> tagRepository.getReference(tagId)).toList() : newsRepository.readById(dtoRequest.id()).get().getTags())")})
    public abstract News dtoToModel(NewsDtoRequest dtoRequest);

    @Mappings({
            @Mapping(source = "author", target = "authorDtoResponse"),
            @Mapping(source = "tags", target = "tagDtoResponseList")})
    public abstract NewsDtoResponse modelToDto(News news);
}
