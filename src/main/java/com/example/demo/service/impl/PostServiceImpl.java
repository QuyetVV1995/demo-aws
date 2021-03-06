package com.example.demo.service.impl;

import com.example.demo.controller.request.CommentRequest;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.exception.PostException;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PostService;
import org.hibernate.CacheMode;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagRepository tagRepository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    @Override
    public void addComment(CommentRequest commentRequest, Long user_id) throws PostException {
        Optional<Post> optionalPost = postRepository.findById(commentRequest.getPost_id());
        Optional<User> optionalUser =userRepository.findById(user_id);
        if(optionalPost.isPresent() && optionalUser.isPresent()){
            Post post = optionalPost.get();

            Comment comment = new Comment();
            comment.setContent(commentRequest.getContent());
            comment.setUser(optionalUser.get());
            comment.setPost(post);
            commentRepository.save(comment);
            post.addComment(comment);
            postRepository.saveAndFlush(post);
        }else{
            throw new PostException("Post or User is missing");
        }
    }

    @Override
    public List<Post> searchPost(String terms, int limit, int offset) {
        return Search.session(em).search(Post.class).where(f -> f.match().fields("title", "content").
                matching(terms)).fetchHits(offset, limit);
    }

    @Override
    public void reindexFullText() {
        SearchSession searchSession = Search.session(em);

        MassIndexer indexer = searchSession.massIndexer(Post.class).dropAndCreateSchemaOnStart(true)
                .typesToIndexInParallel( 2 )
                .batchSizeToLoadObjects(10)
                .idFetchSize(200)
                .threadsToLoadObjects(5)
                .cacheMode(CacheMode.IGNORE);
        indexer.start();
    }

    @Override
    public Page<Post> findAllPaging(int page, int pageSize) {
        return postRepository.findAll(PageRequest.of(page, pageSize)); // Bổ xung pagination vào đây !
    }

    @Override
    public List<Post> getAllPostsByUserID(long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public List<Post> getAllPostByTagId(String category, long tag_id){
        List<Post> postList = new ArrayList<>();
        List<Post> postResult = new ArrayList<>();
        switch (category){
            case "N1":     // N1
                postList = postRepository.getAllPostCategoryByTagId(tag_id);
                postResult = removeElementDuplicateWithTagId(postList, 1);
                break;
            case "N2":     // N2
                postList = postRepository.getAllPostCategoryByTagId(tag_id);
                postResult = removeElementDuplicateWithTagId(postList, 2);
                break;
            case "N3":     // N3
                postList = postRepository.getAllPostCategoryByTagId(tag_id);
                postResult = removeElementDuplicateWithTagId(postList, 3);
                break;
            case "N4":     // N4
                postList = postRepository.getAllPostCategoryByTagId(tag_id);
                postResult = removeElementDuplicateWithTagId(postList, 4);
                break;
            case "N5":     // N5
                postList = postRepository.getAllPostCategoryByTagId(tag_id);;
                postResult = removeElementDuplicateWithTagId(postList, 5);
                break;
            case "it-japanese":     // IT Japanese
            case "java-basic":     // Java Basic
            case "spring-boot":     // Spring boot
                postResult = postRepository.getAllPostCategoryByTagId(tag_id);
                break;
            default:
                break;
        }

        return postResult;
    }

    @Override
    public List<Post> totalPostofCategory(String category) {
        List<Post> postList = new ArrayList<>();
        switch (category) {
            case "N1":     // N1
                postList = postRepository.totalPostOfCategory(1);
                postList = removeElementDuplicate(postList);
                break;
            case "N2":     // N2
                postList = postRepository.totalPostOfCategory(2);
                postList = removeElementDuplicate(postList);
                break;
            case "N3":     // N3
                postList = postRepository.totalPostOfCategory(3);
                postList = removeElementDuplicate(postList);
                break;
            case "N4":     // N4
                postList = postRepository.totalPostOfCategory(4);
                postList = removeElementDuplicate(postList);
                break;
            case "N5":     // N5
                postList = postRepository.totalPostOfCategory(5);
                postList = removeElementDuplicate(postList);
                break;
            case "it-japanese":
                postList = postRepository.totalPostOfCategory(6);
                break;
            case "java-basic":
                postList = postRepository.totalPostOfCategory(7);
                break;
            case "spring-boot":
                postList = postRepository.totalPostOfCategory(8);
                break;


        }
        return postList;
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deleteByID(long id) {
        postRepository.deleteById(id);
    }

    public List<Post> removeElementDuplicateWithTagId(List<Post> posts, long tag_id){
        Tag tag = tagRepository.findById(tag_id).get();
        List<Post> post = new ArrayList<>();
        for (Post post_element: posts){
            if(!post.contains(post_element)){
                for(Tag tag_element:post_element.getTags()){
                    if(tag == tag_element){
                        post.add(post_element);
                    }
                }
            }
        }
        return post;
    }

    public List<Post> removeElementDuplicate(List<Post> posts){
        List<Post> post = new ArrayList<>();
        for (Post post_element: posts){
            if(!post.contains(post_element)){
                    post.add(post_element);
                }
            }
        return post;
    }

}
