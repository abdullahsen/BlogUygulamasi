package com.abdullahsen.beans;

import com.abdullahsen.model.*;
import com.abdullahsen.veritabani.VeritabaniIslemleri;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@ManagedBean(name = "KullaniciBean")
@SessionScoped

public class KullaniciBean implements Serializable {

	private String kullaniciAdi;
	private String sifre;
	private Kullanici kullaniciBilgisi = null;
	private List<Blog> blogListesi = null;
	private BlogDetay seciliBlog = null;
	private GirdiDetay seciliGirdi = null;
	private Yorum seciliYorum = null;
	private List<Girdi> girdiListesi = null;
	private List<Girdi> enGuncelBesGirdi = null;

	private String blogBaslik = null;
	private String blogAciklama = null;
	private String girdiBaslik = null;
	private String girdiIcerik = null;
	private String yorumBaslik = null;
	private String yorumIcerik = null;

	public String girisYap() throws SQLException {
		try {
			VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
			veritabaniIslemleri.baglan();
			kullaniciBilgisi = veritabaniIslemleri.kullaniciBilgisiniGetir(kullaniciAdi, sifre);
			kullanicininBloglariniListele();
			if (kullaniciBilgisi != null) {
				return "kullanici";
			}
		} catch (Exception ex) {
			return "index";
		}
		return "index";

	}

	public String cikis() {
		this.kullaniciBilgisi = null;
		this.kullaniciAdi = null;
		this.sifre = null;
		this.blogListesi = null;
		this.seciliBlog = null;
		this.seciliGirdi = null;
		this.seciliYorum = null;
		return "index";

	}

	public String kullanicininBloglariniListele() {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		try {
			veritabaniIslemleri.baglan();
			blogListesi = veritabaniIslemleri.birKullaniciyaAitTumBloglarıGetir(this.kullaniciBilgisi);
			veritabaniIslemleri.baglantiyiKes();
		} catch (Exception e) {
			return null;
		}
		return "kullanici";
	}

	public String tumBloglarıListele() {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		try {
			veritabaniIslemleri.baglan();
			blogListesi = veritabaniIslemleri.tumBloglarıGetir();
			veritabaniIslemleri.baglantiyiKes();
		} catch (Exception e) {
			return null;
		}
		return "kullanici";
	}

	public String yeniBlog() {
		this.blogBaslik = "";
		this.blogAciklama = "";
		return "blogduzenle";
	}

	public boolean isKullaniciBlogSahibiVeyaAdminMi() {
		if (this.kullaniciBilgisi.getKullaniciIzin().charAt(0) == 'A')
			return true;
		else if ((this.kullaniciBilgisi.getKullaniciIzin().charAt(0) == 'Y')
				&& (this.seciliBlog.getKullanici().getKullaniciID() == this.kullaniciBilgisi.getKullaniciID()))
			return true;
		else
			return false;
	}

	public String blogaGit(Blog blog) {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		try {
			veritabaniIslemleri.baglan();
			int id = veritabaniIslemleri.blogIDsiniBul(blog.getOlusturmaTarih());
			blog.setBlogID(id);
			this.seciliBlog = veritabaniIslemleri.blogDetayGetir(blog.getBlogID());
			this.seciliGirdi = veritabaniIslemleri.enGuncelGirdiyiGetir(blog.getBlogID());
			this.enGuncelBesGirdi = veritabaniIslemleri.enGuncelBesGirdiyiGetir(blog.getBlogID());
			veritabaniIslemleri.baglantiyiKes();
			return "blog";

		} catch (Exception e) {
			System.out.println("BLoga gitmiyor");
			return null;
		}
	}
	
	public String bloguSil(Blog b) throws Exception {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		veritabaniIslemleri.baglan();
		veritabaniIslemleri.bloguSil(b);
		veritabaniIslemleri.baglantiyiKes();
		kullanicininBloglariniListele();
		return "kullanici?faces-redirect=true";

	}

	public String blogEkle() throws Exception{
		Blog b= new Blog();
		b.setAciklama(this.blogAciklama);
		b.setBlogBaslik(this.blogBaslik);
		b.setKullaniciID(this.kullaniciBilgisi.getKullaniciID());
		b.setOlusturmaTarih(Araclar.yeniTimeStampOlustur());
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		veritabaniIslemleri.baglan();
		veritabaniIslemleri.blogOlustur(b);
		veritabaniIslemleri.baglantiyiKes();
		return blogaGit(b);
	}
	
