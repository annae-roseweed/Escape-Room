public class Driver {
    public static void main(String[] args) {
    Room room1 = new Room("Backroom 1", false);
    Room room2 = new Room("Backroom 2", false);
    Room room3 = new Room("Backroom 3", false);
    Room room4 = new Room("Backroom 4", false);
    Room room5 = new Room("Backroom 5", true);
    Room rRoom2 = new Room("Backroom of Backroom 2", false);
    Room rRoom3 = new Room("Backroom of Backroom 3", false);
    room2.addSubRoom(rRoom2);
    room3.addSubRoom(rRoom3);
    // room1.connectRoom(room2);
    // room2.connectRoom(room1);
    // room2.connectRoom(room3);
    // room3.connectRoom(room2);
    // room3.connectRoom(room4);
    // room4.connectRoom(room3);
    // room4.connectRoom(room5);
    // room5.connectRoom(room4);
    Player you = new Player();
    Item pen = new Item("Pen from the Void", 1, 2, true);
    Item key = new Item("Key from the Void", 1, 1, true);
    Item clue1 = new Item("Clueless clue", 1, 3, true);
    Item statue = new Item("Totally not heavy statue", 1, 2, false);

    room1.addComponent(pen);
    room2.addComponent(key);
    room3.addComponent(clue1);
    room2.addComponent(statue);
    
    GameEngine engine = new GameEngine(you);
    engine.mapInit(room1);
    engine.mapInit(room2);
    engine.mapInit(room3);
    engine.mapInit(room4);
    engine.mapInit(room5);
    engine.run();

    }
}
