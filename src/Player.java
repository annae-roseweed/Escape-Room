
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
    public boolean hasKey(){ //Find if the inventory of the player has at least a key
        for (Item i : inventory){
            if (i.getType().toUpperCase().equals("KEY")) return true;
        }
        return false;
    }

    public void pickUpItem(String name){
        for (Item i: inventory){
            if(i.getName().equals(name)) {
                System.out.println("Item already exists.");
                return;
            }
        }
        
    }
    
}
