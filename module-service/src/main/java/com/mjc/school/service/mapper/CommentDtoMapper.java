package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentDtoMapper {

    List<CommentDtoResponse> modelListToDtoList(List<Comment> modelList);

    CommentDtoResponse modelToDto(Comment model);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
    })
    Comment dtoToModel(CommentDtoRequest dtoRequest);
}
