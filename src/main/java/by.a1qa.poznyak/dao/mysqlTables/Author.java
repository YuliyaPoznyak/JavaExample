package by.a1qa.poznyak.dao.mysqlTables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Author implements Serializable {
    private Long id;
    private String name;
    private String login;
    private String email;



    public Author(String name, String login, String email) {
        this.name = name;
        this.login = login;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
