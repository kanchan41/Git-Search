package com.kanchan.GitSearch.Payload;
import com.google.gson.annotations.SerializedName;
public class RepositoryItem  {
    @SerializedName("description")
    public String description;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("forks")
    public Integer forks;
    @SerializedName("subscribers_count")
    public Integer subscribers_count;
    @SerializedName("watchers")
    public Integer watchers;
    @SerializedName("owner")
    public Owner owner;
    public class Owner {
        @SerializedName("avatar_url")
        public String avatar_url;
    }
}