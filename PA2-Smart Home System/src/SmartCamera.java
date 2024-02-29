import java.time.Duration;
import java.time.LocalDateTime;

public class SmartCamera extends Device{

    private double megabytesPerMinute;
    private double totalStorage = 0;

    /**
     * Construct a SmartCamera object using name and megabytes per unite value.
     * SmartCamera's status is off by default.
     * @param name smart camera's name
     * @param megabytesPerMinute smart camera's value of how much megabytes it stores per minute
     */
    public SmartCamera(String name, double megabytesPerMinute) {
        super(name);
        setMegabytesPerMinute(megabytesPerMinute);
    }

    /**
     * Construct a SmartCamera object using name, megabytes per unite value and on or off status.
     * @param name smart camera's name
     * @param megabytesPerMinute smart camera's value of how much megabytes it stores per minute
     * @param status smart camera's on or off status
     */
    public SmartCamera(String name, double megabytesPerMinute, String status) {
        super(name, status);
        setMegabytesPerMinute(megabytesPerMinute);
    }

    /**
     *
     * @return information about the smart camera
     */
    public String toString() {
        return String.format("Smart Camera %s is %s and used %.2f MB of storage so far (excluding current status)," +
                " and its time to switch its status is %s.",
                getName(), getStatus().toLowerCase(), getTotalStorage(),
                (getSwitchTime() == null) ? getSwitchTime() : getSwitchTime().format(Device.OUTPUT_FORMATTER));
    }

    /**
     * Sets the device's on or off status.
     * If the device gets switched from on to off, total storage use gets updated.
     * @param status device's on or off status
     */
    public void setStatus(String status) {
        super.setStatus(status);
        if (getStatus().equals("Off") && getOnTime() != null) {
            if (getSwitchTime() != null && getSwitchTime().isBefore(Device.getCurrentTime())) {
                addTotalStorage(getSwitchTime());
            }
            else {
                addTotalStorage(Device.getCurrentTime());
            }
            setOnTime(null);
        }
        else if (getStatus().equals("On")) {
            setOnTime(Device.getCurrentTime());
        }
    }

    /**
     * Adds to the total storage
     * @param destinationTime date that will be used to calculate how long a device has been on
     */
    public void addTotalStorage(LocalDateTime destinationTime) {
        Duration duration = Duration.between(getOnTime(), destinationTime);
        totalStorage += (getMegabytesPerMinute()*duration.getSeconds())/60.0;
    }

    /**
     *
     * @return smart camera's megabytes per minute value
     */
    public double getMegabytesPerMinute() {
        return megabytesPerMinute;
    }

    /**
     * Sets smart camera's megabytes per minute value.
     * @param megabytesPerMinute smart camera's megabytes per minute value
     * @throws CustomExceptions.IllegalMegabyteException thrown if megabytes per minute value is not positive
     */
    public void setMegabytesPerMinute(double megabytesPerMinute) throws CustomExceptions.IllegalMegabyteException {
        if (megabytesPerMinute > 0) {
            this.megabytesPerMinute = megabytesPerMinute;
        }
        else {
            throw new CustomExceptions.IllegalMegabyteException();
        }
    }

    /**
     *
     * @return total storage of smart camera
     */
    public double getTotalStorage() {
        return totalStorage;
    }

}
