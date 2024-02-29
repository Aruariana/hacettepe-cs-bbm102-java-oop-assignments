import java.time.format.DateTimeParseException;
import java.util.Arrays;


public class Main {

    /**
     * Main method of the program
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String[] input = FileInput.readFile(args[0], true, true);
        HomeOperator homeOperator = new HomeOperator();

        homeOperator.addOutputStr("COMMAND: " + input[0] + "\n");
        // Check the first command to continue or terminate the program
        if (!(input[0].split("\t")[0].equals("SetInitialTime") && input[0].split("\t").length == 2)) {
            homeOperator.addOutputStr("ERROR: First command must be set initial time! Program is going to terminate!\n");
            FileOutput.writeToFile(args[1], homeOperator.getOutputStr(), false, false);
            return;
        }
        else {
            try {
                homeOperator.setInitialTime(input[0].split("\t")[1]);
            }
            catch (DateTimeParseException e) {
                homeOperator.addOutputStr("ERROR: Format of the initial date is wrong! Program is going to terminate!\n");
                FileOutput.writeToFile(args[1], homeOperator.getOutputStr(), false, false);
                return;
            }
        }

        // Main loop
        for (String inputLine: (Arrays.copyOfRange(input, 1, input.length))) {
            homeOperator.executeCommand(inputLine);
        }
        // Add a ZReport if the last command is not ZReport
        if (!input[input.length-1].equals("ZReport")) {
            homeOperator.addOutputStr("ZReport:\n");
            homeOperator.zReport();
        }
        FileOutput.writeToFile(args[1], homeOperator.getOutputStr(), false, false);
    }

}
