# REST-Assured API Automation Framework

Enterprise-level REST API testing framework built with Java, REST-Assured, TestNG, and Maven.

## Project Structure

```
RestAssuredFramework/
├── pom.xml                                    # Maven configuration with all dependencies
├── src/
│   ├── main/
│   │   ├── java/com/restassured/
│   │   │   ├── config/
│   │   │   │   ├── ConfigReader.java         # Load environment properties
│   │   │   │   └── Configuration.java        # Constants and config values
│   │   │   ├── clients/
│   │   │   │   ├── AuthClient.java           # POST /auth endpoint wrapper
│   │   │   │   └── HealthClient.java         # GET /ping endpoint wrapper
│   │   │   ├── models/
│   │   │   │   ├── AuthRequest.java          # POJO for auth request
│   │   │   │   ├── AuthResponse.java         # POJO for auth response
│   │   │   │   └── HealthResponse.java       # POJO for health response
│   │   │   └── utils/
│   │   │       ├── RequestSpecificationBuilder.java  # Reusable request specs
│   │   │       ├── ResponseValidator.java            # Response validation
│   │   │       └── LoggerUtil.java                   # Logging utility
│   │   └── resources/
│   │       ├── application.properties        # Dev environment
│   │       ├── application-stage.properties  # Stage environment
│   │       ├── application-prod.properties   # Prod environment
│   │       └── log4j2.xml                    # Logging configuration
│   └── test/
│       ├── java/com/restassured/tests/
│       │   ├── AuthAPITest.java              # 5 auth test cases
│       │   └── HealthAPITest.java            # 1 timeout test case
│       └── resources/
│           └── testng.xml                    # TestNG suite configuration
```

## Dependencies

- **REST-Assured:** 5.3.1 - REST API testing
- **TestNG:** 7.8.1 - Test framework
- **Jackson:** 2.15.2 - JSON serialization/deserialization
- **Log4j2:** 2.20.0 - Logging
- **Allure:** 2.21.0 - Test reporting
- **Java:** 11+

## Test Cases

### Auth API Tests (5 cases)
1. **testValidAuthCredentials** - Valid username/password → 200 OK with token
2. **testInvalidUsername** - Invalid username → 401 Unauthorized
3. **testInvalidPassword** - Invalid password → 401 Unauthorized
4. **testMissingUsername** - Null username → 400 Bad Request
5. **testMissingPassword** - Null password → 400 Bad Request

### Health API Tests (1 case)
6. **testRequestTimeoutError** - 100ms timeout → SocketTimeoutException

## How to Run Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Suite
```bash
mvn clean test -Dtest=AuthAPITest
```

### Run with Specific Environment
```bash
mvn clean test -Denvironment=stage
mvn clean test -Denvironment=prod
```

### Generate Allure Report
```bash
mvn allure:report
```

## Configuration

### Environment Properties
Edit `application.properties` (dev), `application-stage.properties`, or `application-prod.properties`:
- `base.url` - API base URL
- `request.timeout` - Request timeout in ms
- `connection.timeout` - Connection timeout in ms
- `socket.timeout` - Socket timeout in ms

### Endpoints
- **Auth:** POST `/auth` - Login endpoint
- **Health:** GET `/ping` - Health check endpoint

## Framework Features

✅ Multi-environment support (dev/stage/prod)
✅ Reusable RequestSpecification for centralized setup
✅ POJO-based request/response handling
✅ Comprehensive error handling with try-catch blocks
✅ Response validation utilities
✅ Structured logging with Log4j2
✅ TestNG annotations (@Test, @BeforeClass, @AfterClass)
✅ Data isolation (no shared mutable state)
✅ CI/CD ready with testng.xml

## Build & Run

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Build
```bash
mvn clean install
```

### Run Tests
```bash
mvn clean test
```

### View Logs
- Console: Logs printed to console
- File: Logs written to `logs/test-execution.log`

## Key Classes

| Class | Purpose |
|-------|---------|
| `ConfigReader` | Load environment-specific properties |
| `Configuration` | Constants for URLs, timeouts, credentials |
| `AuthClient` | Wraps POST /auth endpoint |
| `HealthClient` | Wraps GET /ping endpoint |
| `RequestSpecificationBuilder` | Builds reusable request specifications |
| `ResponseValidator` | Validates response status and content |
| `AuthAPITest` | Auth endpoint test cases |
| `HealthAPITest` | Health endpoint test cases |

## Enterprise Standards Applied

- Separation of concerns (clients, models, utils, tests)
- Configuration management via properties files
- Reusable request/response specifications
- Comprehensive exception handling
- Structured logging
- TestNG framework with proper lifecycle
- CI/CD integration ready
- No hardcoded values or test data duplication
