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

	// Fields: instance private
	private boolean die = false;
	private Logs log;
	
	// Constructors: public
	public Bootstrap() {
		log = new Logs();
	}
	

	// Methods: instance public
	/**
	 * Handle bootstrap arguments.
	 * 
	 * @param args
	 *            the program arguments.
	 */
	public void arg(String[] args) {
		args = new String[]{"-log"};
		
		Arguments argo = new Arguments();
		try {
			argo.parse(args);
		} catch (Exception ex) {
			argerror("!" + ex.getMessage());
			System.exit(1);
		}
		
		// Argument: version
		int ave = argo.getCheckEmptyAndRemove("version");
		switch (ave) {
		case 1:
			System.out.println("Finecraft Bootstrap V" + _version);
			System.out.println(_website);
			System.exit(0);
			break;

		case 2:
			argerror("!-version should not be a flag.");
			break;
		}

		// Argument: help
		ave = argo.getCheckEmptyAndRemove("help");
		switch (ave) {
		case 1:
			System.out.println("NAME");
			System.out.println("       fcbootstrap.jar - the bootstrap for the Finecraft launcher.");
			System.out.println();
			System.out.println("SYNTAX");
			argprint(1, "       ");
			System.out.println();
			System.out.println("OPTIONS");
			argprint(2, "       ");
			System.exit(0);
			break;

		case 2:
			argerror("!-help should not be a flag.");
			break;
		}
		
		ave = argo.getCheckEmptyAndRemove("log");
		switch (ave) {
		case 1:
			log.stream(System.out);
			log.enable();
			log.i("Bootstrap logging enabled.");
			log.i("--------------------------");
			break;

		case 2:
			argerror("!-log should not be a flag.");
			break;
		}
		
		// Argument: configuration entries
		Configuration[] config = Configuration.values();
		String val;
		String key;
		for (int i = 0; i < config.length; i++) {
			key = config[i].key();
			if (key.startsWith("bootstrapper.")) {
				key = key.substring("bootstrapper.".length());
			}
			
			val = argo.getAndRemove("-" + key);
			if (val != null) {
				if (val.isEmpty()) {
					argerror("!Configuration arguments are not flags.");
				}
				
				config[i].setTemporarily(val);
			}
		}

		// Unknown Arguments
		String[] loa = argo.getArgumentKeys();
		if (loa.length > 0) {
			if (loa.length == 1) {
				System.out.println("Unknown argument: -" + loa[0]);
			} else {
				System.out.print("Unknown arguments: ");
				for (int i = 0; i < loa.length; i++) {
					System.out.print("-" + loa[i] + (i == loa.length - 1 ? "" : " "));
				}
			}
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
		argprint(-1);
	}

	public void argprint(int section) {
		argprint(section, "");
	}
	
	public void argprint(int section, String padding) {
		if (section == -1 || section == 1) {
			System.out.println(padding + "java -jar bootstrap.jar [-flag] [-arg value] ");
		}
		if (section == -1 || section == 2) {
			System.out.println(padding + "-help");
			System.out.println(padding + padding + "Display information on how to use the command line");
			System.out.println(padding + padding + "tools included in fcbootstrap.jar");
			System.out.println();
			System.out.println(padding + "-version");
			System.out.println(padding + padding + "Display the version information of fcbootstrap.jar");
			System.out.println();
			System.out.println(padding + "--[key] [value]");
			System.out.println(padding + padding + "Temporarily change a configuration value. Replace");
			System.out.println(padding + padding + "[key] with the configuration key and [value] with");
			System.out.println(padding + padding + "the new value for the key.");
			System.out.println();
			System.out.println(padding + "- [argument] [argument]");
			System.out.println(padding + padding + "Pass all arguments following '-' to the launcher.");
			System.out.println(padding + padding + "The arguments will not be validated by the");
			System.out.println(padding + padding + "bootstrap and will be safely passed without changes.");
		}

		if (die) {
			System.exit(1);
		}
	}

	public void run() {

	}

}
