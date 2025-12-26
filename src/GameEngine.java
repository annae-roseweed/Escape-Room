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

            turnCounter++;
            if (turnCounter % 3 == 0 && !hintQueue.isEmpty()) {
                System.out.println("HINT: " + hintQueue.poll());
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
                if (parts.length < 2) throw new InvalidCommandException("Specify puzzle name.");
                solvePuzzle(parts[1]);
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
    private void solvePuzzle(String puzzleName) throws InvalidPuzzleAnswerException {
        ArrayList<Puzzle> puzzles = new ArrayList<>();
        for (GameComponent gc : p.getCurrentRoom().getContents()) {
            if (gc instanceof Puzzle puzzle && gc.getName().equalsIgnoreCase(puzzleName)) {
                puzzles.add(puzzle);
                puzzle.inspect();
                System.out.print("\nEnter answer: ");
                String ans = scan.nextLine();
                if (puzzle.attemptSolve(ans)) {
                    System.out.println("Puzzle solved!");
                    hintQueue.add(puzzle.getHint()); // add hint for later
                }
                // insertion sort puzzles by difficulty
                sortPuzzles(puzzles);
                return;
            }
        }
        System.out.println("Puzzle not found: " + puzzleName);
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
