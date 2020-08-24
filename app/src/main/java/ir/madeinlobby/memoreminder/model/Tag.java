package ir.madeinlobby.memoreminder.model;

public class Tag {
    private String title;
    private String colorHex;

    public Tag(String title, String colorHex) {
        this.title = title;
        this.colorHex = colorHex;
    }

    public String getTitle() {
        return title;
    }

    public String getColorHex() {
        return colorHex;
    }
}
