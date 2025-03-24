// klasa do walidacji danych , gdyz bedziemy rozbudowywac projekt w prszyslosci to moze sie przydac
class Walidator {
    public static boolean sprawdzWyrazenie(String wyrazenie) {

        if(wyrazenie.isEmpty()){
            throw new java.lang.IllegalArgumentException("wyrazenie jest puste");
        }
        // '\\' sluzy doslownemu wskazaniu znaku bo inaczej tworzy nam zakres \s oznacza dowolny znak bialy
        // b oznacza slowo
        if(!wyrazenie.matches("[0-9+\\-*/()^!%\\s]+|.*\\bsqrt\\b.*")){
            throw new java.lang.IllegalArgumentException("wyrazenie zawiera niedozwolone znaki: " + wyrazenie);
        }

        return true;
    }

}
