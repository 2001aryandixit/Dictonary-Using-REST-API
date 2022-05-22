package com.example.dictonaryapi;

import android.content.Context;
import android.widget.Toast;

import com.example.dictonaryapi.Models.APIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RequestManager {
    Context context;

    //retrofit lib for base url
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //constructor for context
    public RequestManager(Context context) {
        this.context = context;
    }

    //method to handle calling two parameters--- listener(create custom listener) that will catch the response from main activity and 2. is word
    public void getWordMeaning(OnFetchDataListener listener, String word){
        //create object of interface
        CallDictonary callDictonary=retrofit.create(CallDictonary.class);
        //call object
        Call<List<APIResponse>> call= callDictonary.callMeanings(word);

        //try catch block to get response
        try {
            call.enqueue(new Callback<List<APIResponse>>() {
                @Override
                public void onResponse(Call<List<APIResponse>> call, Response<List<APIResponse>> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(context, "Error!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //call listener from on data fetch method
                    listener.OnFetchData(response.body().get(0),response.message());
                }

                @Override
                public void onFailure(Call<List<APIResponse>> call, Throwable t) {
                    listener.OnError("Request Failed!!");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "An Error Occurred!!!", Toast.LENGTH_SHORT).show();
        }
    }


    // now create interface for api calling
    public interface CallDictonary{
        @GET("entries/en/{word}")
        //call of retrofit
        Call<List<APIResponse>> callMeanings(
                @Path("word") String word
        );
    }
}

