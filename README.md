# Selenium and RestAssured Testing

This project contains automated UI and API tests using Selenium, RestAssured, and TestNG. It allows running UI tests on different browser screen resolutions and API tests against the Star Wars API.

## Prerequisites

- Java Development Kit (JDK)
- Maven
- A web browser (Chrome, Firefox, Edge)

## Dependencies

The project uses the following dependencies:

- Selenium
- WebDriverManager
- TestNG
- RestAssured
- Log4j

These dependencies are managed using Maven. Refer to the `pom.xml` file for details.

## Setup

1. **Clone the repository**:

   ```sh
   git clone git@github.com:pvarenik/java-selenium-restassured-testing.git
   cd java-selenium-restassured-testing
   ```

2. **Install dependencies**:

   Maven will automatically download the necessary dependencies when you run the tests.

## Running Tests

You can run either UI tests or API tests based on the provided maven profile (`-PUI` or `-PAPI`).

### Running UI Tests

To run UI tests, use the following command:

```sh
mvn clean test -PUI -DbaseUrl=https://www.xm.com
```

### Running API Tests

To run API tests, use the following command:

```sh
mvn clean test -PAPI -DbaseURI=https://swapi.dev/api
```

## Configuration

### Environment Parameters

- `baseUrl`: The base URL for UI tests (default: `https://www.xm.com`).
- `baseURI`: The base URI for API tests (default: `https://swapi.dev/api`).

These parameters can be passed as system properties when running the tests.

### Tests

- **UITest.java**: Contains the UI tests using Selenium and TestNG.
- **APITest.java**: Contains the API tests using RestAssured and TestNG.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.