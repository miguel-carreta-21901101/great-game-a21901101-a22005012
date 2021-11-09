package pt.ulusofona.lp2.deisiGreatGame;

public class Main {
    public static void main(String[] args) {

        String[][] bros = new String[2][4];

        bros[0][0] = "1";
        bros[0][1] = "Miguel";
        bros[0][2] = "LP1 CONA";
        bros[0][3] = "Purple";
        bros[1][0] = "2";
        bros[1][1] = "Filipe";
        bros[1][2] = "AED";
        bros[1][3] = "Green";


        System.out.println(bros.length);
        GameManager game = new GameManager();

       System.out.println(game.createInitialBoard(bros, 4));
        System.out.println(game.getProgrammers());








    }
}