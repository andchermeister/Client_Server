package lection2.tictak;

public class Data {
    private int state = 3;

    public int getState() { return state; }

    public void Tic(){
        System.out.print("Tic-");
        state = 1;
    }
    public void Tak(){
        System.out.print("Tak-");
        state = 2;
    }
    public void Toe(){
        System.out.println("Toe");
        state = 3;
    }
}
