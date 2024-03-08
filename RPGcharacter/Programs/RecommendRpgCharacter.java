package Programs;

/*
 * File name: RecommendRpgCharacter.java
 * Description: Console version of the RPG Character Recommender 9000
 * Author: Jamie Zhang
 * Date: December 29 2023
 */

import java.io.*;
import java.util.*;

public class RecommendRpgCharacter {

    // ---------------- global variables ------
    static HashMap<String, Integer> characterTotal = new HashMap<String, Integer>();
    static Integer sum = 0;
    static Integer counter = Integer.valueOf(1);
    static Scanner input = new Scanner(System.in);
    static HashMap<String, ArrayList<String>> eligibleCharacters = new HashMap<String, ArrayList<String>>();
    
    // arrays and lists to be manipulated constantly to store characters for each question
    static List<String> listForA = new ArrayList<String>();
    static ArrayList<String> forA = new ArrayList<String>(listForA);
    static List<String> listForB = new ArrayList<String>();
    static ArrayList<String> forB = new ArrayList<String>(listForB);
    static List<String> listForC = new ArrayList<String>();
    static ArrayList<String> forC = new ArrayList<String>(listForC);
    static List<String> listForD = new ArrayList<String>();
    static ArrayList<String> forD = new ArrayList<String>(listForD);
    static List<String> listForE = new ArrayList<String>();
    static ArrayList<String> forE = new ArrayList<String>(listForE);
    static List<String> listForF = new ArrayList<String>();
    static ArrayList<String> forF = new ArrayList<String>(listForF);


