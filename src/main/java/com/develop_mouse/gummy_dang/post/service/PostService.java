package com.develop_mouse.gummy_dang.post.service;

import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import com.develop_mouse.gummy_dang.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // 모든 게시글 조회
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // 게시글 조회 (id 이용)
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    // 저장
    public Post save(Post post){
        return postRepository.save(post);
    }

    // 게시글 삭제
    public void deleteById(Long id){
        postRepository.deleteById(id);
    }

}
