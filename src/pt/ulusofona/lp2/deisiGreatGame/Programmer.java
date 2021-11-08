package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public class Programmer {

    String name;
    int id;
    int pos;
    String linguagens;
    boolean inGame = true;
    String estado;
    ProgrammerColor color;
    String cor;


    private String[] playersInfo;


    public Programmer() {
        playersInfo = new String[3];

    }

    public Programmer(int id, String name,  String linguagens , String cor) {
        this.id = id;
        this.name = name;

        this.linguagens = linguagens;
        this.cor = cor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Programmer(int id) {
        this.id = id;
    }



    public void setPlayersInfo(String[] playersInfo) {
        this.playersInfo = playersInfo;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getPos(){
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String[] getPlayersInfo(){
        return playersInfo;
    }

    public String getCor(){
        return cor;
    }

    public ProgrammerColor getColor(){

        return  color;
    }




    public void stillInGame(){
        if (inGame){
            estado = "Em Jogo";
        } else {
            estado = "Derrotado";
        }
    }

    public String toString() {
        return id + " | " + name + " | " + pos + " | " + linguagens
                 + " | Em Jogo";
    }
}
