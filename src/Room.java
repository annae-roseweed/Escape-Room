import java.util.ArrayList;

public class Room extends GameComponent {

    private ArrayList<GameComponent> contents;
    private ArrayList<Room> connectedRooms;
    private boolean isExit;
    private boolean requiresKey;

    public Room(String name, boolean isExit) {
        super(name);
        this.isExit = isExit;
        contents = new ArrayList<>();
        connectedRooms = new ArrayList<>();
    }
    
    public void addSubRoom(Room sr) {
        this.contents.add(sr);
        sr.connectedRooms.add(this);
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

    public ArrayList<GameComponent> getContents(){
        return contents;
    }

    public boolean requiresKey(){
        return requiresKey;
    }
      

    public void look() {
        System.out.println("Room: " + name);
        if (requiresKey) {
            System.out.println("This room requires a key to enter.");
        }
        for (GameComponent gc : contents) {
            gc.inspect();
            System.out.println();
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
