package com.example.autogradertyp.backend;

public class PlagiarismInstance {


    private String userName;

    private String studentNumber;

    private String plagiarismResult;

    private String numberOfPlagiarismMatches;

    public PlagiarismInstance (String userName, String studentNumber, String plagiarismResult, Long numberOfPlagiarismMatches) {
        this.userName = userName;
        this.studentNumber = studentNumber;
        this.plagiarismResult = plagiarismResult;
        this.numberOfPlagiarismMatches = String.valueOf(numberOfPlagiarismMatches);

    }

    /**
     * A method to get the username of the user
     *
     * @return The username of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * A method that sets the username of the user
     *
     * @param userName The username of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * A method that gets the university id
     *
     * @return The university id of the user
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * A method that sets the university id of the user
     *
     * @param studentNumber The university id of the user
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * Gets the plagiarism result
     *
     * @return the plagiarism result of the submission
     */
    public String getPlagiarismResult() {
        return plagiarismResult;
    }

    /**
     * Sets the plagiarism result
     *
     * @param plagiarismResult the plagiarism result of the submission
     */
    public void setPlagiarismResult(String plagiarismResult) {
        this.plagiarismResult = plagiarismResult;
    }

    /**
     * Sets the number of plagiarism matches
     *
     * @return the number of plagiarism matches
     */
    public String getNumberOfPlagiarismMatches() {
        return numberOfPlagiarismMatches;
    }

    /**
     * sets the number of plagiarism matches
     *
     * @param numberOfPlagiarismMatches the number of plagiarism matches
     */
    public void setNumberOfPlagiarismMatches(String numberOfPlagiarismMatches) {
        this.numberOfPlagiarismMatches = numberOfPlagiarismMatches;
    }
}
