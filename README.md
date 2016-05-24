# ApartmentManagement
It shows interaction between admin and tenants.
It has 2 different login one as Admin and another as user/tenant.

Admin Login: Can be created from the firebase Registered users section.

Mainactivity: It has email and password fields with 2 buttons one for admin login and another for tenants login.

On successfull login as admin by click on admin login button takes to AdminScreen, where it provides several options like:


Add/Edit/Delete tenant: Admin can create account for tenants by entering details like, flat number, name, email, password,rent.
                        They can also edit the details of existing profile by doing longpress on any row, 
                        which pop's-up dialog provides options to edit or delete tenant profile from the firebase.
                        Swipe left/right does hide of profile data from view later can restored by using UNDO.

Send Notification:      Admin can send notification to individual resident by entering there flatno.

Parking request:        Any parking request made by tenants can be viewed here. Each row displays details of flat number and message
                        Long press on any row, pop's up a dialog where admin has option to delete the details from the firebase.

Repair Request:         Can view repair request made by residents. Each Row displays details of flat number and problem area.
                        Long press on any row, pop's up a dialog where admin has option to delete the details from the firebase.

Add Event:              Admin can updates calendar with events.

Logout:                 used for logout, which takes to mainactivity.


Tenants zone:

Once admin creates user profile they send out to tenants, and they will be using that to login to this app by using same mainactivity 
login screen but clicking on Tenants login button

 on successfull login as tenant , it takes to UserScreen Provided with slider option from the menu bar. it displays listview with options:

Complaint:             Any problem related to apartment can be done here, additionally user provided with option to select the problem area.
                       Request sent to firebase with user flat number, message and problem area.

Request for parking:   Requests for parking place alot can be made.
                       Request sent with flat number and message.

Pay Rent:              Online payment can be done. Rent amount to be paid is fetched from the database.

Inbox:                 Any notification sent by admin is received here, which is particular to resident logged in.
                       Long press on any row, pop's up a dialog where tenant has option to delete the details from the firebase.

Event:                 By selecting particular date users are able to see events for that date which has been updated by admin.

Logout:                Takes back user to mainactivity.

Login screen:
Admin login button fetches login details from the firebase created auth details, Tenant login button fetch from the profile created by admin.
