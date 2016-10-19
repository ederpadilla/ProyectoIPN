package homecomingalpha.ederpadilla.example.com.proyectoipn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Ken on 30/07/16.
 */
public class Picture extends RealmObject {

    @SerializedName("data")
    @Expose
    private Data data;
    /**
     * @return The data
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "data=" + data +
                '}';
    }
}
