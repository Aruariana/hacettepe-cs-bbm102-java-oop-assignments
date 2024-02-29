public class CustomExceptions {

    public static class IllegalAmpereException extends RuntimeException {
        public IllegalAmpereException() {
            super("ERROR: Ampere value must be a positive number!\n");
        }
    }

    public static class IllegalMegabyteException extends RuntimeException {
        public IllegalMegabyteException() {
            super("ERROR: Megabyte value must be a positive number!\n");
        }
    }

    public static class IllegalStatusException extends RuntimeException {
        public IllegalStatusException() {
            super("ERROR: Erroneous command!\n");
        }
    }

    public static class IllegalKelvinException extends RuntimeException {
        public IllegalKelvinException() {
            super("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
        }
    }

    public static class IllegalBrightnessException extends RuntimeException {
        public IllegalBrightnessException() {
            super("ERROR: Brightness must be in range of 0%-100%!\n");
        }
    }

    public static class ColorCodeOutOfBoundsException extends RuntimeException {
        public ColorCodeOutOfBoundsException() {
            super("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
        }
    }

    public static class IllegalColorCodeException extends RuntimeException {
        public IllegalColorCodeException() {
            super("ERROR: Erroneous command!\n");
        }
    }

    public static class SmartPlugCastException extends RuntimeException {
        public SmartPlugCastException() {
            super("ERROR: This device is not a smart plug!\n");
        }
    }

    public static class SmartLampCastException extends RuntimeException {
        public SmartLampCastException() {
            super("ERROR: This device is not a smart lamp!\n");
        }
    }

    public static class SmartColorLampCastException extends RuntimeException {
        public SmartColorLampCastException() {
            super("ERROR: This device is not a smart color lamp!\n");
        }
    }

    public static class ReverseTimeException extends RuntimeException {
        public ReverseTimeException() {
            super("ERROR: Time cannot be reversed!\n");
        }
    }

    public static class InvalidTimeFormatException extends RuntimeException {
        public InvalidTimeFormatException() {
            super("ERROR: Time format is not correct!\n");
        }
    }

    public static class NothingToSwitchException extends RuntimeException {
        public NothingToSwitchException() {
            super("ERROR: There is nothing to switch!\n");
        }
    }

    public static class InvalidSwitchTimeException extends RuntimeException {
        public InvalidSwitchTimeException() {
            super("ERROR: Switch time cannot be in the past!\n");
        }
    }

}
