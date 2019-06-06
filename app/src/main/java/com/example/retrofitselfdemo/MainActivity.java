package com.example.retrofitselfdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//youtube video : https://youtu.be/4JGvDUlfk7Y

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private ApiInterface apiInterface;
    private int postId = 3;
    private int userId = 2;
    private String sortBy = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.txt);

        //to see the exchange of data in log (add dependency)
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        //creating object of retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        //.client(okHttpClient) is added to see data exchange in log

        //retrofit will add the necessary code to the interface methods
        apiInterface = retrofit.create(ApiInterface.class);

        //getPosts();
        //getComments();
        createPost();
        //updatePost();
        //deletePost();

    }

    public void deletePost() {
        Call<Void> call = apiInterface.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                text.setText("HTTP Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    public void updatePost() {
        PostsModal post = new PostsModal(5, null, "qwerty");

        //Call<PostsModal> call = apiInterface.putPost(5, post);
        Call<PostsModal> call = apiInterface.patchPost(5, post);

        call.enqueue(new Callback<PostsModal>() {
            @Override
            public void onResponse(Call<PostsModal> call, Response<PostsModal> response) {
                if (!response.isSuccessful()) {
                    text.setText("HTTP Code: " + response.code());
                    //we will not perform further operations as it will crash the app
                    return;
                }

                StringBuffer sb = new StringBuffer();
                PostsModal postresponse = response.body();
                sb.append("Code: " + response.code() + "\n");
                sb.append("Id: " + postresponse.getId() + "\n");
                sb.append("UserId: " + postresponse.getUserId() + "\n");
                sb.append("Title: " + postresponse.getTitle() + "\n");
                sb.append("Text: " + postresponse.getBody() + "\n\n");
                text.append(sb);

            }

            @Override
            public void onFailure(Call<PostsModal> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });

    }

    public void createPost() {
        //PostsModal post = new PostsModal(25, "qwerty", "qwertyuiop");
        //Call<PostsModal> call = apiInterface.sendPost(post);
        //using @FormUrlEncoded
        Call<PostsModal> call = apiInterface.sendPost(25, "qwertyyy", "qwertyuiop");
        call.enqueue(new Callback<PostsModal>() {
            @Override
            public void onResponse(Call<PostsModal> call, Response<PostsModal> response) {
                if (!response.isSuccessful()) {
                    text.setText("HTTP Code: " + response.code());
                    //we will not perform further operations as it will crash the app
                    return;
                }

                StringBuffer sb = new StringBuffer();
                PostsModal postresponse = response.body();
                sb.append("Code: " + response.code() + "\n");
                sb.append("Id: " + postresponse.getId() + "\n");
                sb.append("UserId: " + postresponse.getUserId() + "\n");
                sb.append("Title: " + postresponse.getTitle() + "\n");
                sb.append("Text: " + postresponse.getBody() + "\n\n");
                text.append(sb);

            }

            @Override
            public void onFailure(Call<PostsModal> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    public void getPosts() {
        //to execute out network request we need to use Call object
        //Call<List<PostsModal>> call = apiInterface.getPosts(userId, sortBy);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "5");
        parameters.put("_sort", "id");
        Call<List<PostsModal>> call = apiInterface.getPosts(parameters);

        //we don't use Call.execute because it starts running on the current thread
        //for this we have Call.enqueue in retrofit which performs the task on separate thread automatically
        call.enqueue(new Callback<List<PostsModal>>() {
            @Override
            public void onResponse(Call<List<PostsModal>> call, Response<List<PostsModal>> response) {
                //if the request was unsuccessful it will return the response code
                if (!response.isSuccessful()) {
                    text.setText("HTTP Code: " + response.code());
                    //we will not perform further operations as it will crash the app
                    return;
                }
                List<PostsModal> posts = response.body();

                StringBuffer sb = new StringBuffer();

                for (PostsModal p : posts) {
                    sb.append("Id: " + p.getId() + "\n");
                    sb.append("UserId: " + p.getUserId() + "\n");
                    sb.append("Title: " + p.getTitle() + "\n");
                    sb.append("Text: " + p.getBody() + "\n\n");
                    text.append(sb);
                }
            }

            @Override
            public void onFailure(Call<List<PostsModal>> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    public void getComments() {
        Call<List<CommentsModal>> call = apiInterface.getComments(postId);
        call.enqueue(new Callback<List<CommentsModal>>() {
            @Override
            public void onResponse(Call<List<CommentsModal>> call, Response<List<CommentsModal>> response) {
                if (!response.isSuccessful()) {
                    text.setText("Http Code: " + response.code());
                    return;
                }

                List<CommentsModal> comments = response.body();
                StringBuffer sb = new StringBuffer();
                int counter = 0;
                for (CommentsModal c : comments) {
                    if (counter < 10) {
                        sb.append("Post Id: " + c.getPostId() + "\n");
                        sb.append("Id: " + c.getId() + "\n");
                        sb.append("Name: " + c.getName() + "\n");
                        sb.append("Email: " + c.getEmail() + "\n");
                        sb.append("Text: " + c.getBody() + "\n\n");
                        text.append(sb);
                        counter++;
                    } else {
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommentsModal>> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }
}
