# REST-Assured API Framework - Architecture Diagrams

## 1. Layered Architecture

```mermaid
graph TB
    subgraph Test Layer
        TC["Test Classes<br/>AuthAPITest<br/>HealthAPITest"]
    end
    
    subgraph Client Layer
        AC["AuthClient<br/>authenticate()"]
        HC["HealthClient<br/>ping()"]
    end
    
    subgraph Utility Layer
        RSB["RequestSpecificationBuilder<br/>buildRequestSpec()"]
        RV["ResponseValidator<br/>validateStatusCode()"]
        LU["LoggerUtil<br/>info/error/debug()"]
    end
    
    subgraph Model Layer
        AR["AuthRequest<br/>username, password"]
        ARS["AuthResponse<br/>token, reason"]
        HR["HealthResponse<br/>status"]
    end
    
    subgraph Configuration Layer
        CR["ConfigReader<br/>loadProperties()"]
        CFG["Configuration<br/>BASE_URL, TIMEOUT"]
    end
    
    subgraph External Resources
        PROPS["application.properties<br/>application-stage.properties<br/>application-prod.properties"]
        LOG["log4j2.xml<br/>Logging Configuration"]
    end
    
    subgraph REST-Assured Core
        RA["REST-Assured<br/>RestAssured.given().post()"]
    end
    
    subgraph External API
        API["Restful-Booker API<br/>https://restful-booker.herokuapp.com"]
    end
    
    TC -->|calls| AC
    TC -->|calls| HC
    AC -->|uses| RSB
    AC -->|uses| LU
    HC -->|uses| RSB
    HC -->|uses| LU
    AC -->|uses| AR
    AC -->|returns| ARS
    HC -->|returns| HR
    RSB -->|reads| CFG
    CR -->|reads| PROPS
    CFG -->|uses| CR
    AC -->|uses| RA
    HC -->|uses| RA
    RA -->|calls| API
    LU -->|writes to| LOG
    RV -->|validates| ARS
    RV -->|validates| HR
```

## 2. Component Interaction Flow

```mermaid
sequenceDiagram
    participant Test as AuthAPITest
    participant AC as AuthClient
    participant RSB as RequestSpecBuilder
    participant CFG as Configuration
    participant RA as REST-Assured
    participant API as Restful-Booker API
    participant Logger as LoggerUtil
    participant RV as ResponseValidator
    
    Test->>AC: authenticate(authRequest)
    AC->>Logger: info("Sending POST request")
    AC->>RSB: buildRequestSpec()
    RSB->>CFG: getBaseUrl()
    CFG-->>RSB: https://restful-booker.herokuapp.com
    RSB-->>AC: RequestSpecification
    AC->>RA: given(spec).body(request).post(/auth)
    RA->>API: POST /auth
    API-->>RA: Response{status:200, token:abc123}
    RA-->>AC: Response
    AC->>Logger: info("Response Status: 200")
    AC-->>Test: Response
    Test->>RV: validateStatusCode(response, 200)
    RV-->>Test: Assertion Passed
```

## 3. Multi-Environment Configuration Flow

```mermaid
graph LR
    Test["Test Execution<br/>mvn clean test<br/>-Denvironment=stage"]
    ConfigReader["ConfigReader.java<br/>loadProperties()"]
    
    subgraph "Environment Properties"
        Dev["application.properties<br/>base.url: localhost"]
        Stage["application-stage.properties<br/>base.url: stage-api"]
        Prod["application-prod.properties<br/>base.url: prod-api"]
    end
    
    Configuration["Configuration.java<br/>BASE_URL constant"]
    Clients["AuthClient<br/>HealthClient"]
    API["API Endpoints"]
    
    Test -->|"System.getProperty<br/>environment"| ConfigReader
    ConfigReader -->|"Load selected"| Stage
    Stage -->|"Parse properties"| Configuration
    Configuration -->|"Inject BASE_URL"| Clients
    Clients -->|"API calls"| API
```

## 4. Test Case Structure

