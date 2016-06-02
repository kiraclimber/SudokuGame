
import java.util.ArrayList;
import java.util.Arrays;

public class Line {
 private int[] line;
 private ArrayList<Integer> needNums; //numbers missing from the row/col
 
 public Line(int[] arr){
  line = arr.clone();
  needNums = new ArrayList<Integer>();
  for(int i=1; i<=line.length; i++){
   needNums.add(i); //add all possible numbers
  }
  for(int i=0; i<line.length; i++){
   if(line[i] != 0){
    needNums.remove(needNums.indexOf(line[i])); //remove numbers already in the row
   }
  }
 }
 
 public String toString(){
  return Arrays.toString(line);
 }
 
 public void print(){
  for(int i=0; i<line.length; i++){
   System.out.print(line[i] + " ");
  }
  System.out.println();
 }
 
 public ArrayList<Integer> getNums(){ //returns nums still needed in that row or column
  return needNums;
 }
 
 private void removeNum(int num){ //removes a number from the list of nums needed when it is added to the row/col
  needNums.remove(needNums.indexOf(num));
 }
 
 public void setNum(int index, int num){ //changes a number in the row/column from 0 to the chosen number
  line[index]= num;
  removeNum(num);
 }
 
 public void unsetNum(int index, int prevNum){
  line[index] = 0;
  needNums.add(prevNum);
 }
 
 public int[] getLine(){
  return line;
 }
 
}

