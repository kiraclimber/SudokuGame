import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class Game implements ActionListener, DocumentListener{
	 private Sudoku puzzle = new Sudoku();
	 private int[][] solved; //solved sudoku
	 private int[][] playerGuesses; //unsolved sudoku, add numbers the player types in to this array
	 private Color[][] colors; //correct color for each spot in array/sudoku
	 private JLabel[][] labels; //for the numbers given in the puzzle
	 private JTextField[][] textfields; //for the numbers you have to fill in
	 private JLabel win; //win message
	 private JLabel verify; // do you want to clear? message
	 private JFrame f;
	 private JFrame fverify; //frame that asks you if you want to clear
	 private JPanel panel; //panel that the board is displayed on
	 private JPanel winpanel; //panel that the win message is displayed on once you win the game
	 private JPanel verifypanel; //panel in fverify
	 private JButton check; //checks to see if you have made any mistakes in the solving process
	 private JButton clear;
	 private JButton yes; //yes I want to clear the board
	 private JButton no; //no i don't want to clear the board
	 private GridBagConstraints cc; //helps with layout
	 
	 public Game(){
		 getColors(); //set up colors array
		 setUpSudokus(); //set up solved and playerGuesses
		 createBoard(); //make labels and textfields arrays and set it all up as desired on screen 
	 }
	 
	 private void createBoard(){
		 //instantiate everything
		 f = new JFrame();
		 fverify = new JFrame();
		 panel = new JPanel();
		 winpanel = new JPanel();
		 verifypanel = new JPanel();
		 win = new JLabel("YOU WIN");
		 verify = new JLabel("Are you sure you want to clear the board?");
		 check = new JButton("CHECK");
		 clear = new JButton("CLEAR");
		 yes = new JButton("YES");
		 no = new JButton("NO");
		 cc = new GridBagConstraints();
		 panel.setLayout(new GridBagLayout()); //set layout of the panel - grid
		 labels = new JLabel[9][9];
		 textfields = new JTextField[9][9];
		 JLabel l; //generic label to simplify code below
		 JTextField tf; //generic text field to simplify code below
		 int num; //number that goes in the space in the puzzle
		 cc.fill = GridBagConstraints.BOTH; //makes text field and buttons stretch to fill entire space
		 cc.weightx = cc.weighty = 1; //rows and columns expand with window
		 for(int row=0; row<labels.length; row++){
			 for(int col=0; col<labels[0].length; col++){
				 num = puzzle.getPuzzle()[row][col];
				 if(num!=0){ //create a label with that num on it
					 l = new JLabel(Integer.toString(num));
					 l.setHorizontalAlignment(JLabel.CENTER); //makes number appear in center of the label
					 l.setForeground(colors[row][col]); //changes text color of number (setBackground didn't do anything...)
					 labels[row][col] = l; //add new label to the labels array
					 textfields[row][col] = new JTextField(1); //SO THERE ARE NO FREAKING NULL POINTER EXCEPTIONS
					 cc.gridx = col; //x coordinate in grid
					 cc.gridy = row; //y coordinate in grid - evidently top left is 0,0
					 panel.add(l, cc); //stick label in appropriate place in sudoku grid (panel)
				 }
				 else{ //create space where user can type in correct number
					 tf = new JTextField(1);
					 tf.setHorizontalAlignment(JTextField.CENTER); //numbers appear in center when you type
					 tf.setBackground(colors[row][col]); //changes background of the text field to appropriate color
					 tf.getDocument().addDocumentListener(this); //lets you know when a number has been typed or deleted
					 textfields[row][col] = tf; //add text field to textfields array
					 cc.gridx = col; 
					 cc.gridy = row;
					 panel.add(tf, cc); //stick text field in appropriate spot
				 }
			 }
		 }
		 check.addActionListener(this); //know when you click on the button
		 cc.gridwidth = 5; //spans first five columns
		 cc.gridx = 0; //starts in first column
		 cc.gridy = 10; //in tenth row
		 panel.add(check, cc); //add it to the panel with these constraints
		 clear.addActionListener(this); //know when you click on the button
		 cc.gridwidth = 4; //spans last four columns
		 cc.gridx = 5; //starts in fifth column
		 cc.gridy = 10; //in last row
		 panel.add(clear, cc);
		 f.setContentPane(panel);
		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 f.pack();
		 f.setVisible(true);
		 f.setSize(500,500);
		 
		 //set up "do you want to clear?" frame
		 yes.addActionListener(this);
		 no.addActionListener(this);
		 verifypanel.setLayout(new GridBagLayout()); //set layout of the panel - grid
		 cc.gridx = 0;
		 cc.gridy = 0;
		 cc.gridwidth = 2;
		 verifypanel.add(verify, cc);
		 cc.gridx = 0;
		 cc.gridy = 1;
		 cc.gridwidth = 1;
		 verifypanel.add(yes, cc);
		 cc.gridx = 1;
		 verifypanel.add(no, cc);
		 fverify.setContentPane(verifypanel);
		 fverify.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 fverify.pack();
		 fverify.setVisible(false);
		 fverify.setSize(300, 100);
		 
	 }
	 
	 private void setUpSudokus(){
		 solved = new int[9][9]; //gets completed sudoku board before the sudoku is created
		 for(int row=0; row<solved.length; row++){
			 for(int col=0; col<solved[0].length; col++){
				 solved[row][col] = puzzle.getPuzzle()[row][col];
			 }
		 }
		 puzzle.createSudoku(); //get a sudoku to solve
		 playerGuesses = new int[9][9]; //in progress sudoku
		 //copy puzzle into playerGuesses so that you know the original sudoku (puzzle.getPuzzle()) and the guesses the player makes
		 for(int row=0; row<puzzle.getPuzzle().length; row++){
			 for(int col=0; col<puzzle.getPuzzle()[0].length; col++){
				 playerGuesses[row][col] = puzzle.getPuzzle()[row][col];
			 }
		 }
	 }
	 
	 private void getColors(){ //color codes the board so adjacent blocks are different colors
		 colors = new Color[9][9];
		 for(int row=0; row<9; row++){
			 for(int col=0; col<9; col++){
				 if(row<3){
					 if(col<3){colors[row][col] = Color.GREEN;}
					 else if(col<6){colors[row][col] = Color.MAGENTA;}
					 else{colors[row][col] = Color.GREEN;}
				 }
				 else if(row<6){
					 if(col<3){colors[row][col] = Color.MAGENTA;}
					 else if(col<6){colors[row][col] = Color.GREEN;}
					 else{colors[row][col] = Color.MAGENTA;}
				 }
				 else{
					 if(col<3){colors[row][col] = Color.GREEN;}
					 else if(col<6){colors[row][col] = Color.MAGENTA;}
					 else{colors[row][col] = Color.GREEN;}
				 }
			 }
		 }
	 }
	 
	 public void actionPerformed(ActionEvent e) { //clicked on button
		boolean won = checkWin();
		if(won){
			winMessage();
		}
		if(e.getSource() == check){
			checkPlayer();
		}
		else if(e.getSource() == yes){
			for(int row=0; row<solved.length; row++){
				for(int col=0; col<solved[0].length; col++){
					if(puzzle.getPuzzle()[row][col] == 0 && playerGuesses[row][col] != 0){ //a number the player added
						playerGuesses[row][col] = 0; //remove number from playerGuesses
						textfields[row][col].setText(""); //removes number typed in space
					}
					textfields[row][col].setBackground(colors[row][col]); //return all spaces to original color
				}
			}
			fverify.setVisible(false);
		}
		else if(e.getSource() == clear){
			fverify.setVisible(true);
		}
		else if(e.getSource() == no){
			fverify.setVisible(false);
		}
	 }
	 
	 
	 /* 
	  * sees what mistakes the player has made. mistakes are highlighted in red. everything else is 
	  * normal color. corrected mistakes are returned to normal color
	  */
	 private void checkPlayer(){ 
		 for(int row=0; row<solved.length; row++){
			 for(int col=0; col<solved[0].length; col++){
				 if(solved[row][col] != playerGuesses[row][col] && playerGuesses[row][col] != 0){
					 textfields[row][col].setBackground(Color.RED); //mistake, make background red
				 }
				 if((solved[row][col] == playerGuesses[row][col] && puzzle.getPuzzle()[row][col] == 0) || playerGuesses[row][col] == 0){
					 textfields[row][col].setBackground(colors[row][col]); //fixed mistake, reset background (actually sets all backgrounds...)
				 }
			 }
		 }
	 }
	 
	 private boolean checkWin(){ //sees if the game has been won yet
		 for(int row=0; row<solved.length; row++){
			 for(int col=0; col<solved[0].length; col++){
				 if(playerGuesses[row][col] != solved[row][col]){
					 return false;
				 }
			 }
		 }
		 return true;
	 }
	 
	 private void winMessage(){ //displayed when the game has been won
		 winpanel.add(win);
		 f.setContentPane(winpanel);
		 f.pack();
		 f.setVisible(true);
		 f.setSize(100,100);
	 }

	@Override
	public void changedUpdate(DocumentEvent e) {
		documentEventHappened(e);
		
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) { //letter is typed in text field
		documentEventHappened(e);
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) { //letter is deleted in text field
		documentEventHappened(e);
		
	}
	
	public void documentEventHappened(DocumentEvent e){ //updates playerGuesses to reflect the new number typed in
		JTextField tf;
	    for (int row=0; row<textfields.length; row++){
	    	for(int col=0; col<textfields[0].length; col++){
	    		if(e.getDocument() == textfields[row][col].getDocument()){ //number was entered in this text field
	    			tf = textfields[row][col];
	    			try{
	    				playerGuesses[row][col] = Integer.parseInt(tf.getText()); //number added to playerGuesses array
	    			} catch(NumberFormatException n){ //if it is not a number (blank, question mark, accident...)
	    				playerGuesses[row][col] = 0;
	    			}
	    		}
	    	}
	    }
	    boolean won = checkWin(); //sees if the number just typed is the last one needed in game
		if(won){
			winMessage();
		}
	}
}


