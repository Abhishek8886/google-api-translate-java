Provides a simple, unofficial, Java client API for using Google Translate.

**News**

Now supports Version 2 of the API - note that this requires using an API key, freely available from http://code.google.com/apis/language/translate/v2/getting_started.html

Although the binaries and issues for the project are maintained here, I've moved the source code over to github.

https://github.com/richmidwinter/google-api-translate-java

The JSON implementation has been split out. You can find it [here](http://mirrors.ibiblio.org/pub/mirrors/maven2/org/json/json/20090211/json-20090211.jar).

Alternatively, add the following to your maven pom.xml dependencies:
```
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20090211</version>
</dependency>
```

**Install**

Simply include the google-api-translate-java.jar file in your application's classpath.

**Quickstart**

This is our "Hello World" example:

```
import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class Main {
  public static void main(String[] args) throws Exception {
    // Set the HTTP referrer to your website address.
    GoogleAPI.setHttpReferrer(/* Enter the URL of your site here */);

    // Set the Google Translate API key
    // See: http://code.google.com/apis/language/translate/v2/getting_started.html
    GoogleAPI.setKey(/* Enter your API key here */);

    String translatedText = Translate.DEFAULT.execute("Bonjour le monde", Language.FRENCH, Language.ENGLISH);

    System.out.println(translatedText);
  }
}
```

Well done. You're now an expert in using this API!
The wiki has a more complete [example usage](http://code.google.com/p/google-api-translate-java/wiki/ExampleUsage) within a desktop application.

**Features**

Small library size - less than 50Kb.

Uses English as an intermediate language to attempt translations between language pairings that are unsupported directly by Google Translate.

The jar is runnable and has a very lightweight GUI to demonstrate translation functionality.

Now available via a [Maven plugin](http://code.google.com/p/google-api-translate-java-maven-plugin/).

**Problems / Suggestions?**

Please do [report an issue](http://code.google.com/p/google-api-translate-java/issues/entry) if you find any bugs or have any feature requests.