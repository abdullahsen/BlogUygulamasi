package com.abdullahsen.model;

import java.sql.Timestamp;

public class Blog {
    private int blogID;
    private int kullaniciID;
    private String blogBaslik;
    private String aciklama;
    private Timestamp olusturmaTarih;

    public Blog(){

    }

    public Blog(Blog b){
        this.blogID = b.blogID;
        this.kullaniciID = b.kullaniciID;
        this.blogBaslik = b.blogBaslik;
        this.aciklama = b.aciklama;
        this.olusturmaTarih = b.olusturmaTarih;
    }

    public void yeniKullaniciIcinBlogBilgisiHazirla(Kullanici k){
        this.kullaniciID = k.getKullaniciID();
        this.blogBaslik = "Merhaba. Ben "+k.getKullaniciAdSoyad()+". Bloguma hoş geldiniz.";
        this.aciklama = "Bu otomatik olarak oluşturulmuş bir yazıdır. Değişiklik yapmak için kullanıcı adı ve şifre ile giriş yapınız";
        this.olusturmaTarih = Araclar.yeniTimeStampOlustur();
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

    public String getBlogBaslik() {
        return blogBaslik;
    }

    public void setBlogBaslik(String blogBaslik) {
        this.blogBaslik = blogBaslik;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Timestamp getOlusturmaTarih() {
        return olusturmaTarih;
    }

    public void setOlusturmaTarih(Timestamp olusturmaTarih) {
        this.olusturmaTarih = olusturmaTarih;
    }
}