	public String girdiyeGit(Girdi girdi) {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		try {
			veritabaniIslemleri.baglan();
			this.seciliGirdi = veritabaniIslemleri.seciliGirdininDetayiniGetir(girdi.getGirdiID());
			veritabaniIslemleri.baglantiyiKes();
			return "blog";
		} catch (Exception e) {
			return null;
		}
	}

	public String girdiSil() throws Exception {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		veritabaniIslemleri.baglan();
		veritabaniIslemleri.girdiyiSil(this.seciliGirdi);
		this.enGuncelBesGirdi = veritabaniIslemleri.enGuncelBesGirdiyiGetir(this.seciliBlog.getBlogID());
		this.girdiListesi = veritabaniIslemleri.tumGirdileriGetir(this.seciliBlog.getBlogID());
		if (this.enGuncelBesGirdi != null)
			this.seciliGirdi = veritabaniIslemleri.enGuncelGirdiyiGetir(this.seciliBlog.getBlogID());
		veritabaniIslemleri.baglantiyiKes();
		return "blog";

	}
	
	public String yeniGirdi() {
		this.girdiBaslik = "";
		this.girdiIcerik = "";
		return "girdiduzenle";
	}

	public String girdiDuzenleSayfasi(Girdi g) throws Exception {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		veritabaniIslemleri.baglan();
		this.seciliGirdi = veritabaniIslemleri.seciliGirdininDetayiniGetir(g.getGirdiID());
		veritabaniIslemleri.baglantiyiKes();
		this.girdiBaslik = this.seciliGirdi.getGirdiBaslik();
		this.girdiIcerik = this.seciliGirdi.getGirdiIcerik();
		return "girdiduzenle";
	}
	
	public String tumGirdileriGetir() {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		try {
			veritabaniIslemleri.baglan();
			this.girdiListesi = veritabaniIslemleri.tumGirdileriGetir(this.seciliBlog.getBlogID());
			veritabaniIslemleri.baglantiyiKes();
			return "tumgirdiler";
		} catch (Exception e) {
			return null;
		}
	}
	
	public String tumGirdileriGetir (int blogID) {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		try {
			veritabaniIslemleri.baglan();
			this.seciliBlog = veritabaniIslemleri.blogDetayGetir(blogID);
			this.seciliGirdi = veritabaniIslemleri.enGuncelGirdiyiGetir(blogID);
			this.enGuncelBesGirdi = veritabaniIslemleri.enGuncelBesGirdiyiGetir(blogID);
			this.girdiListesi = veritabaniIslemleri.tumGirdileriGetir(blogID);
			veritabaniIslemleri.baglantiyiKes();
			return "tumgirdiler";
		} catch (Exception e) {
			return null;
		}
	}
	
	public String girdiEkle() throws Exception{
		Girdi g = new Girdi();
		g.setGirdiBaslik(this.girdiBaslik);
		g.setGirdiIcerik(this.girdiIcerik);
		g.setKullaniciID(this.getKullaniciBilgisi().getKullaniciID());
		g.setBlogID(this.seciliBlog.getBlogID());
		g.setGirdiTarih(Araclar.yeniTimeStampOlustur());
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		veritabaniIslemleri.baglan();
		veritabaniIslemleri.girdiOlustur(g);
		veritabaniIslemleri.baglantiyiKes();
		return blogaGit(seciliBlog);
	}
	
	public String girdiDuzenle() throws Exception{
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		veritabaniIslemleri.baglan();
		this.seciliGirdi.setGirdiBaslik(girdiBaslik);
		this.seciliGirdi.setGirdiIcerik(girdiIcerik);
		this.seciliGirdi.setGirdiTarih(Araclar.yeniTimeStampOlustur());
		veritabaniIslemleri.girdiBilgisiniGuncelle(this.seciliGirdi);
		this.enGuncelBesGirdi = veritabaniIslemleri.enGuncelBesGirdiyiGetir(this.seciliBlog.getBlogID());
		this.girdiListesi = veritabaniIslemleri.tumGirdileriGetir(this.seciliBlog.getBlogID());
		if (this.enGuncelBesGirdi != null) {
			this.seciliGirdi = veritabaniIslemleri.enGuncelGirdiyiGetir(this.seciliBlog.getBlogID());
		}
		veritabaniIslemleri.baglantiyiKes();
		return "blog";
	}
	
	public String tumYorumlarıListele () {
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		try {
			veritabaniIslemleri.baglan();
			this.seciliGirdi.setYorumlar(veritabaniIslemleri.birGirdiyeAitTumYorumDetaylariniGetir(this.seciliGirdi.getGirdiID()));
			this.seciliGirdi.getGirdiID();
			veritabaniIslemleri.baglantiyiKes();
			return "blog";
		} catch (Exception e) {
			return null;
		}
	}
	
