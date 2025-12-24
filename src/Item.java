public class Item extends GameComponent implements Collectable, Comparable<Item> {
    

    private int value;
    private String itemType; //key, tool, clue
    public Item(String name,int value, int typeID) {
        super(name);
        this.value = value;
        switch (typeID) {
            case 0 -> itemType = "KEY";
            case 1 -> itemType = "TOOL";
            case 2 -> itemType = "CLUE";
            default -> throw new AssertionError();
        }
    }
    @Override 
    public void collect(Player p){ //implement collectable
        
    }

    @Override
    public int compareTo(Item rhs){
        return this.value - rhs.value;
    }
    public String getType(){return itemType;}

    @Override 
    public void inspect(){
        System.out.println("Item: " + getName() + "\n" + "itemType: " + itemType); 
    }


    
}
