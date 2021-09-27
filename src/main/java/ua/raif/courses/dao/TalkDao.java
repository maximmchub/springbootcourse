package ua.raif.courses.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.dao.entity.TalkEntity;

public interface TalkDao extends JpaRepository<TalkEntity, Long> {
}
