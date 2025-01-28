# Mobile-Architecture
 

- Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

  The app that I chose to develop was a warehouse inventory tracking app. The application is used to track items in a warehouse and needed to include the following:

  - A database with at least two tables, one to store the inventory items and one to store user logins and passwords.

  - A screen for logging into the app. Note that this should also be used to create a login if the user has never logged in before.
  -  A screen, with a grid, that displays all items in the inventory
  - A mechanism by which the user can add and remove items from inventory
  - A mechanism by which the user can increase or decrease the number of a specific item in the inventory
  - A mechanism by which the application will notify the user when the amount of any item in the inventory has been reduced to 0 (zero)
 
    Users needed to be able to register as a new user if they had not previously used the application. Users then needed to be able to log into the application which then would display a list of all items in the warehouse inventory. Users needed to be able to add, edit, and delete inventory items, and also be notified, if they choose to be, by an SMS message when an item in inventory has been reduced to zero. All together, this application enables its users to effectively manage the warehouse inventory.

- What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?

  The screens that were necessary to support user needs were first the login screen where users can log in to the application. A register screen where new users can register. A main screen that displays all the items in inventory and allows them to add and edit inventory items. This screen also provides a method for users to enable and disable SMS notifications, delete the entire inventory database, and it also displays to the user the total number of items in the warehouse inventory. There is a screen for editing inventory items and on this screen, the user can choose to delete the item from the inventory.

  The UI design keeps users in mind by making it simple to use and navigate. The flow of the application is intuitive and does not require any prior experience to use the app. Buttons are appropriately sized and symbols are easily identifiable and meaningful. Appropriate colors were selected that ensure that the text is easy to read. Combined, these design choices make for a successful design.

- How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?

  The way that the class projects were designed to be completed as a progression toward a completed application was the key to my success. The second project started with us only having to design the UI for the various screens that our application would need. Once I knew what screens I would need, I then knew how to begin working on the code. As I coded, I created the code in the order that the application flowed. This allowed me to test along the way to ensure my code functioned correctly. For example, the first thing I coded was the login activity. I knew I needed a login repository, a user, and a login activity. I then coded the register activity, and from there I was able to test the login and register functionality of the application. I simply followed the same process for the remaining activities. In the future, I will likely follow similar steps.

- How did you test to ensure your code was functional? Why is this process important and what did it reveal?

  As previously stated, once I completed the coding of an activity or feature, I would run the application on the emulated device over and over, testing that each portion of the code functioned appropriately. When it did, I would go back to that portion of the code, make changes then re-test until the application functioned as intended.

- Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?

Mostly in the production of the code itself. At first, I thought I was going to have a settings screen for the SMS notifications, but quickly realized during development that I could simply use an alert dialog to prompt the user for SMS permission without having to take the user to a new activity/screen. I then had to change my initial planning for having a way to delete the database from the settings screen to the main screen, which when pressed, the delete button is also an alert dialog that appears and prompts the user if they want to delete all the items in inventory. 

- In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

  I think the portion of the project that I was particularly successful with was the coding of the MainActivity. This showcased my ability to navigate users between different screens, incorporate the use of an SQLite database to manage inventory items, incorporates the use of alert dialogs, and displays inventory items to the user in an organized and effective, gridded style layout.