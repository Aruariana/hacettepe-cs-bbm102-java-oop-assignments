import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Device {

    private static LocalDateTime currentTime;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-d_H:m:s");
    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

    private LocalDateTime switchTime;
    private LocalDateTime onTime;
    private String name;
    private String status = "Off";

    /**
     * Constructs a device object using name.
     * Device status is off by default.
     * @param name device's name
     */
    public Device(String name) {
        setName(name);
    }

    /**
     * Constructs a device object using name and status.
     * @param name device's name
     * @param status device's on or off status
     */
    public Device(String name, String status) {
        setName(name);
        setStatus(status);
    }

    /**
     * @return current time
     */
    public static LocalDateTime getCurrentTime() {
        return currentTime;
    }

    /**
     * Sets currentTime.
     * @param currentTime LocalDateTime object
     * @throws CustomExceptions.ReverseTimeException thrown if new currentTime is before Device.currentTime
     */
    public static void setCurrentTime(LocalDateTime currentTime) throws CustomExceptions.ReverseTimeException {
        if (Device.getCurrentTime() == null || Device.getCurrentTime().isBefore(currentTime)
                || Device.getCurrentTime().isEqual(currentTime)) {
            Device.currentTime = currentTime;
        }
        else {
            throw new CustomExceptions.ReverseTimeException();
        }
    }

    /**
     * Activates the switch of the device if it's switch time is now or has passed.
     * Sets its new switch time to null.
     */
    public void activateSwitch() {
        if (getSwitchTime() != null &&
                (getSwitchTime().isBefore(Device.getCurrentTime()) || getSwitchTime().isEqual(Device.getCurrentTime()))) {
            setStatus(getStatus().equals("Off") ? "On" : "Off");
            setSwitchTime(null);
        }
    }

    /**
     *
     * @return returns device's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets device's name.
     * @param name device's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return device's switch time
     */
    public LocalDateTime getSwitchTime() {
        return switchTime;
    }

    /**
     * Sets switch time of the device.
     * @param switchTime date that the device will change status
     * @throws CustomExceptions.InvalidSwitchTimeException thrown if switch time is before current time
     */
    public void setSwitchTime(LocalDateTime switchTime) throws CustomExceptions.InvalidSwitchTimeException {
        if (switchTime == null || switchTime.isAfter(Device.getCurrentTime()) || switchTime.isEqual(Device.getCurrentTime())) {
            this.switchTime = switchTime;
        }
        else {
            throw new CustomExceptions.InvalidSwitchTimeException();
        }
    }

    /**
     *
     * @return device's on or off status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the device.
     * @param status device's on or off status
     * @throws CustomExceptions.IllegalStatusException thrown if status is different then on or off
     */
    public void setStatus(String status) throws CustomExceptions.IllegalStatusException {
        if (status.equals("On") || status.equals("Off")) {
            this.status = status;
        }
        else {
            throw new CustomExceptions.IllegalStatusException();
        }
        if (status.equals("On")) {
            setOnTime(Device.getCurrentTime());
        }
    }

    /**
     *
     * @return device's date of getting on
     */
    public LocalDateTime getOnTime() {
        return onTime;
    }

    /**
     * Sets the device's date of getting on.
     * @param onTime device's date of getting on
     */
    public void setOnTime(LocalDateTime onTime) {
        this.onTime = onTime;
    }

}
