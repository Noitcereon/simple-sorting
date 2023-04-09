# Testing

There are two ways to test this project that I am aware of:

1. Manual testing, by launching the client and trying out features.
2. Automatic tests with JUnit.


## Manual testing
To perform manual testing, simply run the gradle task `runClient`.

1. Navigate to the root folder of the project with your CLI.
2. Run the following command:

    ```
    ./gradlew runClient
    ```
Alternatively you can do this via your IDE, if it supports Gradle.

## Automatic testing
Run the Gradle task `test`.

Same steps as above but with `test`.

1. Navigate to the root folder of the project with your CLI.
2. Run the following command:
    ```
    ./gradlew test
    ```

### What Makes JUnit Testing Possible

It is the following three things in grade.build that allows JUnit testing:

```Groovy
   test{
        useJUnitPlatform()
   }
	// Test Dependencies
	testImplementation 'org.junit.jupiter:junit-jupiter:5.8.1'
	testImplementation "net.fabricmc:fabric-loader-junit:${project.loader_version}"
```

Additionally, when you make a unit test class, you must add the following to use Minecraft functionality.
```Java
    @BeforeAll
    public static void setup(){
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
    }
```
