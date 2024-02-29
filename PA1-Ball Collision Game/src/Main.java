import java.util.Arrays;

public class Main {

    public static void main (String[] args) {
        String[] boardTxt = FileInput.readFile(args[0], false, false);
        String[] moveTxt = FileInput.readFile(args[1], false, false);
        String[] moves = moveTxt[0].split(" ");
        Game game = new Game(boardTxt);
        // Play turns until moves end or game ends
        for (int i = 0; i < moves.length; i++) {
            game.playTurn(moves[i]);
            if (game.isGameOver()) {
                // If game ends before all the moves are played, it updates moveTxt to just played moves
                moveTxt[0] = String.join(" ", Arrays.copyOfRange(moves, 0, i+1));
                break;
            }
        }
        String outputStr = game.generateOutputStr(boardTxt, moveTxt);
        FileOutput.writeToFile("output.txt", outputStr, false, false);
    }

}
