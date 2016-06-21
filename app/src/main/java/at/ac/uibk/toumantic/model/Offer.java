package at.ac.uibk.toumantic.model;

/**
 * Created by david on 5/23/16.
 */
public class Offer implements Comparable<Offer> {

    public enum OfferType {
        LodgingBusiness("Hotel"), Event("Event"), Offer("Offer"), Restaurant("Restaurant"), TouristAttraction("Tourist Attraction");
        public final String name;

        OfferType(String name) {
            this.name = name;
        }
    }

    public String[] items = new String[]{};
    private String id;
    public OfferType type;

    // THING
    public String description;
    public String image;
    public String name;
    public String action;
    public Geo geo = new Geo(47.2692, 11.4041);

    // PLACE / TouristAttraction
    public String address;
    public String telephone;

    // LOCAL BUSINESS / LodgingBusiness
    public String openingHours;
    public String priceRange;

    // FOOD ESTABLISHMENT / Restaurant

    // EVENT
    public String doorTime;
    public String location;
    public String performer;
    public String startDate;

    // Offer
    public float price;
    public String category;

    public void setID(int id) {
        this.id = Integer.toString(id);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public String getID() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getAction() {
        return action;
    }

    public String[] getItems() {
        return items;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public int compareTo(Offer another) {
        if (type == OfferType.Offer && another.type != OfferType.Offer)
            return -1;
        if (type != OfferType.Offer && another.type == OfferType.Offer)
            return 1;
        return id.compareTo(another.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (name != null ? !name.equals(offer.name) : offer.name != null) return false;
        if (description != null ? !description.equals(offer.description) : offer.description != null)
            return false;
        if (id != null ? !id.equals(offer.id) : offer.id != null) return false;
        return image != null ? image.equals(offer.image) : offer.image == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "name='" + name + '\'' +
                ", teaser='" + description + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public static class Geo {
        public double latitude = 40.0;
        public double longitude = 10.0;

        public Geo(double lat, double lon) {
            this.latitude = lat;
            this.longitude = lon;
        }
    }
}
