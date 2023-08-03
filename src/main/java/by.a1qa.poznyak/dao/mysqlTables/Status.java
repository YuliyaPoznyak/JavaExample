package by.a1qa.poznyak.dao.mysqlTables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    private Long id;
    private String name;

    public Status(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
