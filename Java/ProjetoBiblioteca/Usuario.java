package ProjetoBiblioteca;

public abstract class Usuario {

    protected static String idUsuario;
    protected static String nome;
    protected static String email;
    protected static String endereco;
    protected static String telefone;
    protected static String cpf;
    protected static String senha;
    protected static TipoUsuario tipo;

    enum TipoUsuario{CLIENTE, FUNCIONARIO}

    public Usuario(String idUser, String name, String e_mail, String address, String phone, String code, String psw, TipoUsuario type) {
        idUsuario = idUser;
        nome = name;
        email = e_mail;
        endereco = address;
        telefone = phone;
        cpf = code;
        senha = psw;
        tipo = type;

        insertUsuario();
    }

    public abstract void insertUsuario();

    @Override
    public String toString() {
        return "idUsuario: "+idUsuario
                +"\nNome: "+nome
                +"\nE-mail: "+email
                +"\nEndereco: "+endereco
                +"\nTelefone: "+telefone
                +"\nCPF: "+cpf
                +"\nSenha: "+senha
                +"\nTipo: "+tipo;
    }

}