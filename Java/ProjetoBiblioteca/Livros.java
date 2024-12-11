package ProjetoBiblioteca;

import java.io.Serializable;

@SuppressWarnings({"unused" })
public class Livros implements Serializable {

    // Classe utilizada para recuperar os dados da tabela livros, para utilizá-los na serialização

    private String idLivro;
    private String titulo;
    private String autor;
    private String editora;
    private String anoPublicacao;
    private String genero;
    private SituacaoLivro situacao;

    enum SituacaoLivro{DISPONIVEL, ALUGADO}

    public Livros(String idLivro, String titulo, String autor, String editora, String anoPublicacao, String genero, SituacaoLivro situacao) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.situacao = situacao;
    }

}