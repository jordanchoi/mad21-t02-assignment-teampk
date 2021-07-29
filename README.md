# mad21-t02-assignment-teampk
Assignment Repository for MAD AY2021 - T02 of TeamPK. Android Application - StuffTrek  
https://play.google.com/store/apps/details?id=sg.edu.np.mad.teampk.stufftrek

It is jointly developed and designed by Jordan Choi, Dong En, and Eddie Chang.

Tutorial Group: P02  
Lecturer: Mr Wesley Teo WQ  
Team Name: Team PK

## Application Logo
![StuffTrek](https://github.com/jordanchoi/mad21-t02-assignment-teampk/blob/master/app-icons/playstore.png?raw=true)

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
* Registration & Login of Account
* Creation of Item’s Categories
* Edit & Delete of Item’s Categories
* Creation of Location
* Edit & Delete of Location
* Creation of Rooms
* Capturing/Selection of Room Images
* Edit & Delete of Rooms
* Creation of Items
* Capturing/Selection of Items Images + Object Recognition 
* Edit & Delete of Items
* Creation of Containers Categories
* Edit & Delete of Container Categories
* Creation of Containers
* Edit & Delete of Containers
* Searching of Items
* View All Items in a Glance
* View Unassigned Items in a Glance
* Back Up to Cloud Storage
* Sync from Cloud Storage

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
Application was test on the following mobile phones:
* Pixel 4 XL (Android Studio Emulator)
* Nexux 5 (Android Studio Emulator)
* Samsung S20 Ultra
* Samsung S20+
* Samsung Galaxy Tab S7
* Samsung Galaxy Tab A7

## Team Members Name & Student ID:
* Choi Shu Yih, Jordan  
  * S10208161D  
  * Leader, Application Developer, UI & UX Designer & Overall Project IC

* **Contributions**
  * **Features**
    *  Full Implementation of ActionBar
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
    *  Involved for all layouts either through implementation or modification for the styling of the User Interface.
    *  Implementation of all three menus within the menu subfolder
    *  Modifications to colors.xml, styles.xml and themes.xml for both Light/Night versions.

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
  * Application Developer, UI & UX Designer

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

## Appendices
* Attach Banners
* Attach Wireframes
* Attach Screenshots
* Attach Class Diagram & ER Diagram
