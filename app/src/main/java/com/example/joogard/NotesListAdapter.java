package com.example.joogard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    public List<Notes> notesList;

    public NotesListAdapter(List<Notes> notesList){
        this.notesList=notesList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleText.setText(notesList.get(position).getTitle());
        holder.descriptionText.setText(notesList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView titleText;
        public  TextView descriptionText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            titleText=(TextView) mView.findViewById(R.id.title_text);
            descriptionText=(TextView) mView.findViewById(R.id.description_text);
        }
    }
}
