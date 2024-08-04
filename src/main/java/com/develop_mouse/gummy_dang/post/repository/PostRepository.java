package com.develop_mouse.gummy_dang.post.repository;

import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long>{

}

