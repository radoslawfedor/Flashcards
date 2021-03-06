package com.example.flashcards;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class FlashcardsMenu extends AppCompatActivity {

    private final Database db = new Database(this);

    private RecyclerView recyclerView;
    private FlashcardsMenuAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addCollectionButton;

    public ArrayList<FlashcardMenuItem> collectionsList;

    private final int REQUEST_RECREATE=0;
    public String login;

    private Intent writhingFlashcardIntent;
    private Intent readingFlashcardIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        recyclerView=findViewById(R.id.recyclerView);
        addCollectionButton= findViewById(R.id.addCollectionButton);

        final Intent createCollectionIntent = new Intent(this,CreateCollectionActivity.class);
        final Intent editCollectionIntent = new Intent(this,EditCollectionActivity.class);
        writhingFlashcardIntent = new Intent(this, WrithingFlashcardActivity.class);
        readingFlashcardIntent = new Intent(this,ReadingFlashcardActivity.class);

        Bundle extras =getIntent().getExtras();
        final String LOGIN=extras.getString("login");
        this.login=LOGIN;

        addCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCollectionIntent.putExtra("login",LOGIN);
                startActivityForResult(createCollectionIntent,REQUEST_RECREATE);
            }
        });

        collectionsList=db.returnCollectionsArrayList(LOGIN);

        layoutManager= new LinearLayoutManager(this);

        adapter= new FlashcardsMenuAdapter(collectionsList, db,this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FlashcardsMenuAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                db.deleteCurrentCollection(collectionsList.get(position).getCollectionId());
            }

            @Override
            public void onEditClick(int position) {
                editCollectionIntent.putExtra("collectionId",db.getCollectionId(LOGIN,position));
                startActivityForResult(editCollectionIntent,REQUEST_RECREATE);
            }

            @Override
            public void onPlayClick(int position) {
                readingFlashcardIntent.putExtra("collectionId",db.getCollectionId(LOGIN,position));
                writhingFlashcardIntent.putExtra("collectionId",db.getCollectionId(LOGIN,position));
                AlertDialog alertDialog=dialogBox().create();
                alertDialog.show();
            }

            @Override
            public void onFavouriteClick(int position) {
                db.setFavourite(collectionsList.get(position).getCollectionId());
                recreate();
            }
        });
    }

    AlertDialog.Builder dialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Choose a learning way")
                .setCancelable(true)
                .setPositiveButton("By reading", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(readingFlashcardIntent);
                    }
                })
                .setNegativeButton("By writing", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(writhingFlashcardIntent);
                    }
                });
        return builder;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_RECREATE){
            if (resultCode == RESULT_OK){
                this.recreate();
            }
        }
    }
}
