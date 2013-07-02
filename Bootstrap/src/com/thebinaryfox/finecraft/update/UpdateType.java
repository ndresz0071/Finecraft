package com.thebinaryfox.finecraft.update;

/**
 * The types of updates.
 * 
 * @author TheBinaryFox
 */
public enum UpdateType {

	// Entries
	/**
	 * Developer updates, potentially unstable or broken.
	 */
	DEVELOPER,

	/**
	 * Beta updates, somewhat stable or buggy.
	 */
	BETA,

	/**
	 * Release updates, stable and mostly bug-free.
	 */
	RELEASE;

	// Static public
	/**
	 * Get the update type by name.
	 * 
	 * @param type
	 *            the name of the retention type.
	 * @return the type specified, or null.
	 */
	static public UpdateType get(String type) {
		UpdateType[] values = values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].name().toLowerCase().equals(type.toLowerCase()))
				return values[i];
		}

		return null;
	}

}
