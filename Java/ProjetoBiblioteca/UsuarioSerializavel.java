package ProjetoBiblioteca;

import java.io.Serializable;

public class UsuarioSerializavel implements Serializable {

    // Classe utilizada para recuperar os dados da tabela usuarios, para utilizá-los na serialização

    protected String idUsuario;
    protected String nome;
    protected String email;
    protected String endereco;
    protected String telefone;
    protected String cpf;
    protected String senha;
    protected TipoUsuario tipo;

    enum TipoUsuario{CLIENTE, FUNCIONARIO}

    public UsuarioSerializavel(String idUser, String name, String e_mail, String address, String phone, String code, String psw, TipoUsuario type) {
        this.idUsuario = idUser;
        this.nome = name;
        this.email = e_mail;
        this.endereco = address;
        this.telefone = phone;
        this.cpf = code;
        this.senha = psw;
        this.tipo = type;
    }

}