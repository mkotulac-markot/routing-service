package sk.markot.routingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import sk.markot.routingservice.data.Country;
import sk.markot.routingservice.exception.DataLoadingException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
@Slf4j
public class JsonService {

    private final ObjectMapper objectMapper;
    private final String remoteFile;
    private final String localFile;

    public JsonService(ObjectMapper objectMapper,
                       @Value("${data.source.remote}") String remoteFile,
                       @Value("${data.source.local}") String localFile) {
        this.objectMapper = objectMapper;
        this.remoteFile = remoteFile;
        this.localFile = localFile;
    }

    public Country[] loadData() {
        try {
            return loadRemoteDataSource();
        } catch (IOException e) {
            log.warn("Cannot read remote data source");
            return loadLocalDataSource(); //fallback
        }
    }

    private Country[] loadLocalDataSource() {
        try {
            log.info("Loading data from local data source");
            File file = ResourceUtils.getFile(localFile);
            return objectMapper.readValue(file, Country[].class);
        } catch (IOException | IllegalArgumentException e) {
            log.error("Cannot read local data source", e);
            throw new DataLoadingException();
        }
    }

    private Country[] loadRemoteDataSource() throws IOException {
        log.info("Loading data from remote data source");
        URL remoteFileUrl = new URL(remoteFile);
        return objectMapper.readValue(remoteFileUrl, Country[].class);
    }

}
