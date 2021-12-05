package pt.ulusofona.lp2.deisiGreatGame;

public class UnitTest extends Tool{
    public UnitTest(int id, String titulo, int pos) {
        super(id, titulo, pos);
    }
    @Override
    public String getTitleInfo(){
        return title;
    }

    @Override
    public String getImagePng(){
        return "unit-tests.png";
    }

}
