public abstract class Puzzle extends GameComponent implements Comparable<Puzzle>  {
    private int difficulty;
    private boolean isSolved;

    public Puzzle(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
        isSolved = false;
    }

    @Override 
    public int compareTo(Puzzle rhs){
        return this.difficulty - rhs.difficulty;
    }

    @Override
    public void inspect(){
        System.out.print("PUZZLE\nDifficulty: " + difficulty + "\t" + "Complete: " + (isSolved ? "No" : "Yes"));
    }

    public abstract boolean attemptSolve(String answer);
}

