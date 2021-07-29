# mad21-t02-assignment-teampk
Assignment Repository for MAD AY2021 - T02 of TeamPK. Android Application - StuffTrek  
https://play.google.com/store/apps/details?id=sg.edu.np.mad.teampk.stufftrek

Tutorial Group: P02  
Lecturer: Mr Wesley Teo WQ  
Team Name: Team PK  
Members Include:
* Choi Shu Yih, Jordan (S10208161D)
* Chua Dong En (S10202623A)
* Chang Li Zhong, Eddie (S10207896F)

## Application Logo
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/app-icons/playstore.png" width="20%">

## Application Description
Often misplace your stuff at home? Let us help! StuffTrek allows you to track where you’ve placed your stuff within your residence. Structured according to a typical home, record each of your items according to their storage location. 

Multiple locations such as different owned properties or offices can be created in the application. Once you’ve created your locations, you may begin recording items within!

If that is not specific enough, you may create rooms within the locations which can contain containers and/or items! We’ve integrated Imagga API which enables object recognition for the items, let us fill in the item for you automatically.

A more detailed list of features are as follows:
* **Account, Backup & Sync**  
As the data of your location and items are stored locally, you may lose these data when you change your phone. You may use our optional feature to create for an account and sync your data to the cloud. Upon successful backup, you may sync these data to any other Android devices! Images captured are presently not backed up, thus you may have to recapture them again! 

* **Search**  
Search for an item along with its details such as the location path, quantity and image easily with the search function. 

 * **Categorization**  
To enable better organization, we’ve included the categorization feature to allow you to categorize your items or containers! 

 * **All Items**  
Instead of going through one storage location at a time to search for your items. See them in a glance through the All Items page.

 * **Unassigned Items**  
Displays the list of items which are not assigned to any location! While that is not possible during creation of the item, items stored within a deleted location may be automatically transferred to this list.

 * **Object Recognition**  
