package sk.markot.routingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CacheNotFoundException extends RuntimeException {
    public CacheNotFoundException(String cacheId) {
        super("Cache with id: " + cacheId + " not found");
    }
}
