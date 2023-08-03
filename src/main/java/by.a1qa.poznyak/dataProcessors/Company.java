package by.a1qa.poznyak.dataProcessors;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
public class Company {
    public String name;
    public  String catchPhrase;
    public String bs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return name.equals(company.name) && catchPhrase.equals(company.catchPhrase) && bs.equals(company.bs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, catchPhrase, bs);
    }
}
