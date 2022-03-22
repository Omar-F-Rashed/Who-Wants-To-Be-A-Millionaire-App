package ae.ac.adu.omar.assignment2;

import android.content.ContentValues;
import android.content.Context;
import ae.ac.adu.omar.assignment2.QuizContract.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLDatabaseHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "MillionaireQuiz.db";
    private final static int DATABASE_VERSION = 2;

    SQLiteDatabase db;
    private final static String SQL_CREATE_TABLE ="CREATE TABLE " +
            QuizContract.QuestionsTable.Table_Name +
            "(" +QuestionsTable._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            QuestionsTable.COLUMN_QUESTION  + " Text ," +
            QuestionsTable.OPTION1 +" Text ," +
            QuestionsTable.OPTION2 +" Text ," +
            QuestionsTable.OPTION3 +" Text ," +
            QuestionsTable.COLUMN_ANSWER_NUMBER +" INTEGER " + ")";
    private  final static String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + QuestionsTable.Table_Name;

    private final static String SQL_SELECT = "SELECT * FROM " +QuestionsTable.Table_Name;
    public SQLDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(SQL_CREATE_TABLE);
        SetQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    private void SetQuestions(){
        Questions q1 = new Questions("Which Disney character famously leaves a glass slipper behind at a royal ball?",
                "Elsa",
                "Sleeping Beauty",
                "Cinderella",
                3);
        addQuestion(q1);
        Questions q2 = new Questions("In the UK, the abbreviation NHS stands for National what Service?",
                "Humanity",
                "Health",
                "Honour",
                2);
        addQuestion(q2);
        Questions q3 = new Questions("Which of these brands was chiefly associated with the manufacture of household locks?",
                "Phillips",
                "Chubb",
                "Ronseal",
                2);
        addQuestion(q3);
        Questions q4 = new Questions("The hammer and sickle is one of the most recognisable symbols of which political ideology?",
                "Phillips",
                "Conservatism",
                "Communism",
                3);
        addQuestion(q4);
        Questions q5 = new Questions("Which toys have been marketed with the phrase “robots in disguise”?",
                "Transformers",
                "Sylvanian Families",
                "Hatchimals",
                1);
        addQuestion(q5);
        Questions q6 = new Questions("Obstetrics is a branch of medicine particularly concerned with what?",
                "Childbirth",
                "Broken bones",
                "Heart conditions",
                1);
        addQuestion(q6);
        Questions q7 = new Questions("In Doctor Who, what was the signature look of the fourth Doctor, as portrayed by Tom Baker?",
                "Bow-tie, braces and tweed jacket",
                "Wide-brimmed hat and extra long scarf",
                "Pinstripe suit and trainers",
                2);
        addQuestion(q7);
        Questions q8 = new Questions("Which of these religious observances lasts for the shortest period of time during the calendar year?",
                "Ramadan",
                "Diwali",
                "Lent",
                2);
        addQuestion(q8);
    }
    private void addQuestion(Questions question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionsTable.OPTION1,question.getOption1());
        cv.put(QuestionsTable.OPTION2,question.getOption2());
        cv.put(QuestionsTable.OPTION3,question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NUMBER,question.getAnswerNumber());
        db.insert(QuestionsTable.Table_Name,null,cv);
    }

    public List<Questions> getAllQuestions(){

        List<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery(SQL_SELECT, null);
        if (c.moveToFirst()){
            do{
                System.out.println(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.OPTION3)));
                question.setAnswerNumber(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUMBER)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
