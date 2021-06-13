package example.com.tourismapp2.classpack;

public class planner_details {
    String id= "",email = "",place_id = "",date_vist = "", from= "", to = "";

    public planner_details() {
    }

    public planner_details(String id, String email, String place_id, String date_vist, String from, String to) {
        this.id = id;
        this.email = email;
        this.place_id = place_id;
        this.date_vist = date_vist;
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getDate_vist() {
        return date_vist;
    }

    public void setDate_vist(String date_vist) {
        this.date_vist = date_vist;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
