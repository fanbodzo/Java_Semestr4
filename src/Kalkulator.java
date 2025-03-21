import java.io.IOException;
import java.util.Scanner;

/*
    ogolnie to ladnie smiga
    moge dodac wczyytwanie z pliku wyrazen zeby bylo prosciej wpisywac wieksze ilosci wyrazen2

    musze dodac
    pierwiastki - mid bo musze odczytywac cala sekwencje sqrt chyba ze przechytrze i dam znak jakis
    silnie - easy
    modulo - easy

    musze dodac wyjatek na:
    niedomkniety nawaias w rownananiu jezeli ilosc nawiasow jest nieprawidlow np otwieramy a nie zamykamy
    na nieobslugiwany operator np & albo //
    na zla postac rownania ze np nienkonczy sie na =
    walidator wypierdoli blad ze jakis znak jest niepoprawny to moge zmienic w instancji klasy
 */

public class Kalkulator {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        Serializer serializer = new Serializer();

        System.out.println("Podaj ilość wyrażeń, które chcesz wpisać:");
        int liczbaRownan = Integer.parseInt(sc.nextLine());
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
            if (Walidator.sprawdzWyrazenie(wyrazenie)) {
                // wysylam wyrazenie  tablicy rownania do konwertera ktory dziala zle
                String onp = ONPKonwerter.toOnp(wyrazenie);
                //jezeli wyrazenie onp nie jest puste to
                if (!onp.isEmpty()) {
                    //wysylam do klasy obliczOnp do metody .kalk wyrazenie onp ktore otrzymalem z konwertera
                    double wynik = obliczONP.kalk(onp);  // Zakładam, że `kalk` zwraca wynik jako double
                    System.out.println("Wyrażenie ONP: " + onp);
                    System.out.println("Wynik: " + wynik);

                    /*
                    // Serializacja do XML i pliku binarnego
                    System.out.println("Czy chcesz zapisać wynik operacji do XML (1) czy binarnie (2)? (wprowadź 1 lub 2):");
                    String wybor = sc.nextLine();
                    if (wybor.equals("1")) {
                        // Zapis do xml
                        serializer.serializeToXMLFile(wyrazenie + " = " + wynik, "wynik.xml");
                        System.out.println("Wynik został zapisany w formacie XML.");
                    } else if (wybor.equals("2")) {
                        // Zapis do bin
                        serializer.serializeToBinary(wyrazenie + " = " + wynik, "wynik.bin");
                        System.out.println("Wynik został zapisany w formacie binarnym.");
                    } else {
                        System.out.println("Nieprawidłowy wybór.");
                    }

                    // test
                    System.out.println("Czy chcesz odczytać wynik z pliku XML (1) czy binarnego (2)? (wprowadź 1 lub 2):");
                    String odczyt = sc.nextLine();
                    if (odczyt.equals("1")) {
                        String wynikZPlikuXML = (String) serializer.deserializeFromXMLFile("wynik.xml");
                        System.out.println("Odczytany wynik z XML: " + wynikZPlikuXML);
                    } else if (odczyt.equals("2")) {
                        String wynikZPlikuBin = (String) serializer.deserializeFromBinary("wynik.bin");
                        System.out.println("Odczytany wynik z pliku binarnego: " + wynikZPlikuBin);
                    } else {
                        System.out.println("Nieprawidłowy wybór.");
                    }

                     */
                }

            } else {
                System.out.println("Nieprawidłowe wyrażenie, jakiś znak jest niedozwolony lub nie ma co liczyć: " + wyrazenie);
            }

        }
    }
}
