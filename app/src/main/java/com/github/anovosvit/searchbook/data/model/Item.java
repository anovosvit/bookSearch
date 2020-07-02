package com.github.anovosvit.searchbook.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Item {
    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("etag")
    @Expose
    private String etag;

    @SerializedName("selfLink")
    @Expose
    private String selfLink;

    @SerializedName("volumeInfo")
    @Expose
    VolumeInfo volumeInfo;

    public String getKind() {
        return kind;
    }

    public String getId() {
        return id;
    }

    public String getEtag() {
        return etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getKind().equals(item.getKind()) &&
                getId().equals(item.getId()) &&
                getEtag().equals(item.getEtag()) &&
                getSelfLink().equals(item.getSelfLink()) &&
                getVolumeInfo().equals(item.getVolumeInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKind(), getId(), getEtag(), getSelfLink(), getVolumeInfo());
    }
}
