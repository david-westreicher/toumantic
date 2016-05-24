package at.ac.uibk.toumantic.model;

/**
 * Created by david on 5/23/16.
 */
public class Offer implements Comparable<Offer> {
    private static int ID = 0;
    private final String[] items;
    private String name;
    private String teaser;
    private String id;
    private String image;
    private String action;
    private float price;

    public Offer(String name, String teaser, String image, String[] items, float price) {
        this.id = Integer.toString(ID++);
        this.name = name;
        this.teaser = teaser;
        this.image = image;
        this.action = "https://shop.khm.at/de/tickets/detail/?shop%5BshowItem%5D=200000000005001-T009-01&shop%5Bfilter%5D%5BtagsFacet%5D=";
        this.items = items;
        this.price = price;
    }

    public void setID(int id) {
        this.id = Integer.toString(id);
    }

    public String getName() {
        return name;
    }

    public String getTeaser() {
        return teaser;
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
        return id.compareTo(another.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (name != null ? !name.equals(offer.name) : offer.name != null) return false;
        if (teaser != null ? !teaser.equals(offer.teaser) : offer.teaser != null) return false;
        if (id != null ? !id.equals(offer.id) : offer.id != null) return false;
        return image != null ? image.equals(offer.image) : offer.image == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (teaser != null ? teaser.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "name='" + name + '\'' +
                ", teaser='" + teaser + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
