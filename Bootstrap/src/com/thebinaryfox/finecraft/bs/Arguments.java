package com.thebinaryfox.finecraft.bs;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * The argument parser of the Finecraft bootstrap.
 * 
 * @author TheBinaryFox
 */
public final class Arguments {

	// Fields: instance private
	private LinkedHashMap<String, String> arguments;
	private ArrayList<String> arguments_post;

	// Constructors: public
	public Arguments() {
		arguments = new LinkedHashMap<String, String>();
		arguments_post = new ArrayList<String>();
	}

	// Methods: instance public
	/**
	 * Parse an array of arguments.
	 * 
	 * @param args
	 *            the arguments to parse.
	 * @throws IllegalArgumentException
	 */
	public void parse(String[] args) throws IllegalArgumentException {
		String key = null;
		boolean def = true;
		boolean end = false;

		for (int i = 0; i < args.length; i++) {
			if (end) {
				arguments_post.add(args[i]);
				continue;
			}

			String arg = args[i].trim();
			if (arg.isEmpty())
				continue;

			if (key == null) {
				if (arg.startsWith("-")) {
					def = false;

					if (arg.equals("-")) {
						end = true;
						continue;
					}

					key = arg.substring(1);
					continue;
				} else if (def) {
					key = "DEFAULT";
					def = false;
				} else {
					throw new IllegalArgumentException("keys must start with a -");
				}
			} else {
				if (arg.startsWith("-")) {
					arguments.put(key, "");
					key = null;
					i--;
					continue;
				}

				arguments.put(key, arg);
				key = null;
			}
		}

		if (!end) {
			if (key != null) {
				arguments.put(key, "");
			}
		}
	}

	/**
	 * Get an argument.
	 * 
	 * @param name
	 *            the name of the argument.
	 * @return the value of the argument.
	 */
	public String get(String name) {
		return arguments.get(name);
	}

	/**
	 * Get an argument and return a value based on its contents.
	 * 
	 * @param name
	 *            the name of the argument.
	 * @return 0 for non-existent, 1 for existent and empty, 2 for existent and contains a value.
	 */
	public int getCheckEmpty(String name) {
		String arg = arguments.get(name);
		return arg == null ? 0 : arg.isEmpty() ? 1 : 2;
	}

	/**
	 * Get an argument, remove it, and return a value based on its contents.
	 * 
	 * @param name
	 *            the name of the argument.
	 * @return 0 for non-existent, 1 for existent and empty, 2 for existent and contains a value.
	 */
	public int getCheckEmptyAndRemove(String name) {
		String arg = arguments.remove(name);
		return arg == null ? 0 : arg.isEmpty() ? 1 : 2;
	}

	/**
	 * Get an argument and then remove it.
	 * 
	 * @param name
	 *            the name of the argument.
	 * @return the value of the argument.
	 */
	public String getAndRemove(String name) {
		return arguments.remove(name);
	}

	/**
	 * Get the number of arguments.
	 * 
	 * @return the number of arguments.
	 */
	public int count() {
		return arguments.size();
	}

	/**
	 * Get all the keys contained in the argument list.
	 * 
	 * @return a list of keys.
	 */
	public String[] getArgumentKeys() {
		return arguments.keySet().toArray(new String[0]);
	}

	/**
	 * Get all program arguments past the first "-" argument.
	 * 
	 * @return all of the extra arguments.
	 */
	public String[] getPost() {
		return arguments_post.toArray(new String[0]);
	}

}
