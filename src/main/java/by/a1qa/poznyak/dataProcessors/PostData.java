package by.a1qa.poznyak.dataProcessors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
public class PostData {
    private int userId;
    private int postId;
    private String textFromPost;
    private int photoId;
    private int likesQuantity;
    private List<Comment> commentList;

}
