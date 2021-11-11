package pt.ulusofona.lp2.deisiGreatGame;

public class Programmer {

    String name;
    int id;
    int pos;
    String linguagens;
    ProgrammerColor color;

    public Programmer(){}

    public Programmer(String name, int pos){
        this.name = name;
        this.pos = pos;
    }

    public Programmer(int id, String name,  String linguagens ,ProgrammerColor color) {
        this.id = id;
        this.name = name;
        this.linguagens = linguagens;
        this.color = color;
        pos = 1;
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

    public void setPos(int pos) {
        this.pos = pos;
    }

    public ProgrammerColor getColor(){
        return  color;
    }

    //Nesta primeira fase o player esta sempre Em Jogo
    public String toString() {
        return String.format("%d | %s | %d | %s | Em Jogo", id, name, pos, linguagens);

    }
}
