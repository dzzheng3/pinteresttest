package interview.legacy.pinterest.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataList<T> {

    @SerializedName("data")
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
