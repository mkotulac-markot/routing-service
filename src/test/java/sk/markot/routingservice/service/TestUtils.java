package sk.markot.routingservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import sk.markot.routingservice.data.Country;
import sk.markot.routingservice.data.Region;

import java.util.List;

public class TestUtils {

    public static Cache createCache() {
        Cache cache = new ConcurrentMapCache("cache");
        cache.put("SVK", createCountry());

        return cache;
    }

    public static Country createCountry() {
        return Country.builder()
                .code("SVK")
                .region(Region.EUROPE)
                .borders(List.of("AUT", "CZE", "HUN", "POL", "UKR"))
                .build();
    }

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
