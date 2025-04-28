import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 * writer ktory wykonuje walidacje , konwersje do onp i obliczanie wykorzystuajca juz
 * utworzone wczesniej klasy i zapisuje
 */
public class KalkulatorTask implements Callable<String> {
    private final String wyrazenie;
    private final Lock lock;

    public KalkulatorTask(String wyrazenie, Lock lock) {
        this.wyrazenie = wyrazenie;
        this.lock = lock;
    }

    @Override
    public String call() throws Exception {
        lock.lock();
        try {
            if (Walidator.sprawdzWyrazenie(wyrazenie)) {
                // wysylam wyrazenie  tablicy rownania do konwertera ktory dziala zle
                String onp = ONPKonwerter.toOnp(wyrazenie);
                //jezeli wyrazenie onp nie jest puste to
                if (!onp.isEmpty()) {
                    //wysylam do klasy obliczOnp do metody .kalk wyrazenie onp ktore otrzymalem z konwertera
                    double wynik = ObliczONP.kalk(onp);
                    System.out.println("Wyrażenie ONP: " + onp);
                    System.out.println("Wynik: " + wynik);

                    //zapis do pliku narazie nowego pliku zebyu sprawdzic czy dziala
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("rownania_wynik.txt", true))) {
                        bw.write(wyrazenie + " = " + wynik);
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return wyrazenie + "=" + wynik;
                }
            }
            return wyrazenie + " = blad";
        } catch (java.lang.IllegalArgumentException e) {
            System.out.println("blad " + e.getMessage());
            return wyrazenie + e.getMessage();
        }finally{
            lock.unlock();
        }
    }

}
