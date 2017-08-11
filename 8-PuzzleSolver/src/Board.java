/*****************************************************************************
 *  Author:         Yan Xu
 *  Written:        9/29/2016
 *  Last updated:   9/30/2016
 *
 *  Compilation:    javac Board.java;
 *  Execution:      java Board;
 *  Dependency:     Stack.java
 *
 *  Board is to solve the 8-puzzle problem using the A* search algorithm
 *****************************************************************************/

import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[] board;   // represents a n-by-n board using 1D array
    private int n;
    /**
     * constructor: construct a board from an n-by-n array of blocks
     *
     * @param blocks: an n-by-n int array of blocks
     */
    public Board(int[][] blocks){
        n = blocks.length;
        board = new int[n * n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                board[n * i + j] = blocks[i][j];
            }
        }
    }

    // return board dimension
    public int dimension(){
        return n;
    }

    // return number of blocks out of place
    public int hamming(){
        int count = 0;
        for(int i = 0; i < n * n - 1; i++){
            if(board[i] != (i+1)) count++;
        }
        return count;
    }

    // return sum of Manhattan distances between blocks and goal
    public int manhattan(){
        int count = 0;
        for(int i = 0; i < n * n; i++){
            if(board[i] != 0) {
                count += Math.abs((board[i] - 1) % n - (i % n)) + Math.abs((board[i] - 1) / n- (i / n));
            }
        }
        return count;
    }

    // return whether this board is the goal board
    public boolean isGoal(){
        if(board[n * n - 1] != 0) return false;
        for(int i = 0; i < n * n - 1; i++){
            if(board[i] != i + 1) return false;
        }
        return true;
    }

    // return a board that is obtained by exchanging any pair of blocks
    public Board twin(){
        int[][] twin_block = new int[n][n];
        int[] twin = new int[n * n];
        for(int i = 0; i < n * n; i++){
            twin[i] = board[i];
        }
        // exchange any pair of non-zero blocks
        if(twin[1] != 0){
            if(twin[2] != 0){
                int x = twin[1];
                twin[1] = twin[2];
                twin[2] = x;
            }else{
                int x = twin[3];
                twin[3] = twin[1];
                twin[1] = x;
            }
        }else{
            int x = twin[2];
            twin[2] = twin[3];
            twin[3] = x;
        }
        // build the n-by-n array of the twin board
        for(int i = 0; i < n * n; i++){
            twin_block[i / n][i % n] = twin[i];
        }
        return new Board(twin_block);
    }
    // return whether this board equals to y
    public boolean equals(Object y){
        if(!(y instanceof Board)) return false;
        if(this.dimension() != ((Board) y).dimension()) return false;
        for(int i = 0; i < n * n; i++){
            if(this.board[i] != ((Board) y).board[i]) return false;
        }
        return true;
    }
    // get all neighboring boards
    public Iterable<Board> neighbors(){
        Stack<Board> stack = new Stack<Board>();
        // insert all the neighboring board in to the stack
        int blank_x = n;
        int blank_y = n;
        for(int i = 0; i < n * n; i++){
            if(board[i] == 0){
                blank_x = i / n;
                blank_y = i % n;
            }
        }
        // blank square are not top edge
        if(blank_x > 0){
            int[][] copy = this.getcopy();
            copy[blank_x][blank_y] = copy[blank_x - 1][blank_y];
            copy[blank_x - 1][blank_y] = 0;
            stack.push(new Board(copy));
        }
        // blank square are not bottom edge
        if(blank_x < n - 1){
            int[][] copy = this.getcopy();
            copy[blank_x][blank_y] = copy[blank_x + 1][blank_y];
            copy[blank_x + 1][blank_y] = 0;
            stack.push(new Board(copy));
        }
        // blank square are not left edge
        if(blank_y > 0){
            int[][] copy = this.getcopy();
            copy[blank_x][blank_y] = copy[blank_x][blank_y - 1];
            copy[blank_x][blank_y - 1] = 0;
            stack.push(new Board(copy));
        }
        // blank square are not right edge
        if(blank_y < n - 1){
            int[][] copy = this.getcopy();
            copy[blank_x][blank_y] = copy[blank_x][blank_y + 1];
            copy[blank_x][blank_y + 1] = 0;
            stack.push(new Board(copy));
        }
        return stack;
    }

    // copy the current board
    private int[][] getcopy(){
        int[][]result = new int[n][n];
        for(int i = 0; i < n * n; i++){
            result[i / n][i % n] = board[i];
        }
        return result;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBuilder.append(String.format("%d ", board[i * n + j]));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
