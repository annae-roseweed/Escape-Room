
public class Riddle extends Puzzle{

    public Riddle(String name, int difficulty, String content, String key) {
        super(name, difficulty, content, key);
    }

    @Override
    public boolean attemptSolve(String input) throws InvalidPuzzleAnswerException { //only accept word or sentence
        if (input.equalsIgnoreCase(getKey())) return true;
        else throw new InvalidPuzzleAnswerException("Incorrect!");
    }
    
}
