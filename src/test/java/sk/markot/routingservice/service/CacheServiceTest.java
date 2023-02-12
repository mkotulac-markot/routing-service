package sk.markot.routingservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import sk.markot.routingservice.data.Country;
import sk.markot.routingservice.exception.CacheNotFoundException;
import sk.markot.routingservice.exception.CountryNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static sk.markot.routingservice.data.Region.EUROPE;
import static sk.markot.routingservice.service.TestUtils.createCache;

class CacheServiceTest {

    JsonService jsonService = mock(JsonService.class);
    CacheManager cacheManager = mock(CacheManager.class);
    CacheService service = new CacheService(jsonService, cacheManager);

    @Test
    void getCountry_cacheNotFound() {
        when(cacheManager.getCache(anyString())).thenReturn(null);

        assertThatExceptionOfType(CacheNotFoundException.class)
                .isThrownBy(() -> service.getCountry("SVK"));
    }

    @Test
    void getCountry_notFound() {
        when(cacheManager.getCache(anyString())).thenReturn(createCache());

        assertThatExceptionOfType(CountryNotFoundException.class)
                .isThrownBy(() -> service.getCountry("HUN"));
    }

    @Test
    void getCountry() {
        when(cacheManager.getCache(anyString())).thenReturn(createCache());

        Country country = service.getCountry("SVK");

        verify(cacheManager, times(1)).getCache("countries");

        assertThat(country).isNotNull();
        assertThat(country.code()).isEqualTo("SVK");
        assertThat(country.region()).isEqualTo(EUROPE);
        assertThat(country.borders()).containsExactly("AUT", "CZE", "HUN", "POL", "UKR");
    }
}