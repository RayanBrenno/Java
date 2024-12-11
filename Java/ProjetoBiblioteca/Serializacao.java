package ProjetoBiblioteca;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Serializacao {

    public static void serializarLivros(){
        // Lista para armazenar os objetos Livros recuperados do banco de dados
        List <Livros> livros = new ArrayList<>();

        ConexaoBD.criarConexao();
        try (Connection conexao = ConexaoBD.conn) {

            String sql = "SELECT * FROM livros";
            try (Statement declaracao = conexao.createStatement();
                 ResultSet resultado = declaracao.executeQuery(sql)) {

                // Iteração sobre o resultado da consulta
                while (resultado.next()) {

                    String idLivro = resultado.getString("idLivro");
                    String titulo = resultado.getString("titulo");
                    String autor = resultado.getString("autor");
                    String editora = resultado.getString("editora");
                    String anoPublicacao = resultado.getString("anoPublicacao");
                    String genero = resultado.getString("genero");

                    Livros.SituacaoLivro situacao;
                    if(resultado.getString("situacao").equals("Disponível")){
                        situacao = Livros.SituacaoLivro.DISPONIVEL;
                    }else{
                        situacao = Livros.SituacaoLivro.ALUGADO;
                    }

                    // Adição do livro à lista de livros
                    Livros livro = new Livros(idLivro, titulo, autor, editora, anoPublicacao, genero, situacao);
                    livros.add(livro);
                }
            }

            // Conversão da lista de livros para formato JSON e escrita no arquivo "Livros.json"
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(livros);

            try (FileWriter writer = new FileWriter("src/ProjetoLP2/Livros.json")) {
                writer.write(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void serializarUsuarios(){

        // Lista para armazenar os objetos UsuarioSerializavel recuperados do banco de dados
        List <UsuarioSerializavel> usuarios = new ArrayList<>();

        ConexaoBD.criarConexao();
        try(Connection conexao = ConexaoBD.conn){

            // Consulta SQL para selecionar todos os usuários da tabela usuarios
            String sql = "SELECT * FROM usuarios";

            try(Statement declaracao = conexao.createStatement();
                ResultSet resultado = declaracao.executeQuery(sql);) {

                while (resultado.next()) {
                    String idUsuario = resultado.getString("idUsuario");
                    String nome = resultado.getString("nome");
                    String email = resultado.getString("email");
                    String endereco = resultado.getString("endereco");
                    String telefone = resultado.getString("telefone");
                    String cpf = resultado.getString("cpf");
                    String senha = resultado.getString("senha");
                    String tipo = resultado.getString("tipo");

                    // Adição do usuário à lista de usuários
                    if (tipo.equalsIgnoreCase("Cliente")) {
                        UsuarioSerializavel us = new UsuarioSerializavel(idUsuario, nome, email, endereco, telefone, cpf, senha, UsuarioSerializavel.TipoUsuario.CLIENTE);
                        usuarios.add(us);
                    } else {
                        UsuarioSerializavel us = new UsuarioSerializavel(idUsuario, nome, email, endereco, telefone, cpf, senha, UsuarioSerializavel.TipoUsuario.FUNCIONARIO);
                        usuarios.add(us);
                    }

                }

                // Conversão da lista de usuários para formato JSON e escrita no arquivo "Usuarios.json"
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(usuarios);

                try (FileWriter writer = new FileWriter("src/ProjetoLP2/Usuarios.json")) {
                    writer.write(json);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }catch(SQLException e){
                throw new RuntimeException(e);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    public static void serializacaoGeral(){
        Serializacao.serializarLivros();
        Serializacao.serializarUsuarios();
    }

}