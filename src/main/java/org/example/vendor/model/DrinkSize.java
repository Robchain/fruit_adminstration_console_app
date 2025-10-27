package org.example.vendor.model;


public enum DrinkSize {
    SMALL("Small", 200),
    MEDIUM("Medium", 300),
    LARGE("Large", 500);

    private final String displayName;
    private final int volumeInMl;

    DrinkSize(String displayName, int volumeInMl) {
        this.displayName = displayName;
        this.volumeInMl = volumeInMl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getVolumeInMl() {
        return volumeInMl;
    }

    @Override
    public String toString() {
        return String.format("%s (%dml)", displayName, volumeInMl);
    }
}
