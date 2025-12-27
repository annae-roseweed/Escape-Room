import java.util.ArrayDeque;
import java.util.Queue;
public abstract class Puzzle extends GameComponent implements Comparable<Puzzle>  {
    protected int difficulty;
    protected boolean isSolved;
    protected String content;
    protected String key;
    protected Queue<String> hints; //store the hints of puzzle


    public Puzzle(String name, int difficulty, String content, String key) {
        super(name);
        this.difficulty = difficulty;
        this.content = content;
        this.key = key;
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

    public String getHint(){
        return hints.remove(); //return ref then remove out of the queue
    }

    public void setIsSolved(boolean isSolved){
        this.isSolved = isSolved;
    }

    @Override 
    public int compareTo(Puzzle rhs){
        return this.difficulty - rhs.difficulty;
    }

    @Override
    public void inspect(){
        System.out.print("PUZZLE: " + getName() + "\nDifficulty: " + difficulty + "\t" + "Complete: " + ((isSolved == false)? "No" : "Yes") + "\n" + content);
    }
    public abstract boolean attemptSolve(String answer) throws InvalidPuzzleAnswerException;
}

