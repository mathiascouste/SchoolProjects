package fr.unice.polytech.tools;

import org.apache.log4j.*;

public class DroneExpressLogger {

	private Logger log;
	private Class<?> clazz;

	public DroneExpressLogger(Class<?> clazz) {
		this.clazz = clazz;
	}

	private void initLogger() {
		log = Logger.getLogger(clazz);

		FileAppender appender = null;
		try {
			appender = new FileAppender();

			PatternLayout layout = new PatternLayout("%d{dd-MM-yyyy HH:mm:ss} [" + clazz.getName() + "] %-8p - %m%n");
			appender.setLayout(layout);
			appender.setFile(System.getProperty("user.home") + "/DroneExpress.log");
			appender.setAppend(true);
			appender.activateOptions();
			log.addAppender(appender);
			log.setLevel(Level.TRACE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Logger getLogger() {
		if (log == null) {
			initLogger();
		}
		return log;
	}

	public DroneExpressLogger logInfo(String message) {
		getLogger().info(message);
		return this;
	}

	public DroneExpressLogger logError(String message) {
		getLogger().error(message);
		return this;
	}
}
