package by.a1qa.poznyak.dataProcessors;

import by.a1qa.poznyak.dataProcessors.Address;
import by.a1qa.poznyak.dataProcessors.Company;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class User {
    public int id;
    public String name;
    public String username;
    public String email;
    public Address address;
    public String phone;
    public String website;
    public Company company;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) && Objects.equals(address, user.address) &&
                Objects.equals(phone, user.phone) && Objects.equals(website, user.website) &&
                Objects.equals(company, user.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email, address, phone, website, company);
    }
}
