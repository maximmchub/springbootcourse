package ua.raif.courses.api.dto;

import org.junit.jupiter.api.Test;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import static org.junit.jupiter.api.Assertions.*;

@Validated
class TalkCreateDtoTest {


    TalkCreateDto dto;
    @Test
    void ok() {
            dto =  getNotOk();
    }

    @Test
    void notOk() {

    }

    private static void validateIt(TalkCreateDto dto) {

    }

    TalkCreateDto getOk() {
         return new TalkCreateDto("Caption","Description","Speaker","CLASS");
    }


    TalkCreateDto getNotOk() {
        return new TalkCreateDto("Caption",null,"","CLASS");

    }
}