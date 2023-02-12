package sk.markot.routingservice.data;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@ToString
public class Countries {

    private final List<Country> countries = new ArrayList<>();

    public Countries(Country[] countries) {
        this.countries.addAll(Set.of(countries));
    }
}
