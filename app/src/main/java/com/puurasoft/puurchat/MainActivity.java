package com.puurasoft.puurchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class MainActivity extends AppCompatActivity {
    EditText et_userSifre,et_userEmail;
    Button buttonGirisYap;
    TextView tv_uyari;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_userSifre= (EditText) findViewById(R.id.editTextUserPassword);
        et_userEmail= (EditText) findViewById(R.id.editTextUserEmail);
        buttonGirisYap= (Button) findViewById(R.id.buttonGirisYap);
        tv_uyari= (TextView) findViewById(R.id.textViewKayitUyarisi);

        mAuth=FirebaseAuth.getInstance();
        buttonGirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=et_userEmail.getText().toString();
                String userSifre=et_userSifre.getText().toString();
                mAuth.signInWithEmailAndPassword(userEmail,userSifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent authIntent=new Intent(getApplicationContext(),ChatOdalariActivity.class);
                            startActivity(authIntent);}
                        else{
                            Toast.makeText(getApplicationContext(),"Giriş yapılamadı.",Toast.LENGTH_SHORT).show();}}
                });
            }});

        tv_uyari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uyariIntent=new Intent(getApplicationContext(),KayitOlActivity.class);
                startActivity(uyariIntent);
            }
        });
    }
}
