import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Bludiste {
    List<Souradnice> prekazky;
    int zacX;
    int zacY;
    int konX;
    int konY;
    int velikostX;
    int velikostY;

    public Bludiste() {
        var b = new int[][] {
            {1,1,1,1,1,1,1,1,1,1},
            {1,2,1,0,0,1,1,1,1,1},
            {1,0,1,0,0,0,1,0,0,1},
            {1,0,1,0,1,0,0,0,0,1},
            {1,0,0,0,1,1,0,1,0,1},
            {1,0,1,1,1,0,0,1,0,1},
            {1,0,0,0,0,0,0,1,0,1},
            {1,0,0,0,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,3,1},
            {1,1,1,1,1,1,1,1,1,1},
        };
        velikostY = b.length;
        velikostX = b[0].length;
        prekazky = new ArrayList<Souradnice>();
        for(int i=0;i<velikostY;i++) {
            for(int j=0;j<velikostX;j++) {
                if (b[i][j] == 2) {
                    // 2 je kde zacnu
                    zacX = j;
                    zacY = i;
                }
                if (b[i][j] == 3) {
                    // 3 je kde skoncim
                    konX = j;
                    konY = i;
                }
                if (b[i][j] == 1) {
                    prekazky.add(new Souradnice(j,i));
                }
            }
        }
    }

    public void Nakresli(List<Souradnice> cesta) {
        var b = new int[velikostY][velikostX];
        b[zacY][zacX] = 2;
        b[konY][konX] = 3;

        for(int i=0;i<prekazky.size();i++) {
            b[prekazky.get(i).y][prekazky.get(i).x] = 1;
        }
        for(int i=0;i<cesta.size();i++) {
            b[cesta.get(i).y][cesta.get(i).x] = 4;
        }

        for(int i=0;i<velikostY;i++) {
            for(int j=0;j<velikostX;j++) {
                    System.out.print(b[i][j]);
            }
            System.out.println();
        }
    }

    public List<Souradnice> NajdiCestu() {
        var start = new Souradnice(zacX, zacY);
        var end = new Souradnice(konX, konY);

        var startNode = new BunkaBludiste(null, start);
        startNode.G = 0;
        startNode.H = 0;
        startNode.F = 0;
        var endNode = new BunkaBludiste(null, end);
        endNode.G = 0;
        endNode.H = 0;
        endNode.F = 0;

        List<BunkaBludiste> openList = new ArrayList<BunkaBludiste>();
        List<BunkaBludiste> closedList = new ArrayList<BunkaBludiste>();

        openList.add(startNode);

        while(openList.size() > 0) {
            var currentNode = openList.get(0);
            var currentIndex = 0;
            for(int i = 0;i<openList.size();i++) {
                var item = openList.get(i);
                if (item.F < currentNode.F) {
                    currentNode = item;
                    currentIndex = i;
                }
            }

            openList.remove(currentIndex);
            closedList.add(currentNode);

            if (currentNode.Pozice.x == endNode.Pozice.x && currentNode.Pozice.y == endNode.Pozice.y) {
                List<Souradnice> path = new ArrayList<Souradnice>();
                var current = currentNode;
                while (current != null) {
                    path.add(current.Pozice);
                    current = current.RodicovskaBunka;
                }
                Collections.reverse(path);
                return path;
            } 
            var children = new ArrayList<BunkaBludiste>();
            var prilehle = new int[][] {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
            for(int p=0; p < prilehle.length;p++) {

                var nodePosition = new Souradnice(currentNode.Pozice.x + prilehle[p][0], currentNode.Pozice.y + prilehle[p][1]);
                
                // Zkontroluj hranice bludiste
                if ((nodePosition.x > velikostX - 1) || (nodePosition.x < 0) || (nodePosition.y > velikostY - 1) || (nodePosition.y < 0))
                    continue;
                
                // zkontroluj ze zde neni prekazka
                boolean skipPrilehly = false;
                for(var k=0; k < prekazky.size();k++) {
                    var prekazka = prekazky.get(k);
                    if (prekazka.x == nodePosition.x && prekazka.y == nodePosition.y) {
                        skipPrilehly = true;
                        break;
                    }
                }
                if (skipPrilehly) {
                    continue;
                }

                var newNode = new BunkaBludiste(currentNode, nodePosition);
                children.add(newNode);
            }

            // Projdi deti
            for(int p=0;p<children.size();p++) {
                var child = children.get(p);

                // Pokud je dite v zavrenem listu
                boolean skipChild = false;
                for(int c=0;c < closedList.size();c++) {
                    if (child.Pozice.x == closedList.get(c).Pozice.x && child.Pozice.y == closedList.get(c).Pozice.y) {
                        skipChild = true;
                        break;
                    }
                }
                if (skipChild) {
                    continue;
                }

                child.G = currentNode.G + 1;
                child.H = (int)(Math.pow(child.Pozice.x - endNode.Pozice.x,2) + Math.pow(child.Pozice.y - endNode.Pozice.y,2));
                child.F = child.G + child.H;

                // pokud je dite uz v otevrenem listu
                skipChild = false;
                for(int c=0;c < openList.size();c++) {
                    if (child.Pozice.x == openList.get(c).Pozice.x && child.Pozice.y == openList.get(c).Pozice.y && child.G > openList.get(c).G) {
                        skipChild = true;
                        break;
                    }
                }
                if (skipChild) {
                    continue;
                }
                openList.add(child);
            }
        }
        return null;
    }
}
