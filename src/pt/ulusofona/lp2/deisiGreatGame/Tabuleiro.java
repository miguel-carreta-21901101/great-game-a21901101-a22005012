package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tabuleiro {
    int tamanho;
    int[] size = new int[tamanho];

    String ultimaPosicao = "glory.png";

    public static HashMap<Integer, List<Programmer>> posicoesMapa = new HashMap<>();
    public Tabuleiro() {
    }


    public int getTamanho(){
        return tamanho;
    }
}
