public class Main {
    public static void main(String[] args) throws Exception {
        Database db = new Database("database.txt");
        db.write("1", "dataType", "User");
    }
}
