package example.com.tourismapp2.classpack;

public class hotel_details {
    String image = "", name = "",price_per_night = "",key="";
    int rating = 5;

    public hotel_details() {
    }

    public hotel_details(String image, String name, String price_per_night, int rating, String key) {
        this.image = image;
        this.name = name;
        this.price_per_night = price_per_night;
        this.rating = rating;
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice_per_night() {
        return price_per_night;
    }

    public void setPrice_per_night(String price_per_night) {
        this.price_per_night = price_per_night;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
