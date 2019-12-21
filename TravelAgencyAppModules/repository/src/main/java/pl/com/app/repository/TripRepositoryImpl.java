package pl.com.app.repository;


import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.models.Trip;
import pl.com.app.sqlbuilder.creator.SqlDeleteCreator;
import pl.com.app.sqlbuilder.creator.SqlSelectCreator;
import pl.com.app.sqlbuilder.creator.SqlUpdateCreator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TripRepositoryImpl implements TripRepository {

    private Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public void add(Trip item) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(TripRepository.getInsertSql(item));
        } catch (Exception e) {
            throw new MyException("TRIP REPOSITORY, ADD");
        }
    }

    @Override
    public void addAll(List<Trip> items) {
        try {
            Statement statement = connection.createStatement();
            for (Trip item : items) {
                statement.addBatch(TripRepository.getInsertSql(item));
            }
            statement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("TRIP REPOSITORY, ADD ALL");
        }
    }

    @Override
    public void update(Trip item) {
        try {
            final String updateSql = new SqlUpdateCreator
                    .SqlUpdateBuilder()
                    .tableName("trip")
                    .addSetClause("destination", item.getDestination())
                    .addSetClause("price", item.getPrice())
                    .addSetClause("people_number", item.getPeople_number())
                    .addSetClause("travel_agency_id", item.getPeople_number())
                    .addCondition("id", item.getId())
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(updateSql);
        } catch (Exception e) {
            throw new MyException("TRIP REPOSITORY, UPDATE");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            final String deleteSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("trip")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteSql);
        } catch (Exception e) {
            throw new MyException("TRIP REPOSITORY, DELETE");
        }
    }

    @Override
    public void deleteAll() {
        try {
            final String deleteAllSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("trip")
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteAllSql);
        } catch (Exception e) {
            throw new MyException("TRIP REPOSITORY, DELETE ALL");
        }
    }

    @Override
    public Optional<Trip> findOneById(Integer id) {
        Optional<Trip> op = Optional.empty();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("trip")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            if (rs.next()) {
                op = Optional.of(new Trip(
                        rs.getInt("id"),
                        rs.getString("destination"),
                        rs.getBigDecimal("price"),
                        rs.getInt("people_number"),
                        rs.getInt("travel_agency_id"))
                );
            }
        } catch (Exception e) {
            throw new MyException("TRIP REPOSITORY, FIND ONE BY ID");
        }
        return op;
    }

    @Override
    public List<Trip> findAll() {

        List<Trip> customers = new ArrayList<>();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("trip")
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            while (rs.next()) {
                customers.add(new Trip(
                        rs.getInt("id"),
                        rs.getString("destination"),
                        rs.getBigDecimal("price"),
                        rs.getInt("people_number"),
                        rs.getInt("travel_agency_id"))
                );
            }
        } catch (Exception e) {
            throw new MyException("TRIP REPOSITORY, FIND ALL");
        }
        return customers;
    }

}