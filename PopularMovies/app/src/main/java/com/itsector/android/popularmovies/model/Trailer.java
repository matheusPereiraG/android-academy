
package com.itsector.android.popularmovies.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trailer implements Parcelable
{

    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("official")
    @Expose
    private Boolean official;
    @SerializedName("published_at")
    @Expose
    private String publishedAt;
    @SerializedName("id")
    @Expose
    private String id;
    public final static Creator<Trailer> CREATOR = new Creator<Trailer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Trailer createFromParcel(android.os.Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return (new Trailer[size]);
        }

    }
    ;

    protected Trailer(android.os.Parcel in) {
        this.iso6391 = ((String) in.readValue((String.class.getClassLoader())));
        this.iso31661 = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.key = ((String) in.readValue((String.class.getClassLoader())));
        this.site = ((String) in.readValue((String.class.getClassLoader())));
        this.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.official = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.publishedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Trailer() {
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(iso6391);
        dest.writeValue(iso31661);
        dest.writeValue(name);
        dest.writeValue(key);
        dest.writeValue(site);
        dest.writeValue(size);
        dest.writeValue(type);
        dest.writeValue(official);
        dest.writeValue(publishedAt);
        dest.writeValue(id);
    }

    public int describeContents() {
        return  0;
    }

}
