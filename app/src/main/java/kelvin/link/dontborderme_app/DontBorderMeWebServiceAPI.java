package kelvin.link.dontborderme_app;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface DontBorderMeWebServiceAPI {


    @GET("get_subscribe_events.php")
    Call<List<Event>> getSubscribeEvents(
            @QueryMap Map<String, String> parameters);

    @GET("get_send_events.php")
    Call<List<Event>> getSendEvents(
            @QueryMap Map<String, String> parameters);

    @GET("create_event.php")
    Call<Void> createEvent(
            @QueryMap Map<String, String> parameters);

    @GET("del_user_event.php")
    Call<Void> deleteEvent(
            @QueryMap Map<String, String> parameters);

    @GET("subscribe_event.php")
    Call<Void> subscribe_event(
            @QueryMap Map<String, String> parameters);


    /*
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @FieldMap Map<String, String> fields
    );

    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);*/
}
