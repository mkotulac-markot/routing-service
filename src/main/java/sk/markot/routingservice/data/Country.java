package sk.markot.routingservice.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
import java.util.Objects;

public record Country(String code, Region region, List<String> borders) {

    @JsonCreator
    @Builder
    public Country(@JsonProperty("cca3") String code,
                   @JsonProperty("region") Region region,
                   @JsonProperty("borders") List<String> borders) {
        this.code = code;
        this.region = region;
        this.borders = borders;
    }

    public boolean isLandConnected(Country destination) {
        return this.region.isLandConnected(destination.region());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(code, country.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
