package fr.unice.polytech.tools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.tools.DroneExpressLogger;
import fr.unice.polytech.utils.LogReader;

public class DroneExpressLoggerTest {

	private DroneExpressLogger classLogger;
	private DroneExpressLogger interfaceLogger;
	private DroneExpressLogger enumLogger;

	LogReader reader = new LogReader();

	@Before
	public void setUp() throws Exception {
		classLogger = new DroneExpressLogger(String.class);
		interfaceLogger = new DroneExpressLogger(List.class);
		enumLogger = new DroneExpressLogger(ColorSpaceType.class);
	}

	@After
	public void tearDown() throws Exception {
		classLogger = null;
		interfaceLogger = null;
		enumLogger = null;

		reader.clean();
	}

	@Test
	public void testDroneExpressLogger() {
		assertNotNull(classLogger);
		assertNotNull(interfaceLogger);
		assertNotNull(enumLogger);
	}

	@Test
	public void testLogInfo() throws IOException {
		String content;
		classLogger.logInfo("was logged from String logger");
		content = reader.read();
		assertTrue(content.contains("was logged from String logger"));
		assertTrue(content.contains("INFO"));
		assertTrue(content.contains("java.lang.String"));

		interfaceLogger.logInfo("was logged from List logger");
		content = reader.read();
		assertTrue(content.contains("was logged from String logger"));
		assertTrue(content.contains("INFO"));
		assertTrue(content.contains("java.lang.String"));
		assertTrue(content.contains("was logged from List logger"));
		assertTrue(content.contains("INFO"));
		assertTrue(content.contains("java.util.List"));

		enumLogger.logInfo("was logged from Enum logger");
		content = reader.read();
		assertTrue(content.contains("was logged from String logger"));
		assertTrue(content.contains("INFO"));
		assertTrue(content.contains("java.lang.String"));
		assertTrue(content.contains("was logged from List logger"));
		assertTrue(content.contains("INFO"));
		assertTrue(content.contains("java.util.List"));
		assertTrue(content.contains("was logged from Enum logger"));
		assertTrue(content.contains("INFO"));
		assertTrue(content.contains("java.awt.MultipleGradientPaint$ColorSpaceType"));
	}

	@Test
	public void testLogError() throws IOException {
		String content;
		classLogger.logError("error").logError("bug");
		content = reader.read();
		assertTrue(content.contains("error"));
		assertTrue(content.contains("bug"));
		assertTrue(content.contains("ERROR"));
		assertTrue(content.contains("java.lang.String"));
	}

}
