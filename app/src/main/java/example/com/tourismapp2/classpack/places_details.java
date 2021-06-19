package example.com.tourismapp2.classpack;

import java.io.Serializable;

public class places_details implements Serializable {
    String place_name,images,description,push_key;
    String rating ;
    String rating_date;
    String date_vist;
    String from;
    public places_details() {
    }

    public String getRating_date() {
        return rating_date;
    }

    public void setRating_date(String rating_date) {
        this.rating_date = rating_date;
    }

    public String  getDate_time() {
        return date_vist;
    }

    public void setDate_time(String date_time) {
        this.date_vist = date_time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public places_details(String place_name, String images, String description, String push_key, String rating, String rating_date, String date_vist, String from) {
        this.place_name = place_name;
        this.images = images;
        this.description = description;
        this.push_key = push_key;
        this.rating = rating;
        this.rating_date=rating_date;
        this.date_vist=date_vist;
        this.from=from;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPush_key() {
        return push_key;
    }

    public void setPush_key(String push_key) {
        this.push_key = push_key;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
