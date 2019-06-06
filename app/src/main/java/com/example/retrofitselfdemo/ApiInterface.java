package com.example.retrofitselfdemo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {
    //we only declare the methods because the retrofit will automatically generate the necessary code for it
    //the below annotation is to make retrofit know about what request we need to process along with the endpoint
    //we can put full url in place of endUrls in network request annotations as it overrides the base url

    @GET("posts")
    Call<List<PostsModal>> getPosts(@Query("userId") Integer userId, @Query("_sort") String sort);
    //@QUERY annotation returns the result after filtering the query
    //we can add multiple query using the api documentation
    //we can pass null to any argument if we don't want any filtering
    //we use Integer instead of int because int is not nullable

    @GET("posts")
    Call<List<PostsModal>> getPosts(@QueryMap Map<String, String> parameters);
    //@QUERYMAP annotation is used to give the queries at the time of method call. The first String key and the second String is value
    //But we cannot pass same query twice using Map approach

    //{id} is to give changeable arguments and it should match with @path argument
    @GET("posts/{id}/comments")
    Call<List<CommentsModal>> getComments(@Path("id") int postId);

    @GET
    Call<List<CommentsModal>> getComments(@Url String endUrl);
    //@URL annotation is used to pass the endUrl or full url at the time of method call

    @POST("posts")
    Call<PostsModal> sendPost(@Body PostsModal post);

    @FormUrlEncoded
    @POST("posts")
    Call<PostsModal> sendPost(@Field("userId") int userId,
                              @Field("title") String title,
                              @Field("body") String text);
    //@FormUrlEncoded is used to Post data by giving the values at the time of method call
    //@Field stores the key of the json
    //@FieldMap works same as @QueryMap but to post data

    @PUT("posts/{id}")
    Call<PostsModal> putPost(@Path("id") int id, @Body PostsModal post);
    //put request changes the whole object

    @PATCH("posts/{id}")
    Call<PostsModal> patchPost(@Path("id") int id, @Body PostsModal post);
    //patch request only updates the fields we send

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
    //delete request
    //Void is used because our current api doest not give any response to delete request

}
