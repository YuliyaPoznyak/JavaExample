package by.a1qa.poznyak.dataProcessors;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
public class Post {
    public int userId;
    public int id;
    public String title;
    public String body;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return userId == post.userId && id == post.id && Objects.equals(title, post.title) && Objects.equals(body, post.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, id, title, body);
    }
}
