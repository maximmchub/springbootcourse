package ua.raif.courses.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.raif.courses.dao.entity.ConferenceEntity;

public interface ConferenceDao  extends JpaRepository<ConferenceEntity, Long> {
}
