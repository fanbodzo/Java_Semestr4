import java.util.concurrent.Callable;

/**
 * klasa do obliczania jednego rownania
 * wykorzystuje poprzednie klasy onp
 * waliduje , oblicza i konwertuje przy uzyciu dostepnych juz klas
 * nie modyfikuje wspoldzielonych danych , tylko liczy
 */

public class ObliczanieRownania implements Callable<WynikObliczenia>{
    final int indexLinii;
    private final String rownanie;

    public ObliczanieRownania(int indexLinii, String rownanie) {
        this.indexLinii = indexLinii;
        this.rownanie = rownanie;
    }

    @Override
    public WynikObliczenia call() throws Exception {
        //pomocnicze przedstwaienie co sie dzieje w programie
        String nazwaWatku = Thread.currentThread().getName();
        System.out.println("watek: " + nazwaWatku + " zaczyna prace");

        //poprawiona wersja zeby reagowalo na znak '='
        String wyrazenieDoObliczenia = rownanie.split("=")[0].trim();

        try{
            Walidator.sprawdzWyrazenie(wyrazenieDoObliczenia);
            String onp = ONPKonwerter.toOnp(wyrazenieDoObliczenia);

            System.out.println("watek: " + nazwaWatku + " zakonczyl prace" );
            double wynik = ObliczONP.kalk(onp);
            return new WynikObliczenia(indexLinii, wynik);

        }catch(Exception e){
            System.err.println(nazwaWatku + " Blad przetwarzania linii " + (indexLinii + 1) + ": " + e.getMessage());
            return new WynikObliczenia(indexLinii, "blad " + e.getMessage());
        }
    }
}
