package pt.ulusofona.lp2.deisiGreatGame;

public enum ProgrammerColor {

    PURPLE("Purple"),  BLUE("Blue"),
    GREEN("Green"),BROWN("Brown");


    String color;
    ProgrammerColor(String color){this.color = color;}

    ProgrammerColor() {
    }


    public void setColor(String cor){
        color = cor;
    }
    public String toString() {return color;}
}
