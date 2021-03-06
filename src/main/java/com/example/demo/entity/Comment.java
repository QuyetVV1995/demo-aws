package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime lastUpdate;

    @PrePersist //Trước khi lưu khi khởi tạo record
    public void prePersist() {
        lastUpdate = LocalDateTime.now();
    }
    @PreUpdate //Khi cập nhật record
    public void preUpdate() {
        lastUpdate = LocalDateTime.now();
    }

    public Comment(String content) {
        this.content = content;
    }

    // -----------------------
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; //Mỗi comment phải do một commenter viết

    public void setUser(User user) {
        user.getComments().add(this);
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post; //Mỗi comment phải gắn vào một post

}
