package com.thebinaryfox.finecraft.bs;

/**
 * The main class of the Finecraft bootstrap.
 * 
 * @author TheBinaryFox
 */
public class Bootstrap {

	// CONSTANTS
	static public final String _version = "0.0.1";
	static public final String _website = "https://github.com/TheBinaryFox/Finecraft";

	// Fields: static private
	static private boolean die = false;

	// Methods: instance public
	/**
	 * Handle bootstrap arguments.
	 * 
	 * @param args
	 *            the program arguments.
	 */
	public void arg(String[] args) {
		args = new String[]{"-version"};
		Arguments argo = new Arguments();
		try {
			argo.parse(args);
		} catch (Exception ex) {
			argerror("!" + ex.getMessage());
			argprint();
			System.exit(1);
		}

		// Arguments
		int ave = argo.getAndEmpty("version");
		switch (ave) {
		case 1:
			System.out.println("Finecraft Bootstrap V" + _version);
			System.out.println(_website);
			System.exit(0);
			break;

		case 2:
			argerror("!-version should not contain a value.");
			argprint();
			break;
		}
	}

	public void argerror(String why) {
		boolean dienow = why.startsWith("!");
		if (dienow)
			why = why.substring(1);

		System.out.println("Invalid arguments provided.");
		System.out.println(": " + why);

		if (dienow) {
			System.exit(1);
		} else {
			die = true;
		}
	}

	public void argprint() {

		if (die) {
			System.exit(1);
		}
	}

	public void run() {

	}

}
