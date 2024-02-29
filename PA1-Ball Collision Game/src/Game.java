public class Game {

    private int boardX;
    private int boardY;
    private String[][] board;
    private int ballX;
    private int ballY;
    private int destX;
    private int destY;
    private int score = 0;
    private boolean gameOver = false;


    public Game(String[] boardTxt) {
        boardX = boardTxt.length;
        boardY = boardTxt[0].split(" ").length;
        board = new String[boardX][boardY];
        generateBoard(boardTxt);
        findBallCor();
        destX = ballX;
        destY = ballY;
    }

    public void playTurn(String direction) {
        // The parameter direction is a single char of direction like "U", "D", "L", "R"
        // I only set the destination to firstly check what is there to swap with the ball
        setDestination(direction);
        switch (board[destX][destY]) {
            // If there is a wall, I set the destination to original and call playTurn to the opposite direction
            case "W":
                switch (direction) {
                    case "U":
                        setDestination("D");
                        playTurn("D");
                        break;
                    case "D":
                        setDestination("U");
                        playTurn("U");
                        break;
                    case "L":
                        setDestination("R");
                        playTurn("R");
                        break;
                    case "R":
                        setDestination("L");
                        playTurn("L");
                        break;
                }
                break;
            case "H":
                // If there is a hole, I set the gameOver flag to true
                board[ballX][ballY] = " ";
                gameOver = true;
                break;
            // If there is R, Y or B, I set them to char X and add the corresponding values to score then move the ball
            case "R":
                board[destX][destY] = "X";
                score += 10;
                moveBall();
                break;
            case "Y":
                board[destX][destY] = "X";
                score += 5;
                moveBall();
                break;
            case "B":
                board[destX][destY] = "X";
                score -= 5;
                moveBall();
                break;
            default:
                // If there is no special char I simply move ball
                moveBall();
                break;
        }
    }

    public String generateOutputStr(String[] boardTxt, String[] moveTxt) {
        String returnStr = "Game board:\n";
        returnStr += String.join("\n", boardTxt) + "\n\n";
        returnStr += "Your movement is:\n";
        returnStr += moveTxt[0] + "\n\n";
        returnStr += "Your output is:\n";
        returnStr += graphBoard() + "\n";
        if (isGameOver()) returnStr += "Game Over!\n";
        returnStr += "Score: " + score;
        return returnStr;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void setDestination(String direction) {
        // Depending on the direction sets destination positions
        switch (direction) {
            case "U":
                destX = (ballX - 1 + boardX) % boardX;
                break;
            case "D":
                destX = (ballX + 1 + boardX) % boardX;
                break;
            case "L":
                destY = (ballY - 1 + boardY) % boardY;
                break;
            case "R":
                destY = (ballY + 1 + boardY) % boardY;
                break;
        }
    }

    private void moveBall() {
        // Swaps ball's position with its destination position which is calculated by setDestination method
        String temp = board[ballX][ballY];
        board[ballX][ballY] = board[destX][destY];
        board[destX][destY] = temp;
        // Updates ballX and ballY
        ballX = destX;
        ballY = destY;
    }

    private void generateBoard(String[] boardTxt) {
        // Assigns 2d array board
        for (int i = 0; i < boardX; i++) {
            String[] balls = boardTxt[i].split(" ");
            System.arraycopy(balls, 0, board[i], 0, boardY);
        }
    }

    private void findBallCor() {
        // Finds the ball's coordinates and assigns ballX and ballY
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                if (board[i][j].equals("*")) {
                    ballX = i;
                    ballY = j;
                    return;
                }
            }
        }
    }

    private String graphBoard() {
        // It returns a string format of
        String returnStr = "";
        for (String[] line : board) {
            returnStr += String.join(" ", line);
            returnStr += "\n";
        }
        return returnStr;
    }
}
