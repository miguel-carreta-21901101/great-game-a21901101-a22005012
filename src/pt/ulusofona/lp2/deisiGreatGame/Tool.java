package pt.ulusofona.lp2.deisiGreatGame;

public class Tool {
    private int id;
    private String titulo;
    private int pos;
    private boolean isRepeated;



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

    public boolean getRepeated() {
        return isRepeated;
    }


    public void setIsRepeated(){
        isRepeated = true;
    }

    public String toString(){
        return titulo;
    }
}
