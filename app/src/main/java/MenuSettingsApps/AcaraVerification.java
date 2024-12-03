package MenuSettingsApps;

public class AcaraVerification {
    private String id;
    private String kodeBooking;
    private String tanggal;
    private String tempat;

    public AcaraVerification() {
        // Constructor kosong untuk Firebase
    }

    public AcaraVerification(String id, String kodeBooking, String tanggal, String tempat) {
        this.id = id;
        this.kodeBooking = kodeBooking;
        this.tanggal = tanggal;
        this.tempat = tempat;
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

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }
}
