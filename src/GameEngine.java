import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class GameEngine {
    private Player p;
    private Scanner scan;
    private int turnCounter;
    private Queue<String> hintQueue;

    public GameEngine(Player p, Room startingRoom){
        this.p = p;
        this.p.moveTo(startingRoom);
        scan = new Scanner(System.in);
        turnCounter = 0;
        hintQueue = new ArrayDeque<>();
    }

    public void run() {
        System.out.println("Welcome to the Backrooms.");
        System.out.println("Type commands: look / move <roomName> / back / pickup <itemName> / inventory / solve <puzzleName> / map / stop");

        while (!winConditionCheck()) {
            System.out.println("\nCurrent room: " + p.getCurrentRoom().getName());
            System.out.print("> ");
            String cmd = scan.nextLine();

            try {
                processCommand(cmd);
            } catch (InvalidCommandException | InvalidPuzzleAnswerException | LockedRoomException e) {
                System.out.println(e.getMessage());
            }
            
        }

        System.out.println("You escaped. Game over!");
    }

    // Process user input
    public void processCommand(String cmd) throws InvalidCommandException, InvalidPuzzleAnswerException, LockedRoomException {
        String[] parts = cmd.split(" ", 2);
        String command = parts[0].toUpperCase();

        switch(command) {
            case "LOOK":
                p.getCurrentRoom().look();
                break;

            case "MOVE":
                if (parts.length < 2) throw new InvalidCommandException("Specify room name to move.");
                moveToRoom(parts[1]);
                break;

            case "BACK":
                p.goBack();
                break;

            case "PICKUP":
                if (parts.length < 2) throw new InvalidCommandException("Specify item to pick up.");
                p.pickUpItem(parts[1]);
                p.sortInventory(); // selection sort
                break;

            case "INVENTORY":
                p.printInventory();
                break;

            case "SOLVE":
                  if (parts.length < 2) {
                System.out.println("Specify a puzzle to solve.");
                } else {
                // Call solvePuzzle and let it handle all attempts
                solvePuzzle(parts[1].trim());
            }
                break;
                
            case "MAP":
                p.getCurrentRoom().exploreRecursive(0);
                break;

            case "STOP":
                System.out.println("You are a coward, goodbye");
                System.exit(0);
                break;    

            default:
                throw new InvalidCommandException("Invalid command.");
        }
    }

    // Move player to a connected room
    private void moveToRoom(String roomName) throws LockedRoomException {
        Room current = p.getCurrentRoom();
        for (Room r : current.getConnectedRooms()) {
            if (r.getName().equalsIgnoreCase(roomName)) {

                if (r.requiresKey() && !p.hasKey(r.getRequiredKeyName())) {
                    throw new LockedRoomException("Room " + r.getName() + " requires key: " + r.getRequiredKeyName());
                } else if (r.requiresKey()) {
                    p.useKey(r.getRequiredKeyName());
                    r.unlock();
                    System.out.println("Used key to unlock " + r.getName());
                }

                p.moveTo(r);
                System.out.println("Moved to " + r.getName());
                return;
            }
        }
        System.out.println("No connected room named " + roomName);
    }

    // Solve a puzzle in the current room
    private void solvePuzzle(String puzzleName) {
    ArrayList<Puzzle> puzzles = new ArrayList<>();
    Puzzle targetPuzzle = null;

    // Find the puzzle in the current room
    for (GameComponent gc : p.getCurrentRoom().getContents()) {
        if (gc instanceof Puzzle puzzle && puzzle.getName().equalsIgnoreCase(puzzleName)) {
            targetPuzzle = puzzle;
            break;
        }
    }

    // Puzzle not found
    if (targetPuzzle == null) {
        System.out.println("Puzzle not found: " + puzzleName);
        return;
    }

    // Already solved
    if (targetPuzzle.getIsSolved()) {
        System.out.println("This puzzle is already finished");
        return;
    }

    puzzles.add(targetPuzzle);
    int attempts = 0;

    // Loop until the puzzle is solved
    while (!targetPuzzle.getIsSolved()) {
        targetPuzzle.inspect();
        System.out.print("\nEnter answer: ");
        String ans = scan.nextLine();

        try {
            if (targetPuzzle.attemptSolve(ans)) {
                System.out.println("Puzzle solved!");
                targetPuzzle.setIsSolved(true);
                hintQueue.add(targetPuzzle.getHint()); // store hint if needed
            }
        } catch (InvalidPuzzleAnswerException e) {
            // Catch incorrect answers or invalid input
            attempts++;
            System.out.print(e.getMessage()); // display the error message

            // Show hint every 3 failed attempts
            if (attempts % 3 == 0) {
                System.out.println("Hint: " + targetPuzzle.getHint());
            }
        }
    }

    // Optional: sort puzzles by difficulty
    sortPuzzles(puzzles);
}





    // Insertion sort for puzzles
    private void sortPuzzles(ArrayList<Puzzle> puzzles) {
        for (int i = 1; i < puzzles.size(); i++) {
            Puzzle key = puzzles.get(i);
            int j = i - 1;
            while (j >= 0 && puzzles.get(j).compareTo(key) > 0) {
                puzzles.set(j + 1, puzzles.get(j));
                j--;
            }
            puzzles.set(j + 1, key);
        }
    }

    // Win condition
    private boolean winConditionCheck() {
        return p.getCurrentRoom().isExit();
    }
}
