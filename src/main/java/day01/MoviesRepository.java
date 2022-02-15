package day01;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MoviesRepository {


    private DataSource dataSource;

    public MoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveMovie(String title, LocalDate releaseDate){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into movies(title,release_date) values (?,?)")){

            statement.setString(1,title);
            statement.setDate(2, Date.valueOf(releaseDate));
            statement.executeUpdate();

        } catch (SQLException se){
            throw new IllegalStateException("Cannot connect",se);
        }
    }

    public List<Movie> findAllMovies(){

        try(Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from movies")){

            List<Movie> result = new ArrayList<>();
            while(rs.next()){
                result.add(new Movie(rs.getLong(1),rs.getString(2),rs.getDate(3).toLocalDate()));
            }
            return result;

        }catch (SQLException se){
            throw new IllegalArgumentException("Cannot query",se);
        }
    }
}
