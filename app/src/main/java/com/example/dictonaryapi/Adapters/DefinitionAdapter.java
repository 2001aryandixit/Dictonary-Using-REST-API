package com.example.dictonaryapi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictonaryapi.Models.Definitions;
import com.example.dictonaryapi.R;
import com.example.dictonaryapi.ViewHolders.DefinitionViewHolder;
import com.example.dictonaryapi.ViewHolders.MeaningViewHolder;

import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {

    private Context context;
    private List<Definitions> definitionsList;

    public DefinitionAdapter(Context context, List<Definitions> definitionsList) {
        this.context = context;
        this.definitionsList = definitionsList;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefinitionViewHolder(LayoutInflater.from(context).inflate(R.layout.definition_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        holder.textView_definition.setText("Definition: "+definitionsList.get(position).getDefinition());
        holder.textView_example.setText("Example: "+definitionsList.get(position).getExample());
        //as synonym and antonym comes with list
        StringBuilder antonyms=new StringBuilder();
        StringBuilder synonyms=new StringBuilder();

        //append both to string builder
        antonyms.append(definitionsList.get(position).getAntonyms());
        synonyms.append(definitionsList.get(position).getSynonyms());

        //attach synonym and antonym to its text views
        holder.textView_synonyms.setText(synonyms);
        holder.textView_antonyms.setText(antonyms);

        holder.textView_synonyms.setSelected(true);
        holder.textView_antonyms.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return definitionsList.size();
    }
}
