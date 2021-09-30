package ua.raif.courses.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.dao.entity.TalkEntity;

import java.util.List;

public interface TalkDao extends JpaRepository<TalkEntity, Long> {
    int countAllBySpeaker(String speaker);
    boolean existsAllByCaption(String caption);
    List<TalkEntity> findAllByConferenceId(Long id);
}
