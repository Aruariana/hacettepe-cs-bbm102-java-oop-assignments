public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        // Read the lines of the input txt into a string array where each element is a line
        String[] inputStr = FileInput.readFile(args[0], false, false);
        for (String line : inputStr) {
            try {
                manager.executeCommand(line);
            }
            catch (Exception e) { // If an unexpected error happens, this will prevent the program from terminating
                manager.addOutputStr("Some kind of error happened here\n");
            }
        }
        FileOutput.writeToFile(args[1], manager.getOutputStr().trim(), false, false);
    }
}
