package Programs;

/*
 * File name: GuiRpgCharacter.java
 * Description: GUI of the RPG Character Recommender 9000
 * Author: Jamie Zhang
 * Date: January 2 2024
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
 
// class extends JFrame
public class GuiRpgCharacter extends JFrame{

    // ************* Methods ~ back-end code ************** // 

    // initialization of characterTotal hashmap 
    public static void initCharacters() throws Exception
    {
        File initTotal = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "InitCharacterTotal.txt"); // file instance
        Scanner scanInitTotal = new Scanner(initTotal);  // scanner for file

        // initialize character totals, which is all zero initially
        while (scanInitTotal.hasNext()) // read from the initialize character totals file
        {
            String characterType = scanInitTotal.next(); // read the character type first and store into variable 
            Integer initSum = Integer.valueOf(scanInitTotal.nextInt());  // read the initial value of zero as the sum
            characterTotal.put(characterType, initSum);  // store character type and sum into a hashmap, the file itself will not be altered
        }
        scanInitTotal.close();
    }


    // method for keeping track of times each character is considered as eligible 
    public static void characterCount(ArrayList<String> array){

        for (String v : characterTotal.keySet()) // iterate through hashmap to work on a character specifically
        {
            for (String i : array) // for each character, iterate through parameter arraylist (eligibleCharacters hashmap value)
            {
                if (v.equals(i)) // if a character shows up in the eligibleCharacters value arraylist
                {
                    sum = characterTotal.get(v); // get the current sum value of that character 
                    characterTotal.replace(i, Integer.valueOf(sum + counter)); // add 1 to the current sum
                } 
            }        
        }
    }

    // method to find eligible characters by comparing user's choice to hashmap keys 
    public static void getCharacters(String choice)
    {
        for (String i : eligibleCharacters.keySet()) 
        {
            if (choice.equalsIgnoreCase(i)) // find the key from the eligibleCharacters hashmap which matches user's choice
            {
                // find the value of the key i 
                // pass arraylist value as parameters for characterCount method
                characterCount(eligibleCharacters.get(i)); 
            }
        }
        // reset arraylists to prepare for next round
        forA.clear();
        forB.clear();
        forC.clear();
        forD.clear();
        forE.clear();
        forF.clear();
    }



    // method to scan files that hold the characters eligible for each option per question
    // reads the files and stores it into arraylists that act as the values in the eligibleCharacters hashmap
    // accepts which file to read and question number as parameters
    public static void assignToChoice(File question, int questionNum) throws Exception
    {
        Scanner scanEligibility = new Scanner(question); // create scanner for parameter file

        while (scanEligibility.hasNext()) // reading from parameter file
        {
            // reading for option a.
            String[] arrayForA = scanEligibility.nextLine().split(";");  // creates string array for characters after reading line
            listForA = new LinkedList<String>(Arrays.asList(arrayForA));
            forA = new ArrayList<String>(listForA); // arraylist 
            eligibleCharacters.put("a", forA);  // key and value for option a assigned to hashmap
            
            // option b.
            scanEligibility.hasNext();  // goes to next line
            String[] arrayForB = scanEligibility.nextLine().split(";");
            listForB = new LinkedList<String>(Arrays.asList(arrayForB));
            forB = new ArrayList<String>(listForB);
            eligibleCharacters.put("b", forB);   // key and value for option b assigned to hashmap

            // option c.
            scanEligibility.hasNext();
            String[] arrayForC = scanEligibility.nextLine().split(";");
            listForC = new LinkedList<String>(Arrays.asList(arrayForC));
            forC = new ArrayList<String>(listForC);
            eligibleCharacters.put("c", forC);
            
            // option d.
            scanEligibility.hasNext();
            String[] arrayForD = scanEligibility.nextLine().split(";");
            listForD = new LinkedList<String>(Arrays.asList(arrayForD));
            forD = new ArrayList<String>(listForD);
            eligibleCharacters.put("d", forD);

            if (questionNum <= 5)  // run code if reading the first five questions
            {
                // for option e.
                scanEligibility.hasNext();
                String[] arrayForE = scanEligibility.nextLine().split(";");
                listForE = new LinkedList<String>(Arrays.asList(arrayForE));
                forE = new ArrayList<String>(listForE);
                eligibleCharacters.put("e", forE); 

                if (questionNum != 5)  // run code if reading the first four questions
                {
                    // option f.
                    scanEligibility.hasNext();
                    String[] arrayForF = scanEligibility.nextLine().split(";");
                    listForF = new LinkedList<String>(Arrays.asList(arrayForF));
                    forF = new ArrayList<String>(listForF);
                    eligibleCharacters.put("f", forF);
                }
                
            }
        }
        scanEligibility.close();
        
        listForA.clear();
        listForA.clear();
        listForB.clear();
        listForC.clear();
        listForD.clear();
        listForE.clear();
        listForF.clear();  
    } 



    // method to read the file that holds the questions and store each question into an array
    // accepts the array to be modified (passby reference) and the numbers of lines skipped inside the file as parameters
    public static String getQuestions(String[] question, int skippedLines) throws Exception
    {
        File questionsFile = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "Questions&Options.txt"); // read the questions from file
        Scanner scanQuestions = new Scanner(questionsFile); // create scanner for file
        boolean done = false;  // marks when the scanner should close
        String arrayToString = ""; 
        
        
        while (scanQuestions.hasNext() && !done)
        {
            for (int i = 0; i < skippedLines; i++)
            {
                scanQuestions.nextLine(); // skip n number of lines; specified through skippedLines parameter
            }
            
            for (int i = 0; i < question.length; i++)
            {
                question[i] = scanQuestions.nextLine(); // store each line in file as an element in question array
            }

            arrayToString = Arrays.toString(question);  // store question array as a string into arrayToString
            arrayToString = arrayToString.replace(",", "<br>    ");  // update arrayToString; remove the commas
            arrayToString = arrayToString.replace("[", "");  // remove the right bracket
            arrayToString = arrayToString.replace("]", "");  // remove the left bracket
            arrayToString = "<html>" + arrayToString + "</html>";  // use <html> <br> to break into lines that will show on gui

            done = true; // mark as finished and close scanner
        }
        scanQuestions.close();
            
        return arrayToString;  // return the string
            
    }



    // ****************** global variables for methods above
    
    static HashMap<String, Integer> characterTotal = new HashMap<String, Integer>();
    static Integer sum = 0;
    static Integer counter = Integer.valueOf(1);
    static HashMap<String, ArrayList<String>> eligibleCharacters = new HashMap<String, ArrayList<String>>();
    
    // arrays and lists to be manipulated constantly to store characters for each question
    static java.util.List<String> listForA = new ArrayList<String>();
    static ArrayList<String> forA = new ArrayList<String>(listForA);
    static java.util.List<String> listForB = new ArrayList<String>();
    static ArrayList<String> forB = new ArrayList<String>(listForB);
    static java.util.List<String> listForC = new ArrayList<String>();
    static ArrayList<String> forC = new ArrayList<String>(listForC);
    static java.util.List<String> listForD = new ArrayList<String>();
    static ArrayList<String> forD = new ArrayList<String>(listForD);
    static java.util.List<String> listForE = new ArrayList<String>();
    static ArrayList<String> forE = new ArrayList<String>(listForE);
    static java.util.List<String> listForF = new ArrayList<String>();
    static ArrayList<String> forF = new ArrayList<String>(listForF);

    // stores questions to be displayed on gui
    static String[] question1 = new String[7];
    static String[] question2 = new String[7];
    static String[] question3 = new String[7];
    static String[] question4 = new String[7];
    static String[] question5 = new String[6];
    static String[] question6 = new String[5];
    static String[] question7 = new String[5];
    
    // create file instances for the eligible characters per option for each question *** // 
    static File firstQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ1.txt");
    static File secondQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ2.txt");
    static File thirdQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ3.txt");
    static File fourthQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ4.txt");
    static File fifthQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ5.txt");
    static File sixthQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ6.txt");
    static File seventhQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ7.txt"); 
    
    static java.io.File[] questionFilesList = {  // stores the 'eligible characters per question' files into an array
        firstQuestion,
        secondQuestion,
        thirdQuestion,
        fourthQuestion,
        fifthQuestion,
        sixthQuestion,
        seventhQuestion
    };

    static String storeChoice = ""; // stores user choice as string
    static String recommendedCharactersString = ""; // stores recommendedCharactersArray as string



    // **************** GUI begins ~ front-end code ***************** //

    // initial value of current card (current panel that is being displayed on gui) is 1
    static int currentCard = 1;
 
    // ****** Declaration of objects of CardLayout class 
    // displays panels one at a time like cards
    CardLayout cl;
 
    // * ---------------- * CONSTRUCTOR * ------------------- * //
    // sets up gui
    public GuiRpgCharacter() throws Exception  
    {
        // functions to create JFrame
        setTitle("RPG Character Recommender 9000");
        setSize(800, 400);
        setResizable(false);
 
        JPanel cardPanel = new JPanel(); // creating Object of "JPanel" class
        cl = new CardLayout(); // initialization of object "c1" of CardLayout class
        cardPanel.setLayout(cl); // set layout of cardPanel; components added to this will have cardlayout
 
        // creating panels ~ will act as the "cards"
        JPanel pan1 = new JPanel();
        JPanel pan2 = new JPanel();
        JPanel pan3 = new JPanel();
        JPanel pan4 = new JPanel();
        JPanel pan5 = new JPanel();
        JPanel pan6 = new JPanel();
        JPanel pan7 = new JPanel();
        JPanel pan8 = new JPanel();
        JPanel invalidPan = new JPanel();
        JPanel resultsPan = new JPanel();
        JPanel endingPan = new JPanel();
        endingPan.setBackground(Color.WHITE);
        


        // creating labels 
        JLabel welcomeLabel = new JLabel(
            "<html>Hello player and welcome to the start of your journey!<br>Please answer some questions to help pick out your recommended type of character</html>",
             JLabel.CENTER);
        JLabel q1Label = new JLabel(getQuestions(question1,0));
        JLabel q2Label = new JLabel(getQuestions(question2,7));
        JLabel q3Label = new JLabel(getQuestions(question3,14));
        JLabel q4Label = new JLabel(getQuestions(question4,21));
        JLabel q5Label = new JLabel(getQuestions(question5,28));
        JLabel q6Label = new JLabel(getQuestions(question6,34));
        JLabel q7Label = new JLabel(getQuestions(question7,39));
        JLabel invalidLabel = new JLabel("Please enter a valid choice");
        invalidLabel.setForeground(Color.RED); // set invalidLabel to be the colour red
        JLabel resultsLabel = new JLabel("<html>Your recommended characters...<br>  </html>");
        JLabel endingLabel = new JLabel("Now it's time for you to begin your adventure!");


        // adding the labels to the panels
        pan1.add(welcomeLabel);
        pan2.add(q1Label);
        pan3.add(q2Label);
        pan4.add(q3Label);
        pan5.add(q4Label);
        pan6.add(q5Label);
        pan7.add(q6Label);
        pan8.add(q7Label);
        invalidPan.add(invalidLabel);
        invalidPan.setVisible(false); // set invalidLabel to be not visible initially
        resultsPan.add(resultsLabel);
        endingPan.add(endingLabel);
        


 
        // ------------------------ add card panels to cardPanel
        // give each card panel a name to be referred to later
        cardPanel.add(pan1, "1"); 
        cardPanel.add(pan2, "2");
        cardPanel.add(pan3, "3");
        cardPanel.add(pan4, "4");
        cardPanel.add(pan5, "5");
        cardPanel.add(pan6, "6");
        cardPanel.add(pan7, "7");
        cardPanel.add(pan8, "8");
        cardPanel.add(resultsPan, "9");
        cardPanel.add(endingPan, "10");



        // -------------- create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.pink);
        JButton nextButton = new JButton("Next");
        JLabel instructionLabel = new JLabel("Enter your answer here:");
        JTextField inputField = new JTextField(5); 
        // add label, button, and text field to the button panel
        buttonPanel.add(instructionLabel);
        buttonPanel.add(inputField);
        buttonPanel.add(nextButton);
        // set initial properties of the button panel
        instructionLabel.setVisible(false);
        inputField.setVisible(false);
        nextButton.setText("Start!");




        // ----------- create the panel that will display the recommended characters at the end
        JPanel recommendPan = new JPanel();
        recommendPan.setBackground(Color.PINK);
        JLabel recommendedCharactersLabel = new JLabel(""); // initialize the label; will be changed later in the program
        recommendPan.add(recommendedCharactersLabel);
        recommendPan.setVisible(false);





        // ----- add image -------
        FlowLayout fl = new FlowLayout(); // use flow layout
        JPanel imagePan = new JPanel();  // create new panel 
        imagePan.setBackground(Color.WHITE);
        imagePan.setLayout(fl);  // set panel as the layout
        
        BufferedImage charactersImage = ImageIO.read(new File("RPGcharacter" + File.separator + "Assets" + File.separator + "charactersImage.png"));  // get image
        JLabel imageLabel = new JLabel(new ImageIcon(charactersImage)); // create image as a label
        imagePan.add(imageLabel);  // add image to the panel
        imagePan.setVisible(false);  // image is not visible initially




        // create printwriter to write recommended characters to file ~ used at the very end 
        PrintWriter writer = new PrintWriter("RPGcharacter" + File.separator + "Assets" + File.separator + "RecommendedCharacters.txt"); // create writer object
        
 


        // * ---------- * BUTTON * ------------------------- *
        // add an ActionListener to the button 
        // code only runs when the button is pressed
        nextButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event) 
            {
                String choice = inputField.getText(); // user's input is stored
                storeChoice = choice; // input is stored as a global string
                boolean valid = true; // check if input is valid

                // for the first question; checks if it's the first time being passed through the questions
                // without it, the invalid label will appear before the user does anything
                boolean firstPass = false; 
                
                // ******** run if conditions apply ********* 

                if (currentCard <= 8) // make textfield and label only for the question panels
                {
                    instructionLabel.setVisible(true);
                    inputField.setVisible(true);
                    nextButton.setText("Next");
                    
                }
                
                if (currentCard == 1)
                {
                    currentCard += 1; // increment the value of currentcard by 1
                    cl.show(cardPanel, "" + currentCard);  // show the value of currentcard
                    firstPass = true; // going into the first question
                }

                if (currentCard == 9)
                {
                    nextButton.setText("Done!"); // change button text 
                }

                if (currentCard < 6) // for the first 4 questions
                {

                    // ****** check if input is considered valid ****** //
                    if (choice.equalsIgnoreCase("a")
                    || choice.equalsIgnoreCase("b")
                    || choice.equalsIgnoreCase("c")
                    || choice.equalsIgnoreCase("d")
                    || choice.equalsIgnoreCase("e")
                    || choice.equalsIgnoreCase("f"))
                    {
                        valid = true;
                    }
                    else
                    {
                        valid = false;
                    }
                    
                    
                }
                
                if (currentCard == 6) // for the 5th question
                {
                    // *** question 5 only has 5 options to choose from *** //
                    if (choice.equalsIgnoreCase("a")
                    || choice.equalsIgnoreCase("b")
                    || choice.equalsIgnoreCase("c")
                    || choice.equalsIgnoreCase("d")
                    || choice.equalsIgnoreCase("e"))
                    {
                        valid = true;
                    }
                    else
                    {
                        valid = false;
                    }
                }

                if (currentCard == 7 || currentCard == 8) // for questions 6 and 7
                {
                    if (choice.equalsIgnoreCase("a")
                    || choice.equalsIgnoreCase("b")
                    || choice.equalsIgnoreCase("c")
                    || choice.equalsIgnoreCase("d"))
                    {       
                        valid = true;            
                    }
                    else
                    {
                        valid = false;
                    }  
                } 
                inputField.setText(""); // reset textfield after each button click

                

                if (currentCard == 9 || currentCard == 10) // for panels 9 and 10
                {
                    currentCard += 1; // when button is pressed, flip to next panel
                    
                    if (currentCard == 10)
                    {
                        recommendPan.setVisible(false); // for panel 10, hide recommendedCharacterLabel
                        imagePan.setVisible(true);
                        getContentPane().add(imagePan, BorderLayout.CENTER); // add the image to container
                    }
                }
 


                if (currentCard == 11)
                {
                    System.exit(0); // exit gui when button is pressed on the 10th panel
                }



                // run only for the panels that print the questions
                if (currentCard < 9)
                {
                    if (valid) // ------------ checks if input is valid and runs code inside
                    {
                        if (currentCard == 8)  // for panel 8 only (after all questions have been answered)
                        {
                            nextButton.setText("Nice"); // change button label 
                            instructionLabel.setVisible(false); // remove the inputting instruction label
                            inputField.setVisible(false); // remove textfield
                        }


                        // actionlistener is not compatible with throws exception
                        // use try and catch to catch exception to read from file
                        try {
                            assignToChoice(questionFilesList[(currentCard)-2], (currentCard - 1));
                        }
                        catch (Exception ex){
                            System.out.println("exception oops for reading files");
                        }

                        // call method to get eligible characters
                        // pass user's input as parameters
                        getCharacters(storeChoice);

                        currentCard += 1; // flip to next panel card
                        invalidPan.setVisible(false);  // don't show the invalid message when input is valid
                    }
                    
                    else // ------------ if input is not valid
                    {
                        // when it flips to the second panel from the first, it will automatically consider the empty textfield as invalid input
                        // this is to make sure that it doesn't show the invalid message immediately
                        if (firstPass) 
                        {
                            firstPass = false;  // deactivated for the rest of the program
                            cl.show(cardPanel, "" + currentCard); // keep showing the first question 
                        }
                        else // if firstPass is already false and input is still invalid, show invalid message and don't flip to the next panel
                        {
                            invalidPan.setVisible(true);
                        }
                    }



                    // * ------------- for panel 9 only (*** processing results ***) -------------- *

                    if (currentCard == 9)  
                    {
                        java.util.List<Integer> sortTotals = new ArrayList<Integer>(); // create list to store the character totals (value)
                        for (String i : characterTotal.keySet())  // iterate through characterTotal hashmap
                        {
                            sortTotals.add(characterTotal.get(i));  // add each total to sortTotals
                        }
                        

                        Integer[] arrayForTotals = sortTotals.toArray(new Integer [16]);  // create Integer array to convert sortTotals to array
                        for (int j = 1; j < arrayForTotals.length; j++) // bubble sort
                        {
                            for (int i = 0; i < arrayForTotals.length - j; i++)
                            {
                                if (arrayForTotals[i] < arrayForTotals[i+1]) // sort from greatest total to least
                                {
                                    Integer temp = arrayForTotals[i];
                                    arrayForTotals[i] = arrayForTotals[i+1];
                                    arrayForTotals[i+1] = temp;
                                }
                            }
                        }

                        
                        Integer maxValue = arrayForTotals[0]; // get the greatest total; the first number in arrayForTotals 
                        int countRecommended = 0;  // counter to count how many characters were recommended
                        for (String i : characterTotal.keySet()) 
                        {
                            if (maxValue == (characterTotal.get(i))) // if the max total equals a value in the characterTotal hashmap
                            {
                                writer.println(i);   // get corresponding key (character) and write it to a new file  
                                countRecommended++;   // keep count each time a character is added to file 
                            }
                        }
                        writer.close();  // commit changes to file



                        String[] recommendedCharactersArray = new String[countRecommended]; // create an array to store recommended characters after reading from file
                        File getRecommended = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "RecommendedCharacters.txt"); // file instance
                        Scanner scanRecommended = null; // scanner for file 

                        // use try and catch again to read from a file inside the action listener
                        try 
                        {
                            scanRecommended = new Scanner(getRecommended); // complete scanner for file
                            while (scanRecommended.hasNext())
                            {
                                for (int i = 0; i < countRecommended; i++)
                                {
                                    recommendedCharactersArray[i] = scanRecommended.nextLine(); // add each line as an element in array
                                }
                            }
                            scanRecommended.close();
                        }
                        catch (Exception e)
                        {
                            System.out.println("Exception oops for reading recommended characters file");
                        }
                        
                        // convert recommendedCharactersArray to string in order to display it on gui
                        recommendedCharactersString = Arrays.toString(recommendedCharactersArray); 
                        recommendedCharactersString = recommendedCharactersString.replace(",", "<br>");  
                        recommendedCharactersString = recommendedCharactersString.replace("[", "");  
                        recommendedCharactersString = recommendedCharactersString.replace("]", "");  
                        recommendedCharactersString = "<html>" + recommendedCharactersString + "</html>";

                        recommendedCharactersLabel.setText(recommendedCharactersString); // update the text of recommendedCharactersLabel
                        recommendPan.setVisible(true);  // set visible

                        getContentPane().add(recommendPan, BorderLayout.CENTER); // add the recommended characters panel as a container to the jframe 
                        
                    }
                }


                // at the end of each button click, show the panel with the name equal to the currentCard value
                cl.show(cardPanel, "" + currentCard);
                    
            }
        });


        // place components (main panels) onto jframe to display
        getContentPane().add(invalidPan, BorderLayout.CENTER);
        getContentPane().add(cardPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);



        initCharacters(); // call method for initialization
    }

 
    // * -------------------- * Main method * --------------------- * //
    public static void main(String[] args) throws Exception
    {
        // creating Object of GuiRpgCharacter class
        GuiRpgCharacter frame = new GuiRpgCharacter(); // the class's name is its data type

        // default; exit if 'x' button on gui is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        // set frame as visible
        frame.setVisible(true);
        
    }
}