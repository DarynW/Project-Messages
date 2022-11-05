public class TestDatabase {
    public static void main(String[] args) {
        try {
            Database database = new Database("hello.txt");

            if (!database.documentExists("1"))
                database.create("1");
            database.add("1", "name", "John");
            database.add("1", "age", "57");

            // read the values from the database
            System.out.println(database.get("1", "name"));
            System.out.println(database.get("1", "age"));
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
