package pt.ulusofona.lp2.deisiGreatGame;

public class Board {

    int tamanho;
    int[] size = new int[tamanho];

    String ultimaPosicaoDoMapa = "glory.png";



    public Board() {
    }

    public void setTamanho(int tamanho){
        this.tamanho = tamanho;
    }

    public int getTamanho(){
        return tamanho;
    }

    public String getUltimaPosicaoDoMapa(){return ultimaPosicaoDoMapa;}

}
