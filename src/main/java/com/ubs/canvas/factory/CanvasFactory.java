package com.ubs.canvas.factory;

import com.ubs.canvas.core.Canvas;
import com.ubs.canvas.enums.ContextEnum;

public class CanvasFactory {
	
	public static Canvas getCanvas(ContextEnum contextType, int width, int height){
		switch(contextType){
		case CONTEXT2D:
			return new Canvas(ContextEnum.CONTEXT2D, width, height);
		default:
			// We can issue warnings or exceptions here but for simplicity,
			// returning null. 
			return null;
		}
	}

}
