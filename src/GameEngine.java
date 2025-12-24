
import java.util.ArrayList;
import java.util.Queue;
public class GameEngine {
    private ArrayList<Room> map;
    private Queue<String> hintQueue;
    public void start(){
        Player p = new Player();
        
    }
    public void processCommand(String cmd){}
    public void printStatus(){}
    public boolean winConditionCheck(){return (map.isEmpty());}
}
