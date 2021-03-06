package com.example.demo.service;

import com.example.demo.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    public Optional<Tag> findById(Long id);

    public List<Tag> findAll();
}
