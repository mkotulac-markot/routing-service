### Technical requirement
    Installed Java 17 

### Start service
- **Windows**
    - via file [START](routing-service-start.cmd) (build + run)
    - alternatively via command ```mvnw spring-boot:run``` (run)
- **Linux/macOS**
  - via file [START](routing-service-start.sh) (build + run)
  - alternatively via command ```./mvnw spring-boot:run```

### Test
- command ```curl --request GET --url http://localhost:8080/routing/CZE/ITA```

### Console output should be JSON:
 ```{"route":["CZE","AUT","ITA"]}```
    
### Used algorithm
Iterative Depth-First Search with Path Memorization </br>
[source](https://www.baeldung.com/cs/dfs-vs-bfs-vs-dijkstra#5-memorizing-the-path-while-searching)

