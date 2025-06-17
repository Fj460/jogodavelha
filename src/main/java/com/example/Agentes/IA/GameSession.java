package com.example.Agentes.IA;


public class GameSession {
    private String[][] board = new String[3][3];
    private String currentPlayer = "X";
    private String status = "PLAYING";

    public GameSession() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = "";
    }

    public String makeMove(String player, int x, int y) {
        if (!board[x][y].equals("") || !player.equals(currentPlayer)) return "Invalid move";

        board[x][y] = player;
        if (checkWin(player)) {
            status = player + " wins!";
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
        return "OK";
    }

    private boolean checkWin(String player) {
        for (int i = 0; i < 3; i++)
            if ((board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) ||
                    (board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player)))
                return true;
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }
    public void reset() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = "";
        currentPlayer = "X";
        status = "PLAYING";
    }




    public String[][] getBoard() {
        return board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getStatus() {
        return status;
    }
}
