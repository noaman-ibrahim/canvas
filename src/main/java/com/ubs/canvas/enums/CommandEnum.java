package com.ubs.canvas.enums;

public enum CommandEnum {

	CREATE_CANVAS("C"),
	LINE("L"),
	RECTANGLE("R"),
	BUCKET_FILL("B"),
	QUIT("Q"),
	OTHER("other");
	
	private String code;
	
	private CommandEnum(String code){
		this.code = code;
	}
	
	public String getCode(){
		return code;
	}
	
	public static CommandEnum getCommand(String str) throws IllegalArgumentException{
		for (CommandEnum cmd: CommandEnum.values()){
			if (cmd.code.equals(str)){
				return cmd;
			}
		}
		
		throw new IllegalArgumentException("Invalid command code " + str + ".");
	}
}
