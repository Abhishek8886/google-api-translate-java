## From the command line ##

You can specify proxy settings at the command line by passing extra parameters, eg:

```
java -Dhttp.proxyHost=proxy -Dhttp.proxyPort=8080 -jar google-api-translate-java-0.7.jar
```


## Within your program ##

Just set the appropriate system properties:

```
System.setProperty("http.proxyHost", "proxy");
System.setProperty("http.proxyPort", "8080");
```