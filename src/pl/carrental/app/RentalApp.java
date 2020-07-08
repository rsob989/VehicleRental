package pl.carrental.app;

public class RentalApp {

    private static final String appName = "Wypożyczalnia samochodów v2.6";

    public static void main(String[] args) {
        
        RentalControl rc = new RentalControl();

        System.out.println(appName);
        rc.controlLoop();
    }
}
