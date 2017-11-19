package com.abdullahsen.veritabani;

import com.abdullahsen.model.*;
import com.sun.crypto.provider.RSACipher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeritabaniIslemleri {

	private Connection conn = null;
	private String veritabaniURL;
	private String kullaniciAdi;
	private String sifre;
	private String[] tabloIsimleri = { "Yorum", "Girdi", "Kullanicilar", "Blog" };

	public VeritabaniIslemleri() {
		this.veritabaniURL = "jdbc:mysql://localhost:3306/BlogUygulamasi?useUnicode=true&characterEncoding=UTF-8";
		this.kullaniciAdi = "root";
		this.sifre = "aa1991";
		System.out.println("bağlantı hazır");
	}

	public VeritabaniIslemleri(String veritabaniURL, String kullaniciAdi, String sifre) {
		this.veritabaniURL = veritabaniURL;
		this.kullaniciAdi = kullaniciAdi;
		this.sifre = sifre;
	}

	public void baglan() throws Exception {
		if (conn != null) {
			if (conn.isClosed() == false) {
				System.out.println("Burda");
				return;

			}
		}

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.veritabaniURL, this.kullaniciAdi, this.sifre);
	}

	public void baglantiyiKes() throws SQLException {
		if (conn != null) {
			if (!conn.isClosed()) {
				conn.close();
			}
		}
	}

	public Kullanici kullaniciBilgisiniGetir(String kullaniciEmail, String kullaniciSifre) throws SQLException {

		String sorgu = "SELECT * FROM Kullanicilar WHERE KullaniciEmail = ? AND KullaniciSifre = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setString(1, kullaniciEmail);
		preparedStatement.setString(2, kullaniciSifre);
		ResultSet resultSet = preparedStatement.executeQuery();
		Kullanici k = null;
		if (resultSet.next()) {
			k = new Kullanici();
			k.setKullaniciID(resultSet.getInt("KullaniciID"));
			k.setKullaniciEmail(resultSet.getString("KullaniciEmail"));
			k.setKullaniciSifre(resultSet.getString("KullaniciSifre"));
			k.setKullaniciAdSoyad(resultSet.getString("KullaniciAdSoyad"));
			k.setKullaniciIzin(resultSet.getString("KullaniciIzin"));
			k.setKayitTarih(resultSet.getTimestamp("KayıtTarih"));
		}
		preparedStatement.close();
		resultSet.close();
		return k;
	}

	public Kullanici kullaniciBilgisiniGetir(String kullaniciEmail) throws SQLException {
		String sorgu = "SELECT * FROM Kullanicilar WHERE KullaniciEmail = ? ";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setString(1, kullaniciEmail);
		ResultSet resultSet = preparedStatement.executeQuery();
		Kullanici k = null;

		if (resultSet.next()) {
			k = new Kullanici();
			k.setKullaniciID(resultSet.getInt("KullaniciID"));
			k.setKullaniciEmail(resultSet.getString("KullaniciEmail"));
			k.setKullaniciSifre(resultSet.getString("KullaniciSifre"));
			k.setKullaniciAdSoyad(resultSet.getString("KullaniciAdSoyad"));
			k.setKullaniciIzin(resultSet.getString("KullaniciIzin"));
			k.setKayitTarih(resultSet.getTimestamp("KayıtTarih"));
		}
		preparedStatement.close();
		resultSet.close();
		return k;
	}

	public Kullanici kullaniciBilgisiniGetir(int kullaniciID) throws SQLException {
		String sorgu = "SELECT * FROM Kullanicilar WHERE KullaniciID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, kullaniciID);
		ResultSet resultSet = preparedStatement.executeQuery();
		Kullanici k = null;

		if (resultSet.next()) {
			k = new Kullanici();
			k.setKullaniciID(resultSet.getInt("KullaniciID"));
			k.setKullaniciEmail(resultSet.getString("KullaniciEmail"));
			k.setKullaniciSifre(resultSet.getString("KullaniciSifre"));
			k.setKullaniciAdSoyad(resultSet.getString("KullaniciAdSoyad"));
			k.setKullaniciIzin(resultSet.getString("KullaniciIzin"));
			k.setKayitTarih(resultSet.getTimestamp("KayıtTarih"));
		}
		preparedStatement.close();
		resultSet.close();
		return k;
	}

	public List<Kullanici> tumKullanicilariGetir() throws SQLException {
		String sorgu = "SELECT * FROM Kullanicilar";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		ResultSet resultSet = preparedStatement.executeQuery();

		List<Kullanici> kullaniciList = null;

		if (resultSet.next())
			kullaniciList = new ArrayList<Kullanici>();

		resultSet.beforeFirst();
		while (resultSet.next()) {
			Kullanici k = new Kullanici();
			k.setKullaniciID(resultSet.getInt("KullaniciID"));
			k.setKullaniciEmail(resultSet.getString("KullaniciEmail"));
			k.setKullaniciSifre(resultSet.getString("KullaniciSifre"));
			k.setKullaniciAdSoyad(resultSet.getString("KullaniciAdSoyad"));
			k.setKullaniciIzin(resultSet.getString("KullaniciIzin"));
			k.setKayitTarih(resultSet.getTimestamp("KayitTarih"));
			kullaniciList.add(k);
		}
		preparedStatement.close();
		resultSet.close();
		return kullaniciList;

	}

	public boolean blogOlustur(Blog b) throws SQLException {
		String sorgu = "INSERT INTO Blog VALUES (Default,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, b.getKullaniciID());
		preparedStatement.setString(2, b.getBlogBaslik());
		preparedStatement.setString(3, b.getAciklama());
		preparedStatement.setTimestamp(4, b.getOlusturmaTarih());
		int sonuc = preparedStatement.executeUpdate();
		preparedStatement.close();
		return (sonuc > 0);
	}

	public Blog blogBilgisiniGetir(int blogID) throws SQLException {
		String sorgu = "SELECT * FROM Blog WHERE BlogID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, blogID);
		ResultSet resultSet = preparedStatement.executeQuery();
		Blog b = null;
		if (resultSet.next()) {
			b = new Blog();
			b.setBlogID(resultSet.getInt("BlogID"));
			b.setKullaniciID(resultSet.getInt("KullaniciID"));
			b.setBlogBaslik(resultSet.getString("BlogBaslik"));
			b.setAciklama(resultSet.getString("Aciklama"));
			b.setOlusturmaTarih(resultSet.getTimestamp("OlusturmaTarih"));
		}
		preparedStatement.close();
		resultSet.close();
		return b;
	}

	public List<Blog> tumBloglarıGetir() throws SQLException {
		String sorgu = "SELECT * FROM Blog";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Blog> blogList = null;
		if (resultSet.next())
			blogList = new ArrayList<Blog>();

		resultSet.beforeFirst();
		while (resultSet.next()) {
			Blog b = new Blog();
			b.setBlogID(resultSet.getInt("BlogID"));
			b.setKullaniciID(resultSet.getInt("KullaniciID"));
			b.setBlogBaslik(resultSet.getString("BlogBaslik"));
			b.setAciklama(resultSet.getString("Aciklama"));
			b.setOlusturmaTarih(resultSet.getTimestamp("OlusturmaTarih"));
			blogList.add(b);
		}
		preparedStatement.close();
		resultSet.close();
		return blogList;
	}

	public List<Blog> birKullaniciyaAitTumBloglarıGetir(Kullanici k) throws SQLException {
		String sorgu = "SELECT * FROM Blog WHERE KullaniciID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, k.getKullaniciID());
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Blog> blogList = null;
		if (resultSet.next())
			blogList = new ArrayList<>();
		resultSet.beforeFirst();
		while (resultSet.next()) {
			Blog b = new Blog();
			b.setBlogID(resultSet.getInt("BlogID"));
			b.setKullaniciID(resultSet.getInt("KullaniciID"));
			b.setBlogBaslik(resultSet.getString("BlogBaslik"));
			b.setAciklama(resultSet.getString("Aciklama"));
			b.setOlusturmaTarih(resultSet.getTimestamp("OlusturmaTarih"));
			blogList.add(b);
		}
		preparedStatement.close();
		resultSet.close();
		return blogList;
	}

	
	public List<Girdi> tumGirdileriGetir (int blogID) throws SQLException{
		String sorgu = "SELECT * FROM Girdi WHERE BlogID = ? ORDER BY GirdiTarih DESC";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, blogID);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (!resultSet.next())
			return null;
		List<Girdi> girdiList = new ArrayList<>();
		resultSet.beforeFirst();
		while(resultSet.next()) {
			Girdi g = new Girdi();
			g.setGirdiID(resultSet.getInt("GirdiID"));
			g.setBlogID(resultSet.getInt("BlogID"));
			g.setKullaniciID(resultSet.getInt("KullaniciID"));
			g.setGirdiBaslik(resultSet.getString("GirdiBaslik"));
			g.setGirdiIcerik(resultSet.getString("GirdiIcerik"));
			g.setGirdiTarih(resultSet.getTimestamp("GirdiTarih"));
			girdiList.add(g);
		}
		return girdiList;
	}
	
	
	public List<Girdi> birBlogaAitTumGirdileriGetir(Blog b) throws SQLException {
		String sorgu = "SELECT * FROM Girdi WHERE BlogID = ? ";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, b.getBlogID());
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Girdi> girdiList = null;
		if (resultSet.next())
			girdiList = new ArrayList<>();
		resultSet.beforeFirst();
		while (resultSet.next()) {
			Girdi g = new Girdi();
			g.setGirdiID(resultSet.getInt("GirdiID"));
			g.setBlogID(resultSet.getInt("BlogID"));
			g.setKullaniciID(resultSet.getInt("KullaniciID"));
			g.setGirdiBaslik(resultSet.getString("GirdiBaslik"));
			g.setGirdiIcerik(resultSet.getString("GirdiIcerik"));
			g.setGirdiTarih(resultSet.getTimestamp("GirdiTarih"));
			girdiList.add(g);
		}
		preparedStatement.close();
		resultSet.close();
		return girdiList;

	}

	public List<Yorum> birGirdiyeAitTumYorumlariGetir(Girdi g) throws SQLException {
		String sorgu = "SELECT * FROM Yorum WHERE GirdiID = ? ";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, g.getGirdiID());
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Yorum> yorumList = null;
		if (resultSet.next())
			yorumList = new ArrayList<>();
		resultSet.beforeFirst();
		while (resultSet.next()) {
			Yorum y = new Yorum();
			y.setYorumID(resultSet.getInt("YorumID"));
			y.setGirdiID(resultSet.getInt("GirdiID"));
			y.setKullaniciID(resultSet.getInt("KullaniciID"));
			y.setYorumBaslik(resultSet.getString("YorumBaslik"));
			y.setYorumIcerik(resultSet.getString("YorumIcerik"));
			y.setYorumTarih(resultSet.getTimestamp("YorumTarih"));
			yorumList.add(y);
		}
		preparedStatement.close();
		resultSet.close();
		return yorumList;

	}
	
	public List<YorumDetay> birGirdiyeAitTumYorumDetaylariniGetir (int girdiID) throws SQLException{
		String sorgu = "SELECT * FROM Yorum WHERE GirdiID = ? ORDER BY YorumTarih DESC";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, girdiID);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<YorumDetay> yorumList = null;
		if (resultSet.next())
			yorumList = new ArrayList<>();
		resultSet.beforeFirst();
		
		while(resultSet.next()) {
			YorumDetay y = new YorumDetay();
			y.setYorumID(resultSet.getInt("YorumID"));
			y.setGirdiID(resultSet.getInt("GirdiID"));
			y.setKullaniciID(resultSet.getInt("KullaniciID"));
			y.setYorumBaslik(resultSet.getString("YorumBaslik"));
			y.setYorumIcerik(resultSet.getString("YorumIcerik"));
			y.setYorumTarih(resultSet.getTimestamp("YorumTarih"));
			y.setKullanici(kullaniciBilgisiniGetir(y.getKullaniciID()));
			yorumList.add(y);
		}
		preparedStatement.close();
		resultSet.close();
		return yorumList;
	}

	public boolean yorumuSil(Yorum y) throws SQLException {
		String sorgu = "DELETE FROM Yorum WHERE YorumID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, y.getYorumID());
		boolean sonuc = (preparedStatement.executeUpdate() > 0);
		preparedStatement.close();
		return sonuc;

	}

	public boolean girdiyiSil(Girdi g) throws SQLException {
		String sorgu = "DELETE FROM Girdi WHERE GirdiID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, g.getGirdiID());
		boolean sonuc = (preparedStatement.executeUpdate() > 0);
		preparedStatement.close();
		return sonuc;

	}

	public boolean kullaniciyiSil(Kullanici k) throws SQLException {
		String sorgu = "DELETE FROM Kullanicilar WHERE KullaniciID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, k.getKullaniciID());
		boolean sonuc = (preparedStatement.executeUpdate() > 0);
		preparedStatement.close();
		return sonuc;
	}

	public boolean kullaniciBilgisiGuncelle(Kullanici k) throws SQLException {
		String sorgu = "UPDATE Kullanicilar SET KullaniciEmail = ? , KullaniciSifre = ?, "
				+ "KullaniciAdSoyad = ?, KullaniciIzin = ? WHERE KullaniciID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setString(1, k.getKullaniciEmail());
		preparedStatement.setString(2, k.getKullaniciSifre());
		preparedStatement.setString(3, k.getKullaniciAdSoyad());
		preparedStatement.setString(4, k.getKullaniciIzin());
		preparedStatement.setInt(5, k.getKullaniciID());
		int sonuc = preparedStatement.executeUpdate();
		preparedStatement.close();
		return (sonuc > 0);
	}

	public boolean blogBilgisiniGuncelle(Blog b) throws SQLException {
		String sorgu = "UPDATE Blog SET BlogBaslik = ?, Aciklama = ?, " + "KullaniciID = ? WHERE BlogID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setString(1, b.getBlogBaslik());
		preparedStatement.setString(2, b.getAciklama());
		preparedStatement.setInt(3, b.getKullaniciID());
		preparedStatement.setInt(4, b.getBlogID());
		boolean sonuc = (preparedStatement.executeUpdate() > 0);
		preparedStatement.close();
		return sonuc;

	}
	
	public boolean girdiBilgisiniGuncelle(Girdi g) throws SQLException{
		String sorgu = "UPDATE Girdi SET GirdiBaslik = ?, GirdiIcerik = ?, GirdiTarih = ? Where GirdiID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setString(1, g.getGirdiBaslik());
		preparedStatement.setString(2, g.getGirdiIcerik());
		preparedStatement.setTimestamp(3, Araclar.yeniTimeStampOlustur());
		preparedStatement.setInt(4, g.getGirdiID());
		int sonuc = preparedStatement.executeUpdate();
		preparedStatement.close();
		return (sonuc > 0);
		
	}

	public boolean bloguSil(Blog b) throws SQLException {
		String sorgu = "DELETE FROM Blog WHERE BlogID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, b.getBlogID());
		boolean sonuc = (preparedStatement.executeUpdate() > 0);
		preparedStatement.close();
		return sonuc;
	}

	public String[] tabloKolonAdlariniGetir(String tabloAdi) throws SQLException {
		String sorgu = "SELECT * FROM " + tabloAdi;
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		ResultSetMetaData resultSetMetaData = preparedStatement.getMetaData();

		int sayi = resultSetMetaData.getColumnCount();
		String[] sonuc = new String[sayi];
		for (int i = 0; i < sayi; i++) {
			sonuc[i] = resultSetMetaData.getCatalogName(i + 1);
		}
		preparedStatement.close();
		return sonuc;
	}

	public void kullanicilariListedenEkle(List<Kullanici> kullaniciListesi) throws SQLException {
		String sorgu = "INSERT INTO Kullanicilar VALUES (DEFAULT, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = null;
		try {
			conn.setAutoCommit(false);
			preparedStatement = conn.prepareStatement(sorgu);
			for (Kullanici k : kullaniciListesi) {
				preparedStatement.setString(1, k.getKullaniciEmail());
				preparedStatement.setString(2, k.getKullaniciSifre());
				preparedStatement.setString(3, k.getKullaniciAdSoyad());
				preparedStatement.setString(4, k.getKullaniciIzin());
				preparedStatement.setTimestamp(5, k.getKayitTarih());
				preparedStatement.executeUpdate();
			}
			conn.commit();
			conn.setAutoCommit(true);
			preparedStatement.close();
		} catch (SQLException ex) {
			conn.rollback();
			throw ex;
		}

	}

	public void tabloIceriginiSil(String tabloAdi) throws SQLException {
		String sorgu = "DELETE FROM " + tabloAdi;
		Statement statement = null;
		statement = conn.createStatement();
		statement.executeUpdate(sorgu);
		statement.close();
	}

	public boolean kullaniciOlustur(Kullanici k) throws SQLException {
		String sorgu = "INSERT INTO Kullanicilar VALUES (DEFAULT,?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setString(1, k.getKullaniciEmail());
		preparedStatement.setString(2, k.getKullaniciSifre());
		preparedStatement.setString(3, k.getKullaniciAdSoyad());
		preparedStatement.setString(4, k.getKullaniciIzin());
		preparedStatement.setTimestamp(5, k.getKayitTarih());

		int sonuc = preparedStatement.executeUpdate();
		preparedStatement.close();
		return (sonuc > 0);
	}

	public boolean girdiOlustur(Girdi g) throws SQLException {
		String sorgu = "INSERT INTO Girdi VALUES (DEFAULT, ?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, g.getBlogID());
		preparedStatement.setInt(2, g.getKullaniciID());
		preparedStatement.setString(3, g.getGirdiBaslik());
		preparedStatement.setString(4, g.getGirdiIcerik());
		preparedStatement.setTimestamp(5, g.getGirdiTarih());
		int sonuc = preparedStatement.executeUpdate();
		preparedStatement.close();
		return (sonuc > 0);

	}

	public boolean yorumOlustur(Yorum y) throws SQLException {
		String sorgu = "INSERT INTO Yorum VALUES (DEFAULT,?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, y.getGirdiID());
		preparedStatement.setInt(2, y.getKullaniciID());
		preparedStatement.setString(3, y.getYorumBaslik());
		preparedStatement.setString(4, y.getYorumIcerik());
		preparedStatement.setTimestamp(5, y.getYorumTarih());
		int sonuc = preparedStatement.executeUpdate();
		preparedStatement.close();
		return (sonuc > 0);
	}

	public boolean emailKayitliMi(String email) throws SQLException {
		String sorgu = "SELECT KullaniciEmail FROM Kullanicilar where KullaniciEmail = ? ";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setString(1, email);
		boolean sonuc = preparedStatement.execute();
		preparedStatement.close();
		return sonuc;
	}

	public int kullaniciIDsiniBul(String kullaniciEmail) throws SQLException {
		String sorgu = "SELECT KullaniciID from Kullanicilar where KullaniciEmail = ? ";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setString(1, kullaniciEmail);
		ResultSet resultSet = preparedStatement.executeQuery();

		int sonuc = 0;

		if (resultSet.next())
			sonuc = resultSet.getInt("KullaniciID");

		preparedStatement.close();
		resultSet.close();
		return sonuc;
	}

	public int blogIDsiniBul(Timestamp olusturmaTarih) throws SQLException {
		String sorgu = "SELECT BlogID FROM Blog WHERE OlusturmaTarih = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setTimestamp(1, olusturmaTarih);
		ResultSet resultSet = preparedStatement.executeQuery();

		int sonuc = 0;

		if (resultSet.next())
			sonuc = resultSet.getInt("BlogID");

		preparedStatement.close();
		resultSet.close();

		return sonuc;
	}

	public int girdiIDsiniBul(Timestamp olusturmaTarih) throws SQLException {
		String sorgu = "SELECT GirdiID FROM Girdi WHERE GirdiTarih = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setTimestamp(1, olusturmaTarih);
		ResultSet resultSet = preparedStatement.executeQuery();

		int sonuc = 0;

		if (resultSet.next())
			sonuc = resultSet.getInt("GirdiID");

		preparedStatement.close();
		resultSet.close();

		return sonuc;
	}

	public BlogDetay blogDetayGetir(int blogID) throws SQLException {
		Blog b = blogBilgisiniGetir(blogID);
		BlogDetay blogDetay = new BlogDetay(b);
		Kullanici k = kullaniciBilgisiniGetir(b.getKullaniciID());
		blogDetay.setKullanici(k);
		int[] istatistik = blogIstatistikGetir(b.getBlogID());
		blogDetay.setGirdiSayisi(istatistik[0]);
		blogDetay.setYorumSayisi(istatistik[1]);
		return blogDetay;
	}

	public int[] blogIstatistikGetir(int blogID) throws SQLException {
		int[] res = new int[2];
		String sorgu = "SELECT count(*) FROM Girdi where BlogID = ? ";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, blogID);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next())
			res[0] = resultSet.getInt(1);

		sorgu = "SELECT count(*) FROM Yorum y WHERE y.GirdiID in (Select g.GirdiID FROM Girdi g WHERE g.BlogID = ?)";
		preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, blogID);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next())
			res[1] = resultSet.getInt(1);

		preparedStatement.close();
		resultSet.close();
		return res;

	}

	public GirdiDetay enGuncelGirdiyiGetir(int blogID) throws SQLException {
		String sorgu = "SELECT * FROM Girdi WHERE BlogID = ? ORDER BY GirdiTarih DESC LIMIT 1";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, blogID);
		ResultSet resultSet = preparedStatement.executeQuery();
		GirdiDetay g = new GirdiDetay();
		if (!resultSet.next()) {
			return null;
		}
		g.setGirdiID(resultSet.getInt("GirdiID"));
		g.setBlogID(resultSet.getInt("BlogID"));
		g.setKullaniciID(resultSet.getInt("KullaniciID"));
		g.setGirdiBaslik(resultSet.getString("GirdiBaslik"));
		g.setGirdiIcerik(resultSet.getString("GirdiIcerik"));
		g.setGirdiTarih(resultSet.getTimestamp("GirdiTarih"));
		g.setYorumlar(birGirdiyeAitEnGuncelUcYorumDetayiniGetir(g.getGirdiID()));
		g.setYorumSayisi(birGirdiyeAitYorumSayisiniGetir(g.getGirdiID()));
		return g;

	}

	public int birGirdiyeAitYorumSayisiniGetir(int girdiID) throws SQLException {
		int sonuc = 0;
		String sorgu = "SELECT count(*) FROM Yorum WHERE GirdiID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, girdiID);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next())
			sonuc = resultSet.getInt(1);

		preparedStatement.close();
		resultSet.close();
		return sonuc;
	}

	public List<Girdi> enGuncelBesGirdiyiGetir(int blogID) throws SQLException {
		String sorgu = "SELECT * FROM Girdi WHERE BlogID = ? ORDER BY GirdiTarih DESC LIMIT 5";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, blogID);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Girdi> girdiList  = new ArrayList<>();
		if (!resultSet.next())
			return null;

		resultSet.beforeFirst();
		while (resultSet.next()) {
			Girdi g = new Girdi();
			g.setGirdiID(resultSet.getInt("GirdiID"));
			g.setKullaniciID(resultSet.getInt("KullaniciID"));
			g.setGirdiBaslik(resultSet.getString("GirdiBaslik"));
			g.setGirdiIcerik(resultSet.getString("GirdiIcerik"));
			g.setGirdiTarih(resultSet.getTimestamp("GirdiTarih"));

			girdiList.add(g);
		}
		preparedStatement.close();
		resultSet.close();
		return girdiList;

	}

	public List<YorumDetay> birGirdiyeAitEnGuncelUcYorumDetayiniGetir(int girdiID) throws SQLException {
		String sorgu = "SELECT * FROM Yorum WHERE GirdiID = ? ORDER BY YorumTarih DESC LIMIT 3";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, girdiID);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<YorumDetay> yorumList = null;
		if (resultSet.next())
			yorumList = new ArrayList<>();
		resultSet.beforeFirst();

		while (resultSet.next()) {
			YorumDetay y = new YorumDetay();
			y.setYorumID(resultSet.getInt("YorumID"));
			y.setGirdiID(resultSet.getInt("GirdiID"));
			y.setKullaniciID(resultSet.getInt("KullaniciID"));
			y.setYorumBaslik(resultSet.getString("YorumBaslik"));
			y.setYorumIcerik(resultSet.getString("YorumIcerik"));
			y.setYorumTarih(resultSet.getTimestamp("YorumTarih"));
			y.setKullanici(kullaniciBilgisiniGetir(y.getKullaniciID()));
			yorumList.add(y);
		}
		preparedStatement.close();
		resultSet.close();
		return yorumList;
	}

	public GirdiDetay seciliGirdininDetayiniGetir(int girdiID) throws SQLException {
		String sorgu = "SELECT * FROM Girdi WHERE GirdiID = ? ORDER BY GirdiTarih DESC LIMIT 1";
		GirdiDetay g = new GirdiDetay();
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, girdiID);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (!resultSet.next())
			return null;

		g.setGirdiID(resultSet.getInt("GirdiID"));
		g.setBlogID(resultSet.getInt("BlogID"));
		g.setKullaniciID(resultSet.getInt("KullaniciID"));
		g.setGirdiBaslik(resultSet.getString("GirdiBaslik"));
		g.setGirdiIcerik(resultSet.getString("GirdiIcerik"));
		g.setGirdiTarih(resultSet.getTimestamp("GirdiTarih"));
		g.setYorumlar(birGirdiyeAitEnGuncelUcYorumDetayiniGetir(g.getGirdiID()));
		g.setYorumSayisi(birGirdiyeAitYorumSayisiniGetir(g.getGirdiID()));
		return g;

	}
	public boolean yorumBilgisiGuncelle(Yorum y) throws SQLException{
		String sorgu = "UPDATE Yorum SET YorumBaslik = ?, YorumIcerik = ? , YorumTarih = ? WHERE YorumID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setString(1, y.getYorumBaslik());
		preparedStatement.setString(2, y.getYorumIcerik());
		preparedStatement.setTimestamp(3, Araclar.yeniTimeStampOlustur());
		preparedStatement.setInt(4, y.getYorumID());
		int sonuc = preparedStatement.executeUpdate();
		preparedStatement.close();
		return (sonuc>0);
		
	}

	public Yorum yorumBilgisiniGetir(int yorumID) throws SQLException {
		String sorgu = "SELECT * FROM Yorum WHERE YorumID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
		preparedStatement.setInt(1, yorumID);
		ResultSet resultSet = preparedStatement.executeQuery();
		Yorum y = null;
		if (resultSet.next()) {
			y = new Yorum();
			y.setYorumID(resultSet.getInt("YorumID"));
			y.setGirdiID(resultSet.getInt("GirdiID"));
			y.setKullaniciID(resultSet.getInt("KullaniciID"));
			y.setYorumBaslik(resultSet.getString("YorumBaslik"));
			y.setYorumIcerik(resultSet.getString("YorumIcerik"));
			y.setYorumTarih(resultSet.getTimestamp("YorumTarih"));
			}
		preparedStatement.close();
		resultSet.close();
		return y;
	}

}
