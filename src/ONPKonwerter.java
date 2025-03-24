import java.util.Stack;

// zamiana z wyrazenia arytemtycznego na onp
class ONPKonwerter {
    public static String toOnp(String wyrazenie) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stos = new Stack<>();
        StringBuilder liczba = new StringBuilder();

        for (int i =0 ; i< wyrazenie.length(); i++) {
            char znak = wyrazenie.charAt(i);

            if (Character.isDigit(znak)) {

                liczba.append(znak); // Gromadzimy cyfry w liczbie
            } else {
                if (liczba.length() > 0) {

                    output.append(liczba).append(' '); // Dodajemy pełną liczbę do wyniku
                    liczba.setLength(0); // Resetujemy bufor liczby
                }

                if (znak == '(') {

                    stos.push(znak);
                } else if (znak == ')') {

                    while (!stos.isEmpty() && stos.peek() != '(') {

                        output.append(stos.pop()).append(' ');
                    }
                    if (!stos.isEmpty() && stos.peek() == '(') {

                        stos.pop(); // Usunięcie '(' ze stosu
                    } else {
                        //nieprawidlowe wyrazenie brak nawiasu otwarcia
                        throw new java.lang.IllegalArgumentException("ilosc nawiasow zamkniecia jest wieksza niz otwarcia");

                    }
                } else if (czyOperator(znak)) {

                    while (!stos.isEmpty() && priorytet(stos.peek()) >= priorytet(znak)) {

                        output.append(stos.pop()).append(' ');
                    }
                    stos.push(znak);
                    //sprawwdzanie sqrt w osobnej metodzie
                }else if (znak == 's') {
                    i = czySqrt(wyrazenie, i, stos);
                }

            }
        }

        // dodawanie ostaniej liczby
        if (liczba.length() > 0) {
            output.append(liczba).append(' ');
        }

        // wyczysczenie stosu z pozostalosci
        while (!stos.isEmpty()) {
            if (stos.peek() == '(') {
                throw new java.lang.IllegalArgumentException("ilosc nawiasow otwarcia jest wieksza niz zamkniecia");
            }
            output.append(stos.pop()).append(' ');
        }

        return output.toString().trim();
    }

    // motoda do sprawdznia czy dany opertor to jest ktorys z znakow
    private static boolean czyOperator(char znak) {
        return znak == '+' || znak == '-' || znak == '*' || znak == '/' || znak == '^' || znak == '!' || znak == '%';
    }

    // nadanie priosrytetu w onp bardzo wazne zeby zachowac skladnie
    private static int priorytet(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            case '√' -> 4;
            default -> -1;
        };
    }

    private static int czySqrt(String wyrazenie, int indeks, Stack<Character> stos) {
        String sqrt = "sqrt";
        for (int j = 0; j < sqrt.length(); j++) {
            if (indeks + j >= wyrazenie.length() || wyrazenie.charAt(indeks + j) != sqrt.charAt(j)) {
                throw new IllegalArgumentException("niepoprwana instancja sqrt");
            }
        }
        // wrzucam na stos x bo jest latwiej
        stos.push('√');
        return indeks + sqrt.length() - 1;
    }
}
