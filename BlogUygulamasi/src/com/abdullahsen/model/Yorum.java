package com.abdullahsen.model;

import java.sql.Timestamp;

public class Yorum {

    private int yorumID;
    private int girdiID;
    private int kullaniciID;
    private String yorumBaslik;
    private String yorumIcerik;
    private Timestamp yorumTarih;

    public void hosGeldinYorumuHazirla(Girdi g, Kullanici k){
        this.girdiID = g.getGirdiID();
        this.kullaniciID = k.getKullaniciID();
        this.yorumBaslik = "İlk yorum";
        this.yorumIcerik = "Merhaba "+ k.getKullaniciAdSoyad()+" bloguma hoş geldiniz.";
        this.yorumTarih = Araclar.yeniTimeStampOlustur();
    }

    public int getYorumID() {
        return yorumID;
    }

    public void setYorumID(int yorumID) {
        this.yorumID = yorumID;
    }

    public int getGirdiID() {
        return girdiID;
    }

    public void setGirdiID(int girdiID) {
        this.girdiID = girdiID;
    }

    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public String getYorumBaslik() {
        return yorumBaslik;
    }

    public void setYorumBaslik(String yorumBaslik) {
        this.yorumBaslik = yorumBaslik;
    }

    public String getYorumIcerik() {
        return yorumIcerik;
    }

    public void setYorumIcerik(String yorumIcerik) {
        this.yorumIcerik = yorumIcerik;
    }

    public Timestamp getYorumTarih() {
        return yorumTarih;
    }

    public void setYorumTarih(Timestamp yorumTarih) {
        this.yorumTarih = yorumTarih;
    }
}
