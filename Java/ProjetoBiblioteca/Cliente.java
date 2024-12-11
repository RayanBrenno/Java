package ProjetoBiblioteca;

import javax.swing.*;

public class Cliente extends Usuario {

    public Cliente(String idUsuario, String nome, String email, String endereco, String telefone, String cpf, String senha) {
        super(idUsuario, nome, email, endereco, telefone, cpf, senha, TipoUsuario.CLIENTE);
    }

    @Override
    public void insertUsuario() {

        // Verifica se os dados do cliente são válidos
        if(Validacao.validacaoDeDados(email, telefone, cpf)){

            String insert = "insert into usuarios (nome, email, endereco, telefone, cpf, senha, tipo)" +
                    " values ('"+nome+"','"+email+"','"+endereco+"','"+telefone+"','"+cpf+"','"+senha+"', 'Cliente') ";

            ConexaoBD.criarConexao();
            ConexaoBD.comandoInsert(insert);

            JOptionPane.showMessageDialog(null, "Cadastro criado!", null, JOptionPane.INFORMATION_MESSAGE);
            UI.Login();
        }else{
            UI.criarCadastro();
        }

    }

}