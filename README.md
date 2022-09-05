Candidate name: Alaa Hassan Mahmoud Hassan, Email: alaahassan0022@gmail.com, Vacancy: Backend Developer with Spring Boot

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
		-Has 2 variables of type Direction too: rotatedLeft and rotatedRight, using a static block they were set to each direction so as to perform the rotation on any of them directly.
		-For example: NORTH.rotatedRight=EAST and so on.
		
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

-Postman is used to send the Post Request.

-The request URL is the Controller's PostMapping method endpoint, it also needs a JSON Body of type EarthCommand.

-To execute Part 1: Leave the array of obstacles empty in the JSON => "obstacles" : []

-Otherwise fill it => "obstacles" : [[5,5]]

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
