
public class Guess {
 private int index;
 private int row;
 private int col;
 
 public Guess(int r, int c, int i){
  index = i;
  row = r;
  col = c;
 }
 
 public int getIndex(){
  return index;
 }
 
 public int getRow(){
  return row;
 }
 
 public int getCol(){
  return col;
 }
 
 public String toString(){
  return Integer.toString(index);
 }
}
