import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Kalkulator {
    private static final String NAZWA_PLIKU = "rownania.txt";
    private static final int LICZBA_WATKOW = 4;

    public static void main(String[] args) throws IOException {
        ExecutorService wykonawca = Executors.newFixedThreadPool(LICZBA_WATKOW);
        Lock lock = new ReentrantLock();

        //odczytywanie pliku
        List<String> poczatkoweLinie = new ArrayList<>();

        try {
            poczatkoweLinie = Files.readAllLines(Paths.get(NAZWA_PLIKU));
            System.out.println("wczytano " + poczatkoweLinie.size() + " linii z pliku: " + NAZWA_PLIKU);
        } catch (IOException e) {
            System.err.println("nie mozna odczytac pliku " + NAZWA_PLIKU + ". " + e.getMessage());
            wykonawca.shutdownNow();
        }

        //lista wynikow ktora bedzie modyfikowana i na niej bedzie lock
        List<String> listaWynikow = new ArrayList<>(poczatkoweLinie);

        for (int i = 0; i < poczatkoweLinie.size(); i++) {
            String linia = poczatkoweLinie.get(i);
            if (linia != null && linia.contains("=") && linia.trim().endsWith("=")) {

                ObliczanieRownania obliczacz = new ObliczanieRownania(i, linia);

                KalkulatorTask zadanie = new KalkulatorTask(obliczacz, lock, listaWynikow);
                // dprzeslanie futuretask do wykonawcy
                wykonawca.submit(zadanie);
            } else {
                System.out.println("pomiteto linie " + (i + 1) + " nie wyglada na rownaie: " + linia);
            }
        }

        //system oczekiwania na zkonczenie zadan
        wykonawca.shutdown();
        try {
            System.out.println("oczkiwanie na zakonczenie zadan");
            // czekam 5 minut (obliczenia sa na tyle proste ze nie zostanie to pewnei wykorzystane)
            if (!wykonawca.awaitTermination(5, TimeUnit.MINUTES)) {
                System.err.println("nie wszyskie zadnaia sie zakoczyly przeywanie");
                wykonawca.shutdownNow();
            } else {
                System.out.println("wszyskie zadania zakonczone");
            }
        } catch (InterruptedException e) {
            System.err.println("przerywanie zadan , oczekiwanie przerwane");
            wykonawca.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // zapis do pliku
        try {
            System.out.println("zapisywanie wynikow do pliku : " + NAZWA_PLIKU);
            Files.write(Paths.get(NAZWA_PLIKU), listaWynikow, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("wyniki zpaisane");
        } catch (IOException e) {
            System.err.println("blad w zapisywaniu do pliku: " + e.getMessage());
        }
        

    }

}
