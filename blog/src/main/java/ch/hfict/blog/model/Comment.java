package ch.hfict.blog.model;

import javax.persistence.*;

@Entity
public class Comment {

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
