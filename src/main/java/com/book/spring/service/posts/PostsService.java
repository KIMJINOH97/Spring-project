package com.book.spring.service.posts;

import com.book.spring.controller.dto.PostsSaveRequestDto;
import com.book.spring.domain.posts.Posts;
import com.book.spring.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        Posts posts = requestDto.toEntity();
        return postsRepository.save(posts).getId();
    }

}
