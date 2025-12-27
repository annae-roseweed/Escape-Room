public class Driver {
    public static void main(String[] args) {
    Room room1 = new Room("Backroom 1", false);
    Room room2 = new Room("Backroom 2", false);
    Room room3 = new Room("Backroom 3", false);
    Room room4 = new Room("Backroom 4", false);
    Room room5 = new Room("Backroom 5", true);
    Room rRoom21 = new Room("Backroom of Backroom 2 1", false);
    Room rRoom22 = new Room("Backroom of Backroom 2 2", false);
    Room rRoom3 = new Room("Backroom of Backroom 3", false);
    Riddle pointer = new Riddle("Java question", 1, "Does Java has pointer?", "NO");
    pointer.addHint("Imagine you only can have the address of your friend house but you can change its location");
    pointer.addHint("You hopeless, Java only have reference");
    Calculate cal = new Calculate("A basic calculation", 1, "4 + 6 = ?", "10");
    cal.addHint("Really? How about using your fingers to solve it?");
    cal.addHint("...... \nFine, answer is 10");
    Code code = new Code("Number sequence", 2, "0-2-4-?-8\nSolve ?", "6");
    code.addHint("Do you see the trend in the sequence?");
    code.addHint("......\n This is a increasing number sequence, Pls solve it");
    code.addHint("The answer is 6 ...");
    room1.addComponent(pointer);
    room1.addComponent(cal);
    room1.addComponent(code);
    room2.addSubRoom(rRoom21);
    room2.addSubRoom(rRoom22);
    room3.addSubRoom(rRoom3);

    Player you = new Player();
    Item pen = new Item("pen", 1, 1, true);
    Item key = new Item("Key from the Void", 1, 0, true);
    Item clue1 = new Item("Clueless clue", 1, 2, true);
    Item statue = new Item("Totally not heavy statue", 1, 1, false);

    room1.addComponent(pen);
    room2.addComponent(key);
    room3.addComponent(clue1);
    room2.addComponent(statue);

    room1.connectRoom(room2);
    room2.connectRoom(room3);
    room3.connectRoom(room4);
    room4.connectRoom(room5);

    GameEngine engine = new GameEngine(you, room1);
    engine.run();

    }
}
