package de.pjskilltrack.pjskilltrack.exception;

public class ProgressNotFoundException extends RuntimeException {
    public ProgressNotFoundException(final String skillName, final String studentEmail) {
        super("Progress for skill '" + skillName + "' and student '" + studentEmail + "' not found.");
    }
}
