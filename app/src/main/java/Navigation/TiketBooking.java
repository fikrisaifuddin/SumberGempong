package Navigation;

public class TiketBooking {
    private String id;
    private String tanggal;
    private String kewarganegaraan;
    private int jumlahPengunjung;
    private int totalHarga;

    public TiketBooking() {}

    public TiketBooking(String id,String tanggal, String kewarganegaraan, int jumlahPengunjung, int totalHarga) {
        this.id=id;
        this.tanggal = tanggal;
        this.kewarganegaraan = kewarganegaraan;
        this.jumlahPengunjung = jumlahPengunjung;
        this.totalHarga = totalHarga;
    }

    public String getId() {return id; }

    public String getTanggal() {
        return tanggal;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public int getJumlahPengunjung() {
        return jumlahPengunjung;
    }

    public int getTotalHarga() {
        return totalHarga;
    }
}

