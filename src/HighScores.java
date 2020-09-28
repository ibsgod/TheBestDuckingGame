import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class HighScores extends JFrame { //class for highscore window

	private JPanel contentPane;
	ArrayList<Pair> scores = new ArrayList<Pair>(); //list of all name-score pairs

	class Pair implements Comparable<Pair> //Pair class being used to connect names with scores in order to sort 
    {
        String name;
        int score;
        Pair (String name, int score)
        {
            this.score = score;
            this.name = name;
        }
        public int compareTo(Pair other) //method for sorting 
        {
            return this.score - other.score;
        }
    }
	/**
	 * Creating highscore screen
	 * @param name Name entered
	 * @param score Score achieved
	 * @param diff Difficulty played
	 */
	public HighScores(String name, int score, int diff) {
		//setting up window and contentPane
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 317, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(250, 210, 250));
		
		//writing to the .txt file using FileWriter when score is submitted
		
		 try {      
	            FileWriter fileWriter = new FileWriter("highscores.txt", true); //the boolean tells the class that we want to append to the existing text
	            BufferedWriter bw = new BufferedWriter(fileWriter); //using buffered writer for easy writing to file
	            //score entry 
	            bw.write("!diff"); //first line contains a string used to separate entries from one another, was also helpful in debugging
	            bw.newLine();
	            bw.write(Integer.toString(diff)); //second line is the difficulty 
	            bw.newLine();
	            bw.write(name); //third line is the name submitted
	            bw.newLine();
	            bw.write(Integer.toString(score)); //fourth line is the score they got
	            bw.newLine();
	            bw.close();
	        }
	        catch(IOException e) {
	        	e.printStackTrace();
	        }
		 //reading from the .txt file using FileReader to display high scores
		 
        try {
			 FileReader fileReader = new FileReader("highscores.txt"); //creating filereader object with our txt file

	         BufferedReader br = new BufferedReader(fileReader); //bufferedreader will read from filereader
	         
            while(true) 
            {
            	String line = br.readLine(); //looping through file line by line
            	if (line == null) //stopping loop when end is reached
            	{
            		break;
            	}
                if (line.equals("!diff")) //checking for start of entry
                {
                	if (Integer.parseInt(br.readLine()) == diff) //when entry is found matching player's difficulty
                	{
            			String currentName = br.readLine();
            			int currentScore = Integer.parseInt(br.readLine());
            			scores.add(new Pair(currentName, currentScore)); //creating a name-score pair and adding it to the list of pairs
                	}
                }
            }   
            br.close();         
        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();               
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        Collections.sort(scores); //sorting all pairs in the list
        
		JLabel lblHighScoresFor = new JLabel("High scores for difficulty " + diff); 
		lblHighScoresFor.setHorizontalAlignment(SwingConstants.CENTER);
		lblHighScoresFor.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 23));
		lblHighScoresFor.setBounds(10, 11, 281, 65);
		contentPane.add(lblHighScoresFor);
		
		for (int i = scores.size()-1; i >= Math.max(scores.size()-5, 0); i--) //only displays the top 5 scores for that difficulty
		{
			JLabel lblNewLabel = new JLabel(scores.get(i).name + " - " + scores.get(i).score);
			lblNewLabel.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(45, 70 + (scores.size() - 1 - i) * 35, 200, 29);
			contentPane.add(lblNewLabel);
		}
	}
}
