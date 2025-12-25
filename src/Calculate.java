public class Calculate extends Puzzle {
    private double answer;
    public Calculate(String name, int difficulty, String content, double answer) {
        super(name, difficulty, content);
        this.answer = answer;
    }
    @Override
    public boolean attemptSolve(String input){
        if(input.contains("-") || input.contains("_")) 
            throw new IllegalArgumentException("Only accept positive number"); 
        while(!isIsSolved()){
        try{
            double attempt = Double.parseDouble(input);
            boolean correct = attempt == answer;
        }catch (NumberFormatException e) {
            System.out.println("Error: Wrong number format.");
} catch()
}
    return true;
}
}