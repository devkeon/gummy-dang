package com.develop_mouse.gummy_dang.comment.repository;

import com.develop_mouse.gummy_dang.comment.domain.entity.Comment;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByPost(Post post);
}