```mermaid
graph TB
    TC["Test Suite<br/>testng.xml"]
    
    subgraph "Auth API Tests"
        TC1["✓ testValidAuthCredentials<br/>Expected: 200 OK"]
        TC2["✗ testInvalidUsername<br/>Expected: 401 Unauthorized"]
        TC3["✗ testInvalidPassword<br/>Expected: 401 Unauthorized"]
        TC4["✗ testMissingUsername<br/>Expected: 400 Bad Request"]
        TC5["✗ testMissingPassword<br/>Expected: 400 Bad Request"]
    end
    
    subgraph "Health API Tests"
        TC6["⏱ testRequestTimeoutError<br/>Expected: SocketTimeout"]
    end
    
    TC -->|includes| TC1
    TC -->|includes| TC2
    TC -->|includes| TC3
    TC -->|includes| TC4
    TC -->|includes| TC5
    TC -->|includes| TC6
    
    TC1 -->|calls| AuthC["AuthClient.authenticate<br/>valid credentials"]
    TC2 -->|calls| AuthC
    TC3 -->|calls| AuthC
    TC4 -->|calls| AuthC
    TC5 -->|calls| AuthC
    TC6 -->|calls| HealthC["HealthClient.pingWithTimeout<br/>100ms timeout"]
    
    AuthC -->|validates| RV["ResponseValidator<br/>Status Code & Payload"]
    HealthC -->|validates| RV
```

## 5. Data Flow - Valid Authentication

```mermaid
graph LR
    A["AuthRequest<br/>{<br/>username: 'admin'<br/>password: 'password123'<br/>}"]
    
    B["POST /auth<br/>Content-Type: application/json<br/>Authorization header"]
    
    C["API Processing<br/>Validate credentials<br/>Generate token"]
    
    D["AuthResponse<br/>{<br/>token: 'abc123token'<br/>reason: 'Success'<br/>}"]
    
    E["Response Validation<br/>Status: 200<br/>Token: not null<br/>Type: AuthResponse"]
    
    F["Test PASSED<br/>✓ Valid token received"]
    
    A -->|JSON Serialized| B
    B -->|HTTP Request| C
    C -->|HTTP Response| D
    D -->|JSON Deserialized| E
    E -->|Assertion| F
```

## 6. Exception Handling Flow

```mermaid
graph TB
    Test["Test Execution"]
    
    Test -->|Try Block| Client["AuthClient/HealthClient"]
    
    Client -->|Success| Response["Response Object"]
    Response -->|Parse| POJO["POJO/DTO Object"]
    POJO -->|Validate| RV["ResponseValidator"]
    RV -->|Success| TestPass["TEST PASSED ✓"]
    
    Client -->|Failure| Catch1["Catch Exception"]
    Response -->|Parse Error| Catch2["Catch ParseException"]
    RV -->|Assert Error| Catch3["Catch AssertionError"]
    
    Catch1 -->|Log| Logger["LoggerUtil.error()"]
    Catch2 -->|Log| Logger
    Catch3 -->|Log| Logger
    
    Logger -->|Throw| TestFail["TEST FAILED ✗"]
```

## 7. Framework Dependencies

```mermaid
graph TB
    subgraph "Framework Layers"
        L1["Presentation Layer<br/>Test Classes"]
        L2["Business Logic Layer<br/>Client Classes"]
        L3["Utility Layer<br/>Specs, Validators, Logger"]
        L4["Data Layer<br/>POJO Models"]
        L5["Configuration Layer<br/>Properties, Config Reader"]
    end
    
    subgraph "Third-Party Libraries"
        REST["REST-Assured 5.3.1"]
        TESTNG["TestNG 7.8.1"]
        JACKSON["Jackson 2.15.2"]
        LOG4J["Log4j2 2.20.0"]
        ALLURE["Allure 2.21.0"]
    end
    
    subgraph "External Services"
        API["Restful-Booker API"]
        GITHUB["GitHub Repository"]
        MAVEN["Maven Central"]
    end
    
    L1 -->|uses| TESTNG
    L1 -->|calls| L2
    L2 -->|uses| REST
    L2 -->|uses| L3
    L3 -->|validates| L4
    L3 -->|reads| L5
    L4 -->|serializes with| JACKSON
    L3 -->|logs with| LOG4J
    L1 -->|reports with| ALLURE
    REST -->|calls| API
    MAVEN -->|provides| REST
    MAVEN -->|provides| JACKSON
    MAVEN -->|provides| LOG4J
```

---

## Legend

- **✓** = Valid test case (expects success)
- **✗** = Invalid test case (expects error)
- **⏱** = Timeout test case (expects exception)
