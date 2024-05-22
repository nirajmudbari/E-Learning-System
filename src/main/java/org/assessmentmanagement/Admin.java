package org.assessmentmanagement;


import java.util.ArrayList;
import java.util.List;

// Singleton Design Pattern
public class Admin {
    private static Admin instance;
    private List<Quiz> quizzes;

    private Admin() {
        quizzes = new ArrayList<>();
    }

    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public void createQuiz() {
        Quiz quiz = new Quiz();
        quizzes.add(quiz);
        System.out.println("Quiz created successfully.");
    }

    public void addQuestionToQuiz(int quizIndex, String question, String answer) {
        if (quizIndex >= 0 && quizIndex < quizzes.size()) {
            quizzes.get(quizIndex).addQuestion(question, answer);
            System.out.println("Question added to quiz successfully.");
        } else {
            System.out.println("Invalid quiz index.");
        }
    }

    public void setInstructionsForQuiz(int quizIndex, String instructions) {
        if (quizIndex >= 0 && quizIndex < quizzes.size()) {
            quizzes.get(quizIndex).setInstructions(instructions);
            System.out.println("Instructions set for quiz successfully.");
        } else {
            System.out.println("Invalid quiz index.");
        }
    }

    public void setPassingCriteriaForQuiz(int quizIndex, int passingCriteria) {
        if (quizIndex >= 0 && quizIndex < quizzes.size()) {
            quizzes.get(quizIndex).setPassingCriteria(passingCriteria);
            System.out.println("Passing criteria set for quiz successfully.");
        } else {
            System.out.println("Invalid quiz index.");
        }
    }

    public void checkQuizStatus(int quizIndex) {
        if (quizIndex >= 0 && quizIndex < quizzes.size()) {
            quizzes.get(quizIndex).checkStatus();
        } else {
            System.out.println("Invalid quiz index.");
        }
    }

    public void finishQuiz(int quizIndex) {
        if (quizIndex >= 0 && quizIndex < quizzes.size()) {
            quizzes.get(quizIndex).finishQuiz();
            System.out.println("Quiz finished successfully.");
        } else {
            System.out.println("Invalid quiz index.");
        }
    }

    public void assignGradesForQuiz(int quizIndex) {
        if (quizIndex >= 0 && quizIndex < quizzes.size()) {
            quizzes.get(quizIndex).assignGrades();
        } else {
            System.out.println("Invalid quiz index.");
        }
    }
}

