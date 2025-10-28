package org.schlunzis.zis.settings;

/**
 * Represents a setting with a key and a default value.
 *
 * @param preferencesKey the key for the setting in the preferences
 * @param defaultValue   the default value for the setting
 * @param <T>            the type of the value to store
 * @see Settings Settings for supported types
 * @since 0.0.1
 */
public record Setting<T>(String preferencesKey, T defaultValue) {
}