Enabled through Imagga API, StuffTrek comes with object recognition feature which identifies the item through the image captured. Only available for items. Note that this is not 100% accurate and may not work sometimes due to the quota limitations. (We’re using a free plan. :()

 * **Night Mode**  
For users who prefers a dark theme, we have it ready for you!

 * **Creation, Display, Edit and Deletion of Category, Locations, Rooms, Container Categories and Items.**  
The core feature of our application allows you to perform basic creation, display, edit and delete of the objects mentioned above!


	Creation can be achieved through the menu in the ActionBar.

	Edit & Delete can be performed through a long tap (Location, Room, Container, Container Category & Item) or through swiping right (Delete) or left (Edit) for the item’s category.

 * **SQLite Local Database**  
All data and images are stored locally unless backed up to the cloud after successful authentication!

Due to time constraints in order to meet this assignment’s deadline, some features may not be implemented perfectly and may not work as intended or fail. We apologize in the event of such cases. Depending on the response of the application, we may optimize and improve it further, along with other cool and new features! 

You may look forward to the following;
* Bug Fixes
* Performance Optimization
* User Interface & Experience Improvements
* Improved Navigation
* Moving of Items/containers
* Multiple selection actions such as deleting item
* Sharing of item's location
* Barcode/QR Code Tagging
* Multi-User Access
* User Profile Management
* Smart Home Integration
* Direct Creation of Items
* Cloud Storage of Object Images
* Much More!

User Guide:
#### Registration & Login of Account
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/login-page.jpg" width="20%">
Upon loading the application, users will be directed to the login page will contains two buttons - "Login" & "Skip Login".

<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/email-input.jpg" width="20%">
Once the user clicked "Login" the user will be prompted by the system to input his/her email into the input textbox.

<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/password-input.jpg" width="20%">
As shown in this images, the email "eddiechang321@gmail.com" is recognised. This will lead the system to prompt the user to enter their password.

<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/home-page.jpg" width="20%">
Once logged in, user will be first directed to the home page. Same for "Skip Login" button in the login page, it will direct the user to the home page without logging in to an account.  

#### Creation of Item’s Categories
Manage Category            |  Creation of Category  |    List of Categories
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/category.jpg" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/create-category.jpg" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/category-list.jpg" width="74%">

In Manage Categories, users are able to create, edit, and delete categories. Users are able to create a category by clicking on the plus button on the top right corner of their device. This will activate the creation dialog and prompting the user to enter a category name. Categories are created to help the user sort their items neatly, User are able to find their items according to the category that he/she assign the item to. 

#### Edit & Delete of Item’s Categories
Edit Category            |  Delete Category
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/edit-category.jpg" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/delete-category.jpg" width="50%">

In Manage Categories, Editing and Deletion of items are using the feature to swipe-to-edit and swipe-to-delete. By swiping to the delete, it will show delete. Likewise swiping to the right, it will show edit.

#### Creation of Location
Manage Location            |  Creation of Location  |    List of Location
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/location.jpg" width="75%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/create-location.jpg" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/location-list.jpg" width="75%">

In Manage Locations, users are able to create, edit, and delete locations. Users are able to create location by clicking on the plus button on the top right corner of their device. This will activate the creation dialog and prompting the user to enter a location name. Locations are create to indicate their homes, warehouses, etc.

#### Edit & Delete of Location
Location Contextual Menu           |  Editing of Location  |    Deletion of Location
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/location-contextual.jpg" width="75%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/location-edit.jpg" width="75%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/location-delete.jpg" width="75%">

Users are able to long-press on the selected locations and activate the contextual menu. In the contextual menu, users are able to edit or delete their location. By entering a new name to the selected location, the location's name will then be updated to its new name. As for deletion, upon clicking on "Delete", there will a warning message indicating that all rooms, containers, and container locations will be deleted. Upon deletion, the items that are within the deleted location, will be unassigned from its location and will be automatically moved to "Unassigned Items".

#### Creation of Rooms
Menu in Location           |  Creation of Room  |    List of Rooms
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/inside-location-menu.jpg" width="65%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/create-room.jpg" width="65%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/room-list.jpg">

Inside location, users are able to create rooms and items within the location. User are able to create rooms and items by clicking on the "3 dots" button on the top right corner of their device and activate the menu. The list of rooms are horizontally scrollable which enhances the user experience dynamics and prevent the page from scrolling too far bottom vertically if there are many rooms and items in the location.

#### Capturing/Selection of Room Images
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/room-photo.jpg" width="20%">
Users are able to capture or select an image of their room to visually indicate the room as well.

#### Edit & Delete of Rooms
Room Contextual Menu           |  Editing of Room  |    Deletion of Room
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/room-contextual.jpg" width="75%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/room-edit.jpg" width="78%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/room-delete.jpg" width="75%">

Users are able to long-press on the selected rooms and activate the contextual menu. In the contextual menu, users are able to edit or delete their rooms. By entering a new name to the selected room, the room's name will then be updated to its new name. As for deletion, upon clicking on "Delete", there will be a warning message indicating that the user is about the delete a room which may container containers and items. Upon deletion, the items that are within the delete room, will be unassigned from its location and will be be automatically moved to "Unassigned Items".

#### Creation of Items
Menu in Room            |  Creation of Items  |    List of Items
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/inside-room-menu.jpg" width="65%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/create-item.jpg" width="65%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/item-list.jpg" width="65%">

Inside rooms, users are able to create container categories, containers, and items. Container categories are the catogeries of the containers, categories such as, wardobe, main cabinet, drawer, etc. Containers are containers within the categories, containers such as, drawer in wardrobe, drawer in cabinet, storage box in drawer, etc.

#### Capturing/Selection of Items Images + Object Recognition
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/item-list.jpg" width="20%">
Users are able to capture or select an image of their items to visually indicate the items as well.

#### Edit & Delete of Item
Item Contextual Menu           |  Editing of Item  |    Deletion of Item
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/item-contextual.jpg" width="63%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/item-edit.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/item-delete.jpg">

Users are able to long-press on the selected item and activate the contextual menu. In the contextual menu, users are able to edit or delete their items. By entering a new name to the selected item, the item's name will then be updated to its new name. As for deletion, upon clicking on "Delete", there will be a warning message indicating that the selected item will be deleted and this action is irreversible. 

#### Creation of Containers Categories
Menu in Room            |  Creation of Container Categories  |    List of Container Categories
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/inside-room-menu.jpg" width="70%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/create-container-category.jpg" width="65%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/container-category-list.jpg">

Same for items, users are to create a container category by activating the menu in rooms and create container categories.

#### Edit & Delete of Container Categories
Container Category Contextual Menu           |  Editing of Container Category  |    Deletion of Container Category
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/container-category-contextual.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/container-category-edit.jpg" width="65%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/container-category-delete.jpg">

Users are able to long-press on the selected container category and activate the contextual menu. In the contextual menu, users are able to edit or delete their container category. By entering a new name to the selected container category, the container category's name will then be updated to its new name. As for deletion, upon clicking on "Delete", there will be a warning message indicating that all items and containers within will be deleted.

#### Creation of Containers
Menu in Room            |  Creation of Container  |    List of Container
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/inside-room-menu.jpg" width="70%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/create-container.jpg" width="65%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/container-list.jpg">

Same for items and container categories, users are to create a container by activating the menu in rooms and create container.

#### Edit & Delete of Containers
Container Contextual Menu           |  Editing of Container Category  |    Deletion of Container Category
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/container-contextual.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/container-edit.jpg" width="65%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/container-delete.jpg" width="65%">

#### Searching of Items
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/search.jpg" width="20%">
Users are able to search their items in the search function. Upon conduction a search, the function will return the user the results container the item's name, category, quantitiy, and location.

#### View Unassigned Items in a Glance
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/items-unassigned-location.jpg" width="20%">
Users are able to view all their items in the "All Items" tab at the home page. Items that are unassign to any location is due to deletion of location, room, container category, and container.

#### View All Items in a Glance
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/all-items.jpg" width="20%">
Users are able to view all their items in the "All Items" tab at the home page. Items that are assigned or unassigned to any locations will be shown in "All Items".

#### Settings Menu
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/setting.jpg" width="20%">
In settings, users will be able to sign out, backup their data to the database, or load their previous saved.

#### Back Up to Cloud Storage
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/backup.jpg" width="20%">
Upon clicking on "Backup", the current data will be saved to the databased and returning the user a success message indicating "Firebase backup success".

#### Load Back Up from Cloud Storage
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/user-guide/backup-loaded.jpg" width="20%">
Upon clicking on "Load Backup", the previous saved data will be loaded to the user and return the user a success message indicating "Firebase load backup success".

## Design Rationale (UI & UX)
The goal of our application is to help our users to track their items in their household to prevent misplace or valuables going missing. StuffTrek is designed using a modern design layout, while retaining simplicity to prevent complications for our users. StuffTrek includes the following colour modes:
* Light Theme
   * White (#FFFFFF) & Turquoise (#7CCBCE)
* Night Theme
   * Black (#000000) & Gold (#FFC107)

## Instances of Core Topics Implemented:
* Camera2
* Intents & Intent Filters
* RecyclerView
* Night Theme
* Spinners
* App Bar
* Toast
* Dialogs
* Handling Bitmaps
* Permissions
* App data & files

## Testing
Application was test on the following devices:
* Pixel 4 XL (Android Studio Emulator)
* Nexux 5 (Android Studio Emulator)
* Samsung S20 Ultra
* Samsung S20+
* Samsung Galaxy Tab S7
* Samsung Galaxy Tab A7
* Sony Xperia 1 (J9110)

## Team Members Name & Student ID:
* Choi Shu Yih, Jordan  
  * S10208161D  
  * Leader, Application Developer, UI & UX Designer & Overall Project IC

* **Contributions**
  * **Features**
    * Full Implementation of ActionBar
    * Full Implementation of Options Menu
    * Full Implementation of Contextual Menu
    * Full Implementation of BottomSheetDialog
    * Full Implementation of Camera Functionality
    * Full Implementation of File IO for the storage of captured images.
    * Full Implementation of Object Recognition from Imagga API 
    * Full Implementation of JSON Serialization for API Responses. 
    * Creation, Reading, Editing and Deletion of Locations. (In Activity)
    * Editing & Deletion of Rooms (In Activity)
    * Creation, Reading of Container Category (In Activity)
    * Creation, Reading of Container (In Activity)
    * Creation of Items (In Activity)
    * Modification to implemented DBHandler for the purpose of resolving logical and other errors.
    * Implementation of Spinner within the CreateContainerActivity, CreateItemActivity

  * **Activities & Classess**
    * Full Implementation of MainActivity (Splash Screen)
    * Full Implementation of MenuActivity
    * Full Implementation of AllItemsActivity
    * Full Implementation of LocationActivity
    * Initial Implementation of RoomActivity
    * Full Implementation of CreateContainerActivity
    * Full Implementation of CreateItemActivity
    * Modification to LocationDetailsActivity for 
    * Full Implementation of LocationAdapter & ViewHolder
    * Initial Implementation of ContainersCategoryAdapter & ViewHolder
    * Initial Implementation of ContainersAdapter & ViewHolder
    * Full Implementation of ItemsAdapter & ViewHolder

  * **Layout & Other XML Resources**
    * Involved for all layouts either through implementation or modification for the styling of the User Interface.
    * Implementation of all three menus within the menu subfolder
    * Modifications to colors.xml, styles.xml and themes.xml for both Light/Night versions.

  * **Others**
    * Publish of Application to Google Playstore
    * Wireframe of Adobe XD Wireframe
    * UI & UX Design of the Application
    * Testing in overall.
    * Debugging and resolving warning & errors in overall.
    * Implementation of Night Mode

## Team Members Name & Student ID:
* Chua Dong En 
  * S10202623A  
  * Database Developer & Application Developer

* **Contributions**
  * **Features**
    * Full implementation of Firebase
        * Firebase Auth (Sign In)
        * Firebase Cloud Storage (Backup, Load Backup)
    * Partial Implementation of Contextual Menu & Handlers Within
        * Room (Create)
        * ContainerCategory (Update, Delete)
        * Container (Update, Delete)
        * Item (Update, Delete)    
    * Database (Design - Implementation - Backup)
    * Reading of LocationDetails

  * **Activities & Classess**
    * Full Implementation of Object Model Classes (Location, Room, Category, Container, ContainerCategory, Item)
    * Full Implementation of LocationDetailsActivity
    * Full Implementation of UnassignedItemsActivity
    * Full Implementation of FireBaseSignInActivity
    * Full Implementation of DBHandler
    * Full Implementation of SettingsActivity
    * Full Implementation of UpdateContainerActivity
    * Full Implementation of UpdateItemActivity
    * Full Implementation of Initial RoomAdapter & ViewHolder
    * Contextual menu
        * ContainersAdapter (Modified Initial)
        * ContainersCategoryAdapter (Modified Initial)
        * ItemAdapter (Modified Initial)
        * LocationDetailsActivity (OnResume Refresh)
        * RoomActivity (OnResume Refresh)
        * ItemsActivity (OnResume Refresh)
    * BottomSheetDialog
        * Create Room
        * Update Item
        * Update ContainerCategory
        * Update Category

  * **Layout & Other XML Resources**
  	* colors.xml, strings.xml
	* items	
	* Initial firebase_sign_in
	* location_details
	* Initial settings
	* Initial vh_room

  * **Others**
  	* Assisting publication of Application
	* Initial Wireframe
	* Initial Idea
	* Testing
	* Fixing
	* Refactoring of layout widget names

## Team Members Name & Student ID:
* Chang Li Zhong Eddie 
  * S10207896F 
  * Application Developer, UI & UX Designer, Documentarian

* **Contributions**
  * **Features**
    * Full implementation of Search Feature in SearchActivity
    * Full implementation of Category (Creation, Update, Deletion)
    * Conceptualization of App Icon

  * **Activities & Classess**
    * Full Implementation of CategoryActivity
    * Full Implementation of CategoryActivity (swipe-to-edit)
    * Full Implementation of CategoryActivity (swipe-to-delete)
    * Full Implementation of CreateCategory BottomSheetDialog
    * Full Implementation of Search Function   

  * **Layout & Other XML Resources**
    * colors.xml
    * string.xml
    * styles.xml
    * activity_search.xml
    * vh_category.xml

  * **Others**
    * Testing
    * README.md
    * User Guide

## Appendices
### StuffTrek Banner
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/banner.jpg">

### StuffTrek Wireframes
Splash-Screen    |  Home Page    |  Search
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/splash-screen.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/home-page.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/search.jpg">

Location     |  Create Location     |  Room     |  Room Menu
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/location.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/location-bottom-dialog.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/room-2.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/room-menu.jpg">

Category     |  Create Category      |   Item     |  Create Item 
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/category-activity.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/category-bottom-dialog.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/item-image.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/create-item.jpg">

Category Container     |  Create Container 
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/category-container.jpg">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/wireframe/create-container.jpg">


### StuffTrek Screenshot
#### Splash-Screen
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/dark-mode/splash-screen.png" width="20%">

#### Home page
Dark Mode - Home Page (mobile)     |  Light Mode - Home Page (tablet)
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/dark-mode/home-page.png" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/light-mode/home-page.png">

#### Location
Dark Mode - Location Page (mobile)     |  Light Mode - Location Page (tablet)
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/dark-mode/location.png" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/light-mode/location-activity.png">

#### Room
Dark Mode - Room Page (mobile)     |  Light Mode - Room Page (tablet)
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/dark-mode/room.png" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/light-mode/room.png">

#### Search
Dark Mode - Search Page (mobile)     |  Light Mode - Search Page (tablet)
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/dark-mode/search.png" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/light-mode/search.png">

#### Category
Dark Mode - Category Page (mobile)     |  Light Mode - Category Page (tablet)
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/dark-mode/category-activity.png" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/light-mode/category-activity.png">

#### All Items
Dark Mode - All Items Page (mobile)     |  Light Mode - All Items Page (tablet)
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/dark-mode/all-items.png" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/light-mode/all-items.png">

#### Settings
Dark Mode - Settings Page (mobile)     |  Light Mode - Settings Page (tablet)
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/dark-mode/setting.png" width="50%">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/screenshot/light-mode/setting.png">


### ER Diagram & Class Diagram
ER Diagram (incomplete)     |  Class Diagram (incomplete)
:-------------------------:|:-------------------------:
<img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/diagram/er-diagram.png">  |  <img src="https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/diagram/class-diagram.png">

#### Disclaimer:
It is not exhaustive, incomplete and not 100% accurate. It was developed in the initial stage of project and was not updated. It should only be used as a reference.

## Copyright
• Copyright © 2021 • Diploma in Information Technology • Mobile Application Development •  Team PK • Ngee Ann Polytechnic •
