package Model;

import GUI.LandingPage;

public class Main {
    public static void main(String[] args) {
        Product.loadProductsFromFile();
        LandingPage landingPage = new LandingPage();
    }
}

