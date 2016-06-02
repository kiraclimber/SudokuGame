import java.util.ArrayList;
import java.util.Arrays;

public class Sudoku {
 static int count = 0;
 static Sudoku[] puzzles = new Sudoku[50]; //keeps list of all sudokus created while guessing
 static Guess[][] guesses = new Guess[50][3]; //keeps track of all guesses - each row has guesses for one space
 private int[][] sudoku;
 private Block[][] blocks;
 private int blockWidth; //size of a block
 private Line[] rows;
 private Line[] cols;
 
 public Sudoku(){ //creates standard 9x9 completed sudokos
  //initialize everything
  count = 0;
  puzzles = new Sudoku[50];
  guesses = new Guess[50][3];
  blockWidth = 3;
  sudoku = new int[9][9]; //initialized with all zeros
  blocks = new Block[3][3];
  rows = new Line[9];
  cols = new Line[9];
  int[][] block = new int[3][3];
  for(int i=0; i<3; i++){
   for(int j=0; j<3; j++){
    blocks[i][j] = new Block(block);
   }
  }
  int[] line = new int[9];
  for(int i=0; i<9; i++){
   rows[i] = new Line(line);
   cols[i] = new Line(line);
  }
  //randomly fill first row with numbers 1-9 (in sudoku fashion)
  int rand;
  ArrayList<Integer> possible;
  for(int col=0; col<sudoku[0].length; col++){
   possible = getPossible(0,col);
   rand = (int) (Math.random()*possible.size());
   sudoku[0][col] = possible.get(rand);
   set(0,col,possible.get(rand));
  }
  print();
  solve(true); //now fill in the rest of the sudoku (true because guessing will obviously be necessary)
  print();
  System.out.println("number of guesses where there were 4+ possibilities: " + count);
 }
 
 public int getCount(){
  return count;
 }
 
 public int create(){ //uses greedy algorithm to create a completed sudoku from a blank board, works approximately .3% of the time
  int rand;
  ArrayList<Integer> possible;
  for(int row=0; row<sudoku.length; row++){
   for(int col=0; col<sudoku[0].length; col++){
    possible = getPossible(row, col);
    if(possible.size() == 0){
     return 0;
    }
    rand = (int) (Math.random()*possible.size());
    sudoku[row][col] = possible.get(rand);
    set(row,col,possible.get(rand));
   }
  }
  return 1;
 }
 
 //input sudokus into this constructor if they need to be solved
 public Sudoku(int[][] puzzle, int numOfBlocksAcross){ //total blocks = numOfBlocksAcross^2
  //copies puzzle into sudoku - this way puzzle doesn't change if sudoku is altered (useful for guessing)
  sudoku = new int[puzzle.length][puzzle[0].length];
  for(int row=0; row<puzzle.length; row++){
   for(int col=0; col<puzzle[0].length; col++){
    sudoku[row][col] = puzzle[row][col];
   }
  }
  blocks = new Block[numOfBlocksAcross][numOfBlocksAcross];
  blockWidth = puzzle[0].length/numOfBlocksAcross;
  rows = new Line[puzzle.length];
  cols = new Line[puzzle.length];
  //put values in rows
  for(int row = 0; row<puzzle.length; row++){
   rows[row] = new Line(sudoku[row]);
  }
  //put values in cols
  int[] column = new int[puzzle.length];
  for(int col=0; col<puzzle.length; col++){
   for(int row=0; row<puzzle.length; row++){
    column[row] = sudoku[row][col];
   }
   cols[col] = new Line(column);
  }
  //create blocks array
  int[][] block = new int[blockWidth][blockWidth];
  for(int i = 0; i<blocks.length; i++){
   for(int j=0; j<blocks[i].length; j++){
    for(int row=i*blockWidth; row<i*blockWidth+blockWidth; row++){
     for(int col = j*blockWidth; col<j*blockWidth+blockWidth; col++){
      block[row%blockWidth][col%blockWidth] = sudoku[row][col];
     }
    }
    blocks[i][j] = new Block(block);
   }
  }
 }
 
