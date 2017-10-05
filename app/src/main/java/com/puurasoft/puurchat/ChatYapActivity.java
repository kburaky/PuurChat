package com.puurasoft.puurchat;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

public class ChatYapActivity extends AppCompatActivity {

    TextView tvBaslik;
    EditText et_mesaj;
    Button buttonGonder;
    ListView lv_chatyap;
    ImageButton imageButton;

    FirebaseDatabase database;
    DatabaseReference dbRef;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    public static final int RC_SELECT_IMAGE=1;
    final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SELECT_IMAGE && resultCode==RESULT_OK){
            Uri secilenResim=data.getData();
            StorageReference resimRef=storageReference.child(secilenResim.getLastPathSegment());
            resimRef.putFile(secilenResim).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl=taskSnapshot.getDownloadUrl();
                    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:dd");
                    String zaman=sdf.format(new Date());
                    dbRef.push().setValue(new Mesaj(firebaseUser.getEmail(),"",zaman,downloadUrl.toString()));
                }
            });
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_yap);

        tvBaslik= (TextView) findViewById(R.id.textViewBaslik);
        et_mesaj= (EditText) findViewById(R.id.editTextMesaj);
        buttonGonder= (Button) findViewById(R.id.buttonMesajGonder);
        lv_chatyap= (ListView) findViewById(R.id.listViewChatYap);

        imageButton= (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resimSec=new Intent(Intent.ACTION_PICK);
                resimSec.setType("image/*");
                startActivityForResult(resimSec,RC_SELECT_IMAGE);
            }
        });
        lv_chatyap.setDivider(null);


        et_mesaj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0){
                    buttonGonder.setEnabled(true);}
                else{
                    buttonGonder.setEnabled(false);}
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        String oda= getIntent().getStringExtra("odaKey");
        tvBaslik.setText(oda);

        final ArrayList<Mesaj> mesajList=new ArrayList<Mesaj>();

        database=FirebaseDatabase.getInstance();
        dbRef=database.getReference("chats/"+oda);

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("chatImages/"+oda);

        buttonGonder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String gonderen=firebaseUser.getEmail();
                String mesaj=et_mesaj.getText().toString();
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:dd");
                String zaman=sdf.format(new Date());

                dbRef.push().setValue(new Mesaj(gonderen,mesaj,zaman,null));
                et_mesaj.setText("");
            }
        });

        final CustomAdapter adapter=new CustomAdapter(this,mesajList,firebaseUser);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mesajList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    mesajList.add(ds.getValue(Mesaj.class));
                }
                lv_chatyap.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
