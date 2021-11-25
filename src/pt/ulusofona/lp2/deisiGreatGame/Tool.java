package pt.ulusofona.lp2.deisiGreatGame;

public class Tool {
    int id;
    String titulo;
    int pos;
    boolean blocksEnabled;


    public Tool(){}

    public Tool(int id, String titulo, int pos){
        this.id = id;
        this.titulo = titulo;
        this.pos = pos;
    }

    boolean blockSyntax(Abyss abyss){
        if (abyss.getId() == 0){
            blocksEnabled = true;
        }
        return false;
    }

    public boolean isBlocksEnabled() {
        return blocksEnabled;
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
