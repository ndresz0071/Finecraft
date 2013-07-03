package com.thebinaryfox.finecraft.bs;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * The class which handles the configuration used by the Finecraft bootstrap.
 * 
 * @author TheBinaryFox
 */
public class _BSC {

	/**
	 * All the configuration options.
	 * 
	 * @author TheBinaryFox
	 */
	static public enum Options {

		UPDATE_TYPE("bootstrap.update.type", "release"),
		UPDATE_CAST("bootstrap.update.cast", ""),
		UI_WIDTH("bootstrap.ui.width", 854),
		UI_HEIGHT("bootstrap.ui.height", 480);

		// Enum stuff.
		private String key;
		private Object def;
		private Object value;

		private Options(String key, Object value) {
			this.key = key;
			this.def = value;
		}

		protected String key() {
			return key;
		}

		protected Object defaults() {
			return def;
		}

		protected void load() {
			if (value instanceof Integer) {
				value = get().prefs.getInt(key, def);
			}
		}

		// Public
		/**
		 * Get the object's value.
		 * 
		 * @return
		 */
		public Object value() {

		}

		public int valueInt() {
			if ()
		}

	}

	// Static private
	static private BootstrapConfig instance;

	// Static public
	/**
	 * Get the bootstrap configuration manager.
	 * 
	 * @return
	 */
	static public BootstrapConfig get() {
		if (instance == null) {
			try {
				instance = new BootstrapConfig();
				instance.load();
				instance.defaults();
				instance.save();
			} catch (Exception ex) {
				instance = null;
			}
		}

		return instance;
	}

	// Private
	private Preferences prefs;

	// Private
	/**
	 * Set a default value for a key.
	 * 
	 * @param key
	 *            the key.
	 * @param value
	 *            the value.
	 */
	private void df(String key, Object value) throws BackingStoreException {
		if (!prefs.nodeExists(key)) {
			if (value instanceof Boolean) {
				prefs.putBoolean(key, ((Boolean) value).booleanValue());
			} else if (value instanceof Integer) {
				prefs.putInt(key, ((Integer) value).intValue());
			} else {
				prefs.put(key, value.toString());
			}
		}
	}

	/**
	 * Load the configuration.
	 */
	private void load() throws BackingStoreException {
		prefs = Preferences.userRoot().node("com.thebinaryfox.finecraft");
		prefs.sync();
	}

	/**
	 * Write the default configuration values.
	 * 
	 * @throws BackingStoreException
	 */
	private void defaults() throws BackingStoreException {
		Options[] opts = Options.values();
		for (int i = 0; i < opts.length; i++) {
			df(opts[i].key(), opts[i].defaults());
		}
	}

	/**
	 * Save the configuration values.
	 * 
	 * @throws BackingStoreException
	 */
	private void save() throws BackingStoreException {
		prefs.flush();
	}
}
