package Navigation;

import com.google.firebase.database.PropertyName;

public class HomestayBooking {
    private String id;
    private String checkin;
    private String checkout;
    private String homestay;
    private int jmlhPengunjung;
    private int totalPrice;

    public HomestayBooking() {}

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("checkin")
    public String getTanggalIn() {
        return checkin;
    }

    @PropertyName("checkout")
    public String getTanggalOut() {
        return checkout;
    }

    @PropertyName("homestay")
    public String getHomestay() {
        return homestay;
    }

    @PropertyName("jmlhPengunjung")
    public int getJmlhPengunjung() {
        return jmlhPengunjung;
    }

    @PropertyName("totalPrice")
    public int getTotalPrice() {
        return totalPrice;
    }
}
