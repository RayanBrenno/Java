package ProjetoBiblioteca;

import java.sql.*;
import java.time.LocalDate;
import java.util.Random;

public class Alimentacao {

    public static void createTableLivros(){

        String criarTabelaLivros =
                "CREATE TABLE if not exists livros (" +
                        "idLivro INT AUTO_INCREMENT PRIMARY KEY," +
                        "titulo VARCHAR(100)," +
                        "autor VARCHAR(45)," +
                        "editora VARCHAR(45)," +
                        "anoPublicacao VARCHAR(45)," +
                        "genero VARCHAR(45)," +
                        "situacao VARCHAR(45)" +
                        ");";

        //Estabelece uma conexão com o banco e executa a query"
        ConexaoBD.criarConexao();
        ConexaoBD.comandoCreate(criarTabelaLivros);
    }

    public static void createTableUsuarios(){

        String criarTabelaUsuarios =
                "CREATE TABLE if not exists usuarios(" +
                        "idUsuario INT AUTO_INCREMENT PRIMARY KEY," +
                        "nome VARCHAR(100),"+
                        "email VARCHAR(45),"+
                        "endereco VARCHAR(45),"+
                        "telefone VARCHAR(16),"+
                        "cpf CHAR(14),"+
                        "senha VARCHAR(100),"+
                        "tipo VARCHAR(20)" +
                        ");";

        ConexaoBD.comandoCreate(criarTabelaUsuarios);
    }

    public static void createTableAluguel(){

        String criarTabelaAlugar =
                "create table if not exists aluguel(" +
                        "idAlugar INT AUTO_INCREMENT PRIMARY KEY," +
                        "idLivro INT," +
                        "idUsuario INT," +
                        "dataAluguel varchar(45)," +
                        "FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario) ON DELETE CASCADE ON UPDATE CASCADE," +
                        "FOREIGN KEY (idLivro) REFERENCES livros(idLivro) ON DELETE CASCADE ON UPDATE CASCADE"+
                        ");";

        ConexaoBD.comandoCreate(criarTabelaAlugar);
    }

