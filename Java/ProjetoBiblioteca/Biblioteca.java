package ProjetoBiblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Biblioteca {

    public static void cadastrarLivros(){

        // Criação dos campos de entrada de texto e botões
        JTextField titulo2 = new JTextField();
        JTextField autor2 = new JTextField();
        JTextField editora2 = new JTextField();
        JTextField anoPublicacao2 = new JTextField();
        JTextField genero2 = new JTextField();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancelar");

        // Array de objetos contendo rótulos e os campos correspondentes
        Object[] mensagem = {
                "Titulo:", titulo2,
                "Autor:", autor2,
                "Editora:", editora2,
                "Ano de publicação:", anoPublicacao2,
                "Gênero:", genero2,
        };

        Object[] botoes = {ok, cancel};

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(ok);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                if(titulo2.getText().isEmpty()
                        || autor2.getText().isEmpty()
                        || editora2.getText().isEmpty()
                        || anoPublicacao2.getText().isEmpty()
                        || genero2.getText().isEmpty()){

                    JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    cadastrarLivros();
                }else{

                    String titulo = titulo2.getText();
                    String autor = autor2.getText();
                    String editora = editora2.getText();
                    String anoPublicacao = anoPublicacao2.getText();
                    String genero = genero2.getText();
                    String status = "Disponível";

                    ConexaoBD.criarConexao();
                    try (Connection conexao = ConexaoBD.conn){

                        Class.forName("com.mysql.cj.jdbc.Driver");

                        String sql = "INSERT INTO livros (titulo, autor, editora, anoPublicacao, genero, situacao) VALUES (?, ?, ?, ?, ?, ?)";

                        // Execução da consulta preparada
                        try (PreparedStatement declaracao = conexao.prepareStatement(sql);) {
                            declaracao.setString(1, titulo);
                            declaracao.setString(2, autor);
                            declaracao.setString(3, editora);
                            declaracao.setString(4, anoPublicacao);
                            declaracao.setString(5, genero);
                            declaracao.setString(6, status);
                            declaracao.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso.", null, JOptionPane.INFORMATION_MESSAGE);

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao cadastrar livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (ClassNotFoundException | SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Retorna ao menu inicial após o cadastro ser concluído
                    UI.menuInicial();
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(cancel);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                JOptionPane.showMessageDialog(null, "Cadastro cancelado", "", JOptionPane.INFORMATION_MESSAGE);
                UI.menuInicial();
            }
        });

        // Exibe um diálogo de opção contendo os campos de entrada e os botões
        JOptionPane.showOptionDialog(null, mensagem,"Inserção de livro", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, botoes, null);

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void removerLivros(){

        // Cria um JComboBox para exibir os nomes dos livros a serem removidos
        JComboBox nome = new JComboBox();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancelar");

        ConexaoBD.criarConexao();
        try(Connection conexao = ConexaoBD.conn){
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "SELECT * FROM livros";
            try (Statement declaracao = conexao.createStatement(); ResultSet resultado = declaracao.executeQuery(sql);) {

                // Adiciona os nomes dos livros ao JComboBox
                while (resultado.next()) {
                    nome.addItem(resultado.getString("titulo"));
                }

            }

            Object[] mensagem = {"Qual livro deseja remover?", nome};
            Object[] botoes = {ok, cancel};

            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Fechando a primeira janela
                    Window window = SwingUtilities.getWindowAncestor(ok);
                    JDialog dialog = (JDialog) window;
                    dialog.dispose();//

                    // Obtém o título do livro selecionado no JComboBox
                    String delete = "delete from livros where titulo = '"+nome.getSelectedItem().toString()+"';";
                    // Executa o comando de exclusão
                    ConexaoBD.comandoDelete(delete);
                    JOptionPane.showMessageDialog(null, "Livro removido com sucesso.", null, JOptionPane.INFORMATION_MESSAGE);
                }
            });
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Fechando a primeira janela
                    Window window = SwingUtilities.getWindowAncestor(cancel);
                    JDialog dialog = (JDialog) window;
                    dialog.dispose();//

                    JOptionPane.showMessageDialog(null, "Remoção de livro cancelada.", "", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, mensagem,"Remoção de livro", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, botoes, null);

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover livros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Retorna ao menu inicial
        UI.menuInicial();

    }

    public static void consultarLivros(){

        ConexaoBD.criarConexao();
        try(Connection conexao = ConexaoBD.conn){
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "SELECT * FROM livros;";
            try (Statement declaracao = conexao.createStatement(); ResultSet resultado = declaracao.executeQuery(sql);) {
                // StringBuilder para construir a mensagem que será exibida
                StringBuilder mensagem = new StringBuilder();
                // Itera sobre o resultado da consulta
                while (resultado.next()) {

                    String titulo = resultado.getString("titulo");
                    String autor = resultado.getString("autor");
                    String editora = resultado.getString("editora");
                    String anoPublicacao = resultado.getString("anoPublicacao");
                    // Formata a representação do livro
                    String livroFormatado = String.format("%s, %s, %s, %s;\n", titulo, autor, editora, anoPublicacao);

                    mensagem.append(livroFormatado);
                    mensagem.append("Genero Literário: ").append(resultado.getString("genero")).append("\n");
                    mensagem.append("Status: ").append(resultado.getString("situacao")).append("\n\n");
                }
                // Verifica se há livros cadastrados
                if (mensagem.length() > 0) {
                    // Remove os últimos caracteres para evitar uma quebra de linha extra
                    mensagem.setLength(mensagem.length() - 2);
                    // Cria um JTextArea para exibir a mensagem
                    JTextArea textArea = new JTextArea(30,60);
                    textArea.setText(mensagem.toString());
                    textArea.setCaretPosition(0);

                    // Configuração do JTextArea para quebrar palavras longas em várias linhas
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    // Cria um JScrollPane com o JTextArea e configura a barra de rolagem vertical
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                    JButton aluguel = new JButton("Alugar livros");
                    JButton ok = new JButton("OK");

                    aluguel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Fechando a primeira janela
                            Window window = SwingUtilities.getWindowAncestor(aluguel);
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();//

                            alugarLivro();
                        }
                    });
                    ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Fechando a primeira janela
                            Window window = SwingUtilities.getWindowAncestor(ok);
                            JDialog dialog = (JDialog) window;
                            dialog.dispose();//

                            UI.menuInicial();
                        }
                    });

                    Object[] botoes = {ok, aluguel};

                    JOptionPane.showOptionDialog(null, scrollPane,"Livros cadastrados no sistema", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, botoes, null);

                } else {
                    JOptionPane.showMessageDialog(null, "Não há livros cadastrados.", "Livros Cadastrados", JOptionPane.INFORMATION_MESSAGE);
                }

            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar livros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            UI.menuInicial();
        }

    }

    public static void alugarLivro() {

        ConexaoBD.criarConexao();
        try (Connection conexao = ConexaoBD.conn) {

            String idUsuario = Validacao.idUsuarioArmazenado;
            String sqllIVRO = "SELECT idLivro, titulo FROM livros WHERE situacao = 'Disponível'";
            String sql = "INSERT INTO aluguel (idLivro, idUsuario, dataAluguel) VALUES (?, ?, ?)";
            String updateLivroSql = "UPDATE livros SET situacao = 'Alugado' WHERE idLivro = ?";
            String countAlugueisSql = "SELECT COUNT(*) AS total FROM aluguel WHERE idUsuario = ?";

            try (Statement declaracao = conexao.createStatement();
                 ResultSet resultado = declaracao.executeQuery(sqllIVRO);
                 PreparedStatement insert = conexao.prepareStatement(sql);
                 PreparedStatement updateLivro = conexao.prepareStatement(updateLivroSql);
                 PreparedStatement countAlugueis = conexao.prepareStatement(countAlugueisSql)) {

                // HashMap para armazenar os livros disponíveis
                HashMap<String, String> livrosDisponiveis = new HashMap<>();
                JComboBox<String> comboBoxLivros = new JComboBox<>();

                // Preenche o JComboBox com os títulos dos livros disponíveis
                while (resultado.next()) {
                    String idLivro = resultado.getString("idLivro");
                    String titulo = resultado.getString("titulo");
                    livrosDisponiveis.put(idLivro, titulo);
                    comboBoxLivros.addItem(titulo);
                }

                Object[] chamarComboBoxLivro = {
                        "Selecione o livro que deseja alugar:", comboBoxLivros
                };
                JButton cancelar = new JButton("Cancelar");
                JButton alugar = new JButton("Alugar");
                Object[] opcoes = {alugar, cancelar};

                cancelar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Fechando a primeira janela
                        Window window = SwingUtilities.getWindowAncestor(cancelar);
                        JDialog dialog = (JDialog) window;
                        dialog.dispose(); //

                        JOptionPane.showMessageDialog(null, "Operação Cancelada. Voltando ao Menu Inicial", null, JOptionPane.INFORMATION_MESSAGE);
                        UI.menuInicial();
                    }
                });
                alugar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Fechando a primeira janela
                        Window window = SwingUtilities.getWindowAncestor(alugar);
                        JDialog dialog = (JDialog) window;
                        dialog.dispose(); //

                        try {
                            // Verifica o número de aluguéis realizados pelo usuário
                            countAlugueis.setString(1, idUsuario);
                            ResultSet rs = countAlugueis.executeQuery();
                            if (rs.next()) {
                                int totalAlugueis = rs.getInt("total");
                                // Verifica se o usuário já alugou 3 livros
                                if (totalAlugueis >= 3) {
                                    JOptionPane.showMessageDialog(null, "Você já possui 3 livros alugados. Devolva um livro antes de alugar outro.", "Limite de Aluguéis", JOptionPane.WARNING_MESSAGE);
                                    UI.menuInicial();
                                } else {
                                    // Obtém a data atual e a data de devolução estimada (2 semanas após a data de aluguel)
                                    LocalDate dataAluguel = LocalDate.now();
                                    LocalDate dataDevolucao = dataAluguel.plusWeeks(2);
                                    String tituloLivroSelecionado = (String) comboBoxLivros.getSelectedItem();
                                    String idLivro = null;
                                    // Obtém o ID do livro selecionado
                                    for (Map.Entry<String, String> entry : livrosDisponiveis.entrySet()) {
                                        if (entry.getValue().equals(tituloLivroSelecionado)) {
                                            idLivro = entry.getKey();
                                            break;
                                        }
                                    }
                                    // Se o ID do livro foi encontrado
                                    if (idLivro != null) {
                                        try {
                                            // Insere o registro de aluguel na tabela 'aluguel'
                                            insert.setString(1, idLivro);
                                            insert.setString(2, idUsuario);
                                            insert.setString(3, dataAluguel.toString());
                                            insert.executeUpdate();

                                            // Atualiza o status do livro para 'Alugado'
                                            updateLivro.setString(1, idLivro);
                                            updateLivro.executeUpdate();

                                            // Exibe uma mensagem de sucesso e retorna ao menu inicial
                                            JOptionPane.showMessageDialog(null, "Aluguel realizado com sucesso.\nData de Devolução: " + dataDevolucao, null, JOptionPane.INFORMATION_MESSAGE);
                                            UI.menuInicial();
                                        } catch (SQLException ex) {
                                            ex.printStackTrace();
                                            JOptionPane.showMessageDialog(null, "Erro ao realizar o aluguel: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }
                            }

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erro ao verificar o número de aluguéis: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                JOptionPane.showOptionDialog(null, chamarComboBoxLivro, "Aluguel de livro", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, opcoes, null);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void devolverlivros() {

        ConexaoBD.criarConexao();
        try (Connection conexao = ConexaoBD.conn) {
            // Obtém o ID do usuário atualmente logado
            String idUsuario = Validacao.idUsuarioArmazenado;
            // Consulta SQL para selecionar os livros atualmente alugados pelo usuário
            String sqllIVRO = "SELECT l.idLivro, l.titulo, a.dataAluguel FROM livros l JOIN aluguel a ON l.idLivro = a.idLivro WHERE a.idUsuario = ? AND l.situacao = 'Alugado'";
            String deleteSql = "DELETE FROM aluguel WHERE idLivro = ? AND idUsuario = ?";
            String updateLivroSql = "UPDATE livros SET situacao = 'Disponível' WHERE idLivro = ?";

            try (PreparedStatement declaracao = conexao.prepareStatement(sqllIVRO);
                 PreparedStatement deleteLivros = conexao.prepareStatement(deleteSql);
                 PreparedStatement updateLivro = conexao.prepareStatement(updateLivroSql)) {

                // Define o ID do usuário na consulta SQL
                declaracao.setString(1, idUsuario);
                ResultSet resultado = declaracao.executeQuery();

                // Lista para armazenar os livros a serem devolvidos
                ArrayList<LivroAlugado> devolverLivros = new ArrayList<>();
                JComboBox<LivroAlugado> comboBoxLivros = new JComboBox<>();

                // Preenche o JComboBox com os livros a serem devolvidos
                while (resultado.next()) {
                    String idLivro = resultado.getString("idLivro");
                    String titulo = resultado.getString("titulo");
                    LocalDate dataAluguel = resultado.getDate("dataAluguel").toLocalDate();
                    LivroAlugado livro = new LivroAlugado(idLivro, titulo, dataAluguel);
                    devolverLivros.add(livro);
                    comboBoxLivros.addItem(livro);
                }

                Object[] chamarComboBoxLivro = {
                        "Selecione o livro que deseja devolver:", comboBoxLivros
                };

                JButton cancelar = new JButton("Cancelar");
                JButton devolver = new JButton("Devolver");
                Object[] opcoes = {
                        devolver, cancelar
                };

                cancelar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Fechando a janela
                        Window window = SwingUtilities.getWindowAncestor(cancelar);
                        JDialog dialog = (JDialog) window;
                        dialog.dispose();

                        JOptionPane.showMessageDialog(null, "Operação Cancelada. Voltando ao Menu Inicial", null, JOptionPane.INFORMATION_MESSAGE);
                        UI.menuInicial();
                    }
                });
                devolver.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Fechando a janela
                        Window window = SwingUtilities.getWindowAncestor(devolver);
                        JDialog dialog = (JDialog) window;
                        dialog.dispose();//

                        // Obtém o livro selecionado para devolução
                        LivroAlugado livroSelecionado = (LivroAlugado) comboBoxLivros.getSelectedItem();
                        if (livroSelecionado != null) {
                            String idLivro = livroSelecionado.getIdLivro();
                            LocalDate dataAluguel = livroSelecionado.getDataAluguel();

                            // Obtém a data atual e a data de devolução prevista (2 semanas após o aluguel)
                            LocalDate dataDevolucao = LocalDate.now();
                            LocalDate dataDevolucaoPrevista = dataAluguel.plusWeeks(2);

                            // Calcula a multa, se houver atraso na devolução
                            long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataDevolucao);
                            double multa = diasAtraso > 0 ? diasAtraso : 0;

                            try {
                                // Deleta o registro de aluguel
                                deleteLivros.setString(1, idLivro);
                                deleteLivros.setString(2, idUsuario);
                                deleteLivros.executeUpdate();

                                // Atualiza o status do livro para 'Disponível'
                                updateLivro.setString(1, idLivro);
                                updateLivro.executeUpdate();

                                // Exibe uma mensagem de sucesso e retorna ao menu inicial
                                String mensagem = "Livro devolvido com sucesso.";
                                if (multa > 0) {
                                    mensagem += "\nVocê tem uma multa de R$" + multa + " por " + diasAtraso + " dias de atraso.";
                                }
                                JOptionPane.showMessageDialog(null, mensagem, null, JOptionPane.INFORMATION_MESSAGE);
                                UI.menuInicial();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            UI.menuInicial();
                        }
                    }
                });

                JOptionPane.showOptionDialog(null, chamarComboBoxLivro, "Devolução de livro", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, opcoes, null);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}