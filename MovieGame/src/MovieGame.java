import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;


public class MovieGame {

    //Declare global variables
    private int numOfMovies;
    private int randomMovie;
    private String selectedMovie;
    private String movies;
    private int numOfGuesses = 0;
    private int numOfLoops = 0;
    private boolean alreadyGuessedThatLetter = false;


    public MovieGame(){
        numOfMovies = 0;
        randomMovie = 0;
        String selectedMovie = "";
        String movies = "";
    }


    public int getNumOfMovies() throws Exception {
        File movieFile = new File("movies.txt");
        Scanner scanner = new Scanner(movieFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                movies += (line + "\n");
                numOfMovies++;
            }
        return numOfMovies;
    }

    public String getRandomMovie() throws Exception {
        File movieFile2 = new File("movies.txt");
        Scanner scanner2 = new Scanner(movieFile2);
        randomMovie = (int) (Math.random() * numOfMovies);
        //Get selected Movie
        for (int i = 0; i <= randomMovie; i++) {
            selectedMovie = scanner2.nextLine();
        }
        return selectedMovie;
    }



    public void StartGame(String selectedMovie) {
        String hiddenTitle = new String(new char[selectedMovie.length()]).replace('\0', '_');
        String lettersGuessed = "";
        String fixedMovie = selectedMovie;

        System.out.println("I have randomly chosen a movie title. Can you guess it? You have 20 guesses.");


        // Remove any special characters
        char[] removedChars = {':', ' '};
        fixedMovie = fixedMovie.replace(":", "_");
        fixedMovie = fixedMovie.replace(" ", "_");
        fixedMovie = fixedMovie.replace(", ", "_");
        fixedMovie = fixedMovie.replace("' ", "_");

        // player's input
        Scanner scanner = new Scanner(System.in);

        for (int i = 20; i > 0; i--) {
            System.out.println("You have " + i + " guess(es) left. Guess again.");
            System.out.println("Letters guessed: " + lettersGuessed);
            System.out.println("Current word: " + hiddenTitle);

            String guess = scanner.nextLine();
            char currentGuess = guess.charAt(0);

            if (Pattern.matches("[a-zA-Z]+", guess)) {
                //If you already guessed once check to make sure its not the same letter.
                for (int x = 1; x <= numOfLoops; x++) {
                    if (currentGuess == lettersGuessed.charAt(x - 1)) {
                        System.out.println("You already guessed the letter: " + currentGuess);
                        i++;
                        numOfGuesses++;
                        alreadyGuessedThatLetter = true;
                        break;
                    } else {
                        alreadyGuessedThatLetter = false;
                    }


                }
                // Reveal the letter in the title
                if (!alreadyGuessedThatLetter) {


                    for (int r = 0; r <= selectedMovie.length() - 1; r++) {
                        char current = selectedMovie.charAt(r);

                        currentGuess = Character.toLowerCase(currentGuess);
                        if (current == currentGuess) {
                            System.out.println("You guessed a correct letter");
                            char[] charHidden = hiddenTitle.toCharArray();
                            charHidden[r] = current;
                            hiddenTitle = String.valueOf(charHidden);

                        }

                        currentGuess = Character.toUpperCase(currentGuess);
                        if (current == currentGuess) {
                            System.out.println("You guessed a correct letter");
                            char[] charHidden = hiddenTitle.toCharArray();
                            charHidden[r] = current;
                            hiddenTitle = String.valueOf(charHidden);

                        }

                    }
                    lettersGuessed = lettersGuessed + currentGuess + ", ";
                    numOfGuesses++;
                    numOfLoops++;
                }

                if (fixedMovie.equals(hiddenTitle)) {
                    System.out.println("YOU WON! The movie title was: " + selectedMovie);
                    break;
                }
            } else {
                System.out.println("Please enter a letter.");
                i++;
            }



        }

        if (!fixedMovie.equals(hiddenTitle)) {
            System.out.println("You lose. The movie title was: " + selectedMovie);

        }



    }

    public static void main(String[] args) throws Exception{
        MovieGame game = new MovieGame();
        game.getNumOfMovies();
        game.getRandomMovie();
        // Print number of movies in txt file
        // System.out.println("Number of movies: " + game.numOfMovies);
        // Print selected random movie number
        // System.out.println("Random movie number: " + game.randomMovie);
        // Print selected movie title
        // System.out.println("Selected movie: " + game.selectedMovie);

        game.StartGame(game.selectedMovie);


    }







}
