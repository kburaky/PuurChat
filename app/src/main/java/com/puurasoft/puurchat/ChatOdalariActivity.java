package com.puurasoft.puurchat;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatOdalariActivity extends AppCompatActivity {

    ListView lv_chatOdalari;
    EditText et_odaIsmi;
    Button buttonOdaEkle;
    ArrayList<String> chatOdalariList = new ArrayList<String>();
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_odalari);

        lv_chatOdalari = (ListView) findViewById(R.id.listView);
        et_odaIsmi = (EditText) findViewById(R.id.editTextNewChatName);
        buttonOdaEkle = (Button) findViewById(R.id.buttonOdaEkle);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, chatOdalariList);

        database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = database.getReference("chats");
        buttonOdaEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oda=et_odaIsmi.getText().toString();
                dbRef.child(oda).setValue("");
                et_odaIsmi.setText("");
            }
        });

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatOdalariList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    chatOdalariList.add(ds.getKey());
                }
                lv_chatOdalari.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv_chatOdalari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String secilenOda=chatOdalariList.get(position);
                Intent odaIntent=new Intent(getApplicationContext(),ChatYapActivity.class);
                odaIntent.putExtra("odaKey",secilenOda);
                startActivity(odaIntent);
            }
        });
    }



}