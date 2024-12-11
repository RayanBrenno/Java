package ProjetoBiblioteca;

import javax.swing.*;

public class Funcionario extends Usuario {

    // Senha padrão para cadastro de funcionário
    private static final String senhaCadastroFuncionario = "funcionario123";

    public Funcionario(String idUsuario, String nome, String email, String endereco, String telefone, String cpf, String senha) {
        super(idUsuario, nome, email, endereco, telefone, cpf, senha, TipoUsuario.FUNCIONARIO);
    }

    @Override
    public void insertUsuario() {

        // Verifica se os dados do funcionário são válidos
        if(Validacao.validacaoDeDados(email, telefone, cpf)){
            String insert = "insert into usuarios (nome, email, endereco, telefone, cpf, senha, tipo)" +
                    " values ('"+nome+"','"+email+"','"+endereco+"','"+telefone+"','"+cpf+"','"+senha+"', 'Funcionario') ";

            ConexaoBD.criarConexao();
            ConexaoBD.comandoInsert(insert);

            JOptionPane.showMessageDialog(null, "Cadastro criado!", null, JOptionPane.INFORMATION_MESSAGE);
            UI.Login();
        }else{
            UI.criarCadastro();
        }

    }

    public static String getSenhaCadastroFuncionario() {return senhaCadastroFuncionario;}

}