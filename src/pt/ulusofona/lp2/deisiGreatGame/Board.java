package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {

    int tamanho;
    int[] size = new int[tamanho];

    String ultimaPosicao = "glory.png";

    private HashMap<Integer, ArrayList<Programmer>> posicoesMapa = new HashMap<>();

    public Board() {
    }


    public void setTamanho(int tamanho){
        this.tamanho = tamanho;
    }
    public int getTamanho(){
        return tamanho;
    }
    public String getUltimaPosicao(){return ultimaPosicao;}
    public HashMap<Integer, ArrayList<Programmer>> getPosicoesMapa (){
        return posicoesMapa;
    }
}
