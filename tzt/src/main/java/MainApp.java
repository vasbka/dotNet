import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext
                ("C:\\Users\\Vasyl_Honcharenko\\Desktop\\nure\\net\\tzt\\src\\main\\java\\context.xml");

        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        System.out.println(obj.getMessage());
    }
}