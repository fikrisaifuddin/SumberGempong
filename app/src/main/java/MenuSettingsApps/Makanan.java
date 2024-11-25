package MenuSettingsApps;

public class Makanan {
    private String id;
    private String nama;
    private String harga;

    public Makanan() {
    }

    public Makanan(String id, String nama, String harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getHarga() {
        return harga;
    }
}
