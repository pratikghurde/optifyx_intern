import java.util.*;

class User {
    private String username;
    private String password;
    private String profile;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profile = "Profile not updated";
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public void updateProfile(String newProfile) {
        this.profile = newProfile;
        System.out.println("Profile updated successfully!");
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Old password is incorrect.");
        }
    }

    public void viewProfile() {
        System.out.println("Profile: " + profile);
    }
}

class Question {
    private String questionText;
    private String[] options;
    private String correctAnswer;

    public Question(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public void displayQuestion() {
        System.out.println(questionText);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public boolean isCorrectAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Exam {
    private List<Question> questions;
    private Map<Integer, String> userAnswers;
    private Timer timer;
    private boolean isSubmitted;

    public Exam(List<Question> questions, int timeLimit) {
        this.questions = questions;
        this.userAnswers = new HashMap<>();
        this.timer = new Timer(timeLimit, this);
        this.isSubmitted = false;
    }

    public void startExam() {
        timer.start();
        for (int i = 0; i < questions.size(); i++) {
            if (isSubmitted) break;
            Question q = questions.get(i);
            q.displayQuestion();
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your answer: ");
            String answer = sc.nextLine();
            userAnswers.put(i, answer);
        }
        submitExam();
    }

    public void submitExam() {
        if (!isSubmitted) {
            isSubmitted = true;
            timer.stop();
            System.out.println("Exam submitted!");
            evaluateExam();
        }
    }

    public void evaluateExam() {
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            if (q.isCorrectAnswer(userAnswers.get(i))) {
                correctAnswers++;
            }
        }
        System.out.println("You scored: " + correctAnswers + "/" + questions.size());
    }

    public void autoSubmit() {
        System.out.println("Time's up! Auto-submitting the exam...");
        submitExam();
    }
}

class Timer {
    private int timeLimit;
    private Exam exam;
    private Thread timerThread;

    public Timer(int timeLimit, Exam exam) {
        this.timeLimit = timeLimit; // Time limit in seconds
        this.exam = exam;
    }

    public void start() {
        timerThread = new Thread(() -> {
            try {
                Thread.sleep(timeLimit * 1000);
                exam.autoSubmit();
            } catch (InterruptedException e) {
                System.out.println("Timer interrupted.");
            }
        });
        timerThread.start();
    }

    public void stop() {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }
    }
}

class MainExam {
    private static Scanner sc = new Scanner(System.in);
    private static Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        // Initializing users
        users.put("user1", new User("user1", "pass123"));
        users.put("user2", new User("user2", "pass456"));

        // Login process
        System.out.println("Welcome to Online Examination System");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        User currentUser = users.get(username);

        if (currentUser != null) {
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (currentUser.validatePassword(password)) {
                boolean exit = false;
                while (!exit) {
                    System.out.println("\n1. Start Exam");
                    System.out.println("2. Update Profile");
                    System.out.println("3. Change Password");
                    System.out.println("4. View Profile");
                    System.out.println("5. Logout");

                    System.out.print("Choose an option: ");
                    int option = sc.nextInt();
                    sc.nextLine(); // Consume newline

                    switch (option) {
                        case 1:
                            // Create a list of questions for the exam
                            List<Question> questions = new ArrayList<>();
                            questions.add(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Rome"}, "Paris"));
                            questions.add(new Question("Which language runs in the JVM?", new String[]{"C", "Python", "Java", "Ruby"}, "Java"));

                            // Set a timer for 60 seconds
                            Exam exam = new Exam(questions, 60);
                            exam.startExam();
                            break;
                        case 2:
                            System.out.print("Enter new profile information: ");
                            String newProfile = sc.nextLine();
                            currentUser.updateProfile(newProfile);
                            break;
                        case 3:
                            System.out.print("Enter old password: ");
                            String oldPassword = sc.nextLine();
                            System.out.print("Enter new password: ");
                            String newPassword = sc.nextLine();
                            currentUser.changePassword(oldPassword, newPassword);
                            break;
                        case 4:
                            currentUser.viewProfile();
                            break;
                        case 5:
                            System.out.println("Logging out...");
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
            } else {
                System.out.println("Invalid password. Exiting...");
            }
        } else {
            System.out.println("User not found. Exiting...");
        }
    }
}
