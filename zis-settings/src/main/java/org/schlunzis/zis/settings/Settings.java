package org.schlunzis.zis.settings;

import java.util.UUID;
import java.util.prefs.Preferences;

/**
 * Interface for accessing settings.
 * Settings are stored as key-value pairs.
 * The key is a {@link Setting} object and the value can be a {@link String}, an {@link Integer} or a {@link Boolean}.
 * Implementations of this interface are guaranteed to return a valid value for each key as long as all writes happen
 * via the same implementation.
 * If no value is stored for a key, the default value is returned.
 * <p>
 * To use this, create a new {@link Setting} object for each setting and pass it to the methods of this interface.
 * <p>
 * Example:
 * <pre>{@code
 *     public static final Setting<String> MY_SETTING = new Setting<>("my_setting", "default_value");
 *     Settings settings = new UserSettings("my_app");
 *     settings.put(MY_SETTING, "new_value");
 *     String value = settings.getString(MY_SETTING);
 *     }</pre>
 * This will store the value "new_value" for the key "my_setting" and retrieve it again.
 * If no value is stored for the key, the default value "default_value" is returned.
 * <p>
 * You can extend implementations of this interface to store additional types of values.
 *
 * @see Setting
 * @see UserSettings
 * @since 0.0.1
 */
public interface Settings {

    /**
     * Returns the underlying preferences object.
     *
     * @return the preferences object
     */
    Preferences getPreferences();

    /**
     * Stores the given value for the given key.
     *
     * @param key   the key
     * @param value the value
     */
    void put(Setting<String> key, String value);

    /**
     * Stores the given value for the given key.
     *
     * @param key   the key
     * @param value the value
     */
    void put(Setting<Integer> key, int value);

    /**
     * Stores the given value for the given key.
     *
     * @param key   the key
     * @param value the value
     */
    void put(Setting<Boolean> key, Boolean value);

    /**
     * Stores the given value for the given key.
     *
     * @param key   the key
     * @param value the value
     */
    void put(Setting<Float> key, Float value);

    /**
     * Stores the given value for the given key.
     *
     * @param key   the key
     * @param value the value
     */
    void put(Setting<Double> key, Double value);

    /**
     * Stores the given value for the given key.
     *
     * @param key   the key
     * @param value the value
     */
    void put(Setting<Long> key, Long value);

    /**
     * Stores the given value for the given key.
     *
     * @param key   the key
     * @param value the value
     */
    void put(Setting<byte[]> key, byte[] value);

    /**
     * Stores the given value for the given key.
     *
     * @param key   the key
     * @param value the value
     */
    void put(Setting<UUID> key, UUID value);

    /**
     * Returns the value for the given key.
     * If no value is stored for the key, the default value is returned.
     *
     * @param key the key
     * @return the value
     */
    String getString(Setting<String> key);

    /**
     * Returns the value for the given key.
     * If no value is stored for the key, the default value is returned.
     *
     * @param key the key
     * @return the value
     */
    int getInt(Setting<Integer> key);

    /**
     * Returns the value for the given key.
     * If no value is stored for the key, the default value is returned.
     *
     * @param key the key
     * @return the value
     */
    boolean getBoolean(Setting<Boolean> key);

    /**
     * Returns the value for the given key.
     * If no value is stored for the key, the default value is returned.
     *
     * @param key the key
     * @return the value
     */
    float getFloat(Setting<Float> key);

    /**
     * Returns the value for the given key.
     * If no value is stored for the key, the default value is returned.
     *
     * @param key the key
     * @return the value
     */
    double getDouble(Setting<Double> key);

    /**
     * Returns the value for the given key.
     * If no value is stored for the key, the default value is returned.
     *
     * @param key the key
     * @return the value
     */
    long getLong(Setting<Long> key);

    /**
     * Returns the value for the given key.
     * If no value is stored for the key, the default value is returned.
     *
     * @param key the key
     * @return the value
     */
    byte[] getByteArray(Setting<byte[]> key);

    /**
     * Returns the value for the given key.
     * If no value is stored for the key, the default value is returned.
     *
     * @param key the key
     * @return the value
     */
    UUID getUUID(Setting<UUID> key);

}