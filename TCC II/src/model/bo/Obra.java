package model.bo;

public class Obra {
    private String tipo;
    private String titulo;
    private String autor;
    private int ano;

    public Obra(String tipo, String titulo, String autor, int ano) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
}
