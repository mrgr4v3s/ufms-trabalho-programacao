package agencias;

import contas.Conta;
import exceptions.AgenciaJaExisteException;
import exceptions.AgenciaNaoEncontradaException;
import exceptions.ContaJaExisteException;
import exceptions.ContaNaoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class Agencia implements Comparable<Agencia> {
    private static List<Agencia> agencias = new ArrayList<>();

    private String nome;
    private String numero;
    private List<Conta> contas;


    private Agencia(String nome, String numero) {
        this.nome = nome;
        this.numero = numero;
        this.contas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) throws ContaJaExisteException {
        if (contas.contains(conta))
            throw new ContaJaExisteException();

        contas.add(conta);
    }

    public Conta consultarConta(String codigoUnico) throws ContaNaoEncontradaException {
        for (Conta conta : contas)
            if (conta.getCodigoUnico().equals(codigoUnico))
                return conta;

        throw new ContaNaoEncontradaException();
    }

    public static void criarAgencia(String nome, String numero) throws AgenciaJaExisteException {
        Agencia agencia = new Agencia(nome, numero);

        if (encontrarAgencia(agencia))
            throw new AgenciaJaExisteException();

        agencias.add(agencia);
    }

    public static Agencia getAgencia(String numeroAgencia) throws AgenciaNaoEncontradaException {
        for (Agencia agencia : agencias)
            if (agencia.numero.equals(numeroAgencia))
                return agencia;

        throw new AgenciaNaoEncontradaException();
    }

    public static boolean encontrarAgencia(Agencia agenciaSendoEncontrada) {
        return agencias.contains(agenciaSendoEncontrada);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Agencia))
            return false;

        Agencia agencia = (Agencia) obj;

        if (!this.nome.equals(agencia.nome))
            return false;

        return this.numero.equals(agencia.numero);
    }

    @Override
    public int compareTo(Agencia agencia) {
        return this.numero.compareTo(agencia.numero);
    }
}
