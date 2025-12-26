
import java.util.ArrayList;
import java.util.Stack;

public class Player {
    private Stack<Room> moveHistory;
    private ArrayList<Item> inventory;
    private Room currentRoom;

    public Player() {
        moveHistory = new Stack<>();
        inventory = new ArrayList<>();
        currentRoom = new Room("Start", false);
        moveHistory.push(currentRoom);
    }
    
    public void addItem(Item item){
        inventory.add(item);
        currentRoom.getContents().remove(item);
    }

    public void moveTo(Room r){
        moveHistory.push(r);
        currentRoom = r;
    }

    public void goBack(){
        if (moveHistory.size() > 1){
        moveHistory.pop();
        }
    }

    public Room getCurrentRoom(){
        return moveHistory.peek();
    
    }

    public boolean hasKey(String keyName) {
    for (Item i : inventory) {
        if (i.getType().equalsIgnoreCase("KEY") && i.getName().equalsIgnoreCase(keyName)) {
            return true;
        }
    }
    return false;
}
    
    public void useKey(String keyName) {
    // Remove the first matching key with this name from inventory
    for (int i = 0; i < inventory.size(); i++) {
        Item item = inventory.get(i);
        if (item.getType().equalsIgnoreCase("KEY") && item.getName().equalsIgnoreCase(keyName)) {
            inventory.remove(i);
            return;
        }
    }
}

    public void sortInventory() {
    for (int i = 0; i < inventory.size() - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < inventory.size(); j++) {
            if (inventory.get(j).compareTo(inventory.get(minIndex)) < 0) {
                minIndex = j;
            }
        }
        Item temp = inventory.get(i);
        inventory.set(i, inventory.get(minIndex));
        inventory.set(minIndex, temp);
    }
}

    public void printInventory(){
        if (!inventory.isEmpty()){
        System.out.println("INVENTORY:");
        for (Item i : inventory){
            System.out.println(i.getName());
        }}
        else System.out.println("You have no item!");
    }
    public boolean hasKey(){ //Find if the inventory of the player has at least a key
        for (Item i : inventory){
            if (i.getType().toUpperCase().equals("KEY")) return true;
        }
        return false;
    }

   public void pickUpItem(String name) throws InvalidCommandException {
    // Check if the player already has the item
    for (Item i : inventory) {
        if (i.getName().equalsIgnoreCase(name)) {
            throw new InvalidCommandException("You already have this item: " + name);
        }
    }

    // Search for the item in the current room
    for (GameComponent gc : currentRoom.getContents()) {
        if (gc instanceof Item item && item.getName().equalsIgnoreCase(name)) {
            inventory.add(item);               // Add to inventory
            currentRoom.getContents().remove(item); // Remove from room
            System.out.println("Picked up: " + item.getName());
            return;
        }
    }

    // Item not found
    throw new InvalidCommandException("Item does NOT exist in this room: " + name);
}

}
