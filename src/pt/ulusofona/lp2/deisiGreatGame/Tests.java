package pt.ulusofona.lp2.deisiGreatGame;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class Tests {
    @Test
    public void testGameResultsSortingByPos(){
        GameManager gameMng = new GameManager();
        Game gm = new Game();

        ArrayList<Programmer> map = new ArrayList<>();

        //numeros de turnos
        gm.setEndedShifts(16);
        gm.setWinner("Goiaba");


        map.add(new Programmer("Goiaba",79));
        map.add(new Programmer("Pato Donald", 8));
        map.add(new Programmer("Bruninho", 9));


        gameMng.programerList = map;
        gameMng.game = gm;


        ArrayList<String> expectedGameResults = new ArrayList<>();
        expectedGameResults.add("O GRANDE JOGO DO DEISI");
        expectedGameResults.add("");
        expectedGameResults.add("NR. DE TURNOS");
        expectedGameResults.add("" + 16);
        expectedGameResults.add("");
        expectedGameResults.add("VENCEDOR");
        expectedGameResults.add("Goiaba");
        expectedGameResults.add("");
        expectedGameResults.add("RESTANTES");
        expectedGameResults.add("Bruninho 9");
        expectedGameResults.add("Pato Donald 8");

        System.out.println(expectedGameResults);
        System.out.println(gameMng.getGameResults());
        Assert.assertTrue(gameMng.getGameResults().toString().equals(expectedGameResults.toString()));
    }
}
