package pt.ulusofona.lp2.deisiGreatGame;

public abstract class Abyss extends GameHouseElement {

    public Abyss(int id, String title, int pos){
        super(id,  title,  pos);
       /* this.id = id;
        this.title = title;
        this.pos = pos;*/
    }

    public static Abyss createAbyss(int id, String title, int pos){

        switch(id){

        case 0:
        return new SyntaxError(id, title, pos);
        case 1:
        return new LogicError(id, title, pos);
        case 2:
        return new ExceptionFault(id, title, pos);
        case 3:
        return new FileNotFoundException(id, title, pos);
        case 4:
        return new Crash(id, title, pos);
        case 5:
        return new DuplicatedCode(id, title, pos);
        case 6:
        return new SideEffect(id, title, pos);
        case 7:
        return new BlueScreenDeath(id, title, pos);
        case 8:
        return new InfiniteLoop(id, title, pos);
        case 9:
        return new SegmentationFault(id, title, pos);

        default:
            return null;
    }}


}
