import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Kalkulator {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Lock lock = new ReentrantLock();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("rownania.txt"));
        String linia;
        while((linia = bufferedReader.readLine()) != null) {
            Callable<String> fileReaderTask = new FileReaderTask(linia , lock);

            FutureTask<String> futureTask = new FutureTask<>(fileReaderTask){
                @Override
                protected void done() {
                    try{
                        String wyrazenie = get();
                        executor.submit(new KalkulatorTask(wyrazenie , lock));
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            };
            executor.submit(futureTask);
        }

    }

}
