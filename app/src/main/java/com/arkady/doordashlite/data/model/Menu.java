package com.arkady.doordashlite.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {
    @SerializedName("popular_items") private List<Object> popularItems = null;
    @SerializedName("is_catering") private Boolean isCatering;
    @SerializedName("subtitle")private String subtitle;
    @SerializedName("id")private Integer id;
    @SerializedName("name")private String name;

    public List<Object> getPopularItems() {
        return popularItems;
    }

    public void setPopularItems(List<Object> popularItems) {
        this.popularItems = popularItems;
    }

    public Boolean getIsCatering() {
        return isCatering;
    }

    public void setIsCatering(Boolean isCatering) {
        this.isCatering = isCatering;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
