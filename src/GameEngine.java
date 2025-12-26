import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
public class GameEngine {
    private ArrayList<Room> map = new ArrayList<>();
    private Queue<String> hintQueue;
    private Player p;
    private Scanner scan;
    private int queueCount;

    GameEngine(Player p){
        this.p = p;
        scan = new Scanner(System.in);
        queueCount = 0;
    }
    public void mapInit(Room r)
    {
        map.add(r);
    }

    public void start(int cap){
        if(map.size() != cap){
            return; //The map is incomplete 
            
        }
        else{
        p.moveTo(map.get(0)); //start game
        map.remove(0);
        
        }
    }

//MAIN GAME LOOP
    public void run() {
        System.out.println("Welcome to the Backrooms.");
        start(7);
        

        while (!winConditionCheck()) {
            printStatus(p);
            System.out.print("> ");
            System.out.println("Pls type your choice: GO, BACK, INTERACT, INVENTORY, LOOK, SUB");

            String cmd = scan.nextLine().toUpperCase();

            try {
                processCommand(cmd);
            } catch (InvalidPuzzleAnswerException e) {
                System.out.println(e.getMessage());
            } catch (InvalidCommandException e){
                System.out.println(e.getMessage());
            }


        }

        System.out.println("You escaped. Game over.");
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
                if (p.getCurrentRoom().getMark() == true) {
                    System.out.println("You are in subroom, can move the other main room, pls choose other option!");
                    break;
                }
                if(map.get(0).requiresKey()){
                    System.out.println("This room requires a key to enter.");
                    if(p.hasKey()){
                        map.get(0).unlock();
                        p.useKey();
                        System.out.println("Unlock successfully!");
                    }
                    else{
                        System.out.println("You don't have the required key!");
                        break; 
                    }
                }
                p.moveTo(map.get(0));
                map.remove(0);


                break;

            case "BACK":
                if (p.getCurrentRoom().getMark() == true) p.getCurrentRoom().setMark(false); //unmark ->Player can enter the main rooms again
                map.add(0, p.getCurrentRoom()); //bring back the room to the map
                p.goBack();
                break;

            case "INTERACT":
                // 4 cases: tool, key, hint, puzzle
                boolean On = true;
                int tries = 0; //reset the tries for the other puzzles

                p.getCurrentRoom().look();
                do{
                System.out.println("Type the name of the item you want to pick: (Type out) ");

                String gp = scan.nextLine();

                for (GameComponent a : p.getCurrentRoom().getContents()){
                    if (a.getName().equals(gp) && a instanceof Item){ //only can pick up item
                        p.pickUpItem(gp);
                        On = false;
                        break;
                    }
                    
                    else if (a.getName().equals(gp) && a instanceof Puzzle puzzle){
                        if(puzzle.getIsSolved() == true){
                            System.out.println("This puzzle's already solved.");
                            On = false;
                            break;
                        }

                        puzzle.inspect();
                        System.out.println("Pls enter your answer: ");
                        String ans = scan.nextLine();
                        try {
                            if(puzzle.attemptSolve(ans)){
                                queueCount++;
                                hintQueue.clear(); //remove the hint of the completed puzzle out of the hintQueue
                                On = false;
                            }
                            System.out.println("Congrats! You solved it!");
                            break;

                        } catch (InvalidPuzzleAnswerException e) {
                            System.out.println(e.getMessage());
                            tries++;
                        }

                        if (tries % 3 == 0 && !hintQueue.isEmpty() && queueCount != 0){
                            hintQueue.add(puzzle.getHint()); //dequeue puzzle own hint to the general hintqueue 
                            queueCount--;
                        }
                        
                        
                        }
                        
                    }
            }while (On == true);


            case "SUB":
                if (p.getCurrentRoom().getMark() == false) {
                    if(p.getCurrentRoom().getConnectedRooms().size() == 0){ //if the room has subroom or player's in subroom 
                    System.out.println("This room doesn't any sub room");
                    break;
                }
                }
                //explore subroom, do the process the same as the above
                int num;
                System.out.println("Pls type the number of subroom you want to go (On range)");
                do{
                    num = scan.nextInt() - 1;
                }while (num > p.getCurrentRoom().getConnectedRooms().size());

                if(p.getCurrentRoom().getConnectedRooms().get(num).requiresKey()){
                    System.out.println("This room requires a key to enter.");
                    if(p.hasKey()){
                        p.getCurrentRoom().getConnectedRooms().get(num).unlock();
                        p.useKey();
                        System.out.println("Unlock successfully!");
                    }
                    else{
                        System.out.println("You don't have the required key!");
                        break; 
                    }
                }
                System.out.println("You enter the subroom successfully!");


                p.getCurrentRoom().setMark(true);
                p.moveTo(p.getCurrentRoom().getConnectedRooms().get(num)); //push sub to historyMove
                p.getCurrentRoom().getConnectedRooms().remove(num); //remove the targeted sub out of connectedRoom list
                p.getCurrentRoom().setMark(true);

                break;

            case "INVENTORY":
                p.printInventory();
                break;
            case "MAP":
                p.getCurrentRoom().exploreRecursive(0);
            case "LOOK": 
                p.getCurrentRoom().look();
                break;
            default:
                throw new InvalidCommandException("Invalid input.\n");
        }
    }


    public void printStatus(Player p){
        System.out.println(p.getCurrentRoom().name); //show the current position of the player
    }

    public boolean winConditionCheck(){return (map.isEmpty());}
}
