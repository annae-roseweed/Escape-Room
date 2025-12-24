import java.util.ArrayList;

public class Room extends GameComponent {

    private ArrayList<GameComponent> contents;
    private ArrayList<Room> connectedRooms;
    private boolean isExit;

    public Room(String name, boolean isExit) {
        super(name);
        this.isExit = isExit;
        contents = new ArrayList<>();
        connectedRooms = new ArrayList<>();
    }

    public void addComponent(GameComponent gc) {
        contents.add(gc);
    }

    public void connectRoom(Room r) {
        connectedRooms.add(r);
    }

    public boolean isExit() {
        return isExit;
    }

    public void look() {
        System.out.println("Room: " + name);
        for (GameComponent gc : contents) {
            System.out.println("- " + gc.getName());
        }
    }
    
    // 1. Recursive traversal
    public void exploreRecursive(int depth) {
        System.out.println("  ".repeat(depth) + name);
        for (GameComponent gc : contents) {
            if (gc instanceof Room) {
                ((Room) gc).exploreRecursive(depth + 1);
            }
        }
    }

    // 2. Recursive search for item
    public boolean containsItemRecursive(String itemName) {
        for (GameComponent gc : contents) {
            if (gc instanceof Item &&
                gc.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
            if (gc instanceof Room &&
                ((Room) gc).containsItemRecursive(itemName)) {
                return true;
            }
        }
        return false;
    }

    // 3. Recursive depth calculation
    public int maxDepthRecursive() {
        int max = 0;
        for (GameComponent gc : contents) {
            if (gc instanceof Room) {
                max = Math.max(max,
                        ((Room) gc).maxDepthRecursive());
            }
        }
        return max + 1;
    }

    @Override
    public void inspect() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
