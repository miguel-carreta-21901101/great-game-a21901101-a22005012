package pt.ulusofona.lp2.deisiGreatGame;

import java.util.*;

public class AuxFunctions {
    public static void resetGame(){
        GameManager.programerList.clear();
        GameManager.programmers.clear();
        GameManager.game = new Game();
        GameManager.board = new Board();
    }

}
