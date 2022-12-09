package puzzle;
/******************************************************************************
 *  Compilation:  javac-algs4 PuzzleChecker.java
 *  Execution:    java-algs4 PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java-algs4 PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleChecker {

    public static void main(String[] args) {

        // for each command-line argument
//        String[] argms = new String[20];
//        argms[0] = "C://Users//amrpa//Downloads//8puzzle//puzzle07.txt";
//        argms[1] = "C://Users//amrpa//Downloads//8puzzle//puzzle15.txt";
//        argms[2] = "C://Users//amrpa//Downloads//8puzzle//puzzle14.txt";
//        argms[3] = "C://Users//amrpa//Downloads//8puzzle//puzzle08.txt";
//        argms[4] = "C://Users//amrpa//Downloads//8puzzle//puzzle09.txt";
//        argms[5] = "C://Users//amrpa//Downloads//8puzzle//puzzle10.txt";
//        argms[6] = "C://Users//amrpa//Downloads//8puzzle//puzzle11.txt";
//        argms[7] = "C://Users//amrpa//Downloads//8puzzle//puzzle12.txt";
//        argms[8] = "C://Users//amrpa//Downloads//8puzzle//puzzle13.txt";
//        argms[9] = "C://Users//amrpa//Downloads//8puzzle//puzzle15.txt";
//        argms[10] = "C://Users//amrpa//Downloads//8puzzle//puzzle16.txt";
//        argms[11] = "C://Users//amrpa//Downloads//8puzzle//puzzle17.txt";
//        argms[12] = "C://Users//amrpa//Downloads//8puzzle//puzzle18.txt";
//        argms[13] = "C://Users//amrpa//Downloads//8puzzle//puzzle19.txt";
//        argms[14] = "C://Users//amrpa//Downloads//8puzzle//puzzle20.txt";
//        argms[15] = "C://Users//amrpa//Downloads//8puzzle//puzzle21.txt";
//        argms[16] = "C://Users//amrpa//Downloads//8puzzle//puzzle22.txt";
//        argms[17] = "C://Users//amrpa//Downloads//8puzzle//puzzle23.txt";
//        argms[18] = "C://Users//amrpa//Downloads//8puzzle//puzzle24.txt";
//        argms[19] = "C://Users//amrpa//Downloads//8puzzle//puzzle25.txt";
//        
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            System.out.println(initial.toString());
            System.out.println(initial.hamming()+" "+ initial.manhattan());
            Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + solver.moves());
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }
        }
    }
}
