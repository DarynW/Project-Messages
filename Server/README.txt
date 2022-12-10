OVERVIEW
----------------------------------------------------------------
1) Description
    This is the server module that is meant to be run independently from the Client.
    The default port that the server listens on is 5000, and can be edited in Start.java
2) Compiling and Running
    The project can be compiled by running javac *.java in this directory.
    The project can be run by running java Start in this directory.
3) Dependencies
    The server acts as a proxy for accessing the database. It is actually very simple, and only handles reading and writing to the database.
    It handles different client instances on seperate threads, allowing for multiple clients to connect at once.


DATABASE
----------------------------------------------------------------
1) File
    A database csv file must be present in this directory and it must be named "database.csv"
    The database file is very difficult to read, and is not meant to be edited by hand. It is meant to be edited by the client module.

2) Corruption
    In the unlikely scenario that the program (client or server) crashes or has issues, it is likely that the database file is corrupted. 
    In this case, the database file can be deleted and replaced with a new one.

