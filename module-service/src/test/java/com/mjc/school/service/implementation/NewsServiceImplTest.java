package com.mjc.school.service.implementation;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.implementation.config.JPAConfig;
import com.mjc.school.service.implementation.config.TestConfig;
import com.mjc.school.service.mapper.NewsDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.exceptions.ExceptionErrorCodes.AUTHOR_DOES_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {
    @Mock private NewsRepository newsRepository;
    @Mock private AuthorRepository authorRepository;
    @Mock private TagRepository tagRepository;
    @Mock private NewsDtoMapper newsDtoMapper;
    @InjectMocks
    private NewsServiceImpl newsService;

    private News news;
    private NewsDtoRequest dtoRequest;

    @BeforeEach
    void setUp() {
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        dtoRequest = new NewsDtoRequest(
                1L,
                "title",
                "content",
                1L,
                new ArrayList<>()
        );
        news = new News(
                1L,
                "title",
                "content",
                date,
                date,
                new Author(
                        1L,
                        "authorName",
                        date,
                        date,
                        new ArrayList<>()),
                new ArrayList<>()
        );
        newsService = new NewsServiceImpl(newsRepository, authorRepository, tagRepository, newsDtoMapper);
    }

    @Test
    void readAllNewsTest() {
        News news1 = new News(
                2L,
                "title2",
                "content2",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                new Author(
                        1L,
                        "authorName",
                        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                        new ArrayList<>()),
                new ArrayList<>()
        );
        List<News> listOfNews = List.of(news, news1);
        given(newsRepository.readAll(1, 10, "id::asc")).willReturn(listOfNews);

        List<NewsDtoResponse> dtoResponseList = newsService.readAll(1, 10, "id::asc");

        //then
        assertThat(dtoResponseList).isEqualTo(listOfNews);
    }

    @Test
    @Disabled
    void readExistentNewsById() {
        //given
        given(newsRepository.existById(1L)).willReturn(true);
        given(newsRepository.readById(1L)).willReturn(Optional.of(news));

        //when
        NewsDtoResponse dtoResponse = newsService.readById(1L);

        //then
        News value = verify(newsRepository).readById(1L).get();
        assertThat(newsDtoMapper.modelToDto(value)).isEqualTo(dtoResponse);
    }

    @Test
    @Disabled
    void createValidNewsTest() {
        //given
        given(authorRepository.existById(1L)).willReturn(true);

        //when
        newsService.create(dtoRequest);

        //then
        ArgumentCaptor<News> argumentCaptor = ArgumentCaptor.forClass(News.class);
        verify(newsRepository).create(argumentCaptor.capture());
        News capturedValue = argumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(news);
    }

    @Test
    @Disabled
    void createNewsThrowsNotFoundExceptionTest() {
        //given
        given(authorRepository.existById(1L)).willReturn(false);
        //when

        newsRepository.create(news);
        //then
        assertThatThrownBy(() ->  newsService.create(dtoRequest)).isInstanceOf(NotFoundException.class)
                .hasMessage(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), 1L));
    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void patch() {
    }

    @Test
    @Disabled
    void deleteById() {
    }

    @Test
    @Disabled
    void readByParams() {
    }
}