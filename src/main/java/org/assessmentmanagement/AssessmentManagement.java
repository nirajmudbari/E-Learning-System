package org.assessmentmanagement;

import java.util.*;

// State Design Pattern
interface QuizState {
    void assignGrades(Quiz quiz);
}

class QuizInProgressState implements QuizState {
    @Override
    public void assignGrades(Quiz quiz) {
        System.out.println("Grades are not assigned yet. Quiz is in progress.");
    }
}

class QuizFinishedState implements QuizState {
    @Override
    public void assignGrades(Quiz quiz) {
        System.out.println("Grades assigned successfully. Quiz is finished.");
    }
}

class Quiz {
    private List<String> questions;
    private List<String> answers;
    private String instructions;
    private int passingCriteria;
    private int participants;
    private QuizState state;

    public Quiz() {
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.instructions = "";
        this.passingCriteria = 0;
        this.participants = 0;
        this.state = new QuizInProgressState();
    }

    public void addQuestion(String question, String answer) {
        questions.add(question);
        answers.add(answer);
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setPassingCriteria(int passingCriteria) {
        this.passingCriteria = passingCriteria;
    }

    public void checkStatus() {
        System.out.println("Number of students participated: " + participants);
    }

    public void finishQuiz() {
        state = new QuizFinishedState();
    }

    public void assignGrades() {
        state.assignGrades(this);
    }

    public void incrementParticipants() {
        participants++;
    }
}

public class AssessmentManagement {
    private static Scanner scanner = new Scanner(System.in);
    private static Admin admin = Admin.getInstance();

       public static void addQuestionToQuiz() {
        System.out.print("Enter quiz index: ");
        int quizIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter question: ");
        String question = scanner.nextLine();
        System.out.print("Enter answer: ");
        String answer = scanner.nextLine();
        admin.addQuestionToQuiz(quizIndex, question, answer);
    }

    public static void setInstructionsForQuiz() {
        System.out.print("Enter quiz index: ");
        int quizIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter instructions: ");
        String instructions = scanner.nextLine();
        admin.setInstructionsForQuiz(quizIndex, instructions);
    }

    public static void setPassingCriteriaForQuiz() {
        System.out.print("Enter quiz index: ");
        int quizIndex = scanner.nextInt();
        System.out.print("Enter passing criteria: ");
        int passingCriteria = scanner.nextInt();
        admin.setPassingCriteriaForQuiz(quizIndex, passingCriteria);
    }

    public static void checkQuizStatus() {
        System.out.print("Enter quiz index: ");
        int quizIndex = scanner.nextInt();
        admin.checkQuizStatus(quizIndex);
    }

    public static void finishQuiz() {
        System.out.print("Enter quiz index: ");
        int quizIndex = scanner.nextInt();
        admin.finishQuiz(quizIndex);
    }

    public static void assignGradesForQuiz() {
        System.out.print("Enter quiz index: ");
        int quizIndex = scanner.nextInt();
        admin.assignGradesForQuiz(quizIndex);
    }
}
