package com.thebinaryfox.finecraft.update;

/**
 * The retention type (or policy) of the launcher jars.
 * 
 * @author TheBinaryFox
 */
public enum RetentionType {

	// Entries
	/**
	 * Store the launcher and only download as updates needed.
	 */
	STORE,

	/**
	 * Store the launcher in RAM and download a new one every time.
	 */
	DISCARD;

	// Static public
	/**
	 * Get the retention type by name.
	 * 
	 * @param type
	 *            the name of the retention type.
	 * @return the type specified, or the default.
	 */
	static public RetentionType get(String type) {
		RetentionType[] values = values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].name().toLowerCase().equals(type.toLowerCase()))
				return values[i];
		}

		return STORE;
	}

}
