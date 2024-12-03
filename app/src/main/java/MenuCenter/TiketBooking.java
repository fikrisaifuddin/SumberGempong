package MenuCenter;

public class TiketBooking {

    private int jumlahPengunjung;
    private double totalHarga;

    // Constructor default untuk Firebase
    public TiketBooking() {
    }

    // Constructor dengan parameter
    public TiketBooking(int jumlahPengunjung, double totalHarga) {
        this.jumlahPengunjung = jumlahPengunjung;
        this.totalHarga = totalHarga;
    }

    // Getter dan Setter
    public int getJumlahPengunjung() {
        return jumlahPengunjung;
    }

    public void setJumlahPengunjung(int jumlahPengunjung) {
        this.jumlahPengunjung = jumlahPengunjung;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }
}
