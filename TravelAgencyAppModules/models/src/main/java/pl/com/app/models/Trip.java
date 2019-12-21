package pl.com.app.models;


import java.math.BigDecimal;
import java.util.Objects;

public class Trip {
    private Integer id;
    private String destination;
    private BigDecimal price;
    private Integer people_number;
    private Integer travel_agency_id;

    public Trip() {
    }

    public Trip(Integer id, String destination, BigDecimal price, Integer people_number, Integer travel_agency_id) {
        this.id = id;
        this.destination = destination;
        this.price = price;
        this.people_number = people_number;
        this.travel_agency_id = travel_agency_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPeople_number() {
        return people_number;
    }

    public void setPeople_number(Integer people_number) {
        this.people_number = people_number;
    }

    public Integer getTravel_agency_id() {
        return travel_agency_id;
    }

    public void setTravel_agency_id(Integer travel_agency_id) {
        this.travel_agency_id = travel_agency_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id) &&
                Objects.equals(destination, trip.destination) &&
                Objects.equals(price, trip.price) &&
                Objects.equals(people_number, trip.people_number) &&
                Objects.equals(travel_agency_id, trip.travel_agency_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, destination, price, people_number, travel_agency_id);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", price=" + price +
                ", people_number=" + people_number +
                ", travel_agency_id=" + travel_agency_id +
                '}';
    }
}