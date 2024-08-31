package me.itzguy.rentableholograms.entities;

public class TitleObject {

    public String title;
    public String subTitle;
    public int fadeIn;
    public int stay;
    public int fadeOut;

    public TitleObject(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subTitle = subTitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }
}
