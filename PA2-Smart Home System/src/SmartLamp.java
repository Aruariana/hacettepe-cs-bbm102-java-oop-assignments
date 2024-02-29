public class SmartLamp extends Device{

    private int kelvin = 4000;
    private int brightness = 100;

    /**
     * Constructs SmartLamp object using name.
     * By default, its kelvin value is 4000, brightness value is 100 and status is off.
     * @param name smart lamp's name
     */
    public SmartLamp(String name) {
        super(name);
    }

    /**
     * Constructs SmartLamp object using name and status.
     * By default, its kelvin value is 4000 and brightness value is 100.
     * @param name smart lamp's name
     * @param status mart lamp's on or off status
     */
    public SmartLamp(String name, String status) {
        super(name, status);
    }

    /**
     * Constructs SmartLamp object using name, status, kelvin value and brightness value.
     * @param name smart lamp's name
     * @param status smart lamp's on or off status
     * @param kelvin smart lamp's kelvin value
     * @param brightness smart lamp's brightness value
     */
    public SmartLamp(String name, String status, int kelvin, int brightness) {
        super(name, status);
        setKelvin(kelvin);
        setBrightness(brightness);
    }

    /**
     *
     * @return information about the smart lamp
     */
    public String toString() {
        return String.format("Smart Lamp %s is %s and its kelvin value is %dK with %d%% brightness," +
                " and its time to switch its status is %s.",
                getName(), getStatus().toLowerCase(), getKelvin(),
                getBrightness(), (getSwitchTime() == null) ? getSwitchTime() : getSwitchTime().format(Device.OUTPUT_FORMATTER));
    }

    /**
     *
     * @return smart lamp's kelvin value
     */
    public int getKelvin() {
        return kelvin;
    }

    /**
     * Sets smart lamp's kelvin value.
     * @param kelvin smart lamp's kelvin value
     * @throws CustomExceptions.IllegalKelvinException thrown if kelvin value is not between 2000 and 6500.
     */
    public void setKelvin(int kelvin) throws CustomExceptions.IllegalKelvinException {
        if (kelvin >= 2000 && kelvin <= 6500) {
            this.kelvin = kelvin;
        }
        else {
            throw new CustomExceptions.IllegalKelvinException();
        }
    }

    /**
     *
     * @return smart lamp's brightness value
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * Sets smart lamp's brightness value.
     * @param brightness smart lamp's brightness value
     * @throws CustomExceptions.IllegalBrightnessException thrown if brightness is not between 0 and 100
     */
    public void setBrightness(int brightness) throws CustomExceptions.IllegalBrightnessException {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
        }
        else {
            throw new CustomExceptions.IllegalBrightnessException();
        }
    }
}
