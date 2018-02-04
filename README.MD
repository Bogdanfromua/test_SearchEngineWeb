Build:
```
./gradlew build
```

Run:
```
java -Dserver.port=8080 -DsearchEngineURL=http://localhost:8888 -jar ./build/libs/searchEngineWeb-1.0-SNAPSHOT.war
```

Run with debug port:
```
java -Dserver.port=8080 -DsearchEngineURL=http://localhost:8888 -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5007,suspend=n -jar ./build/libs/searchEngineWeb-1.0-SNAPSHOT.war

```