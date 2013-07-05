package com.thebinaryfox.finecraft.bs;

/**
 * The program entry class of the Finecraft bootstrap.
 * 
 * @author TheBinaryFox
 */
public final class Main {

	// Fields: static private
	static private Runnable exec;

	// Methods: static public
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            program arguments.
	 */
	static public void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.arg(args);
		bootstrap.run();
		bootstrap = null;

		while (exec != null) {
			Runnable mexc = exec;

			exec = null;
			mexc.run();
		}
	}

	/**
	 * Set a new runnable to be executed.
	 * 
	 * @param run
	 *            the runnable.
	 */
	static public void exec(Runnable run) {
		exec = run;
	}

}
