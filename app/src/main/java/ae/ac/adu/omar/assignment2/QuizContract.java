package ae.ac.adu.omar.assignment2;

import android.provider.BaseColumns;

public final class QuizContract {
    public class QuestionsTable implements BaseColumns {
        public static final String Table_Name = "Quiz_Questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String OPTION1 = "option1";
        public static final String OPTION2 = "option2";
        public static final String OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NUMBER = "answer_nbr";
    }
}
