package com.puurasoft.puurchat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
public class CustomAdapter extends BaseAdapter{
    LayoutInflater layoutInflater;
    ArrayList<Mesaj> mesajList;
    FirebaseUser fUser;
    public CustomAdapter(Activity activity,ArrayList<Mesaj>mesajList,FirebaseUser fUser){
        this.mesajList=mesajList;
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fUser=fUser;}
    @Override
    public int getCount() {
        return mesajList.size();
    }

    @Override
    public Object getItem(int position) {
        return mesajList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satir = null;
        Mesaj mesaj=mesajList.get(position);
        if(mesaj.getGonderen().equals(fUser.getEmail())){
            satir=layoutInflater.inflate(R.layout.custom_sag,null);
            TextView mailim= (TextView) satir.findViewById(R.id.textViewBen);
            mailim.setText(mesaj.getGonderen());
            TextView mesajim= (TextView) satir.findViewById(R.id.textViewMesajim);
            TextView zamanim= (TextView) satir.findViewById(R.id.textViewZamanim);
            zamanim.setText(mesaj.getZaman());

            ImageView gonderilenResim= (ImageView) satir.findViewById(R.id.imageViewResmim);

            if(mesaj.getResimUrl()!=null){
                mesajim.setVisibility(View.GONE);
                gonderilenResim.setVisibility(View.VISIBLE);
                Glide.with(gonderilenResim.getContext())
                        .load(mesaj.getResimUrl())
                        .into(gonderilenResim);
            }
            else
            {
                mesajim.setVisibility(View.VISIBLE);
                gonderilenResim.setVisibility(View.GONE);
                mesajim.setText(mesaj.getMesaj());
            }

        }
        else{
            satir=layoutInflater.inflate(R.layout.custom_sol,null);
            TextView gonderenMail= (TextView) satir.findViewById(R.id.textViewGonderenKisi);
            gonderenMail.setText(mesaj.getGonderen());
            TextView mesaji= (TextView) satir.findViewById(R.id.textViewMesaji);
            TextView zamani= (TextView) satir.findViewById(R.id.textViewZamani);
            zamani.setText(mesaj.getZaman());

            ImageView alinanResim= (ImageView) satir.findViewById(R.id.imageViewResmi);
            if(mesaj.getResimUrl()!=null){
                alinanResim.setVisibility(View.VISIBLE);
                mesaji.setVisibility(View.GONE);
                Glide.with(alinanResim.getContext()).load(mesaj.getResimUrl()).into(alinanResim);
            }
            else{
                alinanResim.setVisibility(View.GONE);
                mesaji.setVisibility(View.VISIBLE);
                mesaji.setText(mesaj.getMesaj());
            }
        }
        return satir;
    }
}

