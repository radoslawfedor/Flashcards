package com.example.flashcards;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FlashcardsMenuAdapter extends RecyclerView.Adapter<FlashcardsMenuAdapter.FlashcardsMenuViewHolder> {

    private Database db;

    private FlashcardsMenu flashcardsMenu;

    private OnItemClickListener onItemClickListener;

    private ArrayList<FlashcardMenuItem> flashcardMenuItemArrayList;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onEditClick(int position);
        void onPlayClick(int position);
        void onFavouriteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public static class FlashcardsMenuViewHolder extends  RecyclerView.ViewHolder{

        private ImageView logoImageView;
        private TextView collectionNameTextView;
        private TextView numberOfElementsTextView;
        private ImageView playImageView;
        private ImageView deleteImageView;
        private ImageView editImageView;

        public FlashcardsMenuViewHolder(@NonNull View itemView, final Database db, final FlashcardsMenu flashcardsMenu, final OnItemClickListener onItemClickListener) {
            super(itemView);

            logoImageView = itemView.findViewById(R.id.logoImageView);
            collectionNameTextView = itemView.findViewById(R.id.collectionNameTextView);
            numberOfElementsTextView = itemView.findViewById(R.id.numberOfElementsTextView);
            playImageView = itemView.findViewById(R.id.playImageView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            editImageView = itemView.findViewById(R.id.editImageView);

            logoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onFavouriteClick(position);
                        }
                    }
                }
            });
            playImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onPlayClick(position);
                        }
                    }
                }
            });
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onDeleteClick(position);
                        }
                    }
                    flashcardsMenu.recreate();
                }
            });
            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }


    public FlashcardsMenuAdapter(ArrayList<FlashcardMenuItem> flashcardMenuItemArrayList, Database db,FlashcardsMenu flashcardsMenu){
        this.flashcardsMenu=flashcardsMenu;
        this.db=db;
        this.flashcardMenuItemArrayList=flashcardMenuItemArrayList;
    }

    @NonNull
    @Override
    public FlashcardsMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_menu_item,viewGroup,false);
        FlashcardsMenuViewHolder flashcardsMenuViewHolder = new FlashcardsMenuViewHolder(view, db, flashcardsMenu,this.onItemClickListener);
        return  flashcardsMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardsMenuViewHolder flashcardsMenuViewHolder, int i) {
        FlashcardMenuItem currentItem = flashcardMenuItemArrayList.get(i);

        flashcardsMenuViewHolder.logoImageView.setImageResource(currentItem.getLogoImageViewResource());
        flashcardsMenuViewHolder.numberOfElementsTextView.setText(currentItem.getCounterElementsText());
        flashcardsMenuViewHolder.collectionNameTextView.setText(currentItem.getNameCollection());
    }

    @Override
    public int getItemCount() {
        return this.flashcardMenuItemArrayList.size();
    }
}
