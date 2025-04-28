import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
                List<String> rownania = Files.readAllLines(Paths.get("rownania.txt"));
                List<String> noweRownania = new ArrayList<>();
                String onp = ONPKonwerter.toOnp(wyrazenie);

                if (!onp.isEmpty()) {
                    //wysylam do klasy obliczOnp do metody .kalk wyrazenie onp ktore otrzymalem z konwertera
                    double wynik = ObliczONP.kalk(onp);
                    System.out.println("Wyrażenie ONP: " + onp);
                    System.out.println("Wynik: " + wynik);

                    for(String rownanie : rownania) {
                        if(rownanie.equals(wyrazenie)) {
                            noweRownania.add(rownanie + " = " + wynik);
                        }else{
                            noweRownania.add(rownanie);
                        }
                    }
                    Files.write(Paths.get("rownania.txt"), noweRownania);

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
