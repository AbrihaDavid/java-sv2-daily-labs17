package day01;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        MariaDbDataSource dataSource = new MariaDbDataSource();

        try{
            dataSource.setUrl("jdbc:mariadb://localhost:3306/actors_test?useUnicode=true");
            dataSource.setUser("root");
            dataSource.setPassword("asdasd11");
        } catch (SQLException se){
            throw new IllegalStateException("Cannot reach database",se);
        }


        Flyway flyway = Flyway.configure().dataSource(dataSource).load();

        flyway.migrate();



        ActorsRepository actorsRepository = new ActorsRepository(dataSource);
        MoviesRepository moviesRepository = new MoviesRepository(dataSource);
   //     moviesRepository.saveMovie("Titanic", LocalDate.of(1997,12,11));
        moviesRepository.saveMovie("LOTR", LocalDate.of(2000,5,5));
        moviesRepository.saveMovie("SW", LocalDate.of(2004,7,8));

        System.out.println(moviesRepository.findAllMovies().size());
        System.out.println(moviesRepository.findAllMovies().get(1).getTitle());

    }
}
