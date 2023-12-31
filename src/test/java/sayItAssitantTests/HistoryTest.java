/**
 * JUnit test file for History class
 */
package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sayItAssistant.*;
import sayItAssistant.data.Answer;
import sayItAssistant.data.History;
import sayItAssistant.data.Question;
import sayItAssistant.mocking.MockHistory;

/**
 * @author Chanho Jeon
 *
 */
class HistoryTest {

	/**
	 * @throws java.lang.Exception
	 * Create QuestionsForTest.txt file before each test
	 */
	@BeforeEach
	void setUp() throws Exception {
		File dbFile = new File("./DB/QuestionsForTest.txt");
		FileWriter fw = new FileWriter(dbFile);
		fw.write("Question for Test1\n");
		fw.flush();
		fw.write("Answer for Test1\n");
		fw.flush();
		fw.write("Question for Test2\n");
		fw.flush();
		fw.write("Answer for Test2\n");
		fw.write("Question for Test3\n");
		fw.flush();
		fw.write("Answer for Test3\n");
		fw.flush();
		fw.close();
	}

	/**
	 * @throws java.lang.Exception
	 * Delete QuestionForTest.txt file after each test
	 */
	@AfterEach
	void tearDown() throws Exception {
		File dbFile = new File("./DB/QuestionsForTest.txt");
		dbFile.delete();
	}

	/**
	 * Test method for {@link main.java.History#History()}.
	 * Test if the constructor of History class correctly load Strings from Question.txt
	 * The Question.txt file has two sets of Strings: question, answer
	 * Check if the String is saved into the right object
	 * Use MockHistory class, so the data base won't be changed by running the test
	 */
	@Test
	void testHistoryConstructor() {
		History csTest = new MockHistory();
		assertEquals("Question for Test1", csTest.getHistory().get(0).getQuestionString());
		assertEquals("Answer for Test1", csTest.getHistory().get(0).getAnswerObject().getAnswerString());
		assertEquals("Question for Test2", csTest.getHistory().get(1).getQuestionString());
		assertEquals("Answer for Test2", csTest.getHistory().get(1).getAnswerObject().getAnswerString());
	}

	/**
	 * Test method for {@link main.java.History#addQuestion(main.java.Question)}.
	 * Test if addQuestion() method increases the size of history array
	 * Use MockHistory class, so the data base won't be changed by running the test
	 */
	@Test
	void testAddSizeIncrease() {
		History csTest = new MockHistory();
		assertEquals(3, csTest.getHistory().size());
		csTest.addQuestion(new Question("New Question Added", new Answer("New Answer Added")));
		assertEquals(4, csTest.getHistory().size());
	}

	/**
	 * Test method for {@link main.java.History#addQuestion(main.java.Question)}.
	 * Test if addQuestion() method add question and answer
	 * Test if addQeustion() method put new element at front
	 * Use MockHistory class, so the data base won't be changed by running the test
	 */
	@Test
	void testAddedQuestionOnTop() {
		History csTest = new MockHistory();
		csTest.addQuestion(new Question("New Question Added", new Answer("New Answer Added")));
		assertEquals("New Question Added", csTest.getHistory().get(0).getQuestionString());
		assertEquals("New Answer Added", csTest.getHistory().get(0).getAnswerObject().getAnswerString());
	}

	/**
	 * Test method for {@link main.java.History#addQuestion(main.java.Question)}.
	 * Test if addQuestion() still contains existing questions
	 * Use MockHistory class, so the data base won't be changed by running the test
	 */
	@Test
	void testAddExistingQuestion() {
		History csTest = new MockHistory();
		csTest.addQuestion(new Question("New Question Added", new Answer("New Answer Added")));
		assertEquals("Question for Test1", csTest.getHistory().get(1).getQuestionString());
		assertEquals("Answer for Test1", csTest.getHistory().get(1).getAnswerObject().getAnswerString());
		assertEquals("Question for Test2", csTest.getHistory().get(2).getQuestionString());
		assertEquals("Answer for Test2", csTest.getHistory().get(2).getAnswerObject().getAnswerString());
	}

