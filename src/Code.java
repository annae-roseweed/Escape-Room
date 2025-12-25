public class Code extends Puzzle {

    public Code(String name, int difficulty, String content, String key) {
        super(name, difficulty, content, key);
    }
    @Override
    public boolean attemptSolve(String input) throws InvalidPuzzleAnswerException{
        int attempt;
        try {
        attempt = Integer.parseInt(input);
        } catch (NumberFormatException e) { throw new InvalidPuzzleAnswerException("Must enter a number.\n"); }
            if (attempt > 9 || attempt < 0) throw new InvalidPuzzleAnswerException("Must enter a single-digit number between 0 and 9.\n");
            else if (attempt != Integer.parseInt(getKey())){
            throw new InvalidPuzzleAnswerException("The answer is incorrect!\n");
            }
        else return true;
}
    
}
