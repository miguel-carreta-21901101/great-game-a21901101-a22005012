package pt.ulusofona.lp2.deisiGreatGame;

public abstract class GameHouseElement {
    protected int id;
    protected String title;
    protected int pos;

    public GameHouseElement(int id, String title, int pos) {
        this.id = id;
        this.title = title;
        this.pos = pos;
    }


    public abstract String getImagePng();

    public int getId() {
        return id;
    }

    public int getPos() {
        return pos;
    }
    public  String toString(){return title;}
}
