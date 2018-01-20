package com.ubs.canvas.core;

import com.ubs.canvas.enums.CommandEnum;

public class ParsedCommand {

	private CommandEnum command;
	private int[] arguments;
	private Object[] extraArguments;

	public ParsedCommand() {
		super();
	}

	public ParsedCommand(CommandEnum command, int[] arguments) {
		this.command = command;
		this.arguments = arguments;
	}

	public CommandEnum getCommand() {
		return command;
	}

	public void setCommand(CommandEnum command) {
		this.command = command;
	}

	public int[] getArguments() {
		return arguments;
	}

	public void setArguments(int[] arguments) {
		this.arguments = arguments;
	}

	public Object[] getExtraArguments() {
		return extraArguments;
	}

	public void setExtraArguments(Object[] extraArguments) {
		this.extraArguments = extraArguments;
	}

}
