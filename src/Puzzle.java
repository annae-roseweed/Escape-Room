import java.util.Queue;
import java.util.ArrayDeque;
public abstract class Puzzle extends GameComponent implements Comparable<Puzzle>  {
    private int difficulty;
    private boolean isSolved;
    private String content;
    private String key;
    private Queue<String> hints; //store the hints of puzzle


    public Puzzle(String name, int difficulty, String content, String key) {
        super(name);
        this.difficulty = difficulty;
        this.content = content;
        isSolved = false;
        hints = new ArrayDeque<>();
    }

    public void addHint(String input){ //add hint to the hints queue
        hints.add(input);
    }

    public String getKey(){
        return key;
    }

    public String getContent(){
        return content; 
    }

    public boolean getIsSolved(){
        return isSolved;
    }

    @Override 
    public int compareTo(Puzzle rhs){
        return this.difficulty - rhs.difficulty;
    }

    @Override
    public void inspect(){
        System.out.print("PUZZLE\nDifficulty: " + difficulty + "\t" + "Complete: " + (isSolved ? "No" : "Yes") + "\n" + content);
    }

    public abstract boolean attemptSolve(String answer);
}

