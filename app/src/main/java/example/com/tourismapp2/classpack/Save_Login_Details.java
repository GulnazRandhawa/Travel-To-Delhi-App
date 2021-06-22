package example.com.tourismapp2.classpack;

public class Save_Login_Details {
    String email="",time_from="", time_to="",current_date="",id="";

//    public Save_Login_Details() {
//    }

    public Save_Login_Details(String email, String time_from, String time_to, String current_date, String id) {
        this.email = email;
        this.time_from = time_from;
        this.time_to = time_to;
        this.current_date = current_date;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
