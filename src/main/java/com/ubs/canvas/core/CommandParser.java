package com.ubs.canvas.core;

import com.ubs.canvas.exception.InvalidCommandException;

public interface CommandParser {	
	
	public static final String COMMAND_SEPARATOR = " ";
	
	/**
	 * Parses user input.
	 *
	 * @param commandString user input string.
	 * @return ParsedCommand object that contains command type and corresponding arguments.
	 */
	ParsedCommand parse(String commandString) throws InvalidCommandException;
	
	/**
	 * Parses integer arguments for a command.
	 * This method should only be used for commands that only have integer arguments and no other
	 * arguments.
	 *  
	 * @param limit expected number of integer arguments.
	 * @param commandStr command string to be parsed. 
	 * @return array of parsed integer arguments
	 * @throws InvalidCommandException if supplied arguments can't be parsed as integers or if 
	 * number of arguments in the supplied command string exceeds the expected number of arguments.
	 */
	public static int[] parseIntegerArguments(int limit, String commandStr) throws InvalidCommandException{		
		
		if (commandStr == null || commandStr.trim().equals(""))
			throw new InvalidCommandException("No command entered");
		
		String[] commandArr = commandStr.split(COMMAND_SEPARATOR);
		
		if (commandArr.length != (limit + 1)){
			throw new InvalidCommandException("Invalid command. Exactly "+ limit + " arguments must be supplied " +
												"for " + commandArr[0] + " command.");
		} else {
			int[] args = new int[limit];
			
			try{
				for (int i = 0; i < limit; i++){
					args[i] = Integer.parseInt(commandArr[i + 1]);
				}								
			} catch (NumberFormatException e){
				throw new InvalidCommandException("Invalid command. " + limit + " integer arguments expected for " +
													commandArr[0] + " command.");
			}					
			
			return args;
		}
		
	}

}
