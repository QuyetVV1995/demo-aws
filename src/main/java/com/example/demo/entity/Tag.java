package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tag")
@Table(name = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    public Tag(String name) {
        this.name = name;
    }

    // ------------------
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    List<Post> posts = new ArrayList<>();


}
