import java.io.BufferedReader;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 *
 * Klasa FileReaderTask (czyli author) czytająca linia po linii z pliku
 * implementujaca callable
 *
 */

public class FileReaderTask implements Callable<String> {

    private final String reader;
    private final Lock lock;

    public FileReaderTask(String reader, Lock lock) {
        this.reader = reader;
        this.lock = lock;
    }
    @Override
    public String call() throws Exception {
        lock.lock();
        try{
            return reader;
        }finally{
            lock.unlock();
        }
    }
}
