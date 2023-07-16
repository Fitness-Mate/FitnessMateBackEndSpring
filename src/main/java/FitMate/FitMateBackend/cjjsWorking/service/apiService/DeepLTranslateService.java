package FitMate.FitMateBackend.cjjsWorking.service.apiService;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeepLTranslateService {
    @Value("${deepl-api-key}")
    private String key;

    public String sendRequest(String source) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://deepl-translator.p.rapidapi.com/translate"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", key)
                .header("X-RapidAPI-Host", "deepl-translator.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString
                        ("{\"text\": \"" + source + "\",\"source\": \"EN\",\"target\": \"KO\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.body());

        String target = jsonObject.get("text").toString();
        log.info("[ 번역 완료 ]");
        log.info("source: " + source);
        log.info("target: " + target);

        return target; //번역문 반환
    }
}