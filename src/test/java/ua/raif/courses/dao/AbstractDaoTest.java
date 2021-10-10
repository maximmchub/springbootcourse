package ua.raif.courses.dao;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSetFormat;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@DBRider
@DBUnit(caseSensitiveTableNames = true)
@ActiveProfiles("test")
@ExportDataSet(format = DataSetFormat.XML_DTD, outputName = "src/test/resources/datasets/database.dtd",
        includeTables = {"conference", "talks"})
public abstract class AbstractDaoTest<D> {

    @Autowired
    protected TestEntityManager em;

    @Autowired
    protected D dao;
}
