package by.a1qa.poznyak.dataProcessors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Comment {
    private String commentText;
    private int commentAuthorId;

    public Comment(String commentText, int commentAuthorId) {
        this.commentText = commentText;
        this.commentAuthorId = commentAuthorId;
    }
}
