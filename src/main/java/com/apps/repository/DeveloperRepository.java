package com.apps.repository;

import com.apps.domain.Developer;
import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
public class DeveloperRepository {

    public Developer getDeveloperById(int id) {
        Developer developer = new Developer();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/movie_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM developers WHERE id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            developer.setId(resultSet.getInt("id"));
            developer.setFirst_name(resultSet.getString("first_name"));
            developer.setLast_name(resultSet.getString("last_name"));
            developer.setAge(resultSet.getInt("age"));
            developer.setBirth_date(resultSet.getDate("biography"));
        } catch (SQLException e) {
            System.out.println("getDeveloperById ERROR");
        }
        return developer;
    }

    public boolean createDeveloper(String firstName, String lastName, int age, Date birth_date) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/movie_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO developers (id, first_name, last_name, age, birth_date) " +
                    "VALUES (DEFAULT, ?, ?, ?, ?)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.setDate(4, birth_date);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("createDeveloper ERROR");
        }
        return result == 1;
    }

    public boolean updateDeveloper(int id, String firstName, String lastName, int age, Date birth_date) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/movie_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("UPDATE developers SET first_name=?, last_name=?, age=?, birth_date=? WHERE id=?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.setDate(4, Date.valueOf(String.valueOf(birth_date)));
            statement.setInt(5, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateDeveloper ERROR");
        }
        return result == 1;
    }

    public boolean deleteDeveloper(int id) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/movie_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM developers WHERE id=?");
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteDeveloper ERROR");
        }
        return result == 1;
    }
}
