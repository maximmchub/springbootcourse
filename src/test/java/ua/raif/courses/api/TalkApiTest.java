package ua.raif.courses.api;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ua.raif.courses.api.dto.TalkViewDto;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class TalkApiTest extends AbstractApiTest {

    @Test
    void addAndViewTalk() {
        addNewConference();
        long conferenceId = 1L;

        Long taskId = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "        \"caption\": \"Talk 3\",\n" +
                        "        \"description\": \"description 3\",\n" +
                        "        \"speaker\": \"Bootstrap Bill\",\n" +
                        "        \"talkType\": \"LECTURE\"\n" +
                        "    }")
                .when()
                .post("/conferences/" + conferenceId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract().body().as(TalkViewDto.class).getId();

        var elements = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/conferences/" + conferenceId + "/talks")
                .as(TalkViewDto[].class);

        assertThat(elements.length).isEqualTo(1);
        assertThat(elements[0].getId()).isEqualTo(taskId);
    }

}