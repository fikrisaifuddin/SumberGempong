package Navigation;

public class HomestayBooking {
    private String checkin;
    private String checkout;
    private String homestayi;
    private int jmlhPengunjung;
    private int totalPrice;
    private String userId;
    private String kodeBooking;
    private long timestamp;



    public HomestayBooking(String checkin, String checkout, String homestay, int jmlhPengunjung, int totalPrice, String userId, String kodeBooking) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.homestayi = homestay;
        this.jmlhPengunjung = jmlhPengunjung;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.kodeBooking = kodeBooking;
        this.timestamp = System.currentTimeMillis();
    }

    public HomestayBooking(){}

    public String getCheckin() {return checkin;
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

    public int getJmlhPengunjung() {
        return jmlhPengunjung;
    }

    public void setJmlhPengunjung(int jmlhPengunjung) {
        this.jmlhPengunjung = jmlhPengunjung;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
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

    public void setKodeBooking(String kodeBooking) {this.kodeBooking = kodeBooking;}
    public long getTimestamp() {
        return timestamp;
    }

    public String getHomestay() {
        return homestayi;
    }

    public void setHomestay(String homestay) {
        this.homestayi = homestay;
    }


}

