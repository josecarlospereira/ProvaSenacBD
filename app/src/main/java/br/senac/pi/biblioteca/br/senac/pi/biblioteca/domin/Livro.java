package br.senac.pi.biblioteca.br.senac.pi.biblioteca.domin;

/**
 * Created by JoséCarlos on 25/11/2015.
 */
public class Livro {
    private Integer id;
    private String nome;
    private String autor;

    public Livro(){}

    public Livro(int  id, String nome, String autor){
        this.id = id;
        this.nome = nome;
        this.autor = autor;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getAutor(){
        return autor;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

}
