import java.util.Arrays;

public class SudokuTester {
 public static void main(String[] args){
  //int[][] test = {{0,0,0,1,1,1,2,2,2},{0,0,0,1,1,1,2,2,2},{0,0,0,1,1,1,2,2,2},
  //  {3,3,3,4,4,4,5,5,5},{3,3,3,4,4,4,5,5,5},{3,3,3,4,4,4,5,5,5},
  //  {6,6,6,7,7,7,8,8,8},{6,6,6,7,7,7,8,8,8},{6,6,6,7,7,7,8,8,8}};
  //int[][] weird = {{0,0,1,1,2,2},{0,0,1,1,2,2},{3,3,4,4,5,5},{3,3,4,4,5,5},{6,6,7,7,8,8},{6,6,7,7,8,8}};
  //int[][] easySudoku = {{3,0,4,5,0,0,0,6,0},{1,0,8,2,6,3,0,0,9},{0,6,0,8,0,0,1,0,0},
  //  {0,4,0,6,0,8,0,0,0},{2,3,0,0,0,0,0,4,8},{0,0,0,7,0,2,0,9,0},
  //  {0,0,3,0,0,7,0,8,0},{7,0,0,4,8,9,3,0,6},{0,8,0,0,0,6,7,0,5}}; //solved
  //int[][] medSudoku = {{0,0,6,0,0,0,0,4,0},{0,5,2,4,0,7,0,3,0},{0,0,0,8,0,0,2,0,0},
  //  {0,9,1,3,0,0,5,0,4},{0,4,0,0,0,0,0,2,0},{6,0,8,0,0,2,7,1,0},
  //  {0,0,5,0,0,6,0,0,0},{0,2,0,7,0,9,6,5,0},{0,6,0,0,0,0,1,0,0}}; //solved
  //int[][] hardSudoku = {{3,0,0,0,2,0,0,0,9},{0,4,0,0,0,1,5,0,0},{0,2,1,0,9,3,0,0,0},
  //  {0,0,0,6,0,0,9,0,0},{1,5,0,0,0,0,0,6,4},{0,0,2,0,0,8,0,0,0},
  //  {0,0,0,2,1,0,4,9,0},{0,0,8,3,0,0,0,7,0},{2,0,0,0,7,0,0,0,6}}; //got one num
  //int[][] num120 = {{9,0,0,0,2,0,0,0,3},{0,8,0,4,0,6,0,5,0},{0,0,4,0,0,0,1,0,0},
  //  {0,1,0,0,9,0,0,3,0},{3,0,0,5,0,1,0,0,9},{0,5,0,0,8,0,0,7,0},
  //  {0,0,1,0,0,0,2,0,0},{0,7,0,9,0,5,0,8,0},{4,0,0,0,3,0,0,0,7}}; //solved with row elimination
  //int[][] num121 = {{3,5,0,0,0,0,0,2,9},{4,9,0,0,0,0,0,3,1},{0,0,6,2,0,3,8,0,0},
  //  {0,0,1,9,0,2,4,0,0},{0,0,0,0,0,0,0,0,0},{0,0,3,5,0,7,2,0,0},
  //  {0,0,7,4,0,5,9,0,0},{9,6,0,0,0,0,0,1,7},{8,2,0,0,0,0,0,6,4}}; //solved
  //int[][] num146 = {{0,0,7,4,5,0,0,0,0},{0,8,0,0,0,1,2,0,0},{9,0,0,0,0,0,0,3,0},
  //  {8,0,0,0,0,0,0,6,0},{0,0,6,7,4,8,9,0,0},{0,5,0,0,0,0,0,0,7},
  //  {0,9,0,0,0,0,0,0,4},{0,0,2,3,0,0,0,7,0},{0,0,0,0,6,9,8,0,0}};
  //int[][] num148 = {{0,0,3,0,0,0,9,0,0},{0,0,0,8,3,4,0,0,0},{5,0,0,0,0,0,0,0,7},
  //  {0,7,0,5,0,8,0,3,0},{0,4,0,0,1,0,0,9,0},{0,3,0,9,0,6,0,2,0},
  //  {7,0,0,0,0,0,0,0,6},{0,0,0,6,2,1,0,0,0},{0,0,1,0,0,0,5,0,0}}; //solved with one guess level
  //int[][] num148partsolved = {{0,0,3,0,0,0,9,0,0},{0,0,7,8,3,4,1,5,2},{5,0,0,0,0,0,3,0,7},
  //  {0,7,0,5,4,8,6,3,1},{8,4,6,0,1,0,7,9,5},{1,3,5,9,7,6,0,2,0},
  //  {7,0,0,0,0,0,2,1,6},{0,5,0,6,2,1,0,7,0},{0,0,1,0,0,0,5,0,0}}; //nothing
  //int[][] num150 = {{0,0,0,6,0,5,0,0,0},{0,0,4,0,0,0,5,0,0},{0,7,0,3,0,1,0,8,0},
  //  {2,0,3,0,0,0,6,0,9},{0,0,0,0,0,0,0,0,0},{4,0,6,0,0,0,2,0,8},
  //  {0,1,0,2,0,8,0,9,0},{0,0,2,0,0,0,1,0,0},{0,0,0,7,0,3,0,0,0}};//not solved with elimination
  //int[][] num151 = {{6,0,0,0,0,0,0,0,8},{0,9,0,0,2,0,0,4,0},{0,0,7,3,0,5,2,0,0},
  //  {0,0,3,6,0,8,4,0,0},{0,4,0,0,0,0,0,7,0},{0,0,6,5,0,1,9,0,0},
  //  {0,0,8,9,0,2,5,0,0},{0,3,0,0,8,0,0,2,0},{2,0,0,0,0,0,0,0,9}}; //not solved with elimination
  //int[][] num175 = {{0,8,0,0,0,0,3,0,0},{1,2,3,4,5,0,0,9,0},{0,9,0,0,6,0,0,0,5},
  //  {0,4,0,7,0,0,0,0,0},{0,6,0,0,0,0,8,5,0},{0,0,0,0,0,3,0,0,1},
  //  {0,0,0,0,1,0,0,0,3},{0,0,0,0,0,0,0,2,0},{0,0,4,0,0,2,1,0,0}}; //solved with one guess level
  //int[][] evil = {{3,2,0,0,6,8,0,0,0},{0,0,0,2,0,0,0,0,0},{9,0,0,7,0,5,4,0,0},
  //  {0,5,8,0,0,0,0,2,0},{0,0,0,0,2,0,0,0,0},{0,4,0,0,0,0,5,7,0},
  //  {0,0,9,8,0,1,0,0,4},{0,0,0,0,0,6,0,0,0}, {0,0,0,3,7,0,0,5,1}}; //solved with (many) returns to previous guess level guessing
  //int[][] evil2 = {{0,0,0,1,0,0,7,0,0},{0,9,0,6,0,2,0,0,0},{0,4,7,0,0,0,0,0,0},
  //  {9,0,8,0,0,0,1,4,0},{0,5,0,0,9,0,0,8,0},{0,1,6,0,0,0,3,0,5},
  //  {0,0,0,0,0,0,2,3,0},{0,0,0,9,0,8,0,6,0},{0,0,2,0,0,6,0,0,0}}; //solved with two guess levels
  //int[][] num204 = {{1,0,0,3,0,0,9,8,0},{0,2,0,0,0,1,0,0,7},{0,0,3,0,2,0,0,0,6},
  //  {0,4,0,0,0,3,0,0,0},{0,0,5,0,0,0,4,0,0},{0,0,0,6,0,0,0,5,0},
  //  {3,0,0,0,7,0,6,0,0},{2,0,0,8,0,0,0,7,0},{0,1,9,0,0,6,0,0,8}}; //solved with one guess level
  //int[][] evil3 = {{4,0,0,0,0,0,6,0,0},{8,0,0,0,0,4,2,0,0},{9,2,0,6,0,0,1,0,0},
  //  {0,0,0,8,0,2,0,0,3},{0,0,0,9,0,6,0,0,0},{7,0,0,4,0,1,0,0,0},
  //  {0,0,3,0,0,5,0,1,8},{0,0,9,1,0,0,0,0,7},{0,0,4,0,0,0,0,0,6}}; //solved with two guess levels
  //int[][] evil4 = {{4,0,2,5,0,0,3,0,0},{0,0,0,0,4,0,0,0,0},{0,0,3,0,0,0,9,0,5},
  //  {0,0,0,0,7,0,8,0,1},{0,8,0,0,3,0,0,6,0},{3,0,4,0,1,0,0,0,0},
  //  {9,0,1,0,0,0,4,0,0},{0,0,0,0,6,0,0,0,0},{0,0,8,0,0,7,1,0,2}}; //solved with two guess levels
  int[][] evil5 = {{0,0,0,0,0,7,1,8,0},{0,0,0,3,1,0,0,2,0},{0,0,0,4,0,0,7,0,3},
    {0,0,7,0,6,0,0,0,0},{8,0,4,0,0,0,6,0,7},{0,0,0,0,3,0,5,0,0},
    {3,0,9,0,0,2,0,0,0},{0,4,0,0,9,5,0,0,0},{0,2,5,8,0,0,0,0,0}}; //solved with reguessing
  //int[][] evil6 = {{7,0,0,4,3,0,0,0,0},{0,3,0,0,7,6,0,0,0},{2,9,0,0,0,1,0,0,0},
  //  {0,0,2,0,1,0,0,0,5},{0,0,4,0,0,0,6,0,0},{5,0,0,0,4,0,3,0,0},
  //  {0,0,0,3,0,0,0,8,1},{0,0,0,1,6,0,0,5,0},{0,0,0,0,5,4,0,0,3}}; //solved with one guess
  int[][] evil7 = {{0,0,1,0,0,9,0,7,0},{6,0,9,0,0,0,2,0,8},{0,0,7,0,0,8,0,0,0},
    {0,7,0,0,4,0,0,0,6},{0,0,0,3,0,1,0,0,0},{3,0,0,0,7,0,0,2,0},
    {0,0,0,5,0,0,3,0,0},{2,0,6,0,0,0,9,0,1},{0,5,0,1,0,0,7,0,0}}; //solved with one guess
  int[][] hardest = {{8,0,0,0,0,0,0,0,0},{0,0,3,6,0,0,0,0,0},{0,7,0,0,9,0,2,0,0},
    {0,5,0,0,0,7,0,0,0},{0,0,0,0,4,5,7,0,0},{0,0,0,1,0,0,0,3,0},
    {0,0,1,0,0,0,0,6,8},{0,0,8,5,0,0,0,1,0},{0,9,0,0,0,0,4,0,0}}; //solved with a crap-ton of guessing
  //Sudoku one = new Sudoku(evil5,3);
  //one.print();
  //one.solve(true); //true means solve with guessing
  //System.out.println(" ");
  //one.print();
  
  //Sudoku a = new Sudoku();
  //a.createSudoku();
  //a.print();
  
  Game g = new Game();
  
  /*Sudoku blank;
  int total = 0;
  for(int i=0; i<100; i++){
   blank = new Sudoku();
   total += blank.getCount();
  }
  System.out.println((double)total/100);*/
  
  /*int[][] mm = {{0,0,0},{1,1,1},{2,2,2}};
  Block b = new Block(mm);
  b.print();
  mm[0][0] = 10;
  b.print();*/
  
  /*int[] m = {1,2,3,4};
  ArrayTest a = new ArrayTest(m);
  System.out.println(a);
  m[0] = 9;
  System.out.println(a);*/
  
 }
}
