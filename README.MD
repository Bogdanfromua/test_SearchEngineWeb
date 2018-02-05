### Build:
```
./gradlew build
```

### Run:

possible options:
- -Dusername=user          - basicAuth user name
- -Duserpassword=password  - basicAuth user password
- -DsearchEngineURL=http://localhost:8888 - url for access searchEngineCore service
- -Dserver.port=8080 - port which should be listened by current service
```
java -Dserver.port=8080 -DsearchEngineURL=http://localhost:8888 -Dusername=user -Duserpassword=password -jar ./build/libs/searchEngineWeb-1.0-SNAPSHOT.war
```


### Run with debug port:
```
java -Dserver.port=8080 -DsearchEngineURL=http://localhost:8888 -Dusername=user -Duserpassword=password -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5007,suspend=n -jar ./build/libs/searchEngineWeb-1.0-SNAPSHOT.war

```

### Use:
```
http://localhost:8080
```
- port - same port as provided by -Dserver.port option 
- login with user and password provided by corresponding service run options ("user" and "password")
