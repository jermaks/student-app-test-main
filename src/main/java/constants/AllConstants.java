package constants;

public class AllConstants {

    public static class GenderConstants {

        public static final String MALE = "MALE";
        public static final String FEMALE = "FEMALE";
        public static final String OTHER = "OTHER";
    }

    public static class Messages {
        public static final String STUDENT_SUCCESSFULLY_ADDED = "Student successfully added";
        public static final String WAS_ADDED_TO_THE_SYSTEM = "%s was added to the system";
        public static final String STUDENT_DELETED = "Student deleted";
        public static final String STUDENT_UPDATE_WAS_SUCCESSFUL = "Student update was successful";
        public static final String WAS_UPDATED = "%s was updated";
        public static final String NAME_FIELD_ERROR_MESSAGE = "Please enter student name";
        public static final String EMAIL_FIELD_ERROR_MESSAGE = "Please enter student email";
        public static final String GENDER_FIELD_ERROR_MESSAGE = "Please select a gender";
        public static final String INCORRECT_EMAIL_TYPE_ERROR = "There was an issue";
        public static final String VALIDATION_FAILED_400 = "Validation failed for object='student'. Error count: 1 [400] [Bad Request]";
    }
}