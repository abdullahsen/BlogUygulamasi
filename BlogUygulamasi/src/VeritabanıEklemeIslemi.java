import com.abdullahsen.model.Blog;
import com.abdullahsen.model.Girdi;
import com.abdullahsen.model.Kullanici;
import com.abdullahsen.model.Yorum;
import com.abdullahsen.veritabani.VeritabaniIslemleri;

public class VeritabanıEklemeIslemi {

	public static void main(String[] args) {
		try {
			VeritabaniIslemleri v1 = new VeritabaniIslemleri();
			v1.baglan();
			
			Kullanici kullanici = new Kullanici();
			kullanici.bosKullaniciOlustur();
			v1.kullaniciOlustur(kullanici);
			kullanici.setKullaniciID(v1.kullaniciIDsiniBul(kullanici.getKullaniciEmail()));
			System.out.println("a");
			
			Blog blog = new Blog();
			blog.yeniKullaniciIcinBlogBilgisiHazirla(kullanici);
			System.out.println(v1.blogOlustur(blog));
			blog.setBlogID(v1.blogIDsiniBul(blog.getOlusturmaTarih()));
			System.out.println(blog.getOlusturmaTarih());
			System.out.println(blog.getBlogID());
			
			Girdi girdi = new Girdi();
			System.out.println(girdi.getBlogID());
			System.out.println(girdi.getKullaniciID());
			girdi.hosgeldinGirdisiHazirla(kullanici, blog);
			v1.girdiOlustur(girdi);
			girdi.setGirdiID(v1.girdiIDsiniBul(girdi.getGirdiTarih()));
			System.out.println("c");
			
			Yorum yorum = new Yorum();
			yorum.hosGeldinYorumuHazirla(girdi, kullanici);
			v1.yorumOlustur(yorum);
			v1.yorumOlustur(yorum);
			v1.yorumOlustur(yorum);
			System.out.println("d");
			
		} catch (Exception e) {
		System.out.println(e);
		}
		
	}

}
