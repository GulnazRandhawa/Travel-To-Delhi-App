package example.com.tourismapp2.classpack;

public class user_added_fav_places_details {
    String fav_places_id = "";
    String user_email_id = "";
    String places_id = "";

    public user_added_fav_places_details() {
    }

    public user_added_fav_places_details(String fav_places_id, String user_email_id, String places_id) {
        this.fav_places_id = fav_places_id;
        this.user_email_id = user_email_id;
        this.places_id = places_id;
    }

    public String getFav_places_id() {
        return fav_places_id;
    }

    public void setFav_places_id(String fav_places_id) {
        this.fav_places_id = fav_places_id;
    }

    public String getUser_email_id() {
        return user_email_id;
    }

    public void setUser_email_id(String user_email_id) {
        this.user_email_id = user_email_id;
    }

    public String getPlaces_id() {
        return places_id;
    }

    public void setPlaces_id(String places_id) {
        this.places_id = places_id;
    }
}
