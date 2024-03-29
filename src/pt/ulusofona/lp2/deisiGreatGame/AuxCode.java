package pt.ulusofona.lp2.deisiGreatGame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public  class AuxCode {

    public static String ordernarLinguagensProgramacao(String linguagens){

        String[] parts = linguagens.split(";");
        StringBuilder linguagensOrdenadas = new StringBuilder();

        Arrays.sort(parts);

        for (String s: parts){
            linguagensOrdenadas.append(s);
            linguagensOrdenadas.append("; ");
        }

        return linguagensOrdenadas.substring(0, linguagensOrdenadas.length() -2);
    }

    public static void changePosAndCasa(int pos, HashMap<Integer, Programmer> programmers, Programmer programmer) {
        programmers.get(programmer.getId()).setPos(pos);
        programmers.get(programmer.getId()).adicionaCasa(pos);
    }
    public static String setTitleAbyss(int id){

        switch (id){

            case 0:
                return "Erro de sintaxe";

            case 1:
                return "Erro de lógica";

            case 2:
                return "Exception";

            case 3:
                return "File Not Found Exception";

            case 4:
                return "Crash (aka Rebentanço)";

            case 5:
                return "Duplicated Code";

            case 6:
                return "Efeitos secundários";

            case 7:
                return "Blue Screen of Death";

            case 8:
                return "Ciclo infinito";

            case 9:
                return "Segmentation Fault";

            default:
                return  "";
        }
    }

    public static boolean isAbyss(List<Abyss> listAbyss, int pos) {
        for (Abyss abyss : listAbyss) {
            if (abyss.getPos() == pos) {
                return true;
            }
        }
        return false;
    }

    public static void auxiliarIncrementaCasasMaisPisadas(HashMap<Integer, Integer> casasMaisPisadas, Programmer programmer){
        if (casasMaisPisadas.containsKey(programmer.getPos())) {
            casasMaisPisadas.put(programmer.getPos(), casasMaisPisadas.get(programmer.getPos()) +1);

        } else {
            casasMaisPisadas.put(programmer.getPos(), 1);
        }
    }

    public static void auxiliarIncrementaAbyssesMaisPisados(HashMap<Integer, Abyss> abyssesMaisPisados, int id, int pos){
        if (abyssesMaisPisados.containsKey(pos)){
            abyssesMaisPisados.get(pos).setCount(abyssesMaisPisados.get(pos).getCount() +1);
        } else {
            abyssesMaisPisados.put(pos, Abyss.createAbyss(id, setTitleAbyss(id), pos));
        }
    }


    public static int setIdAbyss(List<Abyss>hashAbyss,int pos) {
        for (Abyss abyss : hashAbyss) {
            if (abyss.getPos() == pos) {
                return abyss.getId();
            }
        }
        return -1;
    }
    public static Tool setTool(List<Tool>listTool,int pos) {
        for (Tool toolTemp : listTool) {
            if (toolTemp.getPos() == pos) {
                return toolTemp;
            }
        }

        return null;
    }

    public static int setIdTool(String title){
        switch (title){
            case "Herança":
                return 0;

            case "Programação Funcional":
                return 1;

            case "Testes unitários":
                return 2;

            case  "Tratamento de Excepções":
                return 3;

            case "IDE":
                return 4;

            case "Ajuda Do Professor":
                return 5;

            default:
                return -1;

        }
    }

    public static String setTitleToolLoad(int id){

        switch(id){
            case 0:
                return "Herança";

            case 1:
                return "Programação Funcional";

            case 2:
                return "Testes unitários";

            case 3:
                return "Tratamento de Excepções";

            case 4:
                return "IDE";

            case 5:
                return "Ajuda Do Professor";

            default:
                return "";

        }
    }

    public static String setTitleTool(int id){

        switch(id){
            case 0:
                return "Herança";

            case 1:
                return "Programação Funcional";

            case 2:
                return "Testes unitários";

            case 3:
                return "Tratamento de Excepções";

            case 4:
                return "IDE";

            case 5:
                return "Ajuda Do Professor";

            default:
                throw new IllegalArgumentException();

        }
    }
    public static boolean isTool(List<Tool>listTools,int pos) {
        for (Tool toolTemp : listTools) {
            if (toolTemp.getPos() == pos) {

                return true;
            }
        }
        return false;
    }


}
