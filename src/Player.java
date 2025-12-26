
import java.util.ArrayList;
import java.util.Stack;

public class Player {
    private Stack<Room> moveHistory;
    private ArrayList<Item> inventory;
    private Room currentRoom;

    public void moveTo(Room r){
        moveHistory.push(r);
    }

    public void goBack(){
        moveHistory.pop();
    }
    public Room getCurrentRoom(){
        return moveHistory.peek();
    
    }

    public void useKey(){
        if(hasKey()){
        inventory.remove(0);
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

    public void pickUpItem(String name) throws InvalidCommandException{
        for (Item i: inventory){
            if(i.getName().equals(name)) {
                throw new InvalidCommandException("Item already exists.\n");
            }
            else {
                for (GameComponent content : currentRoom.getContents()) {
                if (content.getName().equals(name)) {
                    Item item = (Item) content;
                    item.collect(this); 
                    inventory.add(item);}
                }
            }
        throw new InvalidCommandException("Item does NOT exist.\n");
        }
        
    }
    
}
