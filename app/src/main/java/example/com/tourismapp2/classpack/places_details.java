package example.com.tourismapp2.classpack;

import java.io.Serializable;

public class places_details implements Serializable {
    String place_name,images,description,push_key;

    public places_details() {
    }

    public places_details(String place_name, String images, String description, String push_key) {
        this.place_name = place_name;
        this.images = images;
        this.description = description;
        this.push_key = push_key;
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
}
