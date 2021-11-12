package pt.ulusofona.lp2.deisiGreatGame;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;


public class TestGameManager {
    // fun para 2 jogadores com Infos
    private String[][] getPlayersInfo(){

        String[][] playersInfo = new String[2][4];

        playersInfo[0][0] = "1";
        playersInfo[0][1] = "Miguel";
        playersInfo[0][2] = "D;Common Lisp;Clojure";
        playersInfo[0][3] = "Purple";
        playersInfo[1][0] = "2";
        playersInfo[1][1] = "Filipe";
        playersInfo[1][2] = "AED";
        playersInfo[1][3] = "Green";

        return playersInfo;

    }


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


    //nrPositions está a 0.  tem que ser entre 1 - 6 ( num do dado)
    @Test
    public void teste01Move(){
        GameManager manager = new GameManager();
        manager.createInitialBoard(getPlayersInfo(), 79);
        boolean resultadoObtido = manager.moveCurrentPlayer(0);
        assertFalse(resultadoObtido);
    }

    //nrPositions está a 7.  tem que ser entre 1 - 6 ( num do dado)
    @Test
    public void teste02Move(){
        GameManager manager = new GameManager();
        manager.createInitialBoard(getPlayersInfo(), 79);
        boolean resultadoObtido = manager.moveCurrentPlayer(7);
        assertFalse(resultadoObtido);

    }

    @Test
    public void testeMoveOnePlayer(){
        //andar com o jogadorCurrent (index 0 do array programers) 5 casas para a frente
        GameManager manager = new GameManager();
        manager.createInitialBoard(getPlayersInfo(), 79);
        //andar
        manager.moveCurrentPlayer(5);
        Programmer programmerThatMoved = manager.getProgrammers().get(0);
        assertEquals(6, programmerThatMoved.getPos());
    }

    @Test
    public void testeMoveOneMultiplePlayers(){
        //andar com o jogador (index 0 do array programers) 11 casas para a frente
        //andar com o jogador (index 1 do array programers) 3 casas para a frente
        GameManager manager = new GameManager();
        manager.createInitialBoard(getPlayersInfo(), 79);
        manager.moveCurrentPlayer(5);
        manager.moveCurrentPlayer(3);
        manager.moveCurrentPlayer(6);
        Programmer programmerThatMovedMost = manager.getProgrammers().get(0);
        assertEquals(12, programmerThatMovedMost.getPos());
        Programmer programmerThatMovedLeast = manager.getProgrammers().get(1);
        assertEquals(4, programmerThatMovedLeast.getPos());
    }

    @Test
    public void testeMovePlayerPassFinish(){
        GameManager manager = new GameManager();
        manager.createInitialBoard(getPlayersInfo(), 80);
        //andar

        for(int pos = 0; pos <= 159; pos++) {//andar todos os jogadores até o current ficar naquele que queremos e com pos=79
            manager.moveCurrentPlayer(1);
        }
        manager.moveCurrentPlayer(5);//andar o current na poss 79 5 cassas para a frente e como excedeu por 4 cassas o goal ficar no 76
        Programmer programmerThatMoved = manager.getProgrammers().get(0);
        assertEquals(76, programmerThatMoved.getPos());
    }

}
