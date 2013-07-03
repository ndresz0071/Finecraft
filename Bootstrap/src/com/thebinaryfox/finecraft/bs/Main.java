package com.thebinaryfox.finecraft.bs;

/**
 * The main class of the Finecraft bootstrap.
 * 
 * @author TheBinaryFox
 */
public class Main {

	// Static private
	static private Runnable exec;
	
	// Static public
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            program arguments.
	 */
	static public void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
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
