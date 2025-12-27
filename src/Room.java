import java.util.ArrayList;

public class Room extends GameComponent {

    private ArrayList<GameComponent> contents;
    private ArrayList<Room> connectedRooms;
    private boolean isExit;
    private boolean requiresKey;
    private boolean Mark;
    private String requiredKeyName = null;

    public Room(String name, boolean isExit) {
        super(name);
        this.isExit = isExit;
        contents = new ArrayList<>();
        connectedRooms = new ArrayList<>();
        requiresKey = false;
        Mark = false;
    }
    
    public void setRequiredKey(String keyName) {
    requiredKeyName = keyName;
    requiresKey = true;
}

public String getRequiredKeyName() {
    return requiredKeyName;
}
    
    public void addSubRoom(Room sr) {
        this.contents.add(sr);
        this.connectedRooms.add(sr);
        sr.connectedRooms.add(this);
    }

    public void addComponent(GameComponent gc) {
        contents.add(gc);
    }

    public boolean getMark(){
        return Mark;
    }

    public void setMark(boolean mark){
        this.Mark = mark;
    }

    public void connectRoom(Room r) {
        connectedRooms.add(r);
    }

    public boolean isExit() {
        return isExit;
    }

    public ArrayList<Room> getConnectedRooms(){
        return connectedRooms;
    }

    public ArrayList<GameComponent> getContents(){
        return contents;
    }

    public boolean requiresKey(){
        return requiresKey;
    }

    public void unlock(){
        requiresKey = false;
    }
      

    public void look() {
        System.out.println("Room: " + name);
        // if (requiresKey) {
        //     System.out.println("This room requires a key to enter.");
        // }
        for (GameComponent gc : contents) {
            gc.inspect();
            System.out.println();
        }
    }
    
    // 1. Recursive traversal
    public void exploreRecursive(int depth) {
    if (Mark) return; // already visited
    Mark = true;

    // Print the room name with indentation
    
    // Recurse through sub-rooms
    for (GameComponent gc : contents) {
        if (gc instanceof Room subRoom) {
            subRoom.exploreRecursive(depth + 1);
        }
    }

    System.out.println("  ".repeat(depth) + name);

    // Recurse through connected rooms
    for (Room conn : connectedRooms) {
        conn.exploreRecursive(depth + 1);
    }

    Mark = false; // reset for future calls
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
    String lockTag = requiresKey ? " (LOCKED)" : "";
    String exitTag = isExit ? " (EXIT)" : "";
    System.out.println("Room: " + name + exitTag + lockTag);
    }
}
