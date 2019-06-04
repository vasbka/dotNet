import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

public class Main {
    public static void main(String[] args) throws Exception {

        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = new FileOutputStream(new File("create_table.docx"));
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        String line;
        List<Test> tests = new ArrayList<>();

        while((line = in.readLine()) != null) {
            Test test = new Test();
            if (line.endsWith("?")) {
                test.setQuestion(line);
                test.getAnswers().add(in.readLine());
                test.getAnswers().add(in.readLine());
                test.getAnswers().add(in.readLine());
            }
            if (test.getQuestion() != null) {
                tests.add(test);
            }
        }
        in.close();


        int rowCount = 0;
        tests.forEach(test -> {
            document.createParagraph().createRun().setText(test.getQuestion());
            XWPFTable table = document.createTable(1, 3);
            CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
            
            width.setType(STTblWidth.DXA);
            width.setW(BigInteger.valueOf(9072));

            List<String> answers = test.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                XWPFTableCell cell = table.getRow(rowCount).getCell(i);
                XWPFParagraph xwpfParagraph = cell.addParagraph();
                xwpfParagraph.setAlignment(ParagraphAlignment.CENTER);
                xwpfParagraph.createRun().setText(answers.get(i));
            }
            document.createParagraph().createRun().setText("");
        });
        document.write(out);
        out.close();
    }
}

class Test {
    String question;
    List<String> answers;

    public Test() {
        answers = new ArrayList<>();
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
