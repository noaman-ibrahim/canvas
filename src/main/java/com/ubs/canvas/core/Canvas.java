package com.ubs.canvas.core;

import com.ubs.canvas.enums.ContextEnum;
import com.ubs.canvas.exception.InvalidCommandException;

public class Canvas {
	
	private Context context;
	
	public Canvas(ContextEnum contextType, int width, int height){
		switch(contextType){
		case CONTEXT2D:
			this.context = new Context2D(width, height);
			break;
		default:
			this.context = new Context2D(width, height);				
		}
	}
	
	// Methods like switchContext(ContextEnum contextType) may be added in future.
	
	public void executeCommand(String commandStr) throws InvalidCommandException{		
		// Execute command in canvas's current context.
		// For simplicity, drawing is done as a part of command execution.
		// If we want to be able to execute multiple commands before drawing
		// then a call to context.draw() will be required here after a series 
		// of calls to context.executeCommand().
		context.executeCommand(commandStr);
	}
	
	/**
	 * Visible for testing
	 */
	public Context getContext(){
		return context;
	}

}
