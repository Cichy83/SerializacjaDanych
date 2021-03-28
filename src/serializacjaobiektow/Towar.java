package serializacjaobiektow;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Towar {

    public static final int DLUGOSC_NAZWY = 30;
    public static final int DLUGOSC_REKORDU = (Character.SIZE * DLUGOSC_NAZWY + Double.SIZE + 3 * Integer.SIZE) / 8;

    private double cena; //8 bajtów
    private String nazwa; // DLUGOSC_NAZWY * 2 bajtów
    private Date dataWydania;

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

    public static void zapiszDoPliku(Towar[] towar, DataOutput outS) throws IOException {
        for (int i = 0; i < towar.length; i++){
            towar[i].zapiszDane(outS);
        }
    }

    public static Towar[] odczytajZPliku(RandomAccessFile RAF) throws IOException {
        int ileRekordow = (int)RAF.length()/DLUGOSC_REKORDU;
        Towar[] towar = new Towar[ileRekordow];

        for (int i = 0; i < ileRekordow; i++){
            towar[i] = new Towar();
            towar[i].czytajDane(RAF);
        }
    return towar;
    }

    public void zapiszDane (DataOutput outS) throws IOException {
        outS.writeDouble(this.cena);

        StringBuffer stringB = new StringBuffer(DLUGOSC_NAZWY);
        stringB.append(this.nazwa);
        stringB.setLength(DLUGOSC_NAZWY);
        outS.writeChars(stringB.toString());

        GregorianCalendar kalendarz = new GregorianCalendar();
        kalendarz.setTime(this.dataWydania);

        outS.writeInt(kalendarz.get(Calendar.YEAR));
        outS.writeInt(kalendarz.get(Calendar.MONTH)+1);
        outS.writeInt(kalendarz.get(Calendar.DAY_OF_MONTH));
    }

    public void czytajDane(DataInput inS) throws IOException {
        this.cena = inS.readDouble();

        StringBuffer tString = new StringBuffer(Towar.DLUGOSC_NAZWY);
        for(int i = 0; i < Towar.DLUGOSC_NAZWY; i++){
            char tCh = inS.readChar();
            if (tCh != '\0'){
                tString.append(tCh);
            }
        }
        this.nazwa = tString.toString();

        int rok = inS.readInt();
        int m   = inS.readInt();
        int dz  = inS.readInt();

        GregorianCalendar kalendarz = new GregorianCalendar(rok, m-1, dz);
        this.dataWydania = kalendarz.getTime();
    }

    public void czytajRekord( RandomAccessFile RAF, int n) throws IOException, BrakRekordu {
        if (n <= RAF.length() / Towar.DLUGOSC_REKORDU ) {
            RAF.seek((n-1)*Towar.DLUGOSC_REKORDU);
            this.czytajDane(RAF);
        }
        else {
            throw new BrakRekordu("Niestety nie ma takego rekordu");
        }
    }

}
