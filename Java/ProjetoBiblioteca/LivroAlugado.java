package ProjetoBiblioteca;

import java.time.LocalDate;

class LivroAlugado {

    // Declaração de variáveis privadas para armazenar os dados do livro alugado
    private String idLivro;
    private String titulo;
    private LocalDate dataAluguel;

    public LivroAlugado(String idLivro, String titulo, LocalDate dataAluguel) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.dataAluguel = dataAluguel;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public LocalDate getDataAluguel() {
        return dataAluguel;
    }

    @Override
    public String toString() {
        return titulo;
    }
}