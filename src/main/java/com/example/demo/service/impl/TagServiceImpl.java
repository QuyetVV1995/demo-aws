package com.example.demo.service.impl;

import com.example.demo.entity.Tag;
import com.example.demo.repository.TagRepository;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public List<Tag> findAll(){
        return tagRepository.findAll();
    }
}
