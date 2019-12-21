package pl.com.app.models;


import java.util.Objects;

public class TravelAgency {
    private Integer id;
    private String name;
    private String country;

    public TravelAgency() {
    }

    public TravelAgency(Integer id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelAgency that = (TravelAgency) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

    @Override
    public String toString() {
        return "TravelAgency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }



    public static TravelAgencyBuilder builder() {
        return new TravelAgencyBuilder();
    }

    public static final class TravelAgencyBuilder {
        private Integer id;
        private String name;
        private String country;


        public TravelAgencyBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public TravelAgencyBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TravelAgencyBuilder country(String country) {
            this.country = country;
            return this;
        }

        public TravelAgency build() {
            TravelAgency travelAgency = new TravelAgency();
            travelAgency.setId(id);
            travelAgency.setName(name);
            travelAgency.setCountry(country);
            return travelAgency;
        }
    }
}
