# FeatureForge Java SDK

A lightweight Java SDK for integrating Java applications with the FeatureForge feature evaluation API.

The SDK provides a simple `isEnabled()` method while handling HTTP communication, API-key authentication, JSON serialization/deserialization, and API errors internally.

## Requirements

- Java 21+
- Maven
- A FeatureForge environment API key
- Access to a running FeatureForge backend

## Download

Download the latest `featureforge-sdk` JAR from the **GitHub Releases** section.

## Installation

After downloading the JAR, install it into your local Maven repository:

```bash 
mvn install:install-file -Dfile=featureforge-sdk-1.0.0.jar -DgroupId=com.featureforge -DartifactId=featureforge-sdk -Dversion=1.0.0 -Dpackaging=jar
```

Then add the SDK as a dependency in your project's pom.xml:
```xml
<dependency>
    <groupId>com.featureforge</groupId>
    <artifactId>featureforge-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage
Create a FeatureForgeClient using your FeatureForge API key and backend URL:

```java
import com.featureforge.sdk.FeatureForgeClient;

public class Example {

    public static void main(String[] args) {

        FeatureForgeClient client =
                new FeatureForgeClient(
                        "YOUR_API_KEY",
                        "http://localhost:8080"
                );

        boolean enabled =
                client.isEnabled(
                        "INDEPENDENCE_DAY_HERO",
                        "user123"
                );

        System.out.println("Feature enabled: " + enabled);
    }
}
```

The SDK internally calls:  

```POST /api/v1/evaluate```  

and sends the API key through the X-API-Key header.

## Error Handling
If feature evaluation cannot be completed, the SDK throws FeatureForgeException.

```java
import com.featureforge.sdk.FeatureForgeClient;
import com.featureforge.sdk.exception.FeatureForgeException;

try {

    boolean enabled =
            client.isEnabled(
                    "INDEPENDENCE_DAY_HERO",
                    "user123"
            );

} catch (FeatureForgeException e) {

    System.out.println("Evaluation failed.");
    System.out.println("Status: " + e.getStatusCode());
    System.out.println("Message: " + e.getMessage());
}

```

A ```false``` result is a valid feature evaluation result and does not represent an error.

## What the SDK Handles
The SDK handles:

- Feature evaluation requests
- API-key authentication
- HTTP communication
- JSON serialization and deserialization
- HTTP error handling
- FeatureForge API error messages

The SDK does not contain the feature rollout logic. Feature evaluation remains the responsibility of the FeatureForge backend.

## Building the SDK

To build the JAR locally:
```bash
mvn clean package
```

The generated JAR will be available in:
```declarative
target/featureforge-sdk-1.0.0.jar
```

## Project Coordinates
```declarative
Group ID:    com.featureforge
Artifact ID: featureforge-sdk
Version:     1.0.0
```

## License
This project is part of FeatureForge.
