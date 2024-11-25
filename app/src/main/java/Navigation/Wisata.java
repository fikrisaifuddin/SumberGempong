package Navigation;
public class Wisata {
    private String caption;
    private int imageResId;

    public Wisata(String caption, int imageResId) {
        this.caption = caption;
        this.imageResId = imageResId;
    }

    public String getCaption() {
        return caption;
    }

    public int getImageResId() {
        return imageResId;
    }
}
