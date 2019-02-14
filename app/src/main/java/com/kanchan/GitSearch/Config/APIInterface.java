package com.kanchan.GitSearch.Config;

import com.kanchan.GitSearch.Payload.ContributorItem;
import com.kanchan.GitSearch.Payload.Item;
import com.kanchan.GitSearch.Payload.SearchItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET ("search/repositories?")
    Call<SearchItem> getSearchList(@Query ("q") String searchTopic, @Query ("per_page") String perpage, @Query ("sort") String sort, @Query ("order") String order);

    @GET ("search/repositories?")
    Call<SearchItem> getLanguageList(@Query ("q") String searchTopic, @Query ("per_page") String perpage, @Query ("sort") String sort, @Query ("order") String order);

    @GET ("repos/{login}/{name}/contributors")
    Call<List<ContributorItem>> getContributorList(@Path ("login") String login,@Path ("name") String name);

    @GET ("users/{login}/repos?")
    Call<List<Item>> getContributorRepositoryList(@Path ("login") String login , @Query ("per_page") String perpage);

}
