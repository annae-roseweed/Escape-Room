public class Item extends GameComponent implements Collectable, Comparable<Item> {
    

    private int value;
    private String itemType; //key, tool, clue
    private boolean collectible;
    public Item(String name,int value, int typeID, boolean collectible) {
        super(name);
        this.value = value;
        switch (typeID) {
            case 0 -> itemType = "KEY";
            case 1 -> itemType = "TOOL";
            case 2 -> itemType = "CLUE";
            default -> throw new AssertionError();
        }
        this.collectible = collectible;
    }
    @Override 
    public void collect(Player p) throws InvalidCommandException{ //implement collectable
        if(!isCollectible())
            throw new InvalidCommandException("This item can't be collected.\n");
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

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }


    
}
