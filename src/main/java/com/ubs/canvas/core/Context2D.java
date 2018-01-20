package com.ubs.canvas.core;

import com.ubs.canvas.enums.CommandEnum;
import com.ubs.canvas.exception.InvalidCommandException;

public class Context2D implements Context {	
	
	private char[][] currentState;
	private int width;
	private int height;
	
	public Context2D(int width, int height){
				
		this.width = width;
		// Add 2 to supplied height in order to accommodate
		// top, and bottom boundaries of the canvas (as shown in Readme.md file
		// supplied height is fully available for drawing but left and right corners
		// of supplied width are occupied by canvas boundaries).
		this.height = height + 2;
		
		// Initialize the state of the context.
		currentState = new char[this.width][this.height];			
		
		// Clear the current state to set up initial state.
		clear();
		
		// Draw the initial state
		draw();
		
	}		
		
	@Override
	public void draw() {
		
		for (int i = 0; i < height; i++){			
			for (int j = 0; j < width; j++){												
				System.out.print(currentState[j][i]);				
			}
			// Go to new line after printing each row.
			System.out.println();
		}
		
		// Add an additional empty line for clarity in the UI (as shown in Readme.md)
		System.out.println();
	}
	

	@Override
	public void clear() {
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){				
				
				if (j == 0 || j == height - 1){
					// Set horizontal boundary lines.
					currentState[i][j] = '-';
				}else if (i == 0 || i == width - 1){
					// Set vertical boundary lines.
					currentState[i][j] = '|';
				}else{
					// Fill area inside the boundaries with empty character (space).
					currentState[i][j] = ' ';
				}
				
			}
		}		
	}
	

	@Override
	public void executeCommand(String commandString) throws InvalidCommandException {

		if (commandString == null || commandString.trim().equals(""))
			throw new InvalidCommandException("No command entered");
		
		ParsedCommand result = new ParsedCommand();
		String[] commandArr = commandString.split(CommandParser.COMMAND_SEPARATOR);
		
		try {
			switch (CommandEnum.getCommand(commandArr[0])) {			
			case LINE:
				result.setCommand(CommandEnum.LINE);
				result.setArguments(CommandParser.parseIntegerArguments(4, commandString));
				drawLine(result);
				break;
			case RECTANGLE:
				result.setCommand(CommandEnum.RECTANGLE);
				result.setArguments(CommandParser.parseIntegerArguments(4, commandString));
				drawRectangle(result);
				break;
			case BUCKET_FILL:
				result.setCommand(CommandEnum.BUCKET_FILL);
				
				if (commandArr.length != 4){
					throw new InvalidCommandException("Invalid command. Exactly 3 arguments must be supplied " +
														"for " + CommandEnum.BUCKET_FILL.getCode() + " command.");
				}
				
				int[] args = new int[2];
				try{
					args[0] = Integer.parseInt(commandArr[1]);
					args[1] = Integer.parseInt(commandArr[2]);
				} catch (NumberFormatException e){
					throw new InvalidCommandException("Invalid command. 2 integer arguments expected for " +
														CommandEnum.BUCKET_FILL.getCode() + " command.");
				}	
				
				result.setArguments(args);
				result.setExtraArguments(new Object[] {commandArr[3].charAt(0)});
				
				bucketFill(result);
				break;			
			default:
				throw new InvalidCommandException("Command " + commandArr[0] + " is not supported by 2D Context");
			}
			
			// Draw the updated state upon successful execution of the command.
			// This call to draw() can be removed from here if we want to be able 
			// to execute multiple commands before drawing. In that case call 
			// to draw() will be required explicitly after executing a command 
			// in the context.
			draw();
			
		} catch (IllegalArgumentException e){
			throw new InvalidCommandException(e.getMessage());
		}
	}
	
	/**
	 * Visible for testing
	 */
	public char[][] getCurrentState(){
		return currentState;
	}
	
	/**
	 * Visible for testing
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Visible for testing
	 */
	public int getHeight(){
		return height;
	}
	
	//=============================================================================================================
	// Private Methods
	
	/**
	 * Implementation of Line command in 2D Context.
	 * A call to this method is expected after parsing so we can assume required
	 * arguments will be available for use.
	 * 
	 * @param command parsed line command
	 * @throws InvalidCommandException 
	 */
	private void drawLine(ParsedCommand command) throws InvalidCommandException{
		int[] arguments = command.getArguments();
		
		int x1 = arguments[0];
		int y1 = arguments[1];
		int x2 = arguments[2];
		int y2 = arguments[3];			
		
		// Check if the supplied arguments are outside the boundaries of the canvas.
		checkCoordinatesValidity(x1, y1);
		checkCoordinatesValidity(x2, y2);
				
		if (x1 == x2){ // Request for vertical line
			if (y2 > y1){
				for (int i = y1; i <= y2; i++){
					currentState[x1][i] = 'x';
				}
			}else{
				for (int i = y2; i <= y1; i++){
					currentState[x1][i] = 'x';
				}
			}
		}else if (y1 == y2){ // Request for horizontal line
			if (x2 > x1){
				for (int i = x1; i <= x2; i++){
					currentState[i][y1] = 'x';
				}
			}else{
				for (int i = x2; i <= x1; i++){
					currentState[i][y1] = 'x';
				}
			}
		}else{ // Other lines
			throw new InvalidCommandException("Only horizontal and vertical lines are supported at the moment.");
		}
	}
	
	/**
	 * Implementation of Rectangle command in 2D Context.
	 * A call to this method is expected after parsing so we can assume required
	 * arguments will be available for use.
	 * 
	 * @param command parsed Rectangle command
	 */
	private void drawRectangle(ParsedCommand command) throws InvalidCommandException{
		int[] arguments = command.getArguments();
		
		int x1 = arguments[0];
		int y1 = arguments[1];
		int x2 = arguments[2];
		int y2 = arguments[3];
		
		// Check if the supplied arguments are outside the boundaries of the canvas.
		// Doing this before drawing rectangle lines so that none of the lines is 
		// drawn if either of the coordinates is outside the canvas boundaries.
		checkCoordinatesValidity(x1, y1);
		checkCoordinatesValidity(x2, y2);
				
		command.setArguments(new int[] {x1, y1, x2, y1});
		drawLine(command);
		
		command.setArguments(new int[] {x2, y1, x2, y2});
		drawLine(command);
		
		command.setArguments(new int[] {x2, y2, x1, y2});
		drawLine(command);
		
		command.setArguments(new int[] {x1, y2, x1, y1});
		drawLine(command);
	}
	
	/**
	 * Implementation of Bucket fill command in 2D Context.
	 * A call to this method is expected after parsing so we can assume required
	 * arguments will be available for use.
	 * 
	 * @param command parsed Bucket fill command
	 */
	private void bucketFill(ParsedCommand command) throws InvalidCommandException{
		int[] arguments = command.getArguments();
		Object[] extraArguments = command.getExtraArguments();
		char colour = (char)extraArguments[0];
		
		int x = arguments[0];
		int y = arguments[1];
		
		// Check if the supplied arguments are outside the boundaries of the canvas.
		checkCoordinatesValidity(x, y);
		
		// Get current character at the specified position.
		char charAtPosition = currentState[x][y];
		fillColour(x, y, colour, charAtPosition);
	}
	
	/**
	 * Checks if the supplied coordinates are inside the canvas boundaries.
	 * 
	 * @param x x coordinate of an input
	 * @param y y coordinate of an input
	 * @throws InvalidCommandException if the supplied coordinates are outside the canvas boundaries. 
	 */
	private void checkCoordinatesValidity(int x, int y) throws InvalidCommandException{		
		if (x < 1 || x > width - 2 || y < 1 || y > height - 2){
			throw new InvalidCommandException("Supplied coordinates are outside the boundaries of the canvas");
		}
	}
	
	/**
	 * Recursively fills supplied colour at position (x, y) and it's immediate top, bottom, right, and left
	 * if position (x, y) is inside the canvas boundaries and current character at (x, y) matches the supplied
	 * character to match. 
	 * 
	 * @param x x coordinate of the position to be filled
	 * @param y y coordinate of the position to be filled
	 * @param colour colour to fill the position with
	 * @param match character to match at position (x, y) before filling the colour
	 */
	private void fillColour(int x, int y, char colour, char match){
		
		// Return if the position to be filled is outside the canvas boundaries or
		// the character at the position is not same as "match" character.
		if (x < 1 || x > width - 2 || y < 1 || y > height - 2 || currentState[x][y] != match){
			return;
		}
		
		// Fill colour
		currentState[x][y] = colour;
		
		fillColour(x + 1, y, colour, match); // Fill right
		fillColour(x - 1, y, colour, match); // Fill left
		fillColour(x, y - 1, colour, match); // Fill top
		fillColour(x, y + 1, colour, match); // Fill bottom
	}
	
}
