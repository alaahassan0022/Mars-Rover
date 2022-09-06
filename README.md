Candidate name: Alaa Hassan Mahmoud Hassan

Email: alaahassan0022@gmail.com

CV: https://drive.google.com/file/d/1b0foP2pT989E1CZ1NFkuymt_g44ZnlwW/view

Vacancy: Backend Developer using Java Spring Boot

# Mars-Rover
Technology used: Java Spring Boot

[[User Manual is at the end.]]

Parts 1 and 2 are done together in the same service method 'executeCommand', Part 1 will be executed if the obstacles array was empty, Part 2 will be executed otherwise.

The project structure:

Main Classes:
- RoverPosition: has the coordinates x and y, direction of type enum Direction, and roverStatus of type enum RoverStatus.

- EarthCommand: has the command string itself, and the 2D array of obstacles.

Main Enums:
- Command enum: has the four commands: F,B,R and L
- Direction enum: has the four directions: NORTH, SOUTH, EAST and WEST.
- RoverStatus: has the two status of the rover: MOVING and STOPPED

Helper Classes:
- MapsLoader (mentioned next)
- DirectionConverter: makes sure that the direction that was sent in the API's endpoint is a valid one.

Helper Enums:
- CoordinateUpdate (mentioned next)

In order to reduce the amount of nested IF/CASEs and loops the following approaches were taken:

	-Class MapsLoader was created to store 2 maps:
		-Map 1: 
			-The 'updates' map which contains the actions that should be done with the different combinations of the Directions and the Commands.
			
			-For example: if the command was to move forward and the direction was north, the y-coordiate of the RoverPosition should increment and its x-coordinate should stay the same.
			
			-The key is a composite of the Direction and the Command
			-The value is an enum called CoordinateUpdate that contains the 4 possible updates of the coordinates: 
			INCREMENT_X,INCREMENT_Y,DECREMENT_X,DECREMENT_Y
			-Inside each CoordinateUpdate a method is called to update the RoverPosition accordingly.
			
		-Map 2:
			-The 'obstacles' map was created to save time when checking if the upcoming coordinates were of an obstacle instead of looping on the obstacles array, if there were any.
			-The key is a composite of the x-coordinate and the y-coordinate of the obstacle
			-The value is just an Integer of value '1', it's not used but a value was needed to be put.
			
		-Both maps are loaded at the beggining of the execution of any command string.

	- Direction enum:
		-Has 3 variables of type Direction too: opposite, rotatedLeft and rotatedRight, using a static block they were set to each direction so as to perform the rotation on any of them directly.
		-For example: NORTH.rotatedRight=EAST, NORTH.opposite=SOUTH  and so on.
		-also has a function called reachCommand, it is needed for Part 3, what it does is that it sees which command(s) will convert the current direction to the direction parameter.
		
		
	-Command enum:
		-Each Command of {F,B,R,L} performs a function inside that does the actual movement of the rover, whether it be a rotation or a change in coordinates.
		-The functions in F and B use the 'updates' map to call the right 'CoordinateUpdate' method according to the Direction and the Command itself.
		-The functions in R and L rotate the rover directly.


-MarsRoverController Class has one PostMapping method that has an endpoint of "localhost:8080/api/mars-rover/x/{x}/y/{y}/direction/{direction}"
	
	-Where {x},{y} and {direction} are @PathVariables to set the initial RoverPosition.
	
	-It also has a @RequestBody of type EarthCommand to execute.
	
	-It calls MarsRoverService.executeCommand(EarthCommand,RoverPosition)
	
	-RoverPosition has RoverStatus of MOVING by default.
	
	
-MarsRoverService contains the method 'executeCommand', that iterates over the command string character by character, performing each command using the Command enum and checks if the upcoming coordinates are of an obstacle each iteration, if they were, RoverStatus is set to STOPPED and iterations are broken.
	
	-It also has a helper method that does the checking of whether the command string has any invalid commands and throws a RuntimeException accordingly.
	
-Unit Tests were carried out to test the MarsRoverService method, and can be found at: 'src/test/java/com/example/mars/rover/MarsRoverServiceTest'

	
User Manual:

-Run the Java application by running this file: mars-rover/src/main/java/com/example/mars/rover/MarsRoverDemoApplication.java, keep it running in the background.

-Use Postman to send the Post Request.

-The request URL is the Controller's PostMapping method endpoint, it also needs a JSON Body of type EarthCommand.

-To execute Part 1: Leave the array of obstacles empty in the JSON => "obstacles" : []

-To execute Part 2 => for example: "obstacles" : [[5,5]]

-When the request is sent, a JSON object of type RoverPosition with its updated attributes values is returned.

	-Test Case1:
		-Choose POST method
		-Request URL: localhost:8080/api/mars-rover/x/4/y/2/direction/EAST
		-Body:
			{
			"commandString":"FLFFFRFLB",
			"obstacles":[]
			}
		-Returns:
			{
			"x": 6,
			"y": 4,
			"direction": "NORTH",
			"roverStatus": "MOVING"
			}

	-Test Case 2:
		-Choose POST method
		-Request URL: localhost:8080/api/mars-rover/x/4/y/2/direction/EAST
		-Body:
			{
			"commandString":"FLFFFRFLB",
			"obstacles":[[5,5]]
			}
		-Returns:
			{
				"x": 5,
				"y": 4,
				"direction": "NORTH",
				"roverStatus": "STOPPED"
			}

	-Test Case 3:
		-Choose POST method
		-Request URL: localhost:8080/api/mars-rover/x/4/y/2/direction/EAST
		-Body:
			{
			"commandString":"FLFFFRFLB",
			"obstacles":[[5,5],[5,3]]
			}
		-Returns:
			{
				"x": 5,
				"y": 2,
				"direction": "NORTH",
				"roverStatus": "STOPPED"
			}
			
			
-As for Part 3, an extra method is added in MarsRoverService called calculateCommandString, it uses Breadth First Search grid shortest path algortithm, stores the points in the path, and then the points in the path are translated into a command string, a unit test was carried out to test it, and also a controller method with a different endpoint was also added to call the service method.

-Extra Classes were added: Point Class that has an x and a y coordinate, it acts as a key or a value for multiple data structures used in the service, also CommandStringAndDirection class was added it has a command string and a direction enum, it's used when translating the path of points to a command string.


and as for running it, *you need to be in Debug mode, not Run*, and then use postman as follows:

	-Test Case 1:
		-Choose POST method
		-Request URL: localhost:8080/api/mars-rover/source/x/2/y/2/direction/NORTH/destination/x/7/y/8"

		-Body:
			{
			"commandString":"",
			"obstacles":[]
			}
		-Returns:
			{
			"commandString":"RFFFFFLFFFFFF",
			"obstacles":[]
			}
			
	-Test Case 2:
		-Choose POST method
		-Request URL: localhost:8080/api/mars-rover/source/x/2/y/2/direction/NORTH/destination/x/7/y/8"

		-Body:
			{
			"commandString":"",
			"obstacles":[[4,2]]
			}
		-Returns:
			{
			"commandString":"RFLFRFFFFLFFFFF",
			"obstacles": [
				[
				    4,
				    2
				]
		    	]
			}
