Authors: Andy, Fischer, Daryn and Yash
Date: 12/10/2022

There are two seperate README files that can be read inside the Client and Server directory's.

The Client and Server are two separate projects and are intended to be run seperately. 
Doing this may be tedious, and it is recommended that the projects are opened seperately in seperate IDE windows.

Fischer submitted every file to vocareum and brightspace.



Report on the interworkings of the project.

Our project is made to assist tutors connect with buyers for sessions. The project is seperated into two componenets, the Server and the Client.
The server is actually rather simple. It's function is to store the information of the tutors and buyers, and to allow the client to access this information.
The server is run on seperate threads, and is constantly listening for requests from the clients on those threads. The server is able to handle multiple clients at once.
The requests handled by the server are one of two. Readfile and writefile. The parsing of data and writing to the data stored inside the databaxse.csv file is done
entirely by the client. We chose to do this over creating tens of requests that the client could send the server in order to keep the program simple. The cost for doing this
was the security of the program (not that it mattered in the first place, its not like we're even attempting to keep passwords hidden).

The client is the main part of the project. It is the part that the user interacts with. The client is able to connect to the server, and send requests to the server.
The client is comprised of three main components. The main, the menu and the database. The database contains methods that fetch data from the server and parse or write to that data.
The menu contains methods that are used to display the menu to the user, and to handle the user input. It was modeled after the basic command line interface, with the added functionality of a button and 
a lack of messy text history. The main components gives the user a series of menus that can be selected by given an integer input. The user can also type input to messages and searches.
Multiple clients can be run at once, interfacing with the server without any conflicts. Messages between users are not updated in realtime due to the complexity of such systems. Rather, the user can 
refresh their messages by exiting out of them and reopening to be updated on any incoming messages.

The database is rather elegant, being based off of MongoDB and its document system. The database stores information in seperate "documents" which hold their own key and fields. These fields are 
specified by the client database functions, and can hold any String value. Using the methods, we can search for a document by its key, and then access the fields of that document. We can also search for
documents by a field, and then access the key of that document. This allows us to search for a user by their username, and then access their password, or search for a user by their password and then access
their username. This is the basis of the login system and user searches. It has also allowed us to be extremely organized with our methods of storing data. With it, we're able to keep a document
for every group of data that we see necessary. For example, we have a document for every user, and a document for every message. This allows us to easily access the data that we need, and to keep the
data organized. The database is also able to store arrays of strings, which allows us to store multiple messages in a single document. This is how we store things like blocked users. It's done by parsing an Arraylist
of strings into a string, and then storing that string in the document. When we need to access the blocked users, we parse the string back into an arraylist of strings, and then we can access the blocked users.
