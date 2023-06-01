import java.util.*;

public class Main {

    public static LinkedList<Integer> read(){
        LinkedList<Integer> queue = new LinkedList<>();
        boolean next = true;
        Scanner scan = new Scanner(System.in);
        String sor;

        while (next && scan.hasNextLine()) {
            sor = scan.nextLine();
            while (sor.isEmpty() && next){
                if (scan.hasNextLine()){
                    sor = scan.nextLine();
                }
                else{
                    next = false;
                }
            }

            if (next){
                String[] in = sor.split(",");

                for (String i : in){
                    if (i.length() == 3){
                        queue.add(((int) i.charAt(1) - 48) * 10 + (int) i.charAt(2) - 48);
                    } else if (i.length() == 2){
                        if (i.charAt(0) == '-'){
                            queue.add((int) i.charAt(1) - 48);
                        } else {
                            queue.add(((int) i.charAt(0) - 48) * 10 + (int) i.charAt(1) - 48);
                        }
                    } else {
                        queue.add((int) i.charAt(0) - 48);
                    }
                }
            }
        }
        scan.close();
        return queue;
    }

    public static void main(String[] args) {
        LinkedList<Integer> input = read();
        LinkedList<Frame> fifo = new LinkedList<>();
        String output = null;
        int temp, ret, frozen;
        boolean success;
        Frame fifo_temp;

        while (!input.isEmpty()){
            temp = input.pop();

            if (fifo.size() < 3){
                switch (fifo.size()){
                    case 0:
                        fifo.add(new Frame("A", temp));
                        output = "A";
                        break;
                    case 1:
                        if (fifo.get(0).getNumber() != temp){
                            fifo.add(new Frame("B", temp));
                            output = output.concat("B");
                        } else {
                            output = output.concat("-");
                        }
                        fifo.get(0).decreaseFreeze();
                        break;
                    case 2:
                        if (fifo.get(0).getNumber() != temp && fifo.get(1).getNumber() != temp){
                            fifo.add(new Frame("C", temp));
                            output = output.concat("C");
                        } else {
                            output = output.concat("-");
                        }
                        fifo.get(0).decreaseFreeze();
                        fifo.get(1).decreaseFreeze();
                        break;
                    default:
                        break;
                }
            } else {
                success = false;
                frozen = 0;

                for (Frame f : fifo){
                    if (temp == f.getNumber()){
                        success = true;
                        f.giveChance();
                        output = output.concat("-");
                    }
                }
                while (!success){
                    for (Frame f : fifo){
                        ret = f.overwrite(temp);

                        if (ret == 1){
                            success = true;
                            output = output.concat(f.getId());
                            fifo_temp = f;
                            fifo.remove(f);
                            fifo.add(fifo_temp);
                            break;
                        } else if (ret == 0){
                            frozen++;
                        } else if (ret == 2){
                            fifo_temp = f;
                            fifo.remove(f);
                            fifo.add(fifo_temp);
                            break;
                        }
                    }
                    if (frozen == 3){
                        success = true;
                        output = output.concat("*");
                    }
                }
                for (Frame f : fifo){
                    f.decreaseFreeze();
                }
            }
        }

        System.out.println(output);
        int errors = 0;
        for (int i = 0; i < output.length(); i++){
            if (output.charAt(i) != '-'){
                errors++;
            }
        }
        System.out.println(errors);
    }
}