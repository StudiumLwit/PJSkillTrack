package de.pjskilltrack.pjskilltrack.exception;

public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException(final Long skillId) {
        super("Skill with id '" + skillId + "' not found.");
    }
}
