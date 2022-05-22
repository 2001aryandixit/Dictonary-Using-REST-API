package com.example.dictonaryapi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictonaryapi.Models.Meanings;
import com.example.dictonaryapi.R;
import com.example.dictonaryapi.ViewHolders.DefinitionViewHolder;
import com.example.dictonaryapi.ViewHolders.MeaningViewHolder;
import com.example.dictonaryapi.ViewHolders.PhoneticViewHolder;

import java.util.List;

public class MeaningsAdapter extends RecyclerView.Adapter<MeaningViewHolder>{
    private Context context;
    private List<Meanings> meaningsList;

    public MeaningsAdapter(Context context, List<Meanings> meaningsList) {
        this.context = context;
        this.meaningsList = meaningsList;
    }

    @NonNull
    @Override
    public MeaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeaningViewHolder(LayoutInflater.from(context).inflate(R.layout.meanings_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningViewHolder holder, int position) {
        holder.textView_partsOfSpeech.setText("Parts of Speech: "+meaningsList.get(position).getPartOfSpeech());
        holder.recycler_definitions.setHasFixedSize(true);
        holder.recycler_definitions.setLayoutManager(new GridLayoutManager(context,1));

        //create definition adapter
        DefinitionAdapter definitionAdapter= new DefinitionAdapter(context,meaningsList.get(position).getDefinitions());

        //add adapter to recyclerview
        holder.recycler_definitions.setAdapter(definitionAdapter);
    }

    @Override
    public int getItemCount() {
        return meaningsList.size();
    }
}
