package MenuCenter;

public class HomestayBooking {
    private String checkin;
    private String checkout;
    private String homestay;
    private int jmlhPengunjung;
    private double totalPrice;
    private String userId;
    private String kodeBooking;

    public HomestayBooking(String checkin, String checkout, String homestay, int jmlhPengunjung, double totalPrice, String userId, String kodeBooking) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.homestay = homestay;
        this.jmlhPengunjung = jmlhPengunjung;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.kodeBooking = kodeBooking;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getHomestay() {
        return homestay;
    }

    public void setHomestay(String homestay) {
        this.homestay = homestay;
    }

    public int getJmlhPengunjung() {
        return jmlhPengunjung;
    }

    public void setJmlhPengunjung(int jmlhPengunjung) {
        this.jmlhPengunjung = jmlhPengunjung;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKodeBooking() {
        return kodeBooking;
    }

    public void setKodeBooking(String kodeBooking) {
        this.kodeBooking = kodeBooking;
    }
}
