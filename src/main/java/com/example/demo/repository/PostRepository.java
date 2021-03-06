package com.example.demo.repository;

import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM post AS p WHERE p.user_id = :user_id", nativeQuery = true)
    List<Post> findByUserId(@Param("user_id") long user_id);

    Optional<Post> findById(Long id);

    @Query("SELECT p AS user_id FROM post AS p WHERE p.id = :id")
    Optional<Post> findPostWithUserById(@Param("id") long id);

    /*
    * @Param:
    *   - n : N1, N2, N3, N5
    * */
    @Query(value = "SELECT * FROM post INNER JOIN post_tag ON post_tag.post_id = :n where post.id = post_tag.post_id and post_tag.tag_id = :category", nativeQuery = true)
    List<Post> getAllPostsByCategory(@Param("n") long n, @Param("category") long category);


    /*
     * @Param:
     *   - tag_id : 9, 10, 11, 12 ,13 - tu vung, kanji, ngu phap, doc hieu, de thi
     * */
    @Query(value = "SELECT * FROM post, post_tag, tag WHERE tag.id = :tag_id AND post_tag.tag_id = tag.id AND post_tag.post_id = post.id;", nativeQuery = true)
    List<Post> getAllPostCategoryByTagId(@Param("tag_id") long tag_id);

    /*
     * @Param:
     *   - tag_id : 1,2,3,4,5,6,7,8 - N1, N2, N3, N4,N5, it japan, java basic, spring boot
     * */
    @Query(value = "SELECT * FROM post, post_tag, tag WHERE tag.id = :tag_id AND post_tag.tag_id = tag.id AND post_tag.post_id = post.id;", nativeQuery = true)
    List<Post> totalPostOfCategory(@Param("tag_id") long tag_id);
}
