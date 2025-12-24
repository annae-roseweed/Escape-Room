public class Calculate extends Puzzle {

    public Calculate(String name, int difficulty) {
        super(name, difficulty);
    }
    @Override
    public boolean attemptSolve(String input){
        if(input.contains("-") || input.contains("_")) 
            throw new IllegalArgumentException("Only accept positive number"); 

        try{
            Double.valueOf(input);
            throw new IllegalArgumentException("Only type a number");
            
        }catch (NumberFormatException e) {
            return true;
}
}
}
