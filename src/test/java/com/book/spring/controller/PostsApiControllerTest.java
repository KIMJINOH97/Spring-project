package com.book.spring.controller;

import com.book.spring.controller.dto.PostsSaveRequestDto;
import com.book.spring.controller.dto.PostsUpdateDto;
import com.book.spring.domain.posts.Posts;
import com.book.spring.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void clean(){
        postsRepository.deleteAll();
    }

    @Test
    public void Posts() throws Exception{
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";
        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        //System.out.println(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        Posts posts = all.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void findById() throws Exception{
        // given

        String title1 = "title";
        String content1 = "content";
        Posts posts1 = Posts.builder()
                .title(title1)
                .content(content1)
                .author("author")
                .build();

        String title2 = "hi my name";
        String content2 = "is jinoh";
        Posts posts2 = Posts.builder()
                .title(title2)
                .content(content2)
                .author("author")
                .build();

        // when
        postsRepository.save(posts1);
        postsRepository.save(posts2);


        // then
        Posts findPosts1 = postsRepository.findById(1L).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 아이디 입니다."));

        Posts findPosts2 = postsRepository.findById(2L).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 아이디 입니다."));

        assertThat(findPosts1.getTitle()).isEqualTo(title1);
        assertThat(findPosts1.getContent()).isEqualTo(content1);

        assertThat(findPosts2.getTitle()).isEqualTo(title2);
        assertThat(findPosts2.getContent()).isEqualTo(content2);
    }

    @Test
    public void update() throws Exception{
        // given
        String title = "UPDATE";
        String content = "JUST DO IT";

        Posts posts = Posts.builder().
                title(title)
                .content(content)
                .author("author")
                .build();

        Posts savePosts = postsRepository.save(posts);

        Long updateId = savePosts.getId();
        String change_title = "NIKE";
        String change_content = "JUST DO";

        PostsUpdateDto requestDto = PostsUpdateDto.builder()
                .title(change_title)
                .content(change_content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Posts updatePosts = postsRepository.findById(updateId).orElseThrow(()->
                new IllegalArgumentException("존재하지 않는 아이디 입니다."));
//        List<Posts> all = postsRepository.findAll();
//        Posts updatePosts = all.get(0);

        assertThat(updatePosts.getTitle()).isEqualTo(change_title);
        assertThat(updatePosts.getContent()).isEqualTo(change_content);
    }

    @Test
    public void time() {
        // given

        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title").content("content").author("author").build());

        // when

        List<Posts> all = postsRepository.findAll();

        // then
        Posts posts = all.get(0);
        System.out.println(">>> create : " + posts.getCreatedDate() + ", >>>modified : "+ posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}