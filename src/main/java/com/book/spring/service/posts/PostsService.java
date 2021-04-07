package com.book.spring.service.posts;

import com.book.spring.controller.dto.PostsListResponseDto;
import com.book.spring.controller.dto.PostsResponseDto;
import com.book.spring.controller.dto.PostsSaveRequestDto;
import com.book.spring.domain.posts.Posts;
import com.book.spring.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        Posts posts = requestDto.toEntity();
        return postsRepository.save(posts).getId();
    }


    public PostsResponseDto findById(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. ID = " + id));
        return new PostsResponseDto(posts);
    }

    @Transactional
    public Long update(Long id, PostsSaveRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. ID = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        List<Posts> postsList= postsRepository.findAllDesc();
        return postsList.stream().map(posts -> new PostsListResponseDto(posts)).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        postsRepository.deleteById(id);
    }
}
