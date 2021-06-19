package example.com.tourismapp2.classpack;

public class HotelDetail {
    String name,pic;

    public HotelDetail(String name, String pic) {
        this.name = name;
        this.pic = pic;
    }

    public HotelDetail() {
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
