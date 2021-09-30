package com.amita.githubsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RepositoryAdapter extends RecyclerView.Adapter<RepoViewHolder>{

    Context mcontext;
    Repository[] repoarray;

    public RepositoryAdapter(Context mcontext, Repository[] repoarray) {
        this.mcontext = mcontext;
        this.repoarray = repoarray;
    }

    @NonNull
    @NotNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.itemrepo,parent,false);
        RepoViewHolder viewHolder = new RepoViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RepoViewHolder holder, int position) {
        Repository repo=repoarray[position];
        holder.reponame.setText(repo.name);
        holder.description.setText(repo.description);
        holder.language.setText(repo.language);
    }

    @Override
    public int getItemCount() {
        return repoarray.length;
    }
}

class RepoViewHolder extends RecyclerView.ViewHolder{

    TextView reponame , description , language;

    public RepoViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
        super(itemView);
        reponame = itemView.findViewById(R.id.name);
        description = itemView.findViewById(R.id.desc);
        language = itemView.findViewById(R.id.lang);
    }
}
