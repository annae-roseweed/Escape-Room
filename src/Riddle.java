
public class Riddle extends Puzzle{

    public Riddle(String name, int difficulty, String content) {
        super(name, difficulty, content);
    }
    @Override
    public boolean attemptSolve(String input) { //only accept word or sentence 

        try {
            Double.parseDouble(input);
            throw new IllegalArgumentException(
                "Numbers are not allowed. Please enter text only."
            );
        } catch (NumberFormatException e) {
            return true; //input is not value 
        }
    }
    
    
}
