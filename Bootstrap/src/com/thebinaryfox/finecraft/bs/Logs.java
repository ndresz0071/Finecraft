package com.thebinaryfox.finecraft.bs;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A logging class for the Finecraft bootstrap.
 * 
 * @author TheBinaryFox
 */
public final class Logs {

	// Classes: private
	static private final class CHandler extends Handler {

		private final SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm aa]");
		private PrintStream stream;

		@Override
		public void close() throws SecurityException {
			stream = null;
		}

		@Override
		public void flush() {
			stream.flush();
		}

		@Override
		public void publish(LogRecord record) {
			StringBuilder sb = new StringBuilder();
			sb.append(sdf.format(record.getMillis()));
			sb.append("[");
			sb.append(record.getLevel().getName().charAt(0));
			sb.append("] ");
			sb.append(record.getMessage());
			stream.println(sb.toString());
		}

		public void swap(PrintStream stream) {
			this.stream = stream;
		}

	}

	// Fields: private
	private volatile boolean enabled;
	private CHandler handler;
	private Logger log;

	// Methods: public
	/**
	 * Change the log's stream. This must be called before enable.
	 * 
	 * @param stream
	 *            the new stream.
	 */
	public void stream(PrintStream stream) {
		if (log == null) {
			handler = new CHandler();
			handler.swap(stream);
			log = Logger.getLogger("finecraft-bootstrap");
			log.setUseParentHandlers(false);
			log.addHandler(handler);
		} else {
			handler.swap(stream);
		}
	}

	/**
	 * Enable the logger.
	 */
	public void enable() {
		if (log == null)
			throw new IllegalStateException("Logger needs a source steam.");

		enabled = true;
	}

	/**
	 * Disable the logger.
	 */
	public void disable() {
		if (log == null)
			throw new IllegalStateException("Logger needs a source steam.");

		enabled = false;
	}

	/**
	 * Warning message.
	 * 
	 * @param msg
	 *            the message.
	 */
	public void w(String msg) {
		if (!enabled)
			return;

		log.warning(msg);
	}

	/**
	 * Severe message.
	 * 
	 * @param msg
	 *            the message.
	 */
	public void s(String msg) {
		if (!enabled)
			return;

		log.severe(msg);
	}

	/**
	 * Info message.
	 * 
	 * @param msg
	 *            the message.
	 */
	public void i(String msg) {
		if (!enabled)
			return;

		log.info(msg);
	}

}
