package interview.legacy.pinterest.model;


import com.google.gson.annotations.SerializedName;

public class Board {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
