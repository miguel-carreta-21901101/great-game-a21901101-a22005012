package pt.ulusofona.lp2.deisiGreatGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Programmer {

    String name;
    int id;
    int pos;
    HashSet<String> linguagensProgramacao = new HashSet<>();
    boolean inGame = true;
    String estado;
    ArrayList<Programmer> programmers = new ArrayList<>();

    enum ProgrammerColor {
        PURPLE,
        BLUE,
        GREEN,
        BROWN
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

    public ArrayList<Programmer> getProgrammers(){
        return programmers;
    }
    ProgrammerColor getColor(){
        return ProgrammerColor.BROWN;
    }

    public void stillInGame(){
        if (inGame){
            estado = "Em Jogo";
        } else {
            estado = "Derrotado";
        }
    }

    public String toString() {
        return getId() + " | " + getName() + getPos() + " | " + Arrays.toString(linguagensProgramacao.toString()
                .split("; ")) + " | " + estado;
    }
}
