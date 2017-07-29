package com.vision.beans;

import java.util.List;

/**
 * Created by vipul.pant on 7/29/17.
 */
public class PixabayTO {

    private String totalHits;

    private List<ImageTO> hits;

    public String getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(String totalHits) {
        this.totalHits = totalHits;
    }

    public List<ImageTO> getHits() {
        return hits;
    }

    public void setHits(List<ImageTO> hits) {
        this.hits = hits;
    }
}
