package ch.hfict.blog.model;

public class CommentDto {
    private final String comment;
    private final Long userId;

    public CommentDto(String comment, Long userId) {
        this.comment = comment;
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public Long getUserId() {
        return userId;
    }
}
