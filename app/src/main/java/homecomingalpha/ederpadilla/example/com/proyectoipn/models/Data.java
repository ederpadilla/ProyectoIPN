package homecomingalpha.ederpadilla.example.com.proyectoipn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Ken on 30/07/16.
 */
public class Data extends RealmObject {

    @SerializedName("is_silhouette")
    @Expose
    private boolean isSilhouette;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * @return The isSilhouette
     */
    public boolean isIsSilhouette() {
        return isSilhouette;
    }

    /**
     * @param isSilhouette The is_silhouette
     */
    public void setIsSilhouette(boolean isSilhouette) {
        this.isSilhouette = isSilhouette;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Data{" +
                "isSilhouette=" + isSilhouette +
                ", url='" + url + '\'' +
                '}';
    }
}
