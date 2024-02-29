public class SmartColorLamp extends SmartLamp{

    private String colorCode;
    private String colorModeStatus = "Off";

    /**
     * Constructs SmartColorLamp object using name.
     * By default, its kelvin value is 4000, brightness value is 100, status is off and color mode status is off.
     * @param name smart color lamp's name
     */
    public SmartColorLamp(String name) {
        super(name);
    }

    /**
     * Constructs SmartColorLamp object using name and status.
     * By default, its kelvin value is 4000, brightness value is 100 and color mode status is off.
     * @param name smart color lamp's name
     * @param status smart color lamp's on or off status
     */
    public SmartColorLamp(String name, String status) {
        super(name, status);
    }

    /**
     * Constructs SmartColorLamp object using name, status, kelvin and brightness value.
     * @param name smart color lamp's name
     * @param status smart color lamp's on or off status
     * @param kelvin smart color lamp's kelvin value
     * @param brightness smart color lamp's brightness value
     */
    public SmartColorLamp(String name, String status, int kelvin, int brightness) {
        super(name, status, kelvin, brightness);
    }

    /**
     * Constructs SmartColorLamp object using name, status, color code and brightness value.
     * @param name smart color lamp's name
     * @param status smart color lamp's on or off status
     * @param colorCode smart color lamp's color code
     * @param brightness smart color lamp's brightness value
     */
    public SmartColorLamp(String name, String status, String colorCode, int brightness) {
        super(name, status);
        setColorCode(colorCode);
        setBrightness(brightness);
        setColorModeStatus("On");
    }

    /**
     *
     * @return information about the smart color lamp
     */
    public String toString() {
        if (getColorModeStatus().equals("Off")) {
            return String.format("Smart Color Lamp %s is %s and its color value is %dK with %d%% brightness," +
                            " and its time to switch its status is %s.",
                    getName(), getStatus().toLowerCase(), getKelvin(),
                    getBrightness(), (getSwitchTime() == null) ? getSwitchTime() : getSwitchTime().format(Device.OUTPUT_FORMATTER));
        }
        else {
            return String.format("Smart Color Lamp %s is %s and its color value is %s with %d%% brightness," +
                            " and its time to switch its status is %s.",
                    getName(), getStatus().toLowerCase(), getColorCode(),
                    getBrightness(), (getSwitchTime() == null) ? getSwitchTime() : getSwitchTime().format(Device.OUTPUT_FORMATTER));
        }
    }

    /**
     *
     * @return smart color lamp's color code
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets smart color lamp's color code.
     * @param colorCode smart color lamp's color code
     * @throws CustomExceptions.IllegalColorCodeException thrown if color code is not between 0x000000 and 0xFFFFFF
     */
    public void setColorCode(String colorCode) throws CustomExceptions.IllegalColorCodeException {
        try {
            int value = (Integer.parseInt(colorCode.replace("0x", ""), 16));
            if (value >= 0 && value <= 16777215) {
                this.colorCode = colorCode;
            }
            else {
                throw new CustomExceptions.ColorCodeOutOfBoundsException();
            }
        }
        catch (NumberFormatException e) {
            throw new CustomExceptions.IllegalColorCodeException();
        }
    }

    /**
     *
     * @return smart color lamp's color mode status
     */
    public String getColorModeStatus() {
        return colorModeStatus;
    }

    /**
     * Sets smart color lamp's color mode status.
     * @param colorModeStatus smart color lamp's color mode status
     */
    public void setColorModeStatus(String colorModeStatus) {
        this.colorModeStatus = colorModeStatus;
    }
}
