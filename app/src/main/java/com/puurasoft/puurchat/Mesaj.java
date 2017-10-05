package com.puurasoft.puurchat;

public class Mesaj {
    private String gonderen,mesaj,zaman,resimUrl;

    public Mesaj(){}

    public Mesaj(String gonderen, String mesaj, String zaman, String resimUrl) {
        this.gonderen = gonderen;
        this.mesaj = mesaj;
        this.zaman = zaman;
        this.resimUrl=resimUrl;
    }


    public String getGonderen() {return gonderen;}
    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }
    public String getMesaj() {
        return mesaj;
    }
    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }
    public String getZaman() {
        return zaman;
    }
    public void setZaman(String zaman) {
        this.zaman = zaman;
    }
    public String getResimUrl() {return resimUrl;}
    public void setResimUrl(String resimUrl) {this.resimUrl = resimUrl;}

}

