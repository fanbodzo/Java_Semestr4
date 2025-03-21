import java.util.Stack;

//klasa do obliczania onp
class obliczONP {
    public static double kalk(String onp) {
        Stack<Double> stos = new Stack<>();

        for (String token : onp.split(" ")) {
            if (token.matches("[0-9]")) {

                stos.push(Double.parseDouble(token));

            } else if (token.matches("[+\\-*/^]")) {

                double b = stos.pop();
                double a = stos.pop();
                switch (token) {
                    case "+" -> stos.push(a + b);
                    case "-" -> stos.push(a - b);
                    case "*" -> stos.push(a * b);
                    case "/" -> stos.push(a / b);
                    case "^" -> stos.push(Math.pow(a, b));
                }
            }
        }

        return stos.pop();
    }
}
