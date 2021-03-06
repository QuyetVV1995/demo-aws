package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;

import java.util.Optional;

public interface CommentService {
    public Optional<Comment> findById(long id);
    public void deleteById(long id);
}
