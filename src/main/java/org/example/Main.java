package org.example;

import org.example.vendor.FruitVendorApplication;


public class Main {

    /**
     * Application entry point.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Create and run the fruit vendor application
            FruitVendorApplication app = new FruitVendorApplication();
            app.run();
        } catch (Exception e) {
            System.err.println("Fatal error occurred while starting the application:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}