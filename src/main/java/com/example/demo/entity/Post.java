package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "post")
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Indexed //Thêm annotation này báo cho Hibernate Search đánh chỉ mục bảng này
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @FullTextField //Đánh chỉ mục full text
    private String title;

    @FullTextField //Đánh chỉ mục full text
    @Column(length=5000)
    private String content;

    private LocalDate create_at;

    public Post(String title, String content, Set<Tag> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    //-------
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
    //------
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;  //Tác giả viết post

    //------------
    //Quan hệ nhiều nhiều:
    //- Một post được phân loại bởi 1 hay nhiều tag.
    //- Ngược lại mỗi tag dùng để phân loại nhiều post

    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPosts().remove(this);
    }
}
