package pl.com.app.repository;


import pl.com.app.models.Trip;
import pl.com.app.sqlbuilder.creator.SqlInsertCreator;

public interface TripRepository extends CrudRepository<Trip, Integer> {

    static String getInsertSql(Trip item) {
        return new SqlInsertCreator
                .SqlInsertBuilder()
                .tableName("trip")
                .addCondition("destination", item.getDestination())
                .addCondition("price", item.getPrice())
                .addCondition("people_number", item.getPeople_number())
                .addCondition("travel_agency_id", item.getTravel_agency_id())
                .build();
    }
}