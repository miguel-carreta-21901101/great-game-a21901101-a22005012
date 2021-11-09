package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public class Programmer {

    String name;
    int id;
    int pos;
    ArrayList<String> lngs;
    String linguagens;
    boolean inGame = true;
    String estado;
    ProgrammerColor color;
    String cor;


    public Programmer(){}

    public Programmer(int id, String name,  ArrayList<String> lngs , String cor) {
        this.id = id;
        this.name = name;

        this.lngs = lngs;
        this.cor = cor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Programmer(int id) {
        this.id = id;
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
        return String.format("%d | %s | %d | %s | Em Jogo", id, name, pos+1, lngs);

    }
}