    // method for keeping track of times each character is considered as eligible 
    public static void characterCount(ArrayList<String> array){

        for (String v : characterTotal.keySet()) // iterate through hashmap to work on a character specifically
        {
            for (String i : array) // for each character, iterate through parameter arraylist (eligibleCharacters hashmap value)
            {
                if (v.equals(i)) // if a character shows up in the eligibleCharacters value arraylist
                {
                    sum = characterTotal.get(v); // get the current sum value of that character 
                    characterTotal.replace(i, Integer.valueOf(sum + counter));  // add 1 to the current sum
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


    // initialization
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

    public static void main(String[] args) throws Exception  // main method!
    {

        // create file instance for the file that holds the questions and options
        File questionsFile = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "Questions&Options.txt"); 
        Scanner scanQuestions = new Scanner(questionsFile); // create scanner for file

        // *** create file instances for the eligible characters per option for each question *** // 
        File firstQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ1.txt");
        File secondQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ2.txt");
        File thirdQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ3.txt");
        File fourthQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ4.txt");
        File fifthQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ5.txt");
        File sixthQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ6.txt");
        File seventhQuestion = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "EligibleCharactersQ7.txt"); 
        // ************* //

        java.io.File[] questionFilesList = {  // stores the 'eligible characters per question' files into an array
            firstQuestion,
            secondQuestion,
            thirdQuestion,
            fourthQuestion,
            sixthQuestion,
            seventhQuestion
        };

        initCharacters(); // call method for initialization


        boolean valid = true; // boolean to validate user input 
        String choice = ""; // initialize user input as empty string 
        
        // ** welcome message ** //
        System.out.println("\n*** ~~~ ----- RPG CHARACTER RECOMMENDER 9000 ----- ~~~ ***");
        System.out.println("\n~~ hello player and welcome to the start of your journey! ~~");
        System.out.println("~~ please answer some questions to help pick out your recommended type of character ~~\n");

        // ***** multiple choice begins ***** //
        while (scanQuestions.hasNext())  // read the questions from the questions file
        {

            // ************** for questions 1-4 ************ //
            for (int j = 1; j <= 4; j++)
            {
                for (int i = 0; i < 7; i++)
                {
                    System.out.println(scanQuestions.nextLine());  // print the questions from file onto terminal
                }
                
                System.out.print("enter choice: ");  // get user input
                choice = input.nextLine();
                

                do  // run the block of code at least once; if input is valid first try, it won't loop
                {
                    // ---------- check if input is considered valid --------- //
                    if (choice.equalsIgnoreCase("a")
                    || choice.equalsIgnoreCase("b")
                    || choice.equalsIgnoreCase("c")
                    || choice.equalsIgnoreCase("d")
                    || choice.equalsIgnoreCase("e")
                    || choice.equalsIgnoreCase("f"))
                    {
                        valid = true;  // confirms that input is valid
                        System.out.println("");

                        // *** call method that assigns eligible characters to each option *** //
                        // parameters: the chosen question file that holds the relevant eligible characters, question number
                        assignToChoice(questionFilesList[j-1], j);
                    }
                    else  // if input is not equal to one of the above letters
                    {
                        valid = false;  // signals that input is invalid
                        System.out.print("please enter a valid choice: "); 
                        choice = input.nextLine(); // user can re-enter
                    }
                } while (!valid);  // if input is invalid, loop the block of code until input is valid

                // ***** call method to start comparing user's input to the options for the current question
                // pass user's input as variable choice as parameters
                getCharacters(choice);
            }   
            

            // for question 5 ~ repeated code from questions 1-4 with minor changes
            for (int i = 0; i < 6; i++)
            {
                System.out.println(scanQuestions.nextLine());
            }
            
            System.out.print("enter choice: ");
            choice = input.nextLine();

            do
            {
                // *** question 5 only has 5 options to choose from *** //
                if (choice.equalsIgnoreCase("a")
                || choice.equalsIgnoreCase("b")
                || choice.equalsIgnoreCase("c")
                || choice.equalsIgnoreCase("d")
                || choice.equalsIgnoreCase("e"))
                {
                    valid = true;
                    System.out.println("");
                    assignToChoice(fifthQuestion, 5); // call method to sort the characters
                }
                else
                {
                    valid = false;
                    System.out.print("please enter a valid choice: "); 
                    choice = input.nextLine();
                }
            } while (!valid);

            getCharacters(choice); // call method to compare


            // for questions 6-7
            for (int j = 5; j <= 6; j++)
            {
                for (int i = 0; i < 5; i++)
                {
                    System.out.println(scanQuestions.nextLine());
                }
                
                
                System.out.print("enter choice: ");
                choice = input.nextLine();

                do{
                    if (choice.equalsIgnoreCase("a")
                    || choice.equalsIgnoreCase("b")
                    || choice.equalsIgnoreCase("c")
                    || choice.equalsIgnoreCase("d"))
                    {
                        valid = true;
                        System.out.println("");
                        assignToChoice(questionFilesList[j-1], j+1);
                    }
                    else
                    {
                        valid = false;
                        System.out.print("please enter a valid choice: "); 
                        choice = input.nextLine();
                    }
                } while (!valid);

                getCharacters(choice);

            }  
        }
        scanQuestions.close();  // done scanning file for questions

        



        // ******** begin processing results ******* //

        List<Integer> sortTotals = new ArrayList<Integer>(); // create list to store the character totals (value)
        for (String i : characterTotal.keySet())  // iterate through characterTotal hashmap
        {
            sortTotals.add(characterTotal.get(i));  // add each total to sortTotals
        }
        

        Integer[] arrayForTotals = sortTotals.toArray(new Integer [16]); // create Integer array to convert sortTotals to array
        for (int j = 1; j < arrayForTotals.length; j++)  // bubble sort
        {
            for (int i = 0; i < arrayForTotals.length - j; i++)
            {
                if (arrayForTotals[i] < arrayForTotals[i+1])  // sort from greatest total to least
                {
                    Integer temp = arrayForTotals[i];
                    arrayForTotals[i] = arrayForTotals[i+1];
                    arrayForTotals[i+1] = temp;
                }
            }
        }


        // create printwriter to write recommended characters to file ~ used at the very end 
        PrintWriter writer = new PrintWriter("RPGcharacter" + File.separator + "Assets" + File.separator + "RecommendedCharacters.txt"); // create writer object
       
        Integer maxValue = arrayForTotals[0];  // get the greatest total; the first number in arrayForTotals 
        int countRecommended = 0; // counter to count how many characters were recommended
        for (String i : characterTotal.keySet()) 
        {
            if (maxValue == (characterTotal.get(i))) // if the max total equals a value in the characterTotal hashmap
            {
                writer.println(i);  // get corresponding key (character) and write it to a new file 
                countRecommended++;  // keep count each time a character is added to file    
            }
        }
        writer.close();  // commit changes to file



        String[] recommendedCharactersArray = new String[countRecommended];  // create an array to store recommended characters after reading from file
        File getRecommended = new java.io.File("RPGcharacter" + File.separator + "Assets" + File.separator + "RecommendedCharacters.txt"); // file instance
        Scanner scanRecommended = new Scanner(getRecommended); // scanner for file 
            
        while (scanRecommended.hasNext())
        {
            for (int i = 0; i < countRecommended; i++)
            {
                recommendedCharactersArray[i] = scanRecommended.nextLine(); // add each line as an element in array
            }
        }
        scanRecommended.close(); // close scanner


        // print out the results
        System.out.println("\n~~ your recommended characters ...");
        for (int i = 0; i < countRecommended; i++)
        {
            System.out.println(recommendedCharactersArray[i]);
        }

        // end message
        System.out.println("\n~~ now it's time for you to begin your adventure! ~~");
        

        input.close(); 
    }
}
