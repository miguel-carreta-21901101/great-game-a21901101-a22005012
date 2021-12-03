package pt.ulusofona.lp2.deisiGreatGame;

public abstract class GameHouseElement {
    protected int id;
    protected String titulo;
    protected int pos;

    public GameHouseElement(int id, String titulo, int pos) {
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

}
