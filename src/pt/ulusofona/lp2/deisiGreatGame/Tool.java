package pt.ulusofona.lp2.deisiGreatGame;

public abstract class Tool extends GameHouseElement {

    public Tool(int id, String titulo, int pos){
        super(id,  titulo,  pos);
     /*   this.id = id;
        this.titulo = titulo;
        this.pos = pos;*/
    }


    public static Tool createTool(int id, String titulo, int pos){
        switch(id){
            case 0:
                return new Inheritance(id, titulo, pos);
            case 1:
                return new FunctionalProgramming(id, titulo, pos);
            case 2:
                return new UnitTest(id, titulo, pos);
            case 3:
                return new CatchException(id, titulo, pos);
            case 4:
                return new  Ide(id, titulo, pos);
            case 5:
                return new HelpTeacher(id, titulo, pos);
            default:
                return null;
        }
    }

    public String toString(){
        return titulo;
    }
}
