package ua.raif.courses.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import org.junit.jupiter.api.Test;
import ua.raif.courses.dao.entity.ConferenceEntity;
import ua.raif.courses.dao.entity.TalkEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TalkDaoTest extends AbstractDaoTest<TalkDao> {


    @Test
    @DataSet("talks-two.xml")
    void findById() {
        assertThat(dao.findById(1L).get().getId()).isEqualTo(1L);
        assertThat(dao.findById(3L)).isEmpty();
    }

    @Test
    @DataSet("talks-two.xml")
    void countAllBySpeaker() {
        assertThat(dao.countAllBySpeaker("speaker")).isEqualTo(2L);
        assertThat(dao.countAllBySpeaker("no speaker")).isEqualTo(0L);
    }

    @Test
    @DataSet("talks-two.xml")
    void existsAllByCaption() {
        assertThat(dao.existsAllByCaption("First")).isTrue();
        assertThat(dao.existsAllByCaption("Thirtd")).isFalse();
    }

    @Test
    @DataSet("talks-two.xml")
    void findAllTalksInConference() {
        assertThat(dao.findAllByConferenceId(1L).size()).isEqualTo(2);
    }

    @Test
    @DataSet(value = "talks-empty.xml", strategy = SeedStrategy.REFRESH)
    @ExpectedDataSet("talks-expected.xml")
    void save() {
        var saved = dao.save(buildValidTalkEntity());
        assertThat(saved.getId()).isNotNull();
        em.flush();
    }


    private TalkEntity buildValidTalkEntity() {
        return TalkEntity.builder()
                .id(1L)
                .caption("First")
                .description("Archeological")
                .speaker("speaker")
                .conference(buildValidConferenceEntity())
                .talkType("CLASS")
                .build();
    }

    private ConferenceEntity buildValidConferenceEntity() {
        return ConferenceEntity.builder()
                .id(1L)
                .caption("First")
                .description("Archeological")
                .capacity(250)
                .dateStart(LocalDate.of(2020, 02, 02))
                .dateEnd(LocalDate.of(2020, 02, 02))
                .build();
    }
}