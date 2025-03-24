import java.io.IOException;
import java.util.Scanner;

public class Kalkulator {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);

        int liczbaRownan;

        do{
            System.out.println("Podaj ilosc wyrazen ktore chcesz wpisac:");
            try{
                liczbaRownan = Integer.parseInt(sc.nextLine());
                if(liczbaRownan < 1){
                    throw new IllegalArgumentException("Liczba rownan musi byc wieksza od zera");
                }
            }catch(NumberFormatException e){
                System.out.println("liczba rownan musi wyrazac sie w arabskiej liczbie calkowitej a nie slownie");
                liczbaRownan = 0;
            }catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                liczbaRownan = 0;
            }

        }while(liczbaRownan < 1);

        String[] rownania = new String[liczbaRownan];
        System.out.println("Wprowadź wyrażenia zakończone znakiem '=' :");
        for (int i = 0; i < liczbaRownan; i++) {
            StringBuilder wyrazenieBuilder = new StringBuilder();
            while (true) {
                String linia = sc.nextLine().trim();
                if (linia.endsWith("=")) {
                    wyrazenieBuilder.append(linia.replace("=", ""));
                    break;
                } else {
                    wyrazenieBuilder.append(linia).append(" ");
                }
            }
            rownania[i] = wyrazenieBuilder.toString().trim();
        }

        for (String wyrazenie : rownania) {
            // sprawdzam w walidatorze z listy dozwoloncyh znakow czy sie zgadza
            try{
                if (Walidator.sprawdzWyrazenie(wyrazenie)) {
                    // wysylam wyrazenie  tablicy rownania do konwertera ktory dziala zle
                    String onp = ONPKonwerter.toOnp(wyrazenie);
                    //jezeli wyrazenie onp nie jest puste to
                    if (!onp.isEmpty()) {
                        //wysylam do klasy obliczOnp do metody .kalk wyrazenie onp ktore otrzymalem z konwertera
                        double wynik = ObliczONP.kalk(onp);  // Zakładam, że `kalk` zwraca wynik jako double
                        System.out.println("Wyrażenie ONP: " + onp);
                        System.out.println("Wynik: " + wynik);

                    }

                }
            }catch(java.lang.IllegalArgumentException e){
                System.out.println("blad " + e.getMessage());
            }

        }
    }
}