 public int[][] getPuzzle(){
  return sudoku;
 }
 
 public String toString(){
  return Arrays.deepToString(sudoku);
 }
 
 public void print(){ //prints it out looking like a sudoku
  for(int row=0; row<sudoku.length; row++){
   for(int col=0; col<sudoku[0].length; col++){
    System.out.print(sudoku[row][col] + " ");
   }
   System.out.println();
  }
 }
 
 public void createSudoku(){ //creates a sudoku (from a completed board) that can be solved without guessing
  int[][] puzzlecopy =  new int[sudoku.length][sudoku[0].length]; //needed??
  for(int row=0; row<sudoku.length; row++){
   for(int col=0; col<sudoku[0].length; col++){
    puzzlecopy[row][col] = sudoku[row][col];
   }
  }
  int randrow, randcol, prevnum, prevnum2;
  boolean complete = true; //want to make sure board is still solvable after every removal
  int i=0; //counts the number of times the board wasn't solvable - each time the previous guess is removed and you try again
  while(complete || i<5){
   randrow = (int) (sudoku.length*Math.random());
   randcol = (int) (sudoku[0].length*Math.random());
   //System.out.println("row = " + randrow + " col = " + randcol);
   prevnum = sudoku[randrow][randcol]; //stores the number you took out
   prevnum2 = sudoku[8-randrow][8-randcol]; //stores the other number you took out
   if(randrow == 4 && randcol == 4){ //middle square
    unset(randrow, randcol);
   }
   else{
    unset(randrow, randcol);
    unset(8-randrow, 8-randcol);
   }
   puzzles[0] = new Sudoku(sudoku, 3); //store new sudoku (with num removed) in puzzles so once it is solved you still have the original board
   puzzles[0].solve(false); //try to solve puzzle without guessing
   complete = puzzles[0].complete(); //if true then sudoku is still solvable via simple techniques (not guessing)
   if(!complete){
    i++; //only get 5 failed attempts
set(randrow, randcol, prevnum); //reset previous number
set(8-randrow, 8-randcol, prevnum2); //reset previous number
   }
  }
 }
 
 private ArrayList<Integer> getNums(int row, int col){ //returns possibilities for num based on row/col
  ArrayList<Integer> totalpos = new ArrayList<Integer>(); //all nums (1-9) then remove ones already in row/col
  if(sudoku[row][col] != 0){
   return totalpos;
  }
  for(int i=1; i<=blockWidth*blockWidth; i++){
   totalpos.add(i);
  }
  for(int c=0; c<sudoku[row].length; c++){ //see what nums are in that row & remove them from totalpos
   if(sudoku[row][c] != 0){
    totalpos.remove(totalpos.indexOf(sudoku[row][c]));
   }
  }
  for(int r=0; r<sudoku.length; r++){ //see what nums are in that column & remove them from totalpos
   if(sudoku[r][col] != 0 && totalpos.indexOf(sudoku[r][col])!=-1){
    totalpos.remove(totalpos.indexOf(sudoku[r][col]));
   }
  }
  return totalpos;
 }
 
 private ArrayList<Integer> combine(ArrayList<Integer> a, ArrayList<Integer> b){ //returns elements in both lists
  ArrayList<Integer> same = new ArrayList<Integer>();
  for(int i=0; i<a.size(); i++){
   if(b.indexOf(a.get(i)) != -1){
    same.add(a.get(i));
   }
  }
  return same; //possibilites for num with regard to block and row/col
 }
 
 private ArrayList<Integer> getPossible(int row, int col){ //returns only possible nums for a space
  ArrayList<Integer> linepos = getNums(row,col); //possible numbers based on other nums in row/col
  ArrayList<Integer> blockpos = blocks[row/blockWidth][col/blockWidth].getNums(); //possible numbers based on other nums in block
  ArrayList<Integer> same = combine(linepos, blockpos); //possible numbers based on row/col and block 
  return same;
 }
 
