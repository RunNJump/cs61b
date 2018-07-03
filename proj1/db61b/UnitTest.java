package db61b;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The suite of all JUnit tests for the qirkat package.
 *
 * @author P. N. Hilfinger
 */
public class UnitTest {

    /**
     * Run the JUnit tests in this package. Add xxxTest.class entries to
     * the arguments of runClasses to run other JUnit tests.
     */
    public static void main(String[] ignored) {
        /* textui.runClasses(); */
    }

    @Test
    public void getTest() {
        Table t = new Table(new String[]{"SID", "Lastname", "FirstName"});
        t.add(new String[]{"101", "Knowles", "Jason"});
        t.add(new String[]{"101", "Chan", "Valerie"});
        assertEquals("Knowles", t.get(0, 1));
    }

    @Test
    public void printTest() {
        Table t = new Table(new String[]{"CCN", "Num",
            "Dept", "Time", "Room", "Sem", "Year"});
        t.add(new String[]{"21228", "61A", "EECS",
            "2-3MWF", "1 Pimentel", "F", "2003"});
        t.add(new String[]{"21231", "61A", "EECS",
            "1-2MWF", "1 Pimentel", "S", "2004"});
        t.add(new String[]{
            "21229", "61B", "EECS", "11-12MWF", "155 Dwinelle", "F", "2003"});
        t.add(new String[]{"21232", "61B", "EECS",
            "1-2MWF", "2050 VLSB", "S", "2004"});
        t.add(new String[]{"21103", "54", "Math",
            "1-2MWF", "2050 VLSB", "F", "2003"});
        t.add(new String[]{"21105", "54", "Math",
            "1-2MWF", "1 Pimentel", "S", "2004"});
        t.add(new String[]{"21001", "1A", "English",
            "9-10MWF", "2301 Tolman", "F", "2003"});
        t.add(new String[]{"21005", "1A", "English",
            "230-5TuTh", "130 Wheeler", "S", "2004"});
        t.print();
    }

    @Test
    public void selectTest() {
        Table t = new Table(new String[]{"CCN", "Num",
            "Dept", "Time", "Room", "Sem", "Year"});
        t.add(new String[]{"21228", "61A", "EECS",
            "2-3MWF", "1 Pimentel", "F", "2003"});
        t.add(new String[]{"21231", "61A", "EECS",
            "1-2MWF", "1 Pimentel", "S", "2004"});
        t.add(new String[]{"21229", "61B", "EECS",
            "11-12MWF", "155 Dwinelle", "F", "2003"});
        t.add(new String[]{"21232", "61B", "EECS",
            "1-2MWF", "2050 VLSB", "S", "2004"});
        t.add(new String[]{"21103", "54", "Math",
            "1-2MWF", "2050 VLSB", "F", "2003"});
        t.add(new String[]{"21105", "54", "Math",
            "1-2MWF", "1 Pimentel", "S", "2004"});
        t.add(new String[]{"21001", "1A", "English",
            "9-10MWF", "2301 Tolman", "F", "2003"});
        t.add(new String[]{"21005", "1A", "English",
            "230-5TuTh", "130 Wheeler", "S", "2004"});
        List<String> newCol = new ArrayList<>();
        newCol.add("Time");
        newCol.add("Num");
        newCol.add("Dept");
        Condition cond = new Condition(new Column("Year", t), "<", "2004");
        Condition cond2 = new Condition(new Column("CCN", t), "<", "21200");
        List<Condition> condList = new ArrayList<>();
        condList.add(cond);
        condList.add(cond2);
        Table t1 = t.select(newCol, condList);
        assertEquals("English", t1.get(1, 2));
        assertEquals("Math", t1.get(0, 2));
        t1.print();
    }

