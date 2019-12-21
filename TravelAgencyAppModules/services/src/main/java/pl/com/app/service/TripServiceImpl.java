package pl.com.app.service;


import pl.com.app.exceptions.MyException;
import pl.com.app.models.Trip;
import pl.com.app.repository.TripRepository;
import pl.com.app.repository.TripRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TripServiceImpl implements TripService {
    private TripRepository tripRepository = new TripRepositoryImpl();

    @Override
    public void addAll(List<Trip> items) {
        try {
            if (items == null) {
                throw new NullPointerException("TRIPS IS NULL");
            }
            tripRepository.addAll(items);

        } catch (Exception e) {
            throw new MyException("TRIP SERVICE, ADD ALL");
        }
    }

    @Override
    public void deleteAll() {
        try {
            tripRepository.deleteAll();

        } catch (Exception e) {
            throw new MyException("TRIP SERVICE, DELETE ALL");
        }
    }

    @Override
    public void updateOne(Trip item) {
        try {
            if (item == null) {
                throw new NullPointerException("CUSTOMER IS NULL");
            }
            tripRepository.update(item);
        } catch (Exception e) {
            throw new MyException("TRIP SERVICE, UPDATE ONE");
        }
    }

    @Override
    public void deleteOne(Integer id) {
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }
            tripRepository.delete(id);
        } catch (Exception e) {
            throw new MyException("TRIP SERVICE, DELETE ONE");
        }
    }

    @Override
    public Optional<Trip> findById(Integer id) {
        Optional<Trip> tripOpt = Optional.empty();
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }
            tripOpt = tripRepository.findOneById(id);

        } catch (Exception e) {
            throw new MyException("TRIP SERVICE, FIND BY ID");
        }
        return tripOpt;
    }

    @Override
    public List<Trip> findAll() {
        List<Trip> trips = new ArrayList<>();
        try {
            trips = tripRepository.findAll();

        } catch (Exception e) {
            throw new MyException("TRIP SERVICE, FIND ALL");
        }
        return trips;
    }

    @Override
    public void addTrip(Trip trip) {
        try {
            if (trip == null) {
                throw new NullPointerException("CUSTOMER IS NULL");
            }
            tripRepository.add(trip);

        } catch (Exception e) {
            throw new MyException("TRIP SERVICE, ADD TRIP");
        }
    }
}