    public static void insertUsuarios(){

        String[][] usuarios = {
                {"João da Silva", "joao@gmail.com", "Rua A, 123", "(62) 1 2345-6789", "123.456.789-01", "senha123", "Cliente"},
                {"Maria Oliveira", "maria@gmail.com", "Avenida B, 456", "(62) 9 8765-4321", "987.654.321-02", "senha456", "Funcionario"},
                {"Pedro Santos", "pedro@gmail.com", "Travessa C, 789", "(62) 9 9999-9999", "999.999.999-03", "senha789", "Cliente"},
                {"Ana Souza", "ana@gmail.com", "Rua D, 101", "(62) 1 1122-2333", "111.222.333-04", "senhaabc", "Cliente"},
                {"Carlos Ferreira", "carlos@gmail.com", "Avenida E, 202", "(62) 4 4455-5666", "444.555.666-05", "senhadef", "Funcionario"},
                {"Gabriel Lima", "gabriel@gmail.com", "Rua F, 303", "(62) 1 2312-3123", "123.123.123-45", "senha123", "Cliente"},
                {"Amanda Pereira", "amanda@gmail.com", "Avenida G, 404", "(62) 9 8765-1234", "987.651.234-56", "senha456", "Cliente"},
                {"Lucas Oliveira", "lucas@gmail.com", "Rua H, 505", "(62) 9 9999-8888", "999.988.887-65", "senha789", "Cliente"},
                {"Isabela Santos", "isabela@gmail.com", "Travessa I, 606", "(62) 9 9988-7766", "998.877.665-43", "senhaabc", "Cliente"},
                {"Rafaela Ferreira", "rafaela@gmail.com", "Avenida J, 707", "(62) 4 4445-5566", "444.455.566-77", "senhadef", "Cliente"}
        };

        //Itera sobre os usuários e insere os dados no banco
        for (String[] usuario : usuarios) {
            StringBuilder sql = new StringBuilder("INSERT INTO usuarios (nome, email, endereco, telefone, cpf, senha, tipo) VALUES (");
            for (String inseri : usuario) {
                sql.append("'").append(inseri).append("',");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1));

            ConexaoBD.comandoInsert((sql+");"));
        }
    }

    public static void insertLivros(){

        //Matriz com os dados dos livros
        String[][] livros = {
                {"Harry Potter e a Pedra Filosofal", "J.K. Rowling", "Rocco", "1997", "Fantasia"},
                {"Harry Potter e a Câmara Secreta", "J.K. Rowling", "Rocco", "1998", "Fantasia"},
                {"Harry Potter e o Prisioneiro de Azkaban", "J.K. Rowling", "Rocco", "1999", "Fantasia"},
                {"Harry Potter e o Cálice de Fogo", "J.K. Rowling", "Rocco", "2000", "Fantasia"},
                {"Harry Potter e a Ordem da Fênix", "J.K. Rowling", "Rocco", "2003", "Fantasia"},
                {"Harry Potter e o Enigma do Príncipe", "J.K. Rowling", "Rocco", "2005", "Fantasia"},
                {"Harry Potter e as Relíquias da Morte", "J.K. Rowling", "Rocco", "2007", "Fantasia"},
                {"Percy Jackson e o Ladrão de Raios", "Rick Riordan", "Intrínseca", "2005", "Fantasia"},
                {"Percy Jackson e o Mar de Monstros", "Rick Riordan", "Intrínseca", "2006", "Fantasia"},
                {"Percy Jackson e a Maldição do Titã", "Rick Riordan", "Intrínseca", "2007", "Fantasia"},
                {"Percy Jackson e a Batalha do Labirinto", "Rick Riordan", "Intrínseca", "2008", "Fantasia"},
                {"Percy Jackson e o Último Olimpiano", "Rick Riordan", "Intrínseca", "2009", "Fantasia"},
                {"A Guerra dos Tronos", "George R.R. Martin", "Leya", "1996", "Fantasia"},
                {"A Fúria dos Reis", "George R.R. Martin", "Leya", "1998", "Fantasia"},
                {"A Tormenta de Espadas", "George R.R. Martin", "Leya", "2000", "Fantasia"},
                {"O Festim dos Corvos", "George R.R. Martin", "Leya", "2005", "Fantasia"},
                {"A Dança dos Dragões", "George R.R. Martin", "Leya", "2011", "Fantasia"},
                {"Jogos Vorazes", "Suzanne Collins", "Rocco", "2008", "Distopia"},
                {"Em Chamas", "Suzanne Collins", "Rocco", "2009", "Distopia"},
                {"A Esperança", "Suzanne Collins", "Rocco", "2010", "Distopia"},
                {"A Cantiga dos Pássaros e das Serpentes", "Suzanne Collins", "Rocco", "2020", "Distopia"},
                {"A Sociedade do Anel", "J.R.R. Tolkien", "HarperCollins", "1954", "Fantasia"},
                {"As Duas Torres", "J.R.R. Tolkien", "HarperCollins", "1954", "Fantasia"},
                {"O Retorno do Rei", "J.R.R. Tolkien", "HarperCollins", "1955", "Fantasia"},
                {"Os Homens que Não Amavam as Mulheres", "Stieg Larsson", "Companhia das Letras", "2005", "Mistério/Thriller"},
                {"A Menina que Brincava com Fogo", "Stieg Larsson", "Companhia das Letras", "2006", "Mistério/Thriller"},
                {"A Rainha do Castelo de Ar", "Stieg Larsson", "Companhia das Letras", "2007", "Mistério/Thriller"},
                {"A Bússola de Ouro", "Philip Pullman", "Objetiva", "1995", "Fantasia"},
                {"A Faca Sutil", "Philip Pullman", "Objetiva", "1997", "Fantasia"},
                {"A Luneta Âmbar", "Philip Pullman", "Objetiva", "2000", "Fantasia"},
                {"Divergente", "Veronica Roth", "Rocco", "2011", "Distopia"},
                {"Insurgente", "Veronica Roth", "Rocco", "2012", "Distopia"},
                {"Convergente", "Veronica Roth", "Rocco", "2013", "Distopia"},
                {"O Leão, a Feiticeira e o Guarda-Roupa", "C.S. Lewis", "Martins Fontes", "1950", "Fantasia"},
                {"Príncipe Caspian", "C.S. Lewis", "Martins Fontes", "1951", "Fantasia"},
                {"A Viagem do Peregrino da Alvorada", "C.S. Lewis", "Martins Fontes", "1952", "Fantasia"},
                {"A Cadeira de Prata", "C.S. Lewis", "Martins Fontes", "1953", "Fantasia"},
                {"O Cavalo e seu Menino", "C.S. Lewis", "Martins Fontes", "1954", "Fantasia"},
                {"O Sobrinho do Mago", "C.S. Lewis", "Martins Fontes", "1955", "Fantasia"},
                {"A Última Batalha", "C.S. Lewis", "Martins Fontes", "1956", "Fantasia"},
                {"Batman: O Cavaleiro das Trevas", "Frank Miller", "DC Comics", "1986", "Super-herói"},
                {"Homem-Aranha: A Última Caçada de Kraven", "J.M. DeMatteis", "Marvel Comics", "1987", "Super-herói"},
                {"Homem-Aranha: O Outro", "J. Michael Straczynski", "Marvel Comics", "2005", "Super-herói"},
                {"Homem-Aranha: O Último Desejo", "Joe Kelly", "Marvel Comics", "2002", "Super-herói"},
                {"O Incrível Hulk: A Transformação de Banner", "Peter David", "Marvel Comics", "1989", "Super-herói"},
                {"O Incrível Hulk: Future Imperfect", "Peter David", "Marvel Comics", "1992", "Super-herói"},
                {"O Incrível Hulk: Planeta Hulk", "Greg Pak", "Marvel Comics", "2006", "Super-herói"},
                {"Homem de Ferro: O Invencível", "David Michelinie", "Marvel Comics", "1978", "Super-herói"},
                {"Homem de Ferro: O Demônio na Garrafa", "David Michelinie", "Marvel Comics", "1979", "Super-herói"},
                {"Homem de Ferro: Extremis", "Warren Ellis", "Marvel Comics", "2005", "Super-herói"}
        };

        //Itera sobre os livros e insere os dados no banco
        for (String[] livro : livros) {

            StringBuilder sql = new StringBuilder("INSERT INTO livros (titulo, autor, editora, anoPublicacao, genero, situacao) VALUES (");
            for (String inseri : livro) {
                sql.append("'").append(inseri).append("',");
            }
            Random random = new Random();
            // Configura a situação do livro escolhendo aleatoriamente entre Alugado e Disponível
            String inseri = random.nextInt(3) == 0 ? "Alugado" : "Disponível";
            sql.append("'").append(inseri).append("');");

            ConexaoBD.comandoInsert(sql.toString());
        }

    }

    public static String maximo3Aluguel() {
        // Esse método garante que nenhum usuário tenha mais de três livros alugados, a partir dos aluguéis gerados aleatoriamente

        int alugueis;
        String idUsuario;

        ConexaoBD.criarConexao();
        try (Connection conexao = ConexaoBD.conn) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Consulta SQL para obter o número total de usuários
            String sqlQuantidadeUsuario = "SELECT COUNT(*) AS total FROM usuarios";
            // Consulta SQL para contar o número de aluguéis de um usuário específico
            String sqlContagemAlugueis = "SELECT COUNT(*) AS alugueis FROM aluguel WHERE idUsuario=?";
            Random random = new Random();

            try (Statement declaracaoQuantidadeUsuario = conexao.createStatement();
                 ResultSet resultadoQuantidadeUsuario = declaracaoQuantidadeUsuario.executeQuery(sqlQuantidadeUsuario);
                 PreparedStatement declaracaoContagemAlugueis = conexao.prepareStatement(sqlContagemAlugueis)) {

                // Obtém o número total de usuários
                int quantidadeUsuarios = 0;
                if (resultadoQuantidadeUsuario.next()) {
                    quantidadeUsuarios = resultadoQuantidadeUsuario.getInt("total");
                }

                // Gera um número aleatório correspondente ao ID de um usuário
                int numeroAleatorio = random.nextInt(quantidadeUsuarios) + 1;
                idUsuario = String.valueOf(numeroAleatorio);
                declaracaoContagemAlugueis.setString(1, idUsuario);

                try (ResultSet resultadoContagem = declaracaoContagemAlugueis.executeQuery()) {
                    alugueis = 0;
                    // Obtém o número de aluguéis do usuário
                    if (resultadoContagem.next()) {
                        alugueis = resultadoContagem.getInt("alugueis");
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Retorna o ID do usuário se ele tiver menos de 3 aluguéis, senão, chama recursivamente o método até encontrar um usuário com menos de 3 aluguéis
        if (alugueis < 3) {
            return idUsuario;
        } else {
            return maximo3Aluguel();
        }
    }

    public static void insertAluguel(){

        ConexaoBD.criarConexao();
        try(Connection conexao = ConexaoBD.conn;){
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Consulta SQL para selecionar os IDs dos livros que estão alugados
            String sqlIDLivro = "SELECT idLivro FROM livros WHERE situacao='Alugado'";
            // Consulta SQL para inserir dados de aluguel na tabela 'aluguel'
            String sqlInsertTable = "INSERT INTO aluguel(idLivro, idUsuario, dataAluguel) VALUES(?,?,?)";

            try (Statement declaracaoIDLivro = conexao.createStatement();
                 ResultSet resultadoIDLivro = declaracaoIDLivro.executeQuery(sqlIDLivro);
                 PreparedStatement declaracaoInsert = conexao.prepareStatement(sqlInsertTable)) {

                Random random = new Random();

                while (resultadoIDLivro.next()) {
                    // Gera uma data aleatória para o aluguel
                    LocalDate hoje = LocalDate.now();
                    LocalDate dataAleatoria = hoje.minusDays(random.nextInt(20));
                    String dataAluguel = dataAleatoria.toString();

                    String idUsuario = maximo3Aluguel();
                    String idLivro = resultadoIDLivro.getString("idLivro");

                    // Define os parâmetros da query de inserção
                    declaracaoInsert.setString(1, idLivro);
                    declaracaoInsert.setString(2, idUsuario);
                    declaracaoInsert.setString(3, dataAluguel);

                    declaracaoInsert.executeUpdate();
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void alimentacaoGeral(){

        createTableLivros();
        createTableUsuarios();
        createTableAluguel();

        try (Connection conexao = ConexaoBD.conn) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sqlQtdLivros = "SELECT COUNT(*) AS total FROM livros";
            String sqlQtdUsuarios = "SELECT COUNT(*) AS total FROM usuarios";
            String sqlQtdAluguel = "SELECT COUNT(*) AS total FROM aluguel";

            try (Statement declaracaoLivros = conexao.createStatement();
                 ResultSet qtdLivros = declaracaoLivros.executeQuery(sqlQtdLivros);
                 Statement declaracaoUsuarios = conexao.createStatement();
                 ResultSet qtdUsuarios = declaracaoUsuarios.executeQuery(sqlQtdUsuarios);
                 Statement declaracaoAluguel = conexao.createStatement();
                 ResultSet qtdAluguel = declaracaoAluguel.executeQuery(sqlQtdAluguel);){

                // Verifica se a tabela "usuarios" está vazia e insere dados de exemplo se estiver
                if (qtdUsuarios.next()) {
                    int quantidadeUsuarios = qtdUsuarios.getInt("total");
                    if (quantidadeUsuarios == 0) {insertUsuarios();}
                }

                // Verifica se a tabela "livros" está vazia e insere dados de exemplo se estiver
                if (qtdLivros.next()) {
                    int quantidadeLivros = qtdLivros.getInt("total");
                    if (quantidadeLivros == 0) {insertLivros();}
                }

                // Verifica se a tabela "aluguel" está vazia e insere dados de exemplo se estiver
                if (qtdAluguel.next()) {
                    int quantidadeAluguel = qtdAluguel.getInt("total");
                    if (quantidadeAluguel == 0) {insertAluguel();}
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}