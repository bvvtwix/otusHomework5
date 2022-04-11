public enum ChromeArgumentsData {

    KIOSK("--kiosk"),
    HEADLESS("headless"),
    MAXIMAZE("--start-maximized")
    ;

    private String param;

    ChromeArgumentsData(String param) {
        this.param = param;
    }

    private String getParam(){
        return param;
    }

    @Override
    public String toString() {
        return param;
    }
}
