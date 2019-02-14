//package com.kanchan.Git.PojoItems;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//import com.google.gson.annotations.SerializedName;
//import java.util.List;
//public class TrendingItem implements Parcelable {
//    private String author;
//    private String name;
//    private String description;
//    private Integer stars;
//    private Integer forks;
//    private String language;
//    private List<details> builtBy;
//    public TrendingItem(Parcel in) {
//        author = in.readString();
//        name = in.readString();
//        description = in.readString();
//        if (in.readByte() == 0) {
//            stars = null;
//        } else {
//            stars = in.readInt();
//        }
//        if (in.readByte() == 0) {
//            forks = null;
//        } else {
//            forks = in.readInt();
//        }
//        language = in.readString();
//    }
//
//    public static final Creator<TrendingItem> CREATOR = new Creator<TrendingItem>() {
//        @Override
//        public TrendingItem createFromParcel(Parcel in) {
//            return new TrendingItem(in);
//        }
//
//        @Override
//        public TrendingItem[] newArray(int size) {
//            return new TrendingItem[size];
//        }
//    };
//
//    public List<details> getBuiltBy() {
//        return builtBy;
//    }
//    public void setBuiltBy(List<details> builtBy) {
//        this.builtBy = builtBy;
//    }
//    public TrendingItem(String author, String name, String description, Integer stars, Integer forks, String language, List<details> builtBy) {
//        this.author = author;
//        this.name = name;
//        this.description = description;
//        this.stars = stars;
//        this.forks = forks;
//        this.language = language;
//        this.builtBy = builtBy;
//    }
//    public String getLanguage() {
//        return language;
//    }
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//    public Integer getStars() {
//        return stars;
//    }
//    public void setStars(Integer stars) {
//        this.stars = stars;
//    }
//    public Integer getForks() {
//        return forks;
//    }
//    public void setForks(Integer forks) {
//        this.forks = forks;
//    }
//    public String getDescription() {
//        return description;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }
//    public String getAuthor() {
//        return author;
//    }
//    public void setAuthor(String Author) {
//        this.author = author;
//    }
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeString(author);
//        dest.writeInt(stars);
//        dest.writeString(description);
//        dest.writeInt(forks);
//        dest.writeString(language);
//        dest.writeArray(new List[]{builtBy});
//    }
//    }
//
//    class details {
//        @SerializedName("username")
//        public String username;
//        @SerializedName("avatar")
//        public String avatar;
//        @SerializedName("href")
//        public String href;
//        public String getUsername() {
//            return username;
//        }
//        public void setUsername(String username) {
//            this.username = username;
//        }
//        public String getAvatar() {
//            return avatar;
//        }
//        public void setAvatar(String avatar) {
//            this.avatar = avatar;
//        }
//        public String getHref() {
//            return href;
//        }
//        public void setHref(String href) {
//            this.href = href;
//        }
//    }
//

package com.kanchan.GitSearch.Payload;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContributorItem {
    private String avatar_url;
    private String login;
    private String FullName;
    private String Description;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public ContributorItem(String avatar_url, String login) {
        this.avatar_url = avatar_url;
        this.login = login;

    }





}
