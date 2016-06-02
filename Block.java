import java.util.ArrayList;
import java.util.Arrays;

public class Block {
 private int[][] block;
 private ArrayList<Integer> needNums;
 
 public Block(int[][] arr){
  //copying arr into block
  block = new int[arr.length][arr[0].length]; //can't use .clone() or Arrays.copyOf() because too deep
  for(int row=0; row<arr.length; row++){
   for(int col=0; col<arr.length; col++){
    block[row][col] = arr[row][col];
   }
  }
  //creating needNums
  needNums = new ArrayList<Integer>(); //all possibilities, remove those in block
  for(int i=1; i<=block.length*block[0].length; i++){
   needNums.add(i);
  }
  for(int r=0; r<block.length; r++){
   for(int c=0; c<block[0].length; c++){
    if(block[r][c] != 0){
     needNums.remove(needNums.indexOf(block[r][c])); //remove nums already in block
    }
   }
  }
 }
 
 public String toString(){
  return Arrays.deepToString(block);
 }
 
 public void setNum(int row, int col, int num){
  block[row][col] = num;
  removeNum(num);
 }
 
 public void unsetNum(int row, int col, int previousnum){
  block[row][col] = 0;
  needNums.add(previousnum);
 }
 
 public void print(){
  for(int row=0; row<block.length; row++){
   for(int col=0; col<block[0].length; col++){
    System.out.print(block[row][col] + " ");
   }
   System.out.println();
  }
 }
 
 public ArrayList<Integer> getNums(){ //returns possibilites for number based on other nums in block
  return needNums;
 }
 
 private void removeNum(int num){
  needNums.remove(needNums.indexOf(num));
 }
 
 public int[][] getBlock(){
  return block;
 }
 
}
