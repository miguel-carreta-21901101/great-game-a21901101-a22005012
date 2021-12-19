package pt.ulusofona.lp2.deisiGreatGame;

public abstract class Tool extends GameHouseElement {

    public Tool(int id, String title, int pos){
        super(id,  title,  pos);

    }


    public static Tool createTool(int id, String title, int pos){
        switch(id){
            case 0:
                return new Inheritance(id, title, pos);
            case 1:
                return new FunctionalProgramming(id, title, pos);
            case 2:
                return new UnitTest(id, title, pos);
            case 3:
                return new CatchException(id, title, pos);
            case 4:
                return new  Ide(id, title, pos);
            case 5:
                return new HelpTeacher(id, title, pos);
            default:
                return null;
        }
    }


}