 private boolean eliminationRow(int row){ //sees if there is a number needed in a row that can only go one place
  boolean changed = false; //true if sudoku has been altered (num added)
  ArrayList<Integer> needNums = rows[row].getNums();
  ArrayList<Integer> possibleSpots;
  ArrayList<Integer> possibleNums;
  for(int i=0; i<needNums.size(); i++){ //loops through nums the row needs
   possibleSpots = new ArrayList<Integer>();
   for(int col=0; col<sudoku.length; col++){ //loops through all places that num could go
possibleNums = getPossible(row,col); //possible numbers that could go in that place
if(possibleNums.indexOf(needNums.get(i)) != -1){ //number needed in row could go here
     possibleSpots.add(col);
    }
   }
   if(possibleSpots.size() == 1){ //number needed in row can only go here
//System.out.println("ELIMROW: set column " + possibleSpots.get(0) + " row " + row + " to " + needNums.get(i));
    set(row, possibleSpots.get(0), needNums.get(i));
    changed = true;
   }
  }
  return changed;
 }
 
 private boolean eliminationCol(int col){ //sees if there is a number needed in a column that could only go one place
  boolean changed = false; //true if sudoku has been altered (num added)
  ArrayList<Integer> needNums = cols[col].getNums();
  ArrayList<Integer> possibleSpots;
  ArrayList<Integer> possibleNums;
  for(int i=0; i<needNums.size(); i++){ //loops through nums the column needs
   possibleSpots = new ArrayList<Integer>();
   for(int row=0; row<sudoku.length; row++){ //loops through all places that number could go
possibleNums = getPossible(row,col); //possible numbers that could go in that place
if(possibleNums.indexOf(needNums.get(i)) != -1){ //number needed in column could go here
     possibleSpots.add(row);
    }
   }
   if(possibleSpots.size() == 1){ //number needed in column can only go in one spot
//System.out.println("ELIMCOL: set column " + col + " row " + possibleSpots.get(0) + " to " + needNums.get(i));
    set(possibleSpots.get(0), col, needNums.get(i));
    changed = true;
   }
  }
  return changed;
 }
 
 private boolean eliminationBlock(int blockrow, int blockcol){ //sees if there is num needed in block that can only go in one place
  boolean changed = false; //true if num is added
  ArrayList<Integer> needNums = blocks[blockrow][blockcol].getNums(); //nums needed in block
  ArrayList<Integer> possibleSpots; //row col (2 digit num)
  ArrayList<Integer> possibleNums;
  for(int i=0; i<needNums.size(); i++){ //loops through possible nums
   possibleSpots = new ArrayList<Integer>();
   for(int row=blockrow*blockWidth; row<blockrow*blockWidth+blockWidth; row++){ //loops through spaces in block
for(int col=blockcol*blockWidth; col<blockcol*blockWidth+blockWidth; col++){
 possibleNums = getPossible(row,col); //gets all possible nums for spot
 if(possibleNums.indexOf(needNums.get(i)) != -1){ //num needed could go in this spot
      possibleSpots.add(10*row + col);
     }
    }
   }
   if(possibleSpots.size() == 1){ //num needed can only go in one spot
//System.out.println("ELIMBLOCK: set column " + possibleSpots.get(0)%10 + " row "
//  + possibleSpots.get(0)/10 + " to " + needNums.get(i));
set(possibleSpots.get(0)/10, possibleSpots.get(0)%10, needNums.get(i)); //sets spot to num
    changed = true;
   }
  }
  return changed;
 }
 
 private void set(int row, int col, int num){ //changes a number in the sudoku
  sudoku[row][col] = num;
  blocks[row/blockWidth][col/blockWidth].setNum(row%blockWidth, col%blockWidth, num);
  rows[row].setNum(col, num);
  cols[col].setNum(row, num);
 }
 
