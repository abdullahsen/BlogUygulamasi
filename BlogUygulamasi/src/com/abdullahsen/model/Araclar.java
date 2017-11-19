package com.abdullahsen.model;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Araclar {
    public static Timestamp yeniTimeStampOlustur(){
    	return new Timestamp(new java.util.Date().getTime());
    }
    public static String tarihZamanGoster(Timestamp ts){
        Date date = new Date(ts.getTime());
        String gosterim = "dd/MM/yyyy - [hh:mm]";
        SimpleDateFormat sdf = new SimpleDateFormat(gosterim);
        return sdf.format(date);
    }
    public static String sadeceTarihGoster(Timestamp ts){
        Date date = new Date(ts.getTime());
        String gosterim = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(gosterim);
        return sdf.format(date);
    }
    public static String sadeceZamanGoster(Timestamp ts){
        Date date = new Date(ts.getTime());
        String gosterim = "[hh:mm]";
        SimpleDateFormat sdf = new SimpleDateFormat(gosterim);
        return sdf.format(date);
    }


}
