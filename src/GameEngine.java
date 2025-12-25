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
    public void processCommand(String cmd) throws InvalidCommandException, InvalidPuzzleAnswerException{
        //player does puzzle, move here
        //"Choose your option.");
        //"1.Go to the next room\n2.Go back\n3.Interact with game component on the room\n4.Search connected rooms.");
        //Op 1 as GO, 2 as BACK, 3 as INTERACT, 4 as search connected room, default is throw InvalidCommandException
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
                boolean On = true;
                p.getCurrentRoom().look();
                do{
                System.out.println("Type the name of the item you want to pick: (Type out) ");

                String gp = scan.nextLine();

                for (GameComponent a : p.getCurrentRoom().getContents()){
                    if (a.getName().equals(gp) && a instanceof Item){ //only can pick up item
                        p.pickUpItem(gp);
                    }
                    
                    else if (a.getName().equals(gp) && a instanceof Puzzle puzzle){
                        if(puzzle.getIsSolved() == true){
                            System.out.println("This puzzle's already solved.");
                            On = false;
                        }

                        puzzle.inspect();
                        System.out.println("Pls enter your answer: ");
                        String ans = scan.nextLine();
                        if(puzzle.attemptSolve(ans))
                            System.out.println("Congrat! You solved it!");
                        
                        }
                        
                    }
            }while (On == false);


            case "SUB":
                //explore subroom, do the process the same as the above
            
            default:
                throw new InvalidCommandException("Invalid input.\n");
        }
    }


    public void printStatus(Player p){
        System.out.println(p.getCurrentRoom() + "/" + cap); //show the current position of the player
    }

    public boolean winConditionCheck(){return (map.isEmpty());}
}