	public String yorumDuzenleSayfasi() throws Exception{
		this.yorumBaslik = "";
		this.yorumIcerik = "";
		return "yorumduzenle";
	}
	
	public String yorumDuzenleSayfasi(YorumDetay yorumDetay) throws Exception{
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		veritabaniIslemleri.baglan();
		this.seciliYorum = veritabaniIslemleri.yorumBilgisiniGetir(yorumDetay.getYorumID()); // burada değişiklik yapılacak. veritabanı içerisindeki metodun düzenlenmesi gerekecek.
		veritabaniIslemleri.baglantiyiKes();
		this.yorumBaslik = this.seciliYorum.getYorumBaslik();
		this.yorumIcerik = this.seciliYorum.getYorumIcerik();
		return "yorumduzenle";
		
	}

	public String yorumSil(YorumDetay yorumDetay) throws Exception{
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		veritabaniIslemleri.baglan();
		veritabaniIslemleri.yorumuSil(yorumDetay);
		veritabaniIslemleri.baglantiyiKes();
		return girdiyeGit(seciliGirdi);
	}
	
	public String yorumEkle() throws Exception{
		Yorum y = new Yorum();
		y.setKullaniciID(this.kullaniciBilgisi.getKullaniciID());
		y.setYorumBaslik(yorumBaslik);
		y.setYorumIcerik(yorumIcerik);
		y.setGirdiID(this.seciliGirdi.getGirdiID());
		y.setYorumTarih(Araclar.yeniTimeStampOlustur());
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		veritabaniIslemleri.baglan();
		veritabaniIslemleri.yorumOlustur(y);
		veritabaniIslemleri.baglantiyiKes();
		return girdiyeGit(this.seciliGirdi);
	}
	
	public String yorumGuncelle() throws Exception{
		VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
		Yorum yorum = (Yorum) this.seciliYorum;
		yorum.setYorumBaslik(yorumBaslik);
		yorum.setYorumTarih(Araclar.yeniTimeStampOlustur());
		yorum.setYorumIcerik(yorumIcerik);
		veritabaniIslemleri.baglan();
		veritabaniIslemleri.yorumBilgisiGuncelle(yorum);
		veritabaniIslemleri.baglantiyiKes();
		return girdiyeGit(seciliGirdi);
	}
	

	public int getKullanicininBlogSayisi() {
		if (blogListesi != null)
			return blogListesi.size();
		else
			return 0;
	}

	public String getKullaniciAdi() {
		return kullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public Kullanici getKullaniciBilgisi() {
		return kullaniciBilgisi;
	}

	public void setKullaniciBilgisi(Kullanici kullaniciBilgisi) {
		this.kullaniciBilgisi = kullaniciBilgisi;
	}

	public List<Blog> getBlogListesi() {
		return blogListesi;
	}

	public void setBlogListesi(List<Blog> blogListesi) {
		this.blogListesi = blogListesi;
	}

	public BlogDetay getSeciliBlog() {
		return seciliBlog;
	}

	public void setSeciliBlog(BlogDetay seciliBlog) {
		this.seciliBlog = seciliBlog;
	}

	public GirdiDetay getSeciliGirdi() {
		return seciliGirdi;
	}

	public void setSeciliGirdi(GirdiDetay seciliGirdi) {
		this.seciliGirdi = seciliGirdi;
	}

	public Yorum getSeciliYorum() {
		return seciliYorum;
	}

	public void setSeciliYorum(Yorum seciliYorum) {
		this.seciliYorum = seciliYorum;
	}

	public List<Girdi> getGirdiListesi() {
		return girdiListesi;
	}

	public void setGirdiListesi(List<Girdi> girdiListesi) {
		this.girdiListesi = girdiListesi;
	}

	public List<Girdi> getEnGuncelBesGirdi() {
		return enGuncelBesGirdi;
	}

	public void setEnGuncelBesGirdi(List<Girdi> enGuncelBesGirdi) {
		this.enGuncelBesGirdi = enGuncelBesGirdi;
	}

	public String getBlogBaslik() {
		return blogBaslik;
	}

	public void setBlogBaslik(String blogBaslik) {
		this.blogBaslik = blogBaslik;
	}

	public String getBlogAciklama() {
		return blogAciklama;
	}

	public void setBlogAciklama(String blogAciklama) {
		this.blogAciklama = blogAciklama;
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

}
