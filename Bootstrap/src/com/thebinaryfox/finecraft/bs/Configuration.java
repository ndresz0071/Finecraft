package com.thebinaryfox.finecraft.bs;

import java.net.URL;
import java.util.prefs.Preferences;

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
	},

	/**
	 * Get the download build type.
	 */
	DOWNLOAD_BUILD("bootstrap.download.build", Build.RELEASE.name()) {
		public Build get() {
			try {
				return Build.valueOf(gvo(this).trim().toUpperCase());
			} catch (Exception ex) {
				svo(this);
				return Build.valueOf(gvo(this).trim().toUpperCase());
			}
		}
	},

	/**
	 * Get the URL to the download cast.
	 */
	DOWNLOAD_CAST("bootstrap.download.cast", "") {
		public URL get() {
			try {
				String v = gvo(this);
				if (v.trim().isEmpty())
					return null;

				return new URL(gvo(this));
			} catch (Exception ex) {
				svo(this);
				return null;
			}
		}
	};

	// Fields: static private
	static private Preferences pref;

	// Methods: static public
	/**
	 * Get the configuration entry as a string.
	 * 
	 * @param entry
	 *            the entry.
	 * @return the value of the entry.
	 */
	static public String asString(Configuration entry) {
		return (String) entry.get();
	}

	/**
	 * Get the configuration entry as an integer.
	 * 
	 * @param entry
	 *            the entry.
	 * @return the value of the entry.
	 */
	static public int asInteger(Configuration entry) {
		return ((Integer) entry.get()).intValue();
	}

	/**
	 * Get the configuration entry as a boolean.
	 * 
	 * @param entry
	 *            the entry.
	 * @return the value of the entry.
	 */
	static public boolean asBoolean(Configuration entry) {
		return ((Boolean) entry.get()).booleanValue();
	}

	// Fields: instance private
	private final String key;
	private String value;
	private String defvl;
	private boolean temp;

	// Constructors: private
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
		if (entry.value == null) {
			if (pref == null) {
				pref = Preferences.userRoot().node("com.thebinaryfox.finecraft");
			}

			entry.value = pref.get(entry.key, entry.defvl);
		}

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
