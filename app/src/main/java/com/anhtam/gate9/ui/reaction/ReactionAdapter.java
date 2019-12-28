package com.anhtam.gate9.ui.reaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anhtam.gate9.R;

public class ReactionAdapter extends RecyclerView.Adapter<ReactionAdapter.ReactionViewHoder> {

    @NonNull
    @Override
    public ReactionViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feelling_user, parent,false);
        return new ReactionViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReactionViewHoder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 10;
    }

    class ReactionViewHoder extends RecyclerView.ViewHolder{

        public ReactionViewHoder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
