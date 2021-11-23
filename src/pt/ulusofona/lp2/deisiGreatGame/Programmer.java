package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Programmer {

    String name;
    int id;
    int pos;
    String linguagens;
    ProgrammerColor color;
    //       < Turno, Casa >
    HashMap<Integer, Integer> casasPercorridas = new HashMap<>();
    HashSet<Integer> casasPercorridasHashSet = new HashSet<>();
    List<Integer> casasPercorridasList = new ArrayList<>();
    List<Tool> tools = new ArrayList<>();
    boolean outOfGame = false;
    boolean abyssLastRound = false;
    boolean stuck = false;

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


    public void adicionaCasa(int turno, int casa){
        casasPercorridasHashSet.add(casa);
        casasPercorridasList.add(casa);
       // casasPercorridas.put(turno, casa);
    }

    public void catchTool(Tool tool){
        if (tool != null && !tools.contains(tool)){
            tools.add(tool);
        }
    }


    public void setOutOfGame() {
        this.outOfGame = true;
    }

    public void gotAbyssLastRound(){
        abyssLastRound = true;
    }

    public boolean isOutOfGame() {
        return outOfGame;
    }

    public boolean isGetAbyssLastRound() {
        return abyssLastRound;
    }

    public int getId(){
        return id;
    }

    public boolean isStuck() {
        return stuck;
    }

    public void stuckedByInfiniteCircle(boolean stuck) {
        this.stuck = stuck;
    }

    public HashMap<Integer, Integer> getCasasPercorridas() {
        return casasPercorridas;
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

    public void setPos(int pos) {
        this.pos = pos;
    }

    public ProgrammerColor getColor(){
        return  color;
    }

    //Nesta primeira fase o player esta sempre Em Jogo
    public String toString() {

        StringBuilder ferramentas = new StringBuilder();
        String auxTools;

        if (tools.size() == 0){
            auxTools = "No tools";

        } else {

            for (Tool tool : tools) {
                ferramentas.append(tool.getTitulo());
                ferramentas.append(";");
            }

            auxTools = ferramentas.substring(0, ferramentas.length() - 1);
        }


        return String.format("%d | %s | %d | %s | %s | Em Jogo", id, name, pos, auxTools, linguagens);

    }
}
