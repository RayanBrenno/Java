package ProjetoBiblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {

    public static void Login(){

        // Criação dos campos de texto para e-mail e senha
        JTextField email = new JTextField();
        JTextField password = new JPasswordField();

        // Criação dos botões "OK", "Sair" e "Não possuo cadastro"
        JButton ok = new JButton("OK");
        JButton exit = new JButton("Sair");
        JButton naoPossuoCadastro = new JButton("Não possuo cadastro");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(ok);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                if(Validacao.verificarFuncionario(email.getText())){
                    Validacao.eFuncionario = true;
                }

                // Verifica se o login do usuário é válido e exibe a interface inicial
                if(Validacao.verificarLoginUsuario(email.getText(), password.getText())) {
                    menuInicial();
                }else{
                    JOptionPane.showMessageDialog(null, "Login failed","Erro", JOptionPane.ERROR_MESSAGE);
                    Login();
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(exit);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                // Realiza a serialização dos dados e fecha a conexão com o banco de dados
                Serializacao.serializacaoGeral();
                JOptionPane.showMessageDialog(null, "Programa encerrado.", "", JOptionPane.INFORMATION_MESSAGE);
                ConexaoBD.fecharConexao();

            }
        });
        naoPossuoCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(naoPossuoCadastro);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                criarCadastro();
            }
        });

        Object[] message = {
                "E-mail:", email,
                "Senha:", password
        };

        Object[] botoes = {ok, exit, naoPossuoCadastro};

        JOptionPane.showOptionDialog(null, message,"Login", 0, JOptionPane.PLAIN_MESSAGE, null, botoes, null);

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void criarCadastro() {

        String senhaCadastroFuncionario = Funcionario.getSenhaCadastroFuncionario();

        // Criação dos campos de texto para nome, e-mail, telefone, CPF, endereço e senha
        JTextField name = new JTextField();
        JTextField e_mail = new JTextField();
        JTextField phone = new JTextField();
        JTextField code = new JTextField();
        JTextField address = new JTextField();
        JTextField password = new JPasswordField();

        // Criação de um combobox para selecionar o tipo de usuário (cliente ou funcionário)
        
        JComboBox type = new JComboBox();
        type.addItem("Cliente");
        type.addItem("Funcionário");

        // Criação dos botões "OK" e "Voltar"
        JButton ok = new JButton("OK");
        JButton back = new JButton("Voltar");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(ok);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                if(name.getText().isEmpty()
                        || e_mail.getText().isEmpty()
                        || phone.getText().isEmpty()
                        || code.getText().isEmpty()
                        || address.getText().isEmpty()
                        || password.getText().isEmpty()){

                    JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    criarCadastro();
                }else{
                    // Verifica se o usuário é um funcionário e solicita a senha de confirmação
                    if(type.getSelectedItem().toString().equals("Funcionário")){

                        JTextField pswEmp = new JPasswordField();

                        Object[] msg = {
                                "Informe a senha para cadastro de funcionário:", pswEmp
                        };

                        JOptionPane.showMessageDialog(null, msg, "Confirmação de cadastro", JOptionPane.OK_CANCEL_OPTION);
                        String senhaF = pswEmp.getText();

                        if(!senhaF.equals(senhaCadastroFuncionario)){
                            JOptionPane.showMessageDialog(null, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                            criarCadastro();
                        }else{
                            cadastrarFuncionario(name.getText(), e_mail.getText(), address.getText(), phone.getText(), code.getText(), password.getText());
                        }

                    }else{
                        cadastrarCliente(name.getText(), e_mail.getText(), address.getText(), phone.getText(), code.getText(), password.getText());
                    }

                }

            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(back);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                JOptionPane.showMessageDialog(null, "Cadastro cancelado", "", JOptionPane.INFORMATION_MESSAGE);
                Login();
            }
        });

        Object[] mensagem = {

                "Nome:", name,
                "E-mail:", e_mail,
                "Endereço:", address,
                "Telefone:", phone,
                "CPF:", code,
                "Crie uma senha:", password,
                "Selecione seu tipo de usuário:", type

        };
        Object[] botoes = {ok, back};

        JOptionPane.showOptionDialog(null, mensagem,"Criar cadastro", 0, JOptionPane.PLAIN_MESSAGE, null, botoes, null);

    }

    public static void cadastrarCliente(String nome, String email, String endereco, String telefone, String cpf, String senha){
        new Cliente(null, nome, email, endereco, telefone, cpf, senha);
    }

    public static void cadastrarFuncionario(String nome, String email, String endereco, String telefone, String cpf, String senha){
        new Funcionario(null, nome, email, endereco, telefone, cpf, senha);
    }

    public static void menuInicial(){

        // Criação dos botões para consultar livros, alugar, devolver, inserir e remover livros
        JButton consultar = new JButton("Lista de livros");
        JButton alugar = new JButton("Aluguel");
        JButton devolver = new JButton("Devolver");
        JButton inserir = new JButton("Inserir livros");
        JButton remover = new JButton("Remover livros");
        JButton logout = new JButton("Logout");

        Object[] mensagem = {
                consultar, alugar, devolver, inserir, remover, logout
        };

        consultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(consultar);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                Biblioteca.consultarLivros();
            }
        });
        alugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(alugar);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                Biblioteca.alugarLivro();
            }
        });
        devolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(devolver);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                Biblioteca.devolverlivros();
            }
        });
        inserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(inserir);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                if(Validacao.eFuncionario){
                    Biblioteca.cadastrarLivros();
                }else{
                    JOptionPane.showMessageDialog(null, "Você não tem permissão de acessar essa função", "", JOptionPane.ERROR_MESSAGE);
                    menuInicial();
                }

            }
        });
        remover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(remover);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                if(Validacao.eFuncionario){
                    Biblioteca.removerLivros();
                }else{
                    JOptionPane.showMessageDialog(null, "Você não tem permissão de acessar essa função", "", JOptionPane.ERROR_MESSAGE);
                    menuInicial();
                }

            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Fechando a primeira janela
                Window window = SwingUtilities.getWindowAncestor(logout);
                JDialog dialog = (JDialog) window;
                dialog.dispose();//

                Validacao.eFuncionario = false;
                Login();
            }
        });

        JOptionPane.showOptionDialog(null, mensagem,"Menu principal", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);

    }

}