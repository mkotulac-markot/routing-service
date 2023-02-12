package sk.markot.routingservice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import sk.markot.routingservice.data.Countries;
import sk.markot.routingservice.data.Country;
import sk.markot.routingservice.exception.CacheNotFoundException;
import sk.markot.routingservice.exception.CountryNotFoundException;

import java.util.Optional;

import static sk.markot.routingservice.config.RoutingServiceConfig.CACHE_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheService {

    private final JsonService jsonService;
    private final CacheManager cacheManager;

    public Country getCountry(String code) {
        return Optional.ofNullable(getCache().get(code, Country.class))
                .orElseThrow(() -> new CountryNotFoundException(code));
    }

    @PostConstruct
    private void init() {
        Countries countries = loadCountries();
        putToCache(countries);
    }

    private Countries loadCountries() {
        return new Countries(jsonService.loadData());
    }

    private void putToCache(Countries countries) {
        log.info("Putting data to cache");
        for (Country country : countries.getCountries()) {
            getCache().put(country.code(), country);
        }

    }

    private Cache getCache() {
        return Optional.ofNullable(cacheManager.getCache(CACHE_ID))
                .orElseThrow(() -> new CacheNotFoundException(CACHE_ID));
    }
}
