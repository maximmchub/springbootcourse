package ua.raif.courses.api;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ua.raif.courses.api.dto.ConferenceViewDto;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class ConferenceApiTest extends AbstractApiTest {

    @Test
    void addAndViewConference() {
        long id = addNewConference().then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract().body().as(ConferenceViewDto.class).getId();

        assertThat(id).isPositive();

        var elements = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("/conferences")
                .as(ConferenceViewDto[].class);

        assertThat(elements.length).isEqualTo(1);
        assertThat(elements[0].getId()).isEqualTo(id);
    }

}