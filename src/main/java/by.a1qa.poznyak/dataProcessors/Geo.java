package by.a1qa.poznyak.dataProcessors;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Geo {
    public String lat;
    public String lng;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geo geo = (Geo) o;
        return Objects.equals(lat, geo.lat) && Objects.equals(lng, geo.lng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lng);
    }
}
