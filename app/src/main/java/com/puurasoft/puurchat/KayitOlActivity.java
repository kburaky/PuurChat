package com.puurasoft.puurchat;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class KayitOlActivity extends AppCompatActivity {
    EditText et_sifre,et_email;
    Button buttonKayit;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        et_sifre= (EditText) findViewById(R.id.editTextSifre);
        et_email= (EditText) findViewById(R.id.editTextEmail);
        buttonKayit= (Button) findViewById(R.id.buttonKayitOl);

        mAuth=FirebaseAuth.getInstance();
        buttonKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sifre=et_sifre.getText().toString();
                String email=et_email.getText().toString();

                mAuth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                            Intent  intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Kayıt Başarısız",Toast.LENGTH_SHORT).show();}
                    }
                });}});
    }
}
