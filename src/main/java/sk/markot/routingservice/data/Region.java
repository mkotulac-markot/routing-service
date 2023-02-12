package sk.markot.routingservice.data;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;

@RequiredArgsConstructor
public enum Region {
    ASIA("Asia"),
    EUROPE("Europe"),
    AFRICA("Africa"),
    AMERICAS("Americas"),
    OCEANIA("Oceania"),
    ANTARCTIC("Antarctic");

    private final String value;

    private static final EnumSet<Region> LAND_CONNECTED = EnumSet.of(ASIA, EUROPE, AFRICA);

    @JsonValue
    public String getValue() {
        return value;
    }

    public boolean isLandConnected(Region region) {
        return region == this || (LAND_CONNECTED.contains(region) && LAND_CONNECTED.contains(this));
    }
}
