package interview.legacy.pinterest.model;


import com.google.gson.annotations.SerializedName;

public class Pin {

    @SerializedName("note")
    private String note;
    @SerializedName("url")
    private String url;
    @SerializedName("image")
    private ImageWrapper imageWrapper;
    @SerializedName("id")
    private Long id;
    @SerializedName("board")
    private Board board;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageWrapper getImageWrapper() {
        return imageWrapper;
    }

    public void setImageWrapper(ImageWrapper imageWrapper) {
        this.imageWrapper = imageWrapper;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
