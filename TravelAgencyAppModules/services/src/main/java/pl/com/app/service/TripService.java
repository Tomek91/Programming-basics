package pl.com.app.service;


import pl.com.app.models.Trip;

import java.util.List;
import java.util.Optional;

public interface TripService {
    void addTrip(Trip customer);
    void addAll(List<Trip> items);
    void updateOne(Trip customer);
    void deleteOne(Integer id);
    void deleteAll();
    Optional<Trip> findById(Integer id);
    List<Trip> findAll();
}