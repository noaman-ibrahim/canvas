package com.ubs.canvas.core;

import com.ubs.canvas.exception.InvalidCommandException;

public interface Context{
	
	/**
	 * Draws current state of the context on the canvas.
	 */
	void draw();
	
	/**
	 * Clears the current state of the context.
	 */
	void clear();
	
	/**
	 * Executes the supplied command and updates the state of the context.
	 * 
	 * @param commandStr command string to be executed.
	 */
	void executeCommand(String commandStr) throws InvalidCommandException;
}