	/**
	 * Test method for {@link main.java.History#addQuestion(main.java.Question)}.
	 * Test if addQuestion() save questions into text file
	 * Use MockHistory class, so the data base won't be changed by running the test
	 */
	@Test
	void testAddAutoSave() throws Exception{
		History csTest = new MockHistory();
		csTest.addQuestion(new Question("New Question Added", new Answer("New Answer Added")));
		BufferedReader br = new BufferedReader(new FileReader("./DB/QuestionsForTest.txt"));
		assertEquals("New Question Added", br.readLine());
		assertEquals("New Answer Added", br.readLine());
		assertEquals("Question for Test1", br.readLine());
		assertEquals("Answer for Test1", br.readLine());
		assertEquals("Question for Test2", br.readLine());
		assertEquals("Answer for Test2", br.readLine());
		br.close();
	}

	/**
	 * Test method for {@link main.java.History#removeQuestion(int)}.
	 * Test if removeQuestion() decreases size of history
	 * Use MockHistory class, so the data base won't be changed by running the test
	 */
	@Test
	void testRemoveSizeDecrease() {
		History csTest = new MockHistory();
		assertEquals(3, csTest.getHistory().size());
		csTest.removeQuestion(0);
		assertEquals(2, csTest.getHistory().size());
	}

	/**
	 * Test method for {@link main.java.History#removeQuestion(int)}.
	 * Test if removeQuestion() does not affect non-removing questions
	 * Use MockHistory class, so the data base won't be changed by running the test
	 */
	@Test
	void testRemoveExistingQuestion() {
		History csTest = new MockHistory();
		csTest.removeQuestion(1);
		assertEquals("Question for Test1", csTest.getHistory().get(0).getQuestionString());
		assertEquals("Answer for Test1", csTest.getHistory().get(0).getAnswerObject().getAnswerString());
		assertEquals("Question for Test3", csTest.getHistory().get(1).getQuestionString());
		assertEquals("Answer for Test3", csTest.getHistory().get(1).getAnswerObject().getAnswerString());
	}

	/**
	 * Test method for {@link main.java.History#removeQuestion(int)}.
	 * Test if removeQuestion() save questions into text file
	 * Use MockHistory class, so the data base won't be changed by running the test
	 */
	@Test
	void testRemoveAutoSave() throws Exception{
		History csTest = new MockHistory();
		csTest.removeQuestion(1);
		BufferedReader br = new BufferedReader(new FileReader("./DB/QuestionsForTest.txt"));
		assertEquals("Question for Test1", br.readLine());
		assertEquals("Answer for Test1", br.readLine());
		assertEquals("Question for Test3", br.readLine());
		assertEquals("Answer for Test3", br.readLine());
		br.close();
	}

	@Test
	void testMultiAddRemove() {
		History csTest = new MockHistory();
		csTest.addQuestion(new Question("First Multiple Question", new Answer("First Multiple Answer")));
		csTest.addQuestion(new Question("Second Multiple Question", new Answer("Second Multiple Answer")));
		csTest.removeQuestion(2);
		csTest.addQuestion(new Question("Third Multiple Question", new Answer("Third Multiple Answer")));
		csTest.removeQuestion(1);
		assertEquals("Third Multiple Question", csTest.getHistory().get(0).getQuestionString());
		assertEquals("Third Multiple Answer", csTest.getHistory().get(0).getAnswerObject().getAnswerString());
		assertEquals("First Multiple Question", csTest.getHistory().get(1).getQuestionString());
		assertEquals("First Multiple Answer", csTest.getHistory().get(1).getAnswerObject().getAnswerString());
		assertEquals("Question for Test2", csTest.getHistory().get(2).getQuestionString());
		assertEquals("Answer for Test2", csTest.getHistory().get(2).getAnswerObject().getAnswerString());
		assertEquals("Question for Test3", csTest.getHistory().get(3).getQuestionString());
		assertEquals("Answer for Test3", csTest.getHistory().get(3).getAnswerObject().getAnswerString());

		csTest = new MockHistory();
		assertEquals(4, csTest.getHistory().size());
		assertEquals("Third Multiple Question", csTest.getHistory().get(0).getQuestionString());
		assertEquals("Third Multiple Answer", csTest.getHistory().get(0).getAnswerObject().getAnswerString());
		assertEquals("First Multiple Question", csTest.getHistory().get(1).getQuestionString());
		assertEquals("First Multiple Answer", csTest.getHistory().get(1).getAnswerObject().getAnswerString());
		assertEquals("Question for Test2", csTest.getHistory().get(2).getQuestionString());
		assertEquals("Answer for Test2", csTest.getHistory().get(2).getAnswerObject().getAnswerString());
		assertEquals("Question for Test3", csTest.getHistory().get(3).getQuestionString());
		assertEquals("Answer for Test3", csTest.getHistory().get(3).getAnswerObject().getAnswerString());
	}

}
