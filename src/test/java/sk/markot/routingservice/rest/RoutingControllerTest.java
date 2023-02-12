package sk.markot.routingservice.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sk.markot.routingservice.data.Route;
import sk.markot.routingservice.service.RouteCalculationService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoutingController.class)
public class RoutingControllerTest {

    @MockBean
    RouteCalculationService routeCalculationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void calculateRoute_routeNotFound() throws Exception {
        when(routeCalculationService.calculateRoute(anyString(), anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/routing/SVK/ARG"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateRoute() throws Exception {
        when(routeCalculationService.calculateRoute(anyString(), anyString()))
                .thenReturn(Optional.of(new Route(List.of("CZE", "AUT", "ITA"))));

        mockMvc.perform(get("/routing/CZE/ITA"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.route.[*]", containsInRelativeOrder("CZE", "AUT", "ITA")));
    }
}
