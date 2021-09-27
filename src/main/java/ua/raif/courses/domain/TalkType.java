package ua.raif.courses.domain;

import lombok.Getter;

public enum TalkType {
    WORKSHOP("WORKSHOP"), LECTURE("LECTURE"), CLASS("CLASS");
    @Getter
    public final String id;

    TalkType(String description) {
        this.id = description;
    }
}
