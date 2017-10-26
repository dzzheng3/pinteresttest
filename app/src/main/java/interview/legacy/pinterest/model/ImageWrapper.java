package interview.legacy.pinterest.model;


import com.google.gson.annotations.SerializedName;

public class ImageWrapper {
    @SerializedName("original")
    private Image original;

    public Image getOriginal() {
        return original;
    }

    public void setOriginal(Image original) {
        this.original = original;
    }
}
