package by.a1qa.poznyak.dataProcessors;

import by.a1qa.poznyak.dataProcessors.Geo;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Address {
     public String street;
     public String suite;
     public  String city;
     public String zipcode;
     public Geo geo;


     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          Address address = (Address) o;
          return Objects.equals(street, address.street) && Objects.equals(suite, address.suite) &&
                  Objects.equals(city, address.city) && Objects.equals(zipcode, address.zipcode) &&
                  Objects.equals(geo, address.geo);
     }

     @Override
     public int hashCode() {
          return Objects.hash(street, suite, city, zipcode, geo);
     }
}
