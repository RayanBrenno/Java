package ProjetoBiblioteca;

public class Main {

    public static void main(String[] args) {

        // Estabelece a conexão com o banco de dados,
        // realiza a alimentação de dados no sistema (inserção de alguns livros e usuários)
        // e inicia a interface de login, dando inicio a interação com o usuário.
        ConexaoBD.criarConexao();
        Alimentacao.alimentacaoGeral();
        UI.Login();

    }

}