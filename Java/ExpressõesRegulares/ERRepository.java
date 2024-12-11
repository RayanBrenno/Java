import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ERRepository {
    private HashMap<Integer, MatchValues> matchValues;
    private String text;
    private String expression;

    // Construtor
    public ERRepository(String text, String expression) {
        this.text = text;
        this.expression = expression;
        this.matchValues = new HashMap<>();
    }

    // Método para executar a expressão regular
    public HashMap<Integer, MatchValues> execute() {
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        int id = 1;

        while (matcher.find()) {
            MatchValues match = new MatchValues(id, matcher.group(), matcher.start(), matcher.end());
            matchValues.put(id++, match);
        }

        return matchValues;
    }

    // Método para imprimir o conteúdo do HashMap
    public void toPrint() {
        for (MatchValues match : matchValues.values()) {
            System.out.println(match);
        }
    }

    // Método para gerar JSON usando Gson e salvar em arquivo
    public void toJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type type = new TypeToken<HashMap<Integer, MatchValues>>() {}.getType();
        String json = gson.toJson(matchValues, type);

        try (FileWriter file = new FileWriter("src/Encontros.json")) {
            file.write(json);
            System.out.println("Arquivo JSON gerado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
