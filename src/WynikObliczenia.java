/**
 * Obiekt do przechowywania obliczen dla pojednyczje linii
 * prosty do przenoszenia miedzy callable a futureTask , przekazywanie infomraacji o wyniku
 */

public class WynikObliczenia {
    private final int index;
    private final double wartosc;
    private final String komunikatBledu;
    private final boolean sukces;

    //konstruktor wywolywany gdy obiczanie sie powiedzie
    public WynikObliczenia(int index, double wartosc) {
        this.index = index;
        this.wartosc = wartosc;
        this.komunikatBledu = null;
        this.sukces = true;
    }
    //konstruktor wywolywany gdy bedzie blad
    public WynikObliczenia(int index, String komunikatBledu) {
        this.index = index;
        this.wartosc = Double.NaN; // brak wartosci bo nie obliczylo
        this.komunikatBledu = komunikatBledu;
        this.sukces = false;
    }


    public int getIndex() { return index; }
    public double getWartosc() { return wartosc; }
    public String getKomunikatBledu() { return komunikatBledu; }
    public boolean czySukces() { return sukces; }
}
