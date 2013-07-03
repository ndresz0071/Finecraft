package com.thebinaryfox.finecraft.bs;

/**
 * An enum containing all configuration values used by the Finecraft bootstrap.
 * 
 * @author TheBinaryFox
 */
public enum Configuration {

	/**
	 * The width of the bootstrap and launcher window.
	 */
	WINDOW_WIDTH("ui.width", "854") {
		public Integer get() {
			try {
				return Integer.parseInt(gvo(this));
			} catch (Exception ex) {
				svo(this);
				return Integer.parseInt(gvo(this));
			}
		}
	},

	/**
	 * The height of the bootstrap and launcher window.
	 */
	WINDOW_HEIGHT("ui.height", "480") {
		public Integer get() {
			try {
				return Integer.parseInt(gvo(this));
			} catch (Exception ex) {
				svo(this);
				return Integer.parseInt(gvo(this));
			}
		}
	},

	/**
	 * Whether the bootstrap and launcher window is resizable.
	 */
	WINDOW_RESIZE("ui.resizable", "true") {
		public Boolean get() {
			try {
				return Boolean.parseBoolean(gvo(this));
			} catch (Exception ex) {
				svo(this);
				return Boolean.parseBoolean(gvo(this));
			}
		}
	};

	// Fields
	private final String key;
	private String value;
	private String defvl;
	private boolean temp;

	// Constructors
	private Configuration(String key, String value) {
		this.key = key;
		this.defvl = value;
		this.temp = false;
	}

	// Methods: static private
	/**
	 * Get the value string.
	 * 
	 * @param entry
	 *            the entry to get the value from.
	 * @return the value string.
	 */
	static private String gvo(Configuration entry) {
		return entry.value;
	}

	/**
	 * Reset the value string back to the default.
	 * 
	 * @param entry
	 *            the entry to reset.
	 */
	static private void svo(Configuration entry) {
		if (entry.temp) {
			entry.setTemporarily(entry.defvl);
		} else {
			entry.set(entry.defvl);
		}
	}

	// Methods: instance public
	/**
	 * Get the value of the configuration entry.
	 * 
	 * @return the value.
	 */
	public abstract Object get();

	/**
	 * Get the key of the configuration entry.
	 * 
	 * @return the key.
	 */
	public String key() {
		return key;
	}

	/**
	 * Set the value of the configuration entry.
	 * 
	 * @param value
	 *            the new value.
	 */
	public void set(String value) {

	}

	/**
	 * Set the value of the configuration entry temporarily.
	 * 
	 * @param value
	 *            the new value.
	 */
	public void setTemporarily(String value) {
		this.temp = true;
		this.value = value;
	}

}
