package sk.markot.routingservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.markot.routingservice.data.Route;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RouteCalculationServiceTest {

    @Autowired
    RouteCalculationService service;

    @Test
    void calculateRoute_noLandConnectionCountries() {
        String origin = "SVK";
        String destination = "ARG";

        Optional<Route> route = service.calculateRoute(origin, destination);

        assertThat(route).isEmpty();
    }

    @Test
    void calculateRoute() {
        String origin = "SVK";
        String destination = "TUR";

        Optional<Route> route = service.calculateRoute(origin, destination);

        assertThat(route).isNotEmpty();
        assertThat(route.get().route()).containsExactly("SVK", "HUN", "ROU", "BGR", "TUR");
    }
}