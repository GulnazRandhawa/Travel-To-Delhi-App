package example.com.tourismapp2.classpack;

public class rating_details {

    String  comment="" , emailid="",places_key="",rating_id="",rating_date ="",name="John",pic ="https://firebasestorage.googleapis.com/v0/b/ui-design-india.appspot.com/o/User_images%2FRam.png?alt=media&token=b95d7401-220b-492f-8241-359498376228";
    long date_time =0;

    int rating = 0;
    public rating_details() {
      pic ="https://firebasestorage.googleapis.com/v0/b/ui-design-india.appspot.com/o/User_images%2FRam.png?alt=media&token=b95d7401-220b-492f-8241-359498376228";
    }

    public rating_details(String comment, String emailid, String places_key, String rating_id, String rating_date, long date_time, int rating) {
        this.comment = comment;
        this.emailid = emailid;
        this.places_key = places_key;
        this.rating_id = rating_id;
        this.rating_date = rating_date;
        this.date_time = date_time;
        this.rating = rating;
    }

    public rating_details(String comment, String emailid, String places_key, String rating_id, String rating_date, String name, String pic, long date_time, int rating) {
        this.comment = comment;
        this.emailid = emailid;
        this.places_key = places_key;
        this.rating_id = rating_id;
        this.rating_date = rating_date;
        this.name = name;
        this.pic = pic;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
