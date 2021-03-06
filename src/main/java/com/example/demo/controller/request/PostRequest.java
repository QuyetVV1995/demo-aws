package com.example.demo.controller.request;

import com.example.demo.entity.Tag;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class PostRequest {
    private long id;
    private String title;
    private String content;
    private Set<Tag> tags;
    private MultipartFile[] file;

    public PostRequest(Long id, String title, String content, Set<Tag> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public PostRequest() {

    }
}
