import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
public class GameEngine {
    private ArrayList<Room> map;
    private Queue<String> hintQueue;
    private int cap;
    private Player p;
    private Scanner scan;

    GameEngine(Player p, int cap){
        p = new Player();
        this.cap = cap;
        scan = new Scanner(System.in);
    }

    public void start(int cap){
        if(map.size() != cap){
            return; //The map is incomplete
            
        }
        else{
        p.moveTo(map.get(0)); //start game
        }
    }
    public void processCommand(String cmd){
        //player does puzzle, move here
        //"Choose your option.");
        //"1.Go to the next room\n2.Go back\n3.Interact with game component on the room\n4.Search connected rooms.");
        //Op 1 as GO, 2 as BACK, 3 as INTERACT, 4 as search connected room, default is INTERACT
        if (winConditionCheck()){
            //Player reach the deepest room, then command invalid
            return;
        }
        switch(cmd){
            case "GO":
                p.moveTo(map.get(0));

            case "BACK":
                map.add(0, p.getCurrentRoom()); //bring back the room to the map
                p.goBack();

            case "INTERACT":
                // 4 cases: tool, key, hint, puzzle
                boolean On = false;
                p.getCurrentRoom().look();
                do{
                System.out.println("Type the name of the item you want to pick");

                String gp = scan.nextLine();
                for (GameComponent a : p.getCurrentRoom().getContents()){
                    if (a.getName() == gp && a instanceof Item){ //only can pick up item
                        p.pickUpItem(gp);
                    }
                    else if (a.getName() == gp && a instanceof Puzzle){
                        a.inspect();
                        System.out.println("Pls enter your answer: ");
                        
                    }
                    else 

                    ;
                }
            }while (On == false);


            case "SUB":
                //explore subroom, do the process the same as the above
            
            default:
                System.out.println("Invalid input.");
        }
    }

    public void printStatus(Player p){
        System.out.println(p.getCurrentRoom() + "/" + cap); //show the current position of the player
    }

    public boolean winConditionCheck(){return (map.isEmpty());}
}
