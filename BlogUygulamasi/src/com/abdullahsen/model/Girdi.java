package com.abdullahsen.model;

import java.sql.Timestamp;

public class Girdi {
    private int girdiID;
    private int blogID;
    private int kullaniciID;
    private String girdiBaslik;
    private String girdiIcerik;
    private Timestamp girdiTarih;

    public void hosgeldinGirdisiHazirla(Kullanici k, Blog b){
        this.blogID = b.getBlogID();
        this.kullaniciID = k.getKullaniciID();
        this.girdiBaslik = "Bloguma hoş geldiniz.";
        this.girdiIcerik = "Bu bir deneme girdisidir. Yeni girdi eklemek için giriş yapınız";
        this.girdiTarih = Araclar.yeniTimeStampOlustur();
    }

    public int getGirdiID() {
        return girdiID;
    }

    public void setGirdiID(int girdiID) {
        this.girdiID = girdiID;
    }

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public String getGirdiBaslik() {
        return girdiBaslik;
    }

    public void setGirdiBaslik(String girdiBaslik) {
        this.girdiBaslik = girdiBaslik;
    }

    public String getGirdiIcerik() {
        return girdiIcerik;
    }

    public void setGirdiIcerik(String girdiIcerik) {
        this.girdiIcerik = girdiIcerik;
    }

    public Timestamp getGirdiTarih() {
        return girdiTarih;
    }

    public void setGirdiTarih(Timestamp girdiTarih) {
        this.girdiTarih = girdiTarih;
    }
}
