package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Programmer {

    private String name;
    private int id;
    private int pos;
    private String linguagens;
    private ProgrammerColor color;
    private List<Integer> casasPercorridasList = new ArrayList<>();
    private List<Tool> tools = new ArrayList<>();
   //private List<Tool> toolsToString = new ArrayList<>();
    private boolean outOfGame = false;
    private boolean abyssLastRound = false;
    private boolean stuck = false;

    //************* Constructors ************************

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
    //*********************************************************************************



    //**************************  GETTERS *****************************************************

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getPos(){
        return pos;
    }
    public List<Tool> getTools() {
        return tools;
    }
    public List<Integer> getCasasPercorridasList() {
        return casasPercorridasList;
    }
    public ProgrammerColor getColor(){
        return  color;
    }
    public boolean isOutOfGame() {
        return outOfGame;
    }
    public boolean isGetAbyssLastRound() {
        return abyssLastRound;
    }
    public boolean isStuck() {
        return stuck;
    }

    //**********************************************************************************************



    // ************************************ SETTERS ***************************************************

    public void setOutOfGame() {
        this.outOfGame = true;
    }
    public void gotAbyssLastRound(){
        abyssLastRound = true;
    }
    public void stuckedByInfiniteCircle() {
        this.stuck = true;
    }
    public void freeFromInfiniteCircle(){
        this.stuck = false;
    }
    public void setPos(int pos) {
        this.pos = pos;
    }

    //***************************************************************************************************



    // *********************************** FUNCTIONS **************************************************
    public void adicionaCasa(int casa){
        casasPercorridasList.add(casa);
    }
    public void catchTool(Tool tool){


        if (tool != null && !tools.contains(tool)){

            tools.add(tool);
        }
    }
    public void dropTool(Tool tool){
        if (tool != null){
            tools.remove(tool);
        }
    }



    public String toString() {

        StringBuilder ferramentas = new StringBuilder();
        String auxTools;
        String emJogo = "Em Jogo";

        if (tools.size() == 0){
            auxTools = "No tools";

        } else {

            for (Tool tool : tools) {
                ferramentas.append(tool);
                ferramentas.append(";");
            }

            auxTools = ferramentas.substring(0, ferramentas.length() - 1);
        }

        if (outOfGame){
            emJogo = "Derrotado";
        }

        return String.format("%d | %s | %d | %s | %s | %s", id, name, pos, auxTools, linguagens, emJogo);

    }
}
