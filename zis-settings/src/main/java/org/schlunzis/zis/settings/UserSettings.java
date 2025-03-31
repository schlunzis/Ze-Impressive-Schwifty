package org.schlunzis.zis.settings;

import java.util.UUID;
import java.util.prefs.Preferences;

/**
 * Implementation of the {@link Settings} interface that stores the settings in the user preferences.
 *
 * @since 0.0.1
 */
public class UserSettings implements Settings {

    protected final Preferences preferences;

    /**
     * Creates a new user settings object with the given node name.
     * The node name should be unique for the application.
     *
     * @param nodeName the node name
     */
    public UserSettings(String nodeName) {
        this(Preferences.userRoot().node(nodeName));
    }

    /**
     * Creates a new user settings object with the given preferences object.
     *
     * @param preferences the preferences object
     */
    public UserSettings(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public Preferences getPreferences() {
        return preferences;
    }

    @Override
    public void put(Setting<String> key, String value) {
        preferences.put(key.preferencesKey(), value);
    }

    @Override
    public void put(Setting<Integer> key, int value) {
        preferences.putInt(key.preferencesKey(), value);
    }

    @Override
    public void put(Setting<Boolean> key, Boolean value) {
        preferences.putBoolean(key.preferencesKey(), value);
    }

    @Override
    public void put(Setting<Float> key, Float value) {
        preferences.putFloat(key.preferencesKey(), value);
    }

    @Override
    public void put(Setting<Double> key, Double value) {
        preferences.putDouble(key.preferencesKey(), value);
    }

    @Override
    public void put(Setting<Long> key, Long value) {
        preferences.putLong(key.preferencesKey(), value);
    }

    @Override
    public void put(Setting<byte[]> key, byte[] value) {
        preferences.putByteArray(key.preferencesKey(), value);
    }

    @Override
    public void put(Setting<UUID> key, UUID value) {
        String uuidString = value.toString();
        preferences.put(key.preferencesKey(), uuidString);
    }

    @Override
    public String getString(Setting<String> key) {
        return preferences.get(key.preferencesKey(), key.defaultValue());
    }

    @Override
    public int getInt(Setting<Integer> key) {
        return preferences.getInt(key.preferencesKey(), key.defaultValue());
    }

    @Override
    public boolean getBoolean(Setting<Boolean> key) {
        return preferences.getBoolean(key.preferencesKey(), key.defaultValue());
    }

    @Override
    public float getFloat(Setting<Float> key) {
        return preferences.getFloat(key.preferencesKey(), key.defaultValue());
    }

    @Override
    public double getDouble(Setting<Double> key) {
        return preferences.getDouble(key.preferencesKey(), key.defaultValue());
    }

    @Override
    public long getLong(Setting<Long> key) {
        return preferences.getLong(key.preferencesKey(), key.defaultValue());
    }

    @Override
    public byte[] getByteArray(Setting<byte[]> key) {
        return preferences.getByteArray(key.preferencesKey(), key.defaultValue());
    }

    @Override
    public UUID getUUID(Setting<UUID> key) {
        String uuidString = preferences.get(key.preferencesKey(), key.defaultValue().toString());
        return UUID.fromString(uuidString);
    }

}