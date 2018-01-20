package com.ubs.canvas;

import java.util.Scanner;

import com.ubs.canvas.core.Canvas;
import com.ubs.canvas.core.CommandParser;
import com.ubs.canvas.core.ParsedCommand;
import com.ubs.canvas.enums.CommandEnum;
import com.ubs.canvas.enums.ContextEnum;
import com.ubs.canvas.exception.InvalidCommandException;
import com.ubs.canvas.factory.CanvasFactory;

/**
 * Class to launch the drawing console application.
 *
 */
public class Launcher implements CommandParser {

	private Canvas canvas = null;	

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.launch();
	}

	public void launch() {

		Scanner input = new Scanner(System.in);
		String commandStr;

		while (true) {

			System.out.print("Enter command: ");			
			commandStr = input.nextLine();					
			ParsedCommand parsedCommand;

			try {
				parsedCommand = parse(commandStr);

				if (parsedCommand.getCommand() == CommandEnum.QUIT) {
					break;
				} else if (parsedCommand.getCommand() == CommandEnum.CREATE_CANVAS) {
					canvas = CanvasFactory.getCanvas(ContextEnum.CONTEXT2D, parsedCommand.getArguments()[0],
														parsedCommand.getArguments()[1]);
				} else if (canvas == null) {
					System.out.println("Please create a canvas using \"C\" command before issuing any other command.");
					continue;
				} else {
					canvas.executeCommand(commandStr);
				}

			} catch (InvalidCommandException e) {
				System.out.println(e.getMessage());
			}
		}

		input.close();

	}

	@Override
	public ParsedCommand parse(String commandString) throws InvalidCommandException {

		if (commandString == null || commandString.trim().equals(""))
			throw new InvalidCommandException("No command entered");

		ParsedCommand result = new ParsedCommand();
		String[] commandArr = commandString.split(COMMAND_SEPARATOR);

		try {
			// We are only interested in parsing create and quit command here,
			// other commands are handled by the canvas and it's context because 
			// each context may have it's own set of supported commands and 
			// it's own way of parsing a command.
			switch (CommandEnum.getCommand(commandArr[0])) {
			case CREATE_CANVAS:
				result.setCommand(CommandEnum.CREATE_CANVAS);
				result.setArguments(CommandParser.parseIntegerArguments(2, commandString));
				break;
			case QUIT:
				result.setCommand(CommandEnum.QUIT);
				result.setArguments(CommandParser.parseIntegerArguments(0, commandString));
				break;
			default:
				result.setCommand(CommandEnum.OTHER);
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidCommandException(
					e.getMessage() + " Please enter a valid command code (e.g. C, L, R, B, Q).");
		}

		return result;
	}
	
	/**
	 * Visible for testing
	 */
	public Canvas getCanvas(){
		return canvas;
	}

}
