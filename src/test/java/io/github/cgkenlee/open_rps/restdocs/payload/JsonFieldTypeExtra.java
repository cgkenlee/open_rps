package io.github.cgkenlee.open_rps.restdocs.payload;

/**
 * Extra field types to supplement {@link org.springframework.restdocs.payload.JsonFieldType}
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-03-02
 */
public enum JsonFieldTypeExtra {
    /**
     * Enum's name
     */
    STRING_ENUM("String (Enum)");

    private final String displayName;

    JsonFieldTypeExtra(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
