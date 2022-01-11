package pt.ulusofona.lp2.deisiGreatGame;

import static org.junit.Assert.*;
import static pt.ulusofona.lp2.deisiGreatGame.Abyss.createAbyss;
import static pt.ulusofona.lp2.deisiGreatGame.Tool.createTool;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class TestGameSettingManager {

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
        GameSetting gm = new GameSetting();

        ArrayList<Programmer> map = new ArrayList<>();

        //numeros de turnos
        gm.setEndedShifts(16);
        gm.setWinner("Goiaba");


        map.add(new Programmer("Goiaba",79));
        map.add(new Programmer("Pato Donald", 8));
        map.add(new Programmer("Bruninho", 9));


        gameMng.programmerListGameResults = map;
        gameMng.gameSetting = gm;


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
        assertEquals(gameMng.getGameResults().toString(), expectedGameResults.toString());
    }


    //nrPositions está a 0.  tem que ser entre 1 - 6 ( num do dado)
   @Test
    public void teste01Move(){
        try {
            GameManager manager = new GameManager();
            manager.createInitialBoard(getPlayersInfo(), 79);
            boolean resultadoObtido = manager.moveCurrentPlayer(0);
            assertFalse(resultadoObtido);
        }catch (InvalidInitialBoardException ex){
            assertEquals("Excepcao", ex.getMessage());
        }

    }

    //nrPositions está a 7.  tem que ser entre 1 - 6 ( num do dado)
    @Test
    public void teste02Move(){
        GameManager manager = new GameManager();
        try{
            manager.createInitialBoard(getPlayersInfo(), 79);
            boolean resultadoObtido = manager.moveCurrentPlayer(7);
            assertFalse(resultadoObtido);
        }catch (InvalidInitialBoardException ex){
            assertEquals("Excepcao", ex.getMessage());
        }


    }

    @Test
    public void testeMoveOnePlayer(){
        //andar com o jogadorCurrent (index 0 do array programers) 5 casas para a frente
        GameManager manager = new GameManager();
        try{
        manager.createInitialBoard(getPlayersInfo(), 79);
            manager.moveCurrentPlayer(5);
            Programmer programmerThatMoved = manager.getProgrammers(false).get(0);
            assertEquals(6, programmerThatMoved.getPos());

        }  catch (InvalidInitialBoardException ex){
            assertEquals("Excepcao", ex.getMessage());
        }
        //andar

    }

   @Test
    public void testeMoveOneMultiplePlayers(){
        //andar com o jogador (index 0 do array programers) 11 casas para a frente
        //andar com o jogador (index 1 do array programers) 3 casas para a frente
       try {
           GameManager manager = new GameManager();
           manager.createInitialBoard(getPlayersInfo(), 79);
           manager.moveCurrentPlayer(5);
           manager.moveCurrentPlayer(3);
           manager.moveCurrentPlayer(6);
           Programmer programmerThatMovedMost = manager.getProgrammers(false).get(0);
           assertEquals(15, programmerThatMovedMost.getPos());
           Programmer programmerThatMovedLeast = manager.getProgrammers(false).get(1);
           assertEquals(1, programmerThatMovedLeast.getPos());


       }catch (InvalidInitialBoardException ex){
           assertEquals("Excepcao", ex.getMessage());
       }


    }

    @Test
    public void testeMovePlayerPassFinish(){
        try {
            GameManager manager = new GameManager();
            manager.createInitialBoard(getPlayersInfo(), 80);
            //andar

            for(int pos = 0; pos <= 159; pos++) {//andar todos os jogadores até o current ficar naquele que queremos e com pos=79
                manager.moveCurrentPlayer(1);
            }
            manager.moveCurrentPlayer(5);//andar o current na poss 79 5 cassas para a frente e como excedeu por 4 cassas o goal ficar no 76
            Programmer programmerThatMoved = manager.getProgrammers(false).get(0);
            assertEquals(76, programmerThatMoved.getPos());
        }catch (InvalidInitialBoardException ex){
            assertEquals("Excepcao", ex.getMessage());
        }
        }


    @Test
    public void testeCreateAbyssPos(){
        try {
            GameManager manager = new GameManager();
            manager.createInitialBoard(getPlayersInfo(), 80);
            Abyss abyss0 = createAbyss(0, "Syntax Error", 1);
            manager.abysses.add(0, abyss0);
            //System.out.println(manager.abysses);
            assert abyss0 != null;
            assertEquals(1, abyss0.getPos());

        }catch (InvalidInitialBoardException ex){
            assertEquals("Excepcao", ex.getMessage());
        }


    }/*
    @Test
    public void testePlayerReactAbyss(){
        try {
            GameManager manager = new GameManager();
            manager.createInitialBoard(getPlayersInfo(), 2);

            //make abyss
            Abyss abyss0 = createAbyss(0, "Syntax Error", 1);
            manager.abysses.add(0, abyss0);

            //make programmer
            Programmer programmer0 = new Programmer("Steve", 1);
            manager.gameSetting.setCurrentPlayerID(1);

            assertEquals("Ohhh, caiste num abismo !!", manager.reactToAbyssOrTool());

        }catch (InvalidInitialBoardException ex){
            assertEquals("Excepcao", ex.getMessage());
        }


    }

    @Test
    public void testePlayerReactTool(){
        try {
            GameManager manager = new GameManager();
            manager.createInitialBoard(getPlayersInfo(), 2);

            //make tool
            Tool tool0 = createTool(0, "Inheritance", 1);
            manager.tools.add(0, tool0);

            //make programmer
            Programmer programmer0 = new Programmer("Steve", 1);
            manager.gameSetting.setCurrentPlayerID(1);

            //System.out.println(manager.gameSetting.getCurrentPlayerID());
            //System.out.println(tool0.getPos());
            manager.canCatch = true;

            assertEquals("Parabéns, apanhaste uma ferramenta !!", manager.reactToAbyssOrTool());

        }catch (InvalidInitialBoardException ex){
            assertEquals("Excepcao", ex.getMessage());
        }


    }*/

}
