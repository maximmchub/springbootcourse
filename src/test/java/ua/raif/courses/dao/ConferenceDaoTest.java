package ua.raif.courses.dao;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import ua.raif.courses.dao.entity.ConferenceEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConferenceDaoTest extends AbstractDaoTest<ConferenceDao> {

    @Test
    @DataSet("empty.xml")
    public void findAllEmpty() {
        assertThat(dao.findAll()).isEmpty();
    }

    @Test
    @DataSet("conferences-two.xml")
    public void findAllExists() {
        assertThat(dao.findAll().size()).isEqualTo(2);
    }

    @Test
    @DataSet("conferences-two.xml")
    public void findById() {
        assertThat(dao.findById(1L).get().getId()).isEqualTo(1L);
        assertThat(dao.findById(3L)).isEmpty();
    }

    @Test
    @DataSet("conferences-two.xml")
    public void existsByCaption() {
        assertThat(dao.existsByCaption("First")).isTrue();
        assertThat(dao.existsByCaption("Thirs")).isFalse();
    }

    @Test
    @DataSet(value = "empty.xml", strategy = SeedStrategy.REFRESH)
    @ExpectedDataSet("conferences-expected.xml")
    public void save() {
        var saved = dao.save(buildValidConferenceEntity());
        assertThat(saved.getId()).isNotNull();
        em.flush();
    }

    private ConferenceEntity buildValidConferenceEntity() {
        return ConferenceEntity.builder()
                .id(1L)
                .caption("First")
                .description("Archeological")
                .capacity(250)
                .dateStart(LocalDate.of(2020,02,02))
                .dateEnd(LocalDate.of(2020,02,02))
                .build();
    }

}