import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class HomeOperator {

    private String outputStr = "";
    private ArrayList<Device> devices= new ArrayList<>();

    /**
     * Executes the given command. Gives the proper error message if it fails.
     * @param commandLine a command line to operate smart devices
     */
    public void executeCommand(String commandLine) {
        try {
            addOutputStr("COMMAND: "+commandLine+"\n");
            String[] args = commandLine.split("\t");
            String command = args[0];
            switch (command) {
                case ("Add"):
                    switch (args[1]) {
                        case ("SmartPlug"):
                            addSmartPlug(Arrays.copyOfRange(args, 2, args.length));
                            break;
                        case ("SmartCamera"):
                            addSmartCamera(Arrays.copyOfRange(args, 2, args.length));
                            break;
                        case ("SmartLamp"):
                            addSmartLamp(Arrays.copyOfRange(args, 2, args.length));
                            break;
                        case ("SmartColorLamp"):
                            addSmartColorLamp(Arrays.copyOfRange(args, 2, args.length));
                            break;
                    }
                    break;
                case ("SetTime"):
                    if (args.length == 2) {
                        setTime(args[1]);
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case ("Switch"):
                    if (args.length == 3) {
                        setSwitch(Arrays.copyOfRange(args, 1, args.length));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case ("SkipMinutes"):
                    if (args.length == 2) {
                        skipMinutes(Integer.parseInt(args[1]));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case ("Nop"):
                    if (args.length == 1) {
                        nop();
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case ("SetSwitchTime"):
                    if (args.length == 3) {
                        setSwitchTime(Arrays.copyOfRange(args, 1, args.length));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("Remove"):
                    if (args.length == 2) {
                        remove(args[1]);
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("ChangeName"):
                    if (args.length == 3) {
                        changeName(Arrays.copyOfRange(args, 1, args.length));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("PlugIn"):
                    if (args.length == 3) {
                        plugIn(Arrays.copyOfRange(args, 1, args.length));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("PlugOut"):
                    if (args.length == 2) {
                        plugOut(args[1]);
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("SetKelvin"):
                    if (args.length == 3) {
                        setKelvin(Arrays.copyOfRange(args, 1, args.length));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("SetBrightness"):
                    if (args.length == 3) {
                        setBrightness(Arrays.copyOfRange(args, 1, args.length));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("SetColorCode"):
                    if (args.length == 3) {
                        setColorCode(Arrays.copyOfRange(args, 1, args.length));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("SetWhite"):
                    if (args.length == 4) {
                        setWhite(Arrays.copyOfRange(args, 1, args.length));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("SetColor"):
                    if (args.length == 4) {
                        setColor(Arrays.copyOfRange(args, 1, args.length));
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case("ZReport"):
                    if (args.length == 1) {
                        zReport();
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                    break;
                default:
                    addOutputStr("ERROR: Erroneous command!\n");
                    break;
            }
        }
        catch (CustomExceptions.IllegalStatusException | CustomExceptions.IllegalAmpereException |
               CustomExceptions.IllegalMegabyteException | CustomExceptions.IllegalKelvinException |
               CustomExceptions.IllegalBrightnessException | CustomExceptions.ColorCodeOutOfBoundsException |
               CustomExceptions.IllegalColorCodeException | CustomExceptions.SmartPlugCastException |
               CustomExceptions.SmartLampCastException | CustomExceptions.SmartColorLampCastException |
               CustomExceptions.InvalidTimeFormatException | CustomExceptions.ReverseTimeException |
               CustomExceptions.NothingToSwitchException | CustomExceptions.InvalidSwitchTimeException e) {
            addOutputStr(e.getMessage());
        } catch (Exception e) {
            addOutputStr("ERROR: Erroneous command!\n");
        }

    }

    /**
     * Sets the initial time of the program.
     * @param formattedTime date formatted like "yyyy-M-d_H:m:s"
     */
    public void setInitialTime(String formattedTime) {
        Device.setCurrentTime(LocalDateTime.parse(formattedTime, Device.FORMATTER));
        addOutputStr(String.format("SUCCESS: Time has been set to %s!\n", Device.getCurrentTime().format(Device.OUTPUT_FORMATTER)));
    }

    /**
     * Adds a detailed report about all the devices to the output.
     */
    public void zReport() {
        stableSort();
        addOutputStr("Time is:\t" + Device.getCurrentTime().format(Device.OUTPUT_FORMATTER) +"\n");
        for (Device d : devices) {
            addOutputStr(d+"\n");
        }
    }

    /**
     *
     * @return the output string
     */
    public String getOutputStr() {
        return outputStr;
    }

    /**
     * Adds a string to the output.
     * @param s String that will be added to the output
     */
    public void addOutputStr(String s) {
        outputStr += s;
    }

    private void setTime(String formattedTime) {
        try {
            LocalDateTime newTime = LocalDateTime.parse(formattedTime, Device.FORMATTER);
            if (!newTime.isEqual(Device.getCurrentTime())) {
                nopUntil(newTime);
                Device.setCurrentTime(newTime);
            }
            else {
                addOutputStr("ERROR: There is nothing to change!\n");
            }
        }
        catch (DateTimeException e) {
            throw new CustomExceptions.InvalidTimeFormatException();
        }
    }

    private void skipMinutes(int minutes) {
        if (minutes != 0) {
            setTime(Device.getCurrentTime().plusMinutes(minutes).format(Device.FORMATTER));
        }
        else {
            addOutputStr("ERROR: There is nothing to skip!\n");
        }
    }

    private void nop() {
        LocalDateTime min = LocalDateTime.MAX;
        for (Device d : devices) {
            if (d.getSwitchTime() != null && d.getSwitchTime().isBefore(min)) {
                min = d.getSwitchTime();
            }
        }
        if (!min.equals(LocalDateTime.MAX)) {
            Device.setCurrentTime(min);
            for (Device d: devices) {
                d.activateSwitch();
            }
            stableSort();
        }
        else {
            throw new CustomExceptions.NothingToSwitchException();
        }
    }

    private void nopUntil(LocalDateTime destinationTime) {
        LocalDateTime min = LocalDateTime.MAX;
        while (true) {
            for (Device d : devices) {
                if (d.getSwitchTime() != null && d.getSwitchTime().isBefore(min)) {
                    min = d.getSwitchTime();
                }
            }
            if (!min.equals(LocalDateTime.MAX) && (min.isBefore(destinationTime) || min.equals(destinationTime))) {
                Device.setCurrentTime(min);
                for (Device d: devices) {
                    d.activateSwitch();
                }
                stableSort();
            } else {
                break;
            }
            min = LocalDateTime.MAX;
        }
    }

    private void addSmartPlug(String[] args) {
        int len = args.length;
        if (len == 1 || len == 2 || len == 3) {
            if (!nameExists(args[0])) {
                switch (len) {
                    case 1:
                        devices.add(new SmartPlug(args[0]));
                        break;
                    case 2:
                        devices.add(new SmartPlug(args[0], args[1]));
                        break;
                    case 3:
                        devices.add(new SmartPlug(args[0], args[1], Double.parseDouble(args[2])));
                        break;
                }
            }
            else {
                addOutputStr("ERROR: There is already a smart device with same name!\n");
            }
        }
        else {
            addOutputStr("ERROR: Erroneous command!\n");
        }

    }

    private void addSmartCamera(String[] args) {
        int len = args.length;
        if (len == 2 || len == 3) {
            if (!nameExists(args[0])) {
                switch (len) {
                    case 2:
                        devices.add(new SmartCamera(args[0], Double.parseDouble(args[1])));
                        break;
                    case 3:
                        devices.add(new SmartCamera(args[0], Double.parseDouble(args[1]), args[2]));
                        break;
                }
            }
            else {
                addOutputStr("ERROR: There is already a smart device with same name!\n");
            }
        }
        else {
            addOutputStr("ERROR: Erroneous command!\n");
        }
    }

    private void addSmartLamp(String[] args) {
        int len = args.length;
        if (len == 1 || len == 2 || len == 4) {
            if (!nameExists(args[0])) {
                switch (len) {
                    case 1:
                        devices.add(new SmartLamp(args[0]));
                        break;
                    case 2:
                        devices.add(new SmartLamp(args[0], args[1]));
                        break;
                    case 4:
                        devices.add(new SmartLamp(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3])));
                        break;
                }
            }
            else {
                addOutputStr("ERROR: There is already a smart device with same name!\n");
            }
        }
        else {
            addOutputStr("ERROR: Erroneous command!\n");
        }
    }

    private void addSmartColorLamp(String[] args) {
        int len = args.length;
        if (len == 1 || len == 2 || len == 4) {
            if (!nameExists(args[0])) {
                switch (len) {
                    case 1:
                        devices.add(new SmartColorLamp(args[0]));
                        break;
                    case 2:
                        devices.add(new SmartColorLamp(args[0], args[1]));
                        break;
                    case 4:
                        if (args[2].contains("0x")) {
                            devices.add(new SmartColorLamp(args[0], args[1], args[2], Integer.parseInt(args[3])));
                        }
                        else {
                            devices.add(new SmartColorLamp(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3])));
                        }
                        break;
                }
            }
            else {
                addOutputStr("ERROR: There is already a smart device with same name!\n");
            }
        }
        else {
            addOutputStr("ERROR: Erroneous command!\n");
        }
    }

    private void remove(String name) {
        if (nameExists(name)) {
            Device toBeRemoved = null;
            for (Device d : devices) {
                if (d.getName().equals(name)) {
                    if (d.getStatus().equals("On")) {
                        setSwitch(new String[] {name, "Off"});
                    }
                    addOutputStr("SUCCESS: Information about removed smart device is as follows:\n");
                    addOutputStr(d+"\n");
                    toBeRemoved = d;
                }
            }
            devices.remove(toBeRemoved);
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void setSwitchTime(String[] args) {
        if (nameExists(args[0])) {
            for (Device d : devices) {
                if (d.getName().equals(args[0])) {
                    d.setSwitchTime(LocalDateTime.parse(args[1], Device.FORMATTER));
                }
            }
            stableSort();
            if (LocalDateTime.parse(args[1], Device.FORMATTER).isEqual(Device.getCurrentTime())) {
                nop();
            }
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void setSwitch(String[] args) {
        if (nameExists(args[0])) {
            for (Device d : devices) {
                if (d.getName().equals(args[0])) {
                    if (d.getStatus().equals(args[1])) {
                        addOutputStr(String.format(("ERROR: This device is already switched %s!\n"),
                                d.getStatus().toLowerCase()));
                    }
                    else {
                        d.setStatus(args[1]);
                        d.setSwitchTime(null);
                    }
                }
            }
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void changeName(String[] args) {
        if (args[0].equals(args[1])) {
            addOutputStr("ERROR: Both of the names are the same, nothing changed!\n");
            return;
        }
        if (nameExists(args[0])) {
            if (nameExists(args[1])) {
                addOutputStr("ERROR: There is already a smart device with same name!\n");
            }
            else {
                for (Device d: devices) {
                    if (d.getName().equals(args[0])) {
                        d.setName(args[1]);
                    }
                }
            }
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void plugIn(String[] args) {
        if (nameExists(args[0])) {
            try {
                for (Device d : devices) {
                    if (d.getName().equals(args[0])) {
                        if (!((SmartPlug) d).isPlugged()) {
                            ((SmartPlug) d).setAmpere(Double.parseDouble(args[1]));
                            ((SmartPlug) d).setPlugged(true);
                        }
                        else {
                            addOutputStr("ERROR: There is already an item plugged in to that plug!\n");
                        }
                    }
                }
            }
            catch (ClassCastException e) {
                throw new CustomExceptions.SmartPlugCastException();
            }
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void plugOut(String name) {
        if (nameExists(name)) {
            try {
                for (Device d: devices) {
                    if (d.getName().equals(name)) {
                        if (((SmartPlug) d).isPlugged()) {
                            ((SmartPlug) d).setPlugged(false);
                        }
                        else {
                            addOutputStr("ERROR: This plug has no item to plug out from that plug!\n");
                        }
                    }
                }
            }
            catch (ClassCastException e) {
                throw new CustomExceptions.SmartPlugCastException();
            }

        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void setKelvin(String[] args) {
        if (nameExists(args[0])) {
            try {
                for (Device d : devices) {
                    if (d.getName().equals(args[0])) {
                        ((SmartLamp) d).setKelvin(Integer.parseInt(args[1]));
                    }
                }
            }
            catch (ClassCastException e) {
                throw new CustomExceptions.SmartLampCastException();
            }
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void setBrightness(String[] args) {
        if (nameExists(args[0])) {
            try {
                for (Device d: devices) {
                    if (d.getName().equals(args[0])) {
                        ((SmartLamp) d).setBrightness(Integer.parseInt(args[1]));
                    }
                }
            }
            catch (ClassCastException e) {
                throw new CustomExceptions.SmartLampCastException();
            }
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void setColorCode(String[] args) {
        if (nameExists(args[0])) {
            try {
                for (Device d: devices) {
                    if (d.getName().equals(args[0])) {
                        ((SmartColorLamp) d).setColorCode(args[1]);
                    }
                }
            }
            catch (ClassCastException e) {
                throw new CustomExceptions.SmartColorLampCastException();
            }
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void setWhite(String[] args) {
        if (nameExists(args[0])) {
            try {
                for (Device d: devices) {
                    if (d.getName().equals(args[0])) {
                        int oldKelvin = ((SmartLamp) d).getKelvin();
                        ((SmartLamp) d).setKelvin(Integer.parseInt(args[1]));
                        try {
                            ((SmartLamp) d).setBrightness(Integer.parseInt(args[2]));
                        }
                        catch (CustomExceptions.IllegalBrightnessException e) {
                            ((SmartLamp) d).setKelvin(oldKelvin);
                            throw e;
                        }
                        if (d instanceof SmartColorLamp) {
                            ((SmartColorLamp) d).setColorModeStatus("Off");
                        }
                    }
                }
            }
            catch (ClassCastException e) {
                throw new CustomExceptions.SmartLampCastException(); // ?????????
            }
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private void setColor(String[] args) {
        if (nameExists(args[0])) {
            try {
                for (Device d: devices) {
                    if (d.getName().equals(args[0])) {
                        String oldColorCode = ((SmartColorLamp) d).getColorCode();
                        ((SmartColorLamp) d).setColorCode(args[1]);
                        try {
                            ((SmartColorLamp) d).setBrightness(Integer.parseInt(args[2]));
                        }
                        catch (CustomExceptions.IllegalBrightnessException e) {
                            ((SmartColorLamp) d).setColorCode(oldColorCode);
                            throw e;
                        }
                        ((SmartColorLamp) d).setColorModeStatus("On");
                    }
                }
            }
            catch (ClassCastException e) {
                throw new CustomExceptions.SmartColorLampCastException();
            }
        }
        else {
            addOutputStr("ERROR: There is not such a device!\n");
        }
    }

    private boolean nameExists(String name) {
        boolean flag = false;
        for (Device d : devices) {
            if (d.getName().equals(name)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void stableSort() {
        Comparator<Device> comparator = Comparator.comparing(Device::getSwitchTime, Comparator.nullsLast(LocalDateTime::compareTo));
        devices.sort(comparator);
    }

}
