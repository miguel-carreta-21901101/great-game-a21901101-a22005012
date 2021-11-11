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
}
