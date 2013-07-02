package com.thebinaryfox.finecraft;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * The class which handles the configuration used by the Finecraft bootstrap.
 * 
 * @author TheBinaryFox
 */
public class BootstrapConfig {

	// Static private
	static private BootstrapConfig instance;

	// Static public
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
	 * @throws BackingStoreException
	 */
	private void defaults() throws BackingStoreException {
		df("bootstrap.url", "");
		df("bootstrap.update.type", "release");
		df("bootstrap.launcher.isolated", true);
	}

	private void save() throws BackingStoreException {
		prefs.flush();
	}
}
