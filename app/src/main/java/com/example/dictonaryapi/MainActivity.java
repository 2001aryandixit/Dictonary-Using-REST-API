package com.example.dictonaryapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictonaryapi.Adapters.MeaningsAdapter;
import com.example.dictonaryapi.Adapters.PhoneticsAdapter;
import com.example.dictonaryapi.Models.APIResponse;
import com.example.dictonaryapi.Models.Phonetics;

public class MainActivity extends AppCompatActivity {

    SearchView search_view;
    TextView textView_word,textView_origin;
    RecyclerView recycler_phonetics,recycler_meanings;
    ProgressDialog progressDialog;
    PhoneticsAdapter phoneticsAdapter;
    MeaningsAdapter meaningsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_view=findViewById(R.id.search_view);
        textView_word=findViewById(R.id.textView_word);
        recycler_phonetics=findViewById(R.id.recycler_phonetics);
        recycler_meanings=findViewById(R.id.recycler_meanings);
        progressDialog = new ProgressDialog(this);

        
        //when user first time opens the app
        progressDialog.setTitle("loading");
        progressDialog.show();

        //calling api
        RequestManager requestManager=new RequestManager(MainActivity.this);
        requestManager.getWordMeaning(listener,"hello");

        //create a new package view holder and custom adapter

        // set on query text listner to use api calling
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                progressDialog.setTitle("Fetching response for "+query);
                progressDialog.show();

                //calling api
                RequestManager requestManager=new RequestManager(MainActivity.this);
                requestManager.getWordMeaning(listener,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void OnFetchData(APIResponse apiResponse, String message) {
            progressDialog.dismiss();
            if (apiResponse==null){
                Toast.makeText(MainActivity.this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(apiResponse);
        }

        @Override
        public void OnError(String message) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(APIResponse apiResponse) {
        textView_word.setText("Word: "+apiResponse.getWord());
        recycler_phonetics.setHasFixedSize(true);
        recycler_phonetics.setLayoutManager(new GridLayoutManager(this,1));
        phoneticsAdapter=new PhoneticsAdapter(this,apiResponse.getPhonetics());
        recycler_phonetics.setAdapter(phoneticsAdapter);

        recycler_meanings.setHasFixedSize(true);
        recycler_meanings.setLayoutManager(new GridLayoutManager(this,1));
        meaningsAdapter=new MeaningsAdapter(this,apiResponse.getMeanings());
        recycler_meanings.setAdapter(meaningsAdapter);
    }
}