import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;

/**
 * wlasna implementacja FutureTask ktora opakwuje zadanie do obliczenia ObliczanieRownania
 * done() wywolywuje ise po zakonczeiu obliczen i aktualizuje wspoldzielona liste wynikow
 */
public class KalkulatorTask extends FutureTask<WynikObliczenia> {

    private final Lock lock;
    //lista wspoldzielona z wynikami
    private final List<String> listaWynikow;
    private final int indexLinii;

    public KalkulatorTask(ObliczanieRownania callable, Lock blokadaListy, List<String> listaWynikow) {
        // wylolanie konstruktora nadrzednego z zadaniem callable
        super(callable);

        this.lock = blokadaListy;
        this.listaWynikow = listaWynikow;
        this.indexLinii = callable.indexLinii;
    }

    @Override
    protected void done() {
        String nazwaWatku = Thread.currentThread().getName();
        try{
            WynikObliczenia wynik = get();
            lock.lock();
            try{
                String oryginalnaLinia = listaWynikow.get(wynik.getIndex());
                String prefixLinii = oryginalnaLinia.split("=")[0].trim() + " =";

                if(wynik.czySukces()){
                    listaWynikow.set(wynik.getIndex(), prefixLinii + " " + wynik.getWartosc());
                    System.out.println(nazwaWatku + " wynik zakonczyl sie sukcesem zakutalizowano liste dlalinii: " + (wynik.getIndex() + 1));
                }else {
                    listaWynikow.set(wynik.getIndex(), prefixLinii + " " + wynik.getKomunikatBledu());
                    System.err.println(nazwaWatku + " wynik zakonczyl sie niepowodzeniem zakutalizowano liste dlalinii: " + (wynik.getIndex() + 1));
                }
            }finally{
                lock.unlock();
            }
        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();

        }


    }

}
