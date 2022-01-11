package pt.ulusofona.lp2.deisiGreatGame;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pt.ulusofona.lp2.deisiGreatGame.Abyss.createAbyss;

public class TestCoverage {

    //TESTSAUXCODE
    @Test
    public void testOrdernarLinguagensProgramacao(){
        String messy_string = "Python;Java;Kotlin";
        String ordered_string = AuxCode.ordernarLinguagensProgramacao(messy_string);
        String expected_string = "Java; Kotlin; Python";
        assertEquals(expected_string, ordered_string);
    }

    @Test
    public void testChangePosAndCasa(){
        Programmer programmer = new Programmer("Steve", 2);
        HashMap<Integer, Programmer> programmers = new HashMap<>();
        programmers.put(0, programmer);

        //primeira etapa do precurso
        AuxCode.changePosAndCasa(4, programmers, programmer);
        System.out.println(programmer.getPos());
        System.out.println(programmer.getCasasPercorridasList());

        //segunda etapa do precurso
        AuxCode.changePosAndCasa(7, programmers, programmer);
        System.out.println(programmer.getPos());
        System.out.println(programmer.getCasasPercorridasList());

        List<Integer> expected_precurso = new ArrayList<>();
        expected_precurso.add(4);
        expected_precurso.add(7);

        assertEquals(expected_precurso, programmer.getCasasPercorridasList());
    }

    @Test
    public void testSetTitleAbyss(){
        assertEquals("Erro de sintaxe", AuxCode.setTitleAbyss(0));
    }

    @Test
    public void testIsAbyss(){
        List<Abyss> abysses = new ArrayList<>();
        Abyss abyss0 = createAbyss(0, "Syntax Error", 1);
        abysses.add(0, abyss0);

        assertTrue(AuxCode.isAbyss(abysses, 1));
    }

    @Test
    public void testSetIdAbyss(){
        List<Abyss> abysses = new ArrayList<>();
        Abyss abyss0 = createAbyss(3, "Syntax Error", 1);
        abysses.add(0, abyss0);

        //System.out.println(AuxCode.setIdAbyss(abysses, 1));
        assertEquals(3, AuxCode.setIdAbyss(abysses,1));
    }

    @Test
    public void testSetTool(){
        List<Tool> tools = new ArrayList<>();
        Tool tool0 = Tool.createTool(4, "IDE", 0);
        tools.add(tool0);

        System.out.println(AuxCode.setTool(tools, 0));
        assertEquals("IDE", Objects.requireNonNull(AuxCode.setTool(tools, 0)).title);
    }

    @Test
    public void testSetTitleTool(){
        assertEquals("IDE", AuxCode.setTitleTool(4));
    }

    @Test
    public void testIsTool(){
        List<Tool> tools = new ArrayList<>();
        Tool tool0 = Tool.createTool(4, "IDE", 0);
        tools.add(tool0);

        assertTrue(AuxCode.isTool(tools, 0));
    }

    //TESTSAUXCODE
    
    //TESTSBLUESCREENDEATH
    @Test
    public void testBlueScreenDeathToString(){
        BlueScreenDeath error = new BlueScreenDeath(7, "Blue Screen of Death", 0);
        assertEquals("Blue Screen of Death", error.getTitle());
    }

    @Test
    public void testBlueScreenDeathGetImagemPng(){
        BlueScreenDeath error = new BlueScreenDeath(7, "Blue Screen of Death", 0);
        assertEquals("bsod.png", error.getImagePng());
    }
    //TESTSBLUESCREENDEATH

    //TESTSSYNTAXERROR
    @Test
    public void testSyntaxErrorToString(){
        SyntaxError error = new SyntaxError(7, "Syntax Error", 0);
        assertEquals("Syntax Error", error.getTitle());
    }

    @Test
    public void testSyntaxErrorGetImagemPng(){
        SyntaxError error = new SyntaxError(7, "Syntax Error", 0);
        assertEquals("syntax.png", error.getImagePng());
    }
    //TESTSSYNTAXERROR
}
