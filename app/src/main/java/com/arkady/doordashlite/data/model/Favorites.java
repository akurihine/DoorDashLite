package com.arkady.doordashlite.data.model;

import java.util.List;

public class Favorites {
    private List<Integer> ids;

    public Favorites(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }
}
