package pt.ulusofona.lp2.deisiGreatGame;

public class Tool {
    private int id;
    private String titulo;
    private int pos;



    public Tool(){}

    public Tool(int id, String titulo, int pos){
        this.id = id;
        this.titulo = titulo;
        this.pos = pos;
    }


    public int getId() {
        return id;
    }

    public int getPos() {
        return pos;
    }

    public String toString(){
        return titulo;
    }
}
