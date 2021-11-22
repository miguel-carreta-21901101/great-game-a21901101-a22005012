package pt.ulusofona.lp2.deisiGreatGame;

public class Casa {
    int id;
    String titulo;
    int pos;


    public Casa(int id, String titulo, int pos){
        this.id = id;
        this.titulo = titulo;
        this.pos = pos;
    }

    public int getId(){
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPos() {
        return pos;
    }
}
