package example.com.tourismapp2.classpack;

public class rating_details {

    String  comment="" , emailid="",places_key="",rating_id="",rating_date ="";
    long date_time =0;

    float rating = 0;
    public rating_details() {
    }

    public rating_details(String comment, String emailid, String places_key, String rating_id, String rating_date, long date_time, float rating) {
        this.comment = comment;
        this.emailid = emailid;
        this.places_key = places_key;
        this.rating_id = rating_id;
        this.rating_date = rating_date;
        this.date_time = date_time;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPlaces_key() {
        return places_key;
    }

    public void setPlaces_key(String places_key) {
        this.places_key = places_key;
    }

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRating_date() {
        return rating_date;
    }

    public void setRating_date(String rating_date) {
        this.rating_date = rating_date;
    }

    public long getDate_time() {
        return date_time;
    }

    public void setDate_time(long date_time) {
        this.date_time = date_time;
    }
}
