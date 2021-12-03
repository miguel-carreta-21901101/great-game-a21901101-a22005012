package pt.ulusofona.lp2.deisiGreatGame;

public class Abyss extends GameHouseElement {

    public Abyss(int id, String titulo, int pos){
        super(id,  titulo,  pos);
       /* this.id = id;
        this.titulo = titulo;
        this.pos = pos;*/
    }

    public static Abyss createAbyss(int id, String titulo, int pos){
        switch(id){

        case 0:
        return new SyntaxError(id, titulo, pos);
        case 1:
        return new LogicError(id, titulo, pos);
        case 2:
        return new ExceptionFault(id, titulo, pos);
        case 3:
        return new FileNotFoundException(id, titulo, pos);
        case 4:
        return new Crash(id, titulo, pos);
        case 5:
        return new DuplicatedCode(id, titulo, pos);
        case 6:
        return new SideEffect(id, titulo, pos);
        case 7:
        return new BlueScreenDeath(id, titulo, pos);
        case 8:
        return new InfiniteCircle(id, titulo, pos);
        case 9:
        return new SegmentationFault(id, titulo, pos);

        default:
            return null;
    }}

    public String toString(){return titulo;}
}
