package ch.hfict.blog.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String comment;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Comment(String comment, Post post, User user) {
        this.comment = comment;
        this.post = post;
        this.user = user;
    }

    public Comment() {

    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
