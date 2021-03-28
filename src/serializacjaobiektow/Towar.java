package serializacjaobiektow;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Towar implements  Serializable {

    public static final int DLUGOSC_NAZWY = 30;
    public static final int DLUGOSC_REKORDU = (Character.SIZE * DLUGOSC_NAZWY + Double.SIZE + 3 * Integer.SIZE) / 8;

    private double cena; //8 bajtów
    private String nazwa; // DLUGOSC_NAZWY * 2 bajtów
    private Date dataWydania;

    public String getHaslo() {
        return this.haslo;
    }

    private transient String haslo = "koza";


    public Towar() {
        this.cena = 0.0;
        this.nazwa = " ";
        this.dataWydania = new GregorianCalendar().getTime();
    }

    public Towar(double cena, String nazwa) {
        this();
        this.cena = cena;
        this.nazwa = nazwa;
    }

    public Towar(double cena, String nazwa, int rok, int m, int dz) {
        this(cena,nazwa);
        GregorianCalendar kalendarz = new GregorianCalendar(rok, m-1, dz);
        dataWydania = kalendarz.getTime();
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Date getDataWydania() {
        return dataWydania;
    }

    public void setDataWydania(int r, int m, int d) {
        GregorianCalendar kalendarz = new GregorianCalendar(r, m-1, d);
        this.dataWydania = kalendarz.getTime();
    }

    public String toString(){
        GregorianCalendar kalendarz = new GregorianCalendar();
        kalendarz.setTime(this.dataWydania);
        return this.cena + " zł, nazwa: " + this.nazwa + ", " + kalendarz.get(Calendar.YEAR) + " rok, " +
                (kalendarz.get(Calendar.MONTH)+1) + " miesiąc, " + kalendarz.get(Calendar.DAY_OF_MONTH) + " dzień";
    }

    private void readObject (ObjectInputStream inS) throws IOException, ClassNotFoundException{
        inS.defaultReadObject();
        if (haslo != null){
            if (!haslo.equals("tajne")){
                throw new IOException("Dane są nieprawidłowe");
            }
        }

    }
}
