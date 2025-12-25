public class Calculate extends Puzzle { //Math question
    public Calculate(String name, int difficulty, String content, String key) {
        super(name, difficulty, content, key);
    }
    @Override
    public boolean attemptSolve(String input) throws InvalidPuzzleAnswerException{
        double attempt;
        try {
        attempt = Double.parseDouble(input);
        } catch (NumberFormatException e) { throw new InvalidPuzzleAnswerException("Must enter a number.\n"); }
            if (attempt != Double.parseDouble(getKey())){
            throw new InvalidPuzzleAnswerException("The answer is incorrect!\n");
            }
        else return true;
}
}
