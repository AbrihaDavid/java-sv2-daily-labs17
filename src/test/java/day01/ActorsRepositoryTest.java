package day01;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ActorsRepositoryTest {

    ActorsRepository actorsRepository;
    Flyway flyway;
    @BeforeEach
    void init(){

        MariaDbDataSource dataSource = new MariaDbDataSource();

        try{
            dataSource.setUrl("jdbc:mariadb://localhost:3306/actors_test?useUnicode=true");
            dataSource.setUser("root");
            dataSource.setPassword("asdasd11");
        } catch (SQLException se){
            throw new IllegalStateException("Cannot reach database",se);
        }

        flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        actorsRepository = new ActorsRepository(dataSource);
    }

    @Test
    void testInsert(){
        actorsRepository.saveActor("jack Doe");
    }

}