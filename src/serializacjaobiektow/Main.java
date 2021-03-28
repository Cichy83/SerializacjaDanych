package serializacjaobiektow;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        Towar[] towar = new Towar[3];
        towar[0] = new Towar();
        towar[1] = new Towar(29.0, "VideoKurs Java");
        towar[2] = new Towar(8.2, "VideoKurs C++", 2008,11,21);

        try{
            RandomAccessFile RAF = new RandomAccessFile("baza.txt", "rw");
            Towar.zapiszDoPliku(towar,RAF);

            RAF.seek(0);

            Towar[] a = Towar.odczytajZPliku(RAF);

            for(int i = 0; i < a.length; i++){
                System.out.println(a[i].getCena());
                System.out.println(a[i].getNazwa());
                System.out.println(a[i].getDataWydania());
                System.out.println("---------------------");
            }

            try {
                Towar b = new Towar();
                b.czytajRekord(RAF, 3);
                System.out.println(b);
            }
            catch(BrakRekordu e) {
                System.out.println(e.getMessage());
            }

            RAF.close();
            }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
