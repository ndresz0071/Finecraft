package com.thebinaryfox.finecraft.bs;

import java.io.File;
import java.io.FileOutputStream;
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

		public CHandler(boolean close) {
			this.close = close;
		}

		private final SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm aa]");
		private PrintStream stream;
		private boolean close;

		@Override
		public void close() throws SecurityException {
			if (close)
				stream.close();
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
	private volatile boolean enabled = false;
	private volatile boolean sepe = false;
	private CHandler handlerc;
	private CHandler handlerf;
	private Logger log;

	// Methods: public
	/**
	 * Change the log's stream.
	 * 
	 * @param stream
	 *            the new stream.
	 */
	public void stream(PrintStream stream) {
		if (handlerc == null) {
			handlerc = new CHandler(false);
			handlerc.swap(stream);

			if (log == null) {
				log = Logger.getLogger("finecraft-bootstrap");
				log.setUseParentHandlers(false);
			}
			log.addHandler(handlerc);
		} else {
			handlerc.swap(stream);
		}
	}

	/**
	 * Change the log's file stream.
	 * 
	 * @param stream
	 *            the new stream.
	 */
	public void stream(File file) {
		if (file.getPath().startsWith("~/")) {
			file = new File(System.getProperty("user.home") + "/" + file.getPath().substring(2));
		}

		try {
			if (handlerf == null) {
				handlerf = new CHandler(true);
				handlerf.swap(new PrintStream(new FileOutputStream(file), true));

				if (log == null) {
					log = Logger.getLogger("finecraft-bootstrap");
					log.setUseParentHandlers(false);
				}

				log.addHandler(handlerf);
				log.info("LOG FILE:" + file);
			} else {
				handlerf.swap(new PrintStream(new FileOutputStream(file), true));
			}
		} catch (Exception ex) {
			if (enabled) {
				w("Cannot create file log: " + file);
			} else {
				if (log == null)
					stream(System.err);

				enable();
				w("Cannot create file log: " + file);
				disable();
			}
		}
	}

	/**
	 * Enable the logger.
	 */
	public void enable() {
		if (log == null)
			throw new IllegalStateException("Logger needs a source steam.");

		enabled = true;
		i("LOGGING ENABLED");
	}

	/**
	 * Disable the logger.
	 */
	public void disable() {
		if (log == null)
			throw new IllegalStateException("Logger needs a source steam.");

		i("LOGGING DISABLED");
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

		sepe = false;
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

		sepe = false;
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

		sepe = false;
		log.info(msg);
	}

	/**
	 * Separator.
	 */
	public void x() {
		if (!enabled)
			return;

		if (sepe)
			return;

		sepe = true;
		log.info("-------------------------------------");
	}

}
