public class Main {
    public static void main(String[] args) {
        String text = "As linguagens de programação são ferramentas essenciais que permitem aos desenvolvedores comunicar-se com computadores para criar software. Elas oferecem uma sintaxe e regras específicas que transformam instruções humanas em códigos executáveis pelas máquinas. Existem diferentes paradigmas de linguagens, como as linguagens imperativas (ex: C, Java), que focam em dar comandos sequenciais, e as funcionais (ex: Haskell, Lisp), que tratam funções como elementos fundamentais.\n" +
        "As linguagens de alto nível, como Python e JavaScript, são mais próximas da linguagem humana e abstraem a complexidade do hardware, facilitando o desenvolvimento. Já as de baixo nível, como Assembly, oferecem mais controle sobre o hardware, mas são mais difíceis de usar.\n" +
        "A escolha da linguagem depende do contexto, seja para o desenvolvimento web, de sistemas embarcados, análise de dados ou inteligência artificial. Cada linguagem tem suas próprias vantagens, limitações e casos de uso ideais.";

        String regex = "linguagens";  

        ERRepository repository = new ERRepository(text, regex);

        repository.execute();

        repository.toPrint();

        repository.toJSON();
    }
}
