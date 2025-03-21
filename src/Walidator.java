// klasa do walidacji danych , gdyz bedziemy rozbudowywac projekt w prszyslosci to moze sie przydac
class Walidator {
    public static boolean sprawdzWyrazenie(String wyrazenie) {
        // '\\' sluzy doslownemu wskazaniu znaku bo inaczej tworzy nam zakres \s oznacza dowolny znak bialy
        return wyrazenie.matches("[0-9+\\-*/()^\s]+") && !wyrazenie.isEmpty();
    }
}
