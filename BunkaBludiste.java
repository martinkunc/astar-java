package java;
public class BunkaBludiste {
    public BunkaBludiste RodicovskaBunka;
    public Souradnice Pozice;
    
    // Cena od zacatku do tehle bunky
    public int G;
    
    // Cena od tehle bunky do konce
    public int H;

    // Soucet ceny od zacatku a ceny do konce
    public int F;

    public BunkaBludiste(BunkaBludiste aBunkaBludiste, Souradnice aPozice) {
        RodicovskaBunka = aBunkaBludiste;
        Pozice = aPozice;
        G = 0;
        H = 0;
        F = 0;
    }
}