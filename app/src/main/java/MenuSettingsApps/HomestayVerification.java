package MenuSettingsApps;
public class HomestayVerification {
    private String id;
    private String kodeBooking;
    private String tanggal;

    public HomestayVerification() {
        // Constructor kosong untuk Firebase
    }

    public HomestayVerification(String id, String kodeBooking, String tanggal) {
        this.id = id;
        this.kodeBooking = kodeBooking;
        this.tanggal = tanggal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodeBooking() {
        return kodeBooking;
    }

    public void setKodeBooking(String kodeBooking) {
        this.kodeBooking = kodeBooking;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
