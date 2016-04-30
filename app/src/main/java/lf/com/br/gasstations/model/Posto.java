package lf.com.br.gasstations.model;


import java.io.Serializable;

/**
 * Created by Fernando on 20/04/2016.
 */
public class Posto implements Serializable {

    public String posto;
    public String nome;
    public String endereco;
    public String bairro;
    public String bandeira;
    public String icone;
    public String gasolina;
    public String gnv;
    public String alcool;
    public String latitude;
    public String longitude;


    @Override
    public String toString() {
        return nome;
    }
}
