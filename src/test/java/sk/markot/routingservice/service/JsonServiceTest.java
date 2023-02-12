package sk.markot.routingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import sk.markot.routingservice.data.Country;
import sk.markot.routingservice.exception.DataLoadingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static sk.markot.routingservice.data.Region.EUROPE;
import static sk.markot.routingservice.service.TestUtils.createObjectMapper;

class JsonServiceTest {

    ObjectMapper objectMapper = createObjectMapper();

    @Test
    void loadData() {
        JsonService service = new JsonService(objectMapper, null, "src/test/resources/countries.json");

        Country[] countries = service.loadData();

        assertThat(countries)
                .isNotEmpty()
                .hasSize(1);
        assertThat(countries[0].code()).isEqualTo("SVK");
        assertThat(countries[0].region()).isEqualTo(EUROPE);
        assertThat(countries[0].borders()).containsExactly("AUT", "CZE", "HUN", "POL", "UKR");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "invalid-file-location")
    void loadData_invalidDataSource(String localFile) {
        JsonService service = new JsonService(objectMapper, "invalid-url", localFile);

        assertThatExceptionOfType(DataLoadingException.class)
                .isThrownBy(service::loadData);
    }
}