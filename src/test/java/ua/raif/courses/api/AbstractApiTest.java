package ua.raif.courses.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractApiTest {
    @LocalServerPort
    protected int port;

    @BeforeEach
    public void init() {
        RestAssured.port = port;
    }

    Response addNewConference() {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "  \"caption\" : \"Nautical Main Problems\",\n" +
                        "  \"description\" : \"Sea bottom inhabitants\",\n" +
                        "  \"dateStart\": \"2120-02-02\",\n" +
                        "  \"dateEnd\": \"2120-02-03\",\n" +
                        "  \"capacity\": 200\n" +
                        "}")
                .when()
                .post("/conferences");
    }

}
