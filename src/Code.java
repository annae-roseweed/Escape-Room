public class Code extends Puzzle {

    public Code(String name, int difficulty, String content) {
        super(name, difficulty, content);
    }
    @Override 
    public boolean attemptSolve(String input) { //only accept single digit num
        if (input.contains(".")) {
            throw new IllegalArgumentException("Bro has to type integer value!");
        }
    
        try {
            //convert input to int type
            int number = Integer.parseInt(input);
    
            //range of output check
            if (number < 0 || number > 9) {
                throw new IllegalArgumentException(
                    "Only type the single digit number bro!"
                );
            }
    
            return true;
    
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Bro must enter a number."
            );
        }
    }
    
}