 private void unset(int row, int col){ //changes number in sudoku to zero
  int prev = sudoku[row][col];
  //System.out.println("previous int = " + prev);
  sudoku[row][col] = 0;
  blocks[row/blockWidth][col/blockWidth].unsetNum(row%blockWidth, col%blockWidth, prev);
  rows[row].unsetNum(col, prev);
  cols[col].unsetNum(row, prev);
 }
 
 
 private boolean complete(){ //true if the sudoku is complete
  for(int row=0; row<sudoku.length; row++){
   for(int col=0; col<sudoku[0].length; col++){
    if(sudoku[row][col] == 0){
     return false;
    }
   }
  }
  return true;
 }
 
 
 private void guess(int[][] puzzle){ //creates new sudoku with one number guessed - then try to solve
  int[][] guessednum = new int[puzzle.length][puzzle[0].length];
  for(int row=0; row<puzzle.length; row++){ //copy puzzle over
   for(int col=0; col<puzzle[0].length; col++){
    guessednum[row][col] = puzzle[row][col];
   }
  }
  int r = 0; 
  int c = 0;
  while(r<9 && (sudoku[r][c] != 0 || getPossible(r,c).size()>3)){ //finds first zero with 3 or fewer possibilities in the puzzle, will guess that number
   if(c<sudoku[0].length-1){
    c++;
   }
   else{
    c=0;
    r++;
   }
  }
  if(r==9){ //there was no space with 3 or fewer possibilities
   System.out.print("4+ ");
   count++;
   r=0;
   c=0;
   while(sudoku[r][c] != 0){ //finds first zero in the puzzle, will guess that number
    if(c<sudoku[0].length-1){
     c++;
    }
    else{
     c=0;
     r++;
    }
   }
  }
  int failed = 0;
  int row = 0;
  int i=0;
  Sudoku guess;
  ArrayList<Integer> possible = getPossible(r,c);
  while(failed == 0){ //wrong guess, increment index to try all of the possible nums
   if(i == possible.size()){ //previous guess must have been wrong, go back and increment that index by 1
System.out.println("change previous guess");
    break;
   }
   guessednum[r][c] = possible.get(i);
   if(i == 0){ //find first new row in guesses and puzzles to put data there
for(int k=0; k<guesses.length; k++){
 if(guesses[k][0] == null){ //first new row
  row = k;
  break;
 }
}
puzzles[row] = new Sudoku(puzzle,3); //saves the old sudoku in puzzles
   }
   guesses[row][i] = new Guess(r,c,i); //row, column, index
   System.out.println("GUESS: row = " + r + " col = " + c + " num = " + possible.get(i));
   guess = new Sudoku(guessednum, 3);
   failed = guess.solve(true); //try to solve new sudoku (0=failed, 1=need to guess again, 2=completed)
   if(failed == 2){ //completed
//guess.print();
System.out.println("yay");
this.sudoku = guess.getPuzzle(); //now current sudoku has solution
   }
   i++;
  }
  
 }
 
 /* 
  * similar to method above. for when a guess is made, more information is needed so another guess is made, 
  * none of the options for that guess work so the first guess must have been wrong and we need to go to the
  * next option for the first guess. this manually increases the index in getPossible
  */
 private void guess(int[][] puzzle, int a, int i){ //a is desired row in guesses (the row where another guess must be added)
  int[][] guessednum = new int[puzzle.length][puzzle[0].length];
  for(int row=0; row<puzzle.length; row++){ //copy puzzle over
   for(int col=0; col<puzzle[0].length; col++){
    guessednum[row][col] = puzzle[row][col];
   }
  }
  int r=guesses[a][0].getRow(); //determine which space was being guessed
  int c = guesses[a][0].getCol();
  int failed = 0;
  Sudoku guess;
  ArrayList<Integer> possible = puzzles[a].getPossible(r,c);
  while(failed == 0){ //wrong guess
   if(i == possible.size()){ //this guess before this one was wrong too
System.out.println("two wrong guesses");
    break;
   }
   guessednum[r][c] = possible.get(i); //change guessed num to the next possibility
   puzzles[a] = new Sudoku(puzzle,3); //store previous sudoku board
   guesses[a][i] = new Guess(r,c,i); //store guess
   System.out.println("REGUESS: row = " + r + " col = " + c + " num = " + possible.get(i));
   guess = new Sudoku(guessednum, 3);
   failed = guess.solve(true);
   if(failed == 2){ //completed
//guess.print();
System.out.println("yay");
    this.sudoku = guess.getPuzzle();
   }
   i++;
  }
  
 }
 