    @Test
    public void selectTest2() {
        Table schedule = new Table(new String[]{"CCN", "Num",
            "Dept", "Time", "Room", "Sem", "Year"});
        schedule.add(new String[]{"21228", "61A", "EECS",
            "2-3MWF", "1 Pimentel", "F", "2003"});
        schedule.add(new String[]{"21231", "61A", "EECS",
            "1-2MWF", "1 Pimentel", "S", "2004"});
        schedule.add(new String[]{"21229", "61B", "EECS",
            "11-12MWF", "155 Dwinelle", "F", "2003"});
        schedule.add(new String[]{"21232", "61B", "EECS",
            "1-2MWF", "2050 VLSB", "S", "2004"});
        schedule.add(new String[]{"21103", "54", "Math",
            "1-2MWF", "2050 VLSB", "F", "2003"});
        schedule.add(new String[]{"21105", "54", "Math",
            "1-2MWF", "1 Pimentel", "S", "2004"});
        schedule.add(new String[]{"21001", "1A", "English",
            "9-10MWF", "2301 Tolman", "F", "2003"});
        schedule.add(new String[]{"21005", "1A", "English",
            "230-5TuTh", "130 Wheeler", "S", "2004"});

        Table enrolled = new Table(new String[]{"SID", "CCN", "Grade"});
        enrolled.add(new String[]{"101", "21228", "B"});
        enrolled.add(new String[]{"101", "21105", "B+"});
        enrolled.add(new String[]{"101", "21232", "A-"});
        enrolled.add(new String[]{"101", "21001", "B"});
        enrolled.add(new String[]{"102", "21231", "A"});
        enrolled.add(new String[]{"102", "21105", "A-"});
        enrolled.add(new String[]{"102", "21229", "A"});
        enrolled.add(new String[]{"102", "21001", "B+"});
        enrolled.add(new String[]{"103", "21105", "B+"});
        enrolled.add(new String[]{"103", "21005", "B+"});
        enrolled.add(new String[]{"104", "21228", "A-"});
        enrolled.add(new String[]{"104", "21229", "B+"});
        enrolled.add(new String[]{"104", "21105", "A-"});
        enrolled.add(new String[]{"104", "21005", "A-"});
        enrolled.add(new String[]{"105", "21228", "A"});
        enrolled.add(new String[]{"105", "21001", "B+"});
        enrolled.add(new String[]{"106", "21103", "A"});
        enrolled.add(new String[]{"106", "21001", "B"});
        enrolled.add(new String[]{"106", "21231", "A"});

        List<String> newCol = new ArrayList<>();
        newCol.add("Year");
        newCol.add("SID");
        newCol.add("CCN");
        Condition con1 = new Condition(new Column(
            "Year", schedule, enrolled), ">", "2003");
        List<Condition> conditions = new ArrayList<>();
        conditions.add(con1);

        Table t1 = schedule.select(enrolled, newCol, conditions);
        assertEquals("2004", t1.get(0, 0));
        assertEquals("102", t1.get(0, 1));
    }

    @Test
    public void selectTest3() {
        Table enrolled = new Table(new String[]{"SID", "CCN", "Grade"});
        enrolled.add(new String[]{"101", "21228", "B"});
        enrolled.add(new String[]{"101", "21105", "B+"});
        enrolled.add(new String[]{"101", "21232", "A-"});
        enrolled.add(new String[]{"101", "21001", "B"});
        enrolled.add(new String[]{"102", "21231", "A"});
        enrolled.add(new String[]{"102", "21105", "A-"});
        enrolled.add(new String[]{"102", "21229", "A"});
        enrolled.add(new String[]{"102", "21001", "B+"});
        enrolled.add(new String[]{"103", "21105", "B+"});
        enrolled.add(new String[]{"103", "21005", "B+"});
        enrolled.add(new String[]{"104", "21228", "A-"});
        enrolled.add(new String[]{"104", "21229", "B+"});
        enrolled.add(new String[]{"104", "21105", "A-"});
        enrolled.add(new String[]{"104", "21005", "A-"});
        enrolled.add(new String[]{"105", "21228", "A"});
        enrolled.add(new String[]{"105", "21001", "B+"});
        enrolled.add(new String[]{"106", "21103", "A"});
        enrolled.add(new String[]{"106", "21001", "B"});
        enrolled.add(new String[]{"106", "21231", "A"});

        Table students = new Table(new String[]{"SID", "Lastname",
            "Firstname", "SemEnter", "YearEnter", "Major"});
        students.add(new String[]{"101", "Knowles", "Jason",
            "F", "2003", "EECS"});
        students.add(new String[]{"102", "Chan", "Valerie",
            "S", "2003", "Math"});
        students.add(new String[]{"103", "Xavier", "Jonathan",
            "S", "2004", "LSUnd"});
        students.add(new String[]{"104", "Armstrong", "Thomas",
            "F", "2003", "EECS"});
        students.add(new String[]{"105", "Brown", "Shana",
            "S", "2004", "EECS"});
        students.add(new String[]{"106", "Chan", "Yangfan",
            "F", "2003", "LSUnd"});

        Condition con1 = new Condition(new Column(
            "CCN", students, enrolled), "=", "21001");
        List<Condition> conditions = new ArrayList<>();
        conditions.add(con1);

        List<String> col2 = new ArrayList<>();
        col2.add("Firstname");
        col2.add("Lastname");
        col2.add("Grade");

        Table t1 = students.select(enrolled, col2, conditions);
        t1.print();
        assertEquals("Jason", t1.get(0, 0));
    }

}
