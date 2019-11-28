# CIS4595-Ctrl-Alt-Elite

1. Project report (9)

A. Executive Summary (1)
	
	Lochat is aimed to be a local based social media chat service for Android mobile devices, which will allow its users to find and chat with other members of their nearby community. Users will be able to find chat rooms with others based on similar interests in their area.
	
	The project is aimed to connect users within a close area, and allow them discuss various topics such as, Food, Entertainment, and other interests which may be in their local area.


B. Final Requirements and comparison with initial requirements (2)
	The team will developing and designing a location based chat room service to connect users within a local scale. Initally the project was designed to have its users be entirely anonymous, however a user authentication system was added. The user's personal information will not be publicly displayed to other users in order to protect their privacy.
	
	

C. Final time line and comparison with the initial time line (1)
	
	-----------------------------------------
	-----------------------------------------
	Original Schedule:
	-----------------------------------------
	Dates	Planned Schedule
	-----------------------------------------
	9/16	Sprint 1 Planning Start
	9/23	Sprint 1 - Approx 4 weeks
	10/21	Sprint 1 - Review
	10/28	Sprint 2 - Approx 4 weeks
	11/25	Sprint 2 - Review
	12/02	Final Presentation and Demo
	-----------------------------------------
	-----------------------------------------
	
	
	-----------------------------------------
	Final Schedule
	-----------------------------------------
	Dates 	Planned Schedule
	-----------------------------------------
	9/16	Sprint 1 Planning Start
	9/23	Sprint 1 - Approx 4 weeks
	10/21	Sprint 1 - Review
	10/28	Sprint 2 - Approx 4 weeks 
			** Sprint 2 Extended 1 week **
	12/02	Sprint 2 - Review
	12/02	Final Presentation and Demo
	-----------------------------------------
	-----------------------------------------

D. Project results compared with expectations (2)
	
	Results:
	Sprint 1:
		Goals:
		Create Location Detection - Completed on time
		Establish Database/ Connections - Completed on time
		
	Sprint 2:
		Goals:
		Integration of components - Completed on time
		Messaging System - Partially completed during Sprint 1, Completed during Sprint 2
		User Interface Improvements - In Progress
		Implement Filters for Chat Topics - Completed
	
E. Project process review (2)
	
	The team was using Agile Development processes, the SCRUM process. This process completes its work within incremental builds to be delivered within a set amount of weeks. SCRUM typically involves different events during and after its main cycles known as Sprints. These events for the team included:
		
		Sprint Planning - Sprint planning invloves the planning sessions for the team's development cycles. The Sprint Planning has the goal of determining the goals of the next cycle, including developer goals, project requirements, tools to be used, potential ways of how the team will be creating the software. The sprint planning usually lasts 1 week.
		
		Sprint - The Sprint when the majority of the development occurs. This is when the design of the software and goals of the project from the previous Planning phsase, are created. A typical Sprint will last from around 2 weeks, to 1 month.
		
		Daily SCRUM - The daily SCRUM is a short daily meeting for the development team to discuss project ideas and goals, as well as plan objectives for the next day of development. A daily SCRUM are short in duration, and are a quick approximately 15 mintue session.
		
		Sprint Review - A sprint review is conducted at the end of each sprint cycle. During this phase the team will fully deploy and discuss the work which was completed during the previous sprint. 
		
		Sprint Retrospection - A sprint retrospection occurs after the Sprint Review is completed, and is a quick meeting around 1 hour to 3 hours. The purpose of this meeting is to discuss the goals and objectives from the previous sprint, and determine what went well for the Sprint, and what went wrong, also to discuss what could be improved in the future.
	
F. Work to be done (1)
		The project could see with minor improvements to various features within the software. this includes:
		UI Themes and Improvements.
		Separation of individual messages into separate objects for more visual customization.
		Chats currently do not support non text inputs.
 

2. Manuals (4)
A. User/Administrator manuals (2)
		
		For the user, the project will display 3 separate screens to interact with:
		
		1. Authentication Screen
			User will be prompted to log in to an existing account for Lochat. If the user does not have an account they may create one, or use their Google account linked to their Android Device. When creating a new account the user will need to provide an email account, which they will verify through an email message.
			
		2. User Information Screen
			The application will display a UI displaying various information about the user. For fulll features of the application, allowing using the device's current location is highly recommended. The user will click the Get Location Information button, then the application will detect the location values of the device, and generate location information. The user will then select a Username to appear as for the chatroom, and select a topic they are interested in.
			
		3. Chat Room
			The user will have all the previous information selected in the previous UI, submitted into the chat room chosen based on their location. The application will group the users together by Postal codes. The user will be able to send and receive text based chat messages from other users within the same chat. Currently non-text is not supported in the chat room.

B. Deployment/Installation instructions (2)
		
		For installation of Lochat application, either an Android device, or emulator will be needed. For an emulator, it is recommended to use one of many devices provided by Android Studio IDE. The project should be compatible on majority of devices, but will need a device with API level of at least 24. The team tested the project on Nexus 5x, however majority of devices should be compatible.
		

3. Code, scripts, ... (4)
	GitHub Repository
	https://github.com/RSheeder/CIS4595-Ctrl-Alt-Elite

A. Source code (3)
	Full Source code will be available in the GitHub linked above.  The master branch will have full source code.

B. DB and scripts (1)
	The team used Google's database system Firebase for all DB and data storage. 
	Link: https://console.firebase.google.com/u/1/project/cis4595-ctrl-alt-elite/overview