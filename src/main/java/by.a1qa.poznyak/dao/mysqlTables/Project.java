package by.a1qa.poznyak.dao.mysqlTables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private Long id;
    private String name;

    public Project(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
