package MenuSettingsApps;

public class Homestay {
    private String id;
    private String nama;
    private String fasilitas;
    private String harga;

    public Homestay() {
    }

    public Homestay(String id, String nama, String fasilitas, String harga) {
        this.id = id;
        this.nama = nama;
        this.fasilitas = fasilitas;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public String getHarga() {
        return harga;
    }
}
