package Navigation;

public class AcaraBooking {
    private String id;
    private String penyelenggara;
    private String namaacara;
    private String tanggal;
    private int jumlahPengunjung;
    private int totalHarga;
    private String kodeBooking;

    public AcaraBooking() {}

    public AcaraBooking(String kodeBooking,String id, String tanggal,String penyelenggara,String namaacara, int jumlahPengunjung, int totalHarga) {
        this.id = id;
        this.penyelenggara = penyelenggara;
        this.namaacara = namaacara;
        this.tanggal = tanggal;
        this.jumlahPengunjung = jumlahPengunjung;
        this.totalHarga = totalHarga;
        this.kodeBooking = kodeBooking;
    }

    public String getId() {
        return id;
    }

    public String getTanggal() {return tanggal;}

    public void setpenyelenggara(String penyelenggara) {this.penyelenggara=penyelenggara;}

    public String getpenyelenggara() {return penyelenggara;}

    public void setnamaacara(String namaacara) {this.namaacara=namaacara;}

    public String getnamaacara() {return namaacara;}

    public void setTanggal(String tanggal) {this.tanggal = tanggal;}

    public int getJumlahPengunjung() {return jumlahPengunjung;}

    public void setJumlahPengunjung(int jumlahPengunjung) {this.jumlahPengunjung = jumlahPengunjung;}

    public int getTotalHarga() {return totalHarga;}

    public void setTotalHarga(int totalHarga) {this.totalHarga = totalHarga;}

    public String getKodeBooking() {return kodeBooking;}

    public void setKodeBooking(String kodeBooking) {this.kodeBooking = kodeBooking;}
}
