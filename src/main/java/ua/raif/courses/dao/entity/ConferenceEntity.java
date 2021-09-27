package ua.raif.courses.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "conference")
public class ConferenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String caption;

    @NonNull
    private String description;

    @NonNull
    private LocalDate dateStart;

    @NonNull
    private LocalDate dateEnd;

    @NonNull
    private Integer capacity;
}
