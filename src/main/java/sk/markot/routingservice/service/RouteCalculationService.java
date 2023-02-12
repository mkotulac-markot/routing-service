package sk.markot.routingservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import sk.markot.routingservice.data.Country;
import sk.markot.routingservice.data.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RouteCalculationService {

    private final CacheService cacheService;

      public Optional<Route> calculateRoute(String origin, String destination) {
          Country originCountry = cacheService.getCountry(origin);
          Country destinationCountry = cacheService.getCountry(destination);

          if (originCountry.isLandConnected(destinationCountry)) {
              Queue<CountryPath> queue = new LinkedList<>();
              queue.add(new CountryPath(originCountry, Collections.singletonList(origin)));
              return Optional.ofNullable(calculateRoute(queue, destinationCountry));
          } else {
              return Optional.empty();
          }
    }

    private Route calculateRoute(Queue<CountryPath> queue, Country destination) {
        Set<Country> visited = new HashSet<>();

        while (CollectionUtils.isNotEmpty(queue)) {
            CountryPath countryPath = queue.remove();
            visited.add(countryPath.country());

            for (String countryCode : countryPath.country().borders()) {
                Country neighbour = cacheService.getCountry(countryCode);

                List<String> neighbourPath = new ArrayList<>(countryPath.path());
                neighbourPath.add(neighbour.code());

                CountryPath neighbourCountryPath = new CountryPath(neighbour, neighbourPath);
                if (!visited.contains(neighbour) || !queue.contains(neighbourCountryPath)) {
                    if (destination.equals(neighbour)) {
                        return new Route(neighbourCountryPath.path());
                    }

                    queue.add(neighbourCountryPath);
                }
            }
        }

        return null;
    }

    private record CountryPath(Country country, List<String> path) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CountryPath that = (CountryPath) o;
            return country.equals(that.country);
        }

        @Override
        public int hashCode() {
            return Objects.hash(country);
        }
    }
}
