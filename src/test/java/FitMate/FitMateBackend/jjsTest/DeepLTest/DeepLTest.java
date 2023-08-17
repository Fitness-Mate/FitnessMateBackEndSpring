package FitMate.FitMateBackend.jjsTest.DeepLTest;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DeepLTest {

    @Value("${deepl-api-key}")
    private String key;

    @Test
    public void deepLTest() throws Exception {
        Translator translator = new Translator(key);
        TextResult textResult = translator.translateText("Hello World!", "EN", "KO");
        String text = textResult.getText();
        System.out.println(text);

        List<TextResult> textResults = translator.translateText(List.of("Hello World!", "My name is Jisung", "you look so beautiful"), "EN", "KO");
        System.out.println(textResults.get(0).getText());
        System.out.println(textResults.get(1).getText());
        System.out.println(textResults.get(2).getText());
    }
}
