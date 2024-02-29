import java.time.Duration;
import java.time.LocalDateTime;

public class SmartPlug extends Device{

    public static final int VOLTAGE = 220;
    private double energyConsumption = 0;
    private boolean plugged = false;
    private double ampere = 0;

    /**
     * Constructs SmartPlug object using name.
     * By default, SmartPlug isn't plugged and its status is off.
     * @param name smart plug's name
     */
    public SmartPlug(String name) {
        super(name);
    }

    /**
     * Constructs SmartPlug object using name and on or off status
     * By default, SmartPlug isn't plugged.
     * @param name smart plug's name
     * @param status smart plug's on or off status
     */
    public SmartPlug(String name, String status) {
        super(name, status);
    }

    /**
     * Constructs SmartPlug object using name, on or off status and ampere value.
     * @param name smart plug's name
     * @param status smart plug's on or off status
     * @param ampere ampere value of the device that plugged in the smart plug
     */
    public SmartPlug(String name, String status, double ampere) {
        super(name, status);
        setAmpere(ampere);
        setPlugged(true);
    }

    /**
     *
     * @return information about the smart plug
     */
    public String toString() {
        return String.format("Smart Plug %s is %s and consumed %.2fW so far (excluding current device)," +
                " and its time to switch its status is %s.",
                getName(), getStatus().toLowerCase(), getEnergyConsumption(),
                (getSwitchTime() == null) ? getSwitchTime() : getSwitchTime().format(Device.OUTPUT_FORMATTER));
    }

    /**
     * Sets the device's on or off status.
     * If the device gets switched from on to off, energy consumption value gets updated.
     * @param status device's on or off status
     */
    public void setStatus(String status) {
        super.setStatus(status);
        if (getStatus().equals("Off") && getOnTime() != null && isPlugged()) {
            if (getSwitchTime() != null && getSwitchTime().isBefore(Device.getCurrentTime())) {
                addEnergyConsumption(getSwitchTime());
            }
            else {
                addEnergyConsumption(Device.getCurrentTime());
            }
            setOnTime(null);
        }
        else if (getStatus().equals("On")) {
            setOnTime(Device.getCurrentTime());
        }
    }

    /**
     * Adds to the energy consumption.
     * @param destinationTime date that will be used to calculate how long a device has been on
     */
    public void addEnergyConsumption(LocalDateTime destinationTime) {
        Duration duration = Duration.between(getOnTime(), destinationTime);
        energyConsumption += (VOLTAGE*getAmpere()*duration.getSeconds())/3600.0;
    }

    /**
     *
     * @return energy comsumption of the smart plug
     */
    public double getEnergyConsumption() {
        return energyConsumption;
    }

    /**
     *
     * @return true if there is a device plugged in, else false
     */
    public boolean isPlugged() {
        return plugged;
    }

    /**
     * Sets smart plug's plugged situation.
     * @param plugged true if there is a device plugged in, else false
     */
    public void setPlugged(boolean plugged) {
        this.plugged = plugged;
    }

    /**
     *
     * @return ampere value of the plugged device
     */
    public double getAmpere() {
        return ampere;
    }

    /**
     * Sets the ampere value of the plugged device.
     * @param ampere ampere value of the plugged device
     * @throws CustomExceptions.IllegalAmpereException thrown if ampere is not positice
     */
    public void setAmpere(double ampere) throws CustomExceptions.IllegalAmpereException {
        if (ampere > 0) {
            this.ampere = ampere;
        }
        else {
            throw new CustomExceptions.IllegalAmpereException();
        }
    }
}
