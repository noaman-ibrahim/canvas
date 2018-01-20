package com.ubs.canvas;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.ubs.canvas.core.Context2D;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Launcher.
 */
public class CanvasTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public CanvasTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(CanvasTest.class);
	}

	// =============================================================================================================
	// Tests for 2D Context

	/**
	 * Test create command
	 */
	public void testCreation() {
		InputStream stdin = System.in;
		StringBuilder command = new StringBuilder();
		command.append("C 20 4"); // Create canvas
		command.append("\n"); // new line
		command.append("Q"); // Quit

		try {
			System.setIn(new ByteArrayInputStream(command.toString().getBytes()));

			Launcher launcher = new Launcher();

			assertNull(launcher.getCanvas());

			launcher.launch();

			assertNotNull(launcher.getCanvas());

			Context2D context2D = (Context2D) launcher.getCanvas().getContext();
			char[][] state = context2D.getCurrentState();
			int width = context2D.getWidth();
			int height = context2D.getHeight();

			// Verify that an empty canvas has been created with width 20 and
			// height 4
			assertEquals(20, width);
			assertEquals(6, height); // 4 + 2 due to top and bottom boundaries

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {

					if (j == 0 || j == height - 1) {
						// Check top and bottom boundaries
						assertEquals('-', state[i][j]);
					} else if (i == 0 || i == width - 1) {
						// Check left and right boundaries
						assertEquals('|', state[i][j]);
					} else {
						// Check emptiness of canvas
						assertEquals(' ', state[i][j]);
					}

				}
			}					

		} finally {
			System.setIn(stdin);
		}
	}

// Other tests

}
