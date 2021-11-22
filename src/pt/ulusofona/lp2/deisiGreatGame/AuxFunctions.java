package pt.ulusofona.lp2.deisiGreatGame;

import java.util.Arrays;

public  class AuxFunctions {

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
                throw new IllegalArgumentException();
        }
    }

    public static String setTitleTool(int id){

        switch(id){
            case 0:
                return "Herança";

            case 1:
                return "Programação funcional";

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
}
