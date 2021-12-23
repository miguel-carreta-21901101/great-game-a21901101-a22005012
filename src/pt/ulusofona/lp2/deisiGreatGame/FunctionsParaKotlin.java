package pt.ulusofona.lp2.deisiGreatGame;

public class FunctionsParaKotlin {

    GameManager game = new GameManager();

    public String getPlayer(String firstName){

        for (Programmer p: game.programmers.values()){
            String[] s = p.getName().split(" ");
            if (s[0].equals(firstName)){
                return p.toString();
            }
        }
        return "Inexistent player";
    }


}
