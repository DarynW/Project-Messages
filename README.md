# Project-4
Group CS180 Project


1. Instructions on how to comipel and run our project



2. Who submitted what

      a. Daryn Wang: submitted report on Brightspace
      
      b. Fischer Osterle: submitted the entire project on Brightspace
      
3. Description of classes

      a. Database</p>
      
            1. Handles the offline storage of data for the entire program. All other classes call this class for data.
            
      b. RealStart
      
            1. Starts the Program. Calls the Start class

      c. Start
      
            1. getIntInput
            
                  a. gets an integer input for the main method
                  
            2. getStringInput
            
                  a. gets a string input for the main method
                  
            3. MostCommonWords
            
                  a. returns an arraylist of the most common words in the messages 
                  
            4. displayMessageInteraction
            
                  a. used by all users to show the current messages with the person the user is currently messaging
                  
            5. messages
            
                  a. Used by the seller to display the list of messages they have with other users
                  
            6. main
            
                  a. Instiates the database
                  
                  b. asks for login
                  
                  c. two paths for the seller and buyer
                  
                        1. seller has more actions
                        
                              a. view their stores, create stores, view messages, delete account, search user messages by email
                              
                              b. calls mostCommonWords
                              
                              c. calls displayMessageInteraction
                              
                        2. buyer path

                              a. search sellers by email, view stores, delete their account
                              
                              b. calls displayMessageInteraction
                              
