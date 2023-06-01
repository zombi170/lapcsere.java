public class Frame {
    private String id;
    private int number;
    private int freeze;
    private boolean sc;

    public Frame (String letter, int num){
        id = letter;
        number = num;
        freeze = 3;
        sc = false;
    }

    public int overwrite(int num) {
        if (freeze == 0 && !sc){
            number = num;
            freeze = 4;
            sc = false;
            return 1;
        } else if (freeze != 0) {
            return 0;
        } else if (sc){
            sc = false;
            return 2;
        }
        return -1;
    }

    public void decreaseFreeze(){
        if (freeze != 0)
            freeze--;
    }

    public void giveChance(){
        sc = true;
    }

    public String getId(){
        return id;
    }

    public int getNumber(){
        return number;
    }
}
