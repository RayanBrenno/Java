package ProjetoBiblioteca;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Validacao {

    // Variável booleana para verificar se o usuário é um funcionário
    protected static boolean eFuncionario = false;
    public static String idUsuarioArmazenado;

    public static boolean validarEmail(String email){
        if(email.contains("@")){
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "E-mail inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean validarTelefone(String telefone) {
        String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        // Verifica se o telefone contém letras
        for(String letra : letras){
            if(telefone.toUpperCase().contains(letra)){
                JOptionPane.showMessageDialog(null, "Telefone inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Formata o número de telefone de acordo com o seu comprimento e verifica se está no formato correto
        if(telefone.length() == 8){
            Usuario.telefone = String.format("%s-%s",
                    telefone.substring(0, 4),
                    telefone.substring(4, 8));
            return true;

        }else if(telefone.length() == 9){
            Usuario.telefone = String.format("%s %s-%s",
                    telefone.substring(0, 1),
                    telefone.substring(1, 5),
                    telefone.substring(5, 9));
            return true;

        }else if(telefone.length() == 11) {
            Usuario.telefone = String.format("(%s) %s %s-%s",
                    telefone.substring(0, 2),
                    telefone.substring(2, 3),
                    telefone.substring(3, 7),
                    telefone.substring(7, 11));
            return true;

        }else{
            JOptionPane.showMessageDialog(null, "Telefone inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public static boolean validarCPF(String cpf){
        String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        // Verifica se o CPF contém letras
        for(String letra : letras){
            if(cpf.toUpperCase().contains(letra)){
                JOptionPane.showMessageDialog(null, "CPF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if(cpf.length() != 11){

            // Verifica se o CPF está no formato "xxx.xxx.xxx-xx"
            if(cpf.length() == 14 && cpf.contains(".") && cpf.contains("-")){
                return true;
            }

            JOptionPane.showMessageDialog(null, "CPF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;

        }else{
            // Formata o CPF para o formato "xxx.xxx.xxx-xx"
            Usuario.cpf = String.format("%s.%s.%s-%s",
                    cpf.substring(0, 3),
                    cpf.substring(3, 6),
                    cpf.substring(6, 9),
                    cpf.substring(9, 11));
            return true;
        }

    }

    public static boolean validacaoDeDados(String email, String telefone, String cpf){
        return (validarEmail(email) && validarTelefone(telefone) && validarCPF(cpf));
    }

    public static boolean verificarLoginUsuario(String email, String senha){

        ConexaoBD.criarConexao();
        try(Connection conexao = ConexaoBD.conn){
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Consulta SQL para verificar o login do usuário
            String sql = "select idUsuario, senha from usuarios where email = '"+email+"';";
            try (Statement declaracao = conexao.createStatement(); ResultSet resultado = declaracao.executeQuery(sql);) {

                if(resultado != null && resultado.next()){

                    if(senha.equals(resultado.getString("senha"))){
                        idUsuarioArmazenado = String.valueOf(resultado.getInt("idUsuario"));
                        return true;
                    }else{
                        JOptionPane.showMessageDialog(null, "Senha incorreta", "", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }

                }

            }

        } catch (SQLException | ClassNotFoundException e) {
            // Tratamento de exceção em caso de erro na consulta SQL
            JOptionPane.showMessageDialog(null, "Erro ao verificar login: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static boolean verificarFuncionario(String email){

        ConexaoBD.criarConexao();
        try(Connection conexao = ConexaoBD.conn){
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "select tipo from usuarios where email = '"+email+"';";
            try (Statement declaracao = conexao.createStatement(); ResultSet resultado = declaracao.executeQuery(sql);) {

                if(resultado != null && resultado.next()){

                    if(resultado.getString("tipo").equals("Funcionario")){
                        return true;
                    }

                }

            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar login: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}