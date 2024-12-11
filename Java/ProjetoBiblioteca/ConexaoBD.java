package ProjetoBiblioteca;

import java.sql.*;

public class ConexaoBD {

    // Variáveis estáticas para conexão com o banco de dados e manipulação de consultas
    static Connection conn;
    static Statement stmt;
    static PreparedStatement pstmt;

    public static void criarConexao(){

        String url = "jdbc:mysql://localhost:3306/projeto";
        String user = "root";
        String psw = "12345";

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, psw);
            stmt = conn.createStatement();

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public static void comandoCreate(String create){

        try{
            pstmt = conn.prepareStatement(create);
            pstmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public static void comandoDrop(String drop){

        try{
            pstmt = conn.prepareStatement(drop);
            pstmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public static void comandoInsert(String insert){

        try {
            pstmt = conn.prepareStatement(insert);
            pstmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public static void comandoDelete(String delete){

        try{
            pstmt = conn.prepareStatement(delete);
            pstmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public static void fecharConexao(){

        try{
            conn.close();
        }catch(Exception e){
            System.out.println(e);
        }

    }

}