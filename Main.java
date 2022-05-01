package java;
import java.util.ArrayList;

public class Main {

    public static void main(String[] argv) {
        var blud = new Bludiste();
        var prazdnaCesta = new ArrayList<Souradnice>();
        blud.Nakresli(prazdnaCesta);
        var cesta = blud.NajdiCestu();
        blud.Nakresli(cesta);
    }

}