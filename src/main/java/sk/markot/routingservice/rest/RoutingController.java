package sk.markot.routingservice.rest;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.markot.routingservice.data.Route;
import sk.markot.routingservice.exception.NoLandCrossingException;
import sk.markot.routingservice.service.RouteCalculationService;

import java.util.Optional;

@RestController
@RequestMapping("/routing")
@Validated
@RequiredArgsConstructor
public class RoutingController {

    private final RouteCalculationService routeCalculationService;

    @GetMapping("/{origin}/{destination}")
    public Route calculateRoute(@PathVariable @NotBlank String origin,
                                @PathVariable @NotBlank String destination) {

        Optional<Route> route = routeCalculationService.calculateRoute(origin, destination);

        if (route.isEmpty()) {
            throw new NoLandCrossingException();
        }

        return route.get();
    }
}
