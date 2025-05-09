import java.util.Stack;

//klasa do obliczania onp
class ObliczONP {
    public static double kalk(String onp) {
        Stack<Double> stos = new Stack<>();

        for (String token : onp.split(" ")) {
            if (token.matches("\\d+(\\.\\d+)?")) {

                stos.push(Double.parseDouble(token));

            } else if (token.matches("[+\\-*/^%]")) {

                double b = stos.pop();
                double a = stos.pop();
                switch (token) {
                    case "+" -> stos.push(a + b);
                    case "-" -> stos.push(a - b);
                    case "*" -> stos.push(a * b);
                    case "/" -> stos.push(a / b);
                    case "^" -> stos.push(Math.pow(a, b));
                    case "%" -> stos.push(a % b);

                }

        } else if (token.equals("!") || token.equals("√")) {
                double a = stos.pop();
                switch (token) {
                    case "!" -> stos.push(integral(a));
                    case "√" -> stos.push(Math.sqrt(a));
                }


            }
        }

        return stos.pop();
    }
    // silnia
    public static double integral(double liczba){
        double wynik=1;
        for (int i = 1; i <= liczba; i++) {
            wynik = wynik*i;
        }
        return wynik;
    }
}
