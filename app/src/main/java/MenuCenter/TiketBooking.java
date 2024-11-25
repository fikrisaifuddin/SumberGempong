package MenuCenter;


public class TiketBooking {
    private String tanggal;
    private int jumlahPengunjung;
    private int totalHarga;

    public TiketBooking(String tanggal, int jumlahPengunjung, int totalHarga) {
        this.tanggal = tanggal;
        this.jumlahPengunjung = jumlahPengunjung;
        this.totalHarga = totalHarga;
    }

    // Getter dan setter jika diperlukan
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlahPengunjung() {
        return jumlahPengunjung;
    }

    public void setJumlahPengunjung(int jumlahPengunjung) {
        this.jumlahPengunjung = jumlahPengunjung;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }
}
