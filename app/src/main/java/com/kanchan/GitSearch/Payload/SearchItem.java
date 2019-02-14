

package com.kanchan.GitSearch.Payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total_count",
        "incomplete_results",
        "items"
})
public class SearchItem {

    @SerializedName ("total_count")
    private Integer totalCount;
    @SerializedName("incomplete_results")
    private Boolean incompleteResults;
    @SerializedName("items")
    private List<Item> items = new ArrayList<>();

    @SerializedName("total_count")
    public Integer getTotalCount() {
        return totalCount;
    }

    @SerializedName("total_count")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @SerializedName("items")
    public List<Item> getItems() {
        return items;
    }

    @SerializedName("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

}