 /*
  * method tries some simple solving techniques and then guesses, saving the guesses so if one is wrong it can 
  * go back and fix the guess. if the boolean guess is true, it will guess to solve the sudoku.
  * if false, there will be no guessing. (so that you can be sure to create a sudoku that you can solve
  * without guessing)
  */
 public int solve(boolean guess){ //0=incorrect, 1=need another guess, 2=done
  boolean changed = true; //number was added
  ArrayList<Integer> possible;
  while(changed){
   changed = false;
   for(int row = 0; row<sudoku.length; row++){
    for(int col=0; col<sudoku[0].length; col++){
     possible = getPossible(row,col);
     if(possible.size() == 1){ //only one possibility for number based on block and row/col
  //System.out.println("know row=" + row + " col=" + col + " num=" + possible.get(0));
  set(row,col,possible.get(0));
  changed = true;
 }
 if(possible.size() == 0 && sudoku[row][col] == 0){ //no options for a space that needs a number
  System.out.println("FAILURE");
      return 0;
     }
    }
   }
   if(!complete() && !changed){
    for(int row=0; row<sudoku.length; row++){ //sees if there is a number needed in a row that can only go in one spot
     changed = changed || eliminationRow(row);
    }
   }
   if(!complete() && !changed){
    for(int col=0; col<sudoku[0].length; col++){ //sees if there is a number needed in a col that can only go in one spot
     changed = changed || eliminationCol(col);
    }
   }
   if(!complete() && !changed){ //see if there is a num needed in a block that can only go in one spot
    for(int row=0; row<blocks.length; row++){
     for(int col=0; col<blocks[0].length; col++){
      changed = changed || eliminationBlock(row,col);
     }
    }
   }
   if(!complete() && !changed && guess){ //still can't do anything then you guess
guess(sudoku);
if(!complete()){ //must back up to previous guess and change that one because it was wrong
 System.out.println("going back to previous guess");
 int a=0;
 for(int k = 0; k<guesses.length; k++){
  if(guesses[k][0] == null){ //first row where first entry is null
   a = k-2; //second from last non-null row
   break;
  }
 }
 int b=0;
 for(int k = 0; k<=3; k++){
  if(k==3){
   b=3;
   break;
  }
  if(guesses[a][k] == null){
   b = k; //first null entry in second from last non-null row
   break;
  }
 }
 for(int w = 0; w<3; w++){ //get rid of incorrect guesses that came after previous wrong guess
  guesses[a+1][w] = null;
 }
 System.out.println(Arrays.deepToString(guesses));
 //System.out.print("puzzles[a]: ");
 //puzzles[a].print();
 //System.out.print("puzzles[a-1]");
 //puzzles[a-1].print();
 System.out.println("a = " + a + " b= " + b);
     guess(puzzles[a].getPuzzle(),a,b);
    }
   }
   if(complete()){
    return 2;
   }
  }
  System.out.println("hi");
  return 1;
 }
 
 public void printall(){ //prints out all the info needed to make sure things are running right
  print();
  System.out.println(" ");
  for(Line l:rows){
   l.print();
   System.out.println(" ");
  }
  for(Line l:cols){
   l.print();
   System.out.println(" ");
  }
  for(int row=0; row<blocks.length; row++){
   for(int col=0; col<blocks[0].length; col++){
    blocks[row][col].print();
    System.out.println(" ");
   }
  }
  System.out.println(rows[0].getNums());
 }
 
 
 
}
