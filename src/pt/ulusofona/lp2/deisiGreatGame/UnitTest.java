package pt.ulusofona.lp2.deisiGreatGame;

public class UnitTest extends Tool{
    public UnitTest(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }

    public UnitTest(int id, String title){
        super(id, title);

    }

    @Override
    public String getImagePng(){
        return "unit-tests.png";
    }

}
