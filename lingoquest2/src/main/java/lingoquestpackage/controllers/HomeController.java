package lingoquestpackage.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;
import lingoquestpackage.models.Lesson;
import lingoquestpackage.models.Section;
import lingoquestpackage.models.User;
import lingoquestpackage.models.Word;

public class HomeController implements Initializable {

    private User user;
    private LanguageGame languageGame;

    @FXML
    private Label usernameField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label answerStreak;

    @FXML
    private Label coinLabel;

    @FXML
    private Label wordOfDay;

    @FXML
    private ProgressBar languageCompletion;

    @FXML
    private Label completionLabel;

    // constructor
    public HomeController() {
        try {
            this.languageGame = LanguageGame.getInstance(); // initialize backend
            this.user = languageGame.getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // navigation buttons
    @FXML
    public void goToProfile() throws IOException {
        App.setRoot("/lingoquestpackage/profile");
    }

    @FXML
    public void goToPractice() throws IOException {
        App.setRoot("/lingoquestpackage/practice");
    }

    @FXML
    public void goToLeaderboard() throws IOException {
        App.setRoot("/lingoquestpackage/leaderboard");
    }

    @FXML
    public void goToShop() throws IOException {
        App.setRoot("/lingoquestpackage/shop");
    }

    @FXML
    public void goToFriends() throws IOException {
        App.setRoot("/lingoquestpackage/friends");
    }

    @FXML
    public void handleLogout() throws Exception {
        // instance of the facade
        if(languageGame == null) {
            languageGame = LanguageGame.getInstance();
        }
        // log the current user out
        languageGame.logout();
        // go back to login page
        App.setRoot("/lingoquestpackage/login");
    }

    // initialize the data
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        if(languageGame == null) {
            try {
                languageGame = LanguageGame.getInstance();
                this.user = languageGame.getUser();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // set the text on the top to hold the current user's username
        System.out.println(user.toString());
        usernameField.setText(user.getUsername());
        coinLabel.setText("Coins: "+ user.getCoinBalance());
        answerStreak.setText("Answer Streak: " + user.getCurrentLanguage().getAnswerStreak());
        double progress = user.getCurrentLanguageProgress();

        // initialize
        ProgressBar languageCompletion = new ProgressBar();
        if(progress > 0.0) {
            languageCompletion.setProgress(progress);
        }

        // set the label to show percent completion
        DecimalFormat df = new DecimalFormat("#.00");
        completionLabel.setText(df.format(progress) + "% Language Completion");

        // Root container
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // ScrollPane to contain the sections and lessons
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);

        // get the sections
        List<Section> sections = languageGame.getAllSections();

        // Populate sections and lessons
        for (Section section : sections) {
            // Section container holds the sections
            VBox sectionBox = new VBox(5);
            // set the styling
            sectionBox.setPadding(new Insets(10));
            sectionBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: #f0f0f0;");

            // section title
            Label sectionLabel = new Label(section.getName());
            // style of the label
            sectionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            sectionBox.getChildren().add(sectionLabel);

            // lessons in an HBox
            HBox lessonBox = new HBox(10);
            for (Lesson lesson : section.getAllLessons()) {
                Button lessonButton = new Button(lesson.getLessonName());
                lessonButton.setPrefSize(50, 50);
                // make the lesson green if completed, black if not
                lessonButton.setStyle("-fx-shape: 'M50,0A50,50,0,1,1,49.999,-0.001Z'; " +
                        (lesson.isCompleted() ? "-fx-background-color: green;" : "-fx-background-color: black;") +
                        "-fx-text-fill: white;");

                // click event for starting a lesson
                lessonButton.setOnAction(e -> {
                    // for test, print out lesson being started (name of lesson clicked on)
                    System.out.println("Starting: " + lesson.getLessonName());
                    // use the section of the clicked on lesson to move to that section
                    languageGame.pickASection(section.getID());
                    //System.out.println("section id:" + section.getID());
                    // load in the lesson to facade
                    languageGame.pickALesson(lesson.getLessonID());
                    //System.out.println("lesson id:" + lesson.getLessonID());
                    System.out.println("Going to " + lesson.getLessonName());
                    try {
                        // make sure variables are at starting values for questions
                        languageGame.prepareForQuestions();
                        // move to the default question fxml, will handle the rest from there
                        App.setRoot("/lingoquestpackage/defaultQuestion");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                });

                lessonBox.getChildren().add(lessonButton);
            }

            sectionBox.getChildren().add(lessonBox);
            root.setPadding(new Insets(10));
            root.getChildren().add(sectionBox);
        }

        // set the word of the day
        Word w = languageGame.getRandomWord();
        // initialize to be safe
        wordOfDay.setText("Spanish Version: " + w.getWordinLanguage() + "\n\nEnglish Translation: " + w.getEnglishVersion());

    }

    /*private List<Section> getSections() {
        List<Section> sections = new ArrayList<>();
        /*for (int i = 1; i <= 3; i++) {
            Section section = new Section();
            section.setName("section"+ (i+1));
            ArrayList<Lesson> lessons = new ArrayList<Lesson>();
            // make lessons
            for (int j = 1; j <= 5; j++) {
                Lesson les = new Lesson();
                les.setLessonName("lesson"+ (j+1));
                lessons.add(les);
            }
            section.setLessons(lessons);
            sections.add(section);
        }
        return sections;*/
        //languageGame.getAvailableSections()
    //}
    
}
// The HomeController class is a JavaFX controller that manages the main dashboard or home screen of the LingoQuest application. 
// It integrates with the backend logic provided by the LanguageGame and User models to dynamically display user progress, available lessons, and sections, while also showcasing features like the "Word of the Day." 
// The controller is responsible for initializing the home screen, providing navigation, and enabling lesson interactions.
// During initialization, the initialize method populates the home screen with user-specific information, such as username, coin balance, answer streak, and overall language completion progress. 
// The language completion is displayed both as a percentage and a progress bar, providing a clear visual representation of the user's progress. 
// Additionally, the "Word of the Day" is fetched from the backend and displayed with its English translation, offering users a quick learning opportunity upon logging in.
// The home screen dynamically loads the available sections and lessons from the backend. Each section is displayed as a labeled container (VBox), and its lessons are represented as buttons arranged in a horizontal box (HBox). 
// Completed lessons are styled with a green color, while incomplete lessons are styled in black, providing a clear visual distinction. 
// Clicking on a lesson button triggers a transition to the corresponding lesson, preparing the backend and navigating to the default question interface.
// The controller includes navigation methods such as goToProfile, goToPractice, goToLeaderboard, goToShop, and goToFriends, allowing users to move seamlessly between different sections of the application. 
// It also includes a handleLogout method to log out the user and return to the login page.
// By dynamically generating the content and providing intuitive navigation, the HomeController creates a personalized and engaging home screen experience for users. 
// It facilitates smooth transitions to lessons and other sections of the application, making it a central hub for users to track progress and explore learning opportunities within LingoQuest.


