import agencias.Agencia;
import auxiliares.Endereco;
import clientes.Cliente;
import clientes.Premium;
import contas.Conta;
import contas.Corrente;
import contas.Facil;
import contas.Poupanca;
import exceptions.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Iniciando programa de banco...");

        solicitarOperacao();
        int operacao = teclado.nextInt();

        while (operacao != -1) {
            if (operacao == 1)
                viradaDeMes();

            if (operacao == 2)
                cadastrarAgencia();

            if (operacao == 3)
                aberturaDeConta();

            if (operacao == 4)
                realizarSaque();

            if (operacao == 5)
                realizarDeposito();

            if (operacao == 6)
                realizarTransferencia();

            if (operacao == 7)
                solicitarEmprestimo();

            if (operacao == 8)
                gerarExtrato();

            if (operacao == 9)
                gerarRelatorio();

            solicitarOperacao();
            operacao = teclado.nextInt();
        }

        System.out.println("Finalizando programa...");
    }

    private static void viradaDeMes() {
        List<Agencia> agencias = Agencia.agencias;

        for (Agencia agencia : agencias) {
            agencia.viradaDeMes();
        }
    }

    private static void cadastrarAgencia() {
        String nomeAgencia = teclado.next();
        String numeroAgencia = teclado.next();

        Endereco enderecoAgencia = criarEndereco();

        try {
            Agencia.criarAgencia(nomeAgencia, numeroAgencia, enderecoAgencia);
        } catch (AgenciaJaExisteException e) {
            System.out.println("Agencia informada já cadastrada!");
        }
    }

    private static void aberturaDeConta() {
        try {
            Conta novaConta = criarConta();

            Agencia agencia = encontarAgencia();

            agencia.adicionarConta(novaConta);

        } catch (LimiteSaldoContaFacilAlcancadoException e) {
            System.out.println(e.getMessage());

        } catch (AgenciaNaoEncontradaException e) {
            agenciaNaoEncontrada();

        } catch (ContaJaExisteException e) {
            System.out.println("A conta já existe na Agência!");
        }
    }

    private static void realizarSaque() {
        try {
            Agencia agencia = encontarAgencia();

            Conta conta = encontrarConta(agencia);

            conta.realizarSaque(solicitarValor());

        } catch (AgenciaNaoEncontradaException e) {
            agenciaNaoEncontrada();

        } catch (ContaNaoEncontradaException e) {
            contaNaoEncontrada();

        } catch (LimiteSaqueContaFacilAlcancadoException e) {
            e.printStackTrace();

        } catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void realizarDeposito() {
        try {
            Agencia agencia = encontarAgencia();

            Conta conta = encontrarConta(agencia);

            conta.realizarDeposito(solicitarValor());

        } catch (AgenciaNaoEncontradaException e) {
            agenciaNaoEncontrada();

        } catch (ContaNaoEncontradaException e) {
            contaNaoEncontrada();

        } catch (LimiteSaldoContaFacilAlcancadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void realizarTransferencia() {
        try {
            Agencia agenciaOrigem = encontarAgencia();
            Conta contaOrigem = encontrarConta(agenciaOrigem);

            Agencia agenciaDestino = encontarAgencia();
            Conta contaDestino = encontrarConta(agenciaDestino);

            contaOrigem.realizarTransferencia(contaDestino, solicitarValor());

        } catch (AgenciaNaoEncontradaException e) {
            agenciaNaoEncontrada();

        } catch (LimiteTransferenciaContaFacilAlcancadoException | LimiteSaldoContaFacilAlcancadoException | SaldoInsuficienteException e) {
            System.out.println(e.getMessage());

        } catch (ContaNaoEncontradaException e) {
            contaNaoEncontrada();
        }
    }

    private static void solicitarEmprestimo() {
        try {
            Agencia agencia = encontarAgencia();

            Conta conta = encontrarConta(agencia);

            conta.realizarEmprestimo(solicitarValor());
        } catch (AgenciaNaoEncontradaException e) {
            agenciaNaoEncontrada();

        } catch (LimiteEmprestimoClienteUltrapassadoException | LimiteEmprestimoClientePremiumUltrapassadoException | LimiteSaldoContaFacilAlcancadoException e) {
            System.out.println(e.getMessage());

        } catch (ContaNaoEncontradaException e) {
            contaNaoEncontrada();
        }
    }

    private static void gerarExtrato() {
        try {
            Agencia agencia = encontarAgencia();

            Conta conta = encontrarConta(agencia);

            conta.solicitarExtrato();

        } catch (ContaNaoEncontradaException e) {
            contaNaoEncontrada();

        } catch (AgenciaNaoEncontradaException e) {
            agenciaNaoEncontrada();
        }
    }

    private static void gerarRelatorio() {
    }

    private static Endereco criarEndereco() {
        String pais = teclado.next();
        String cidade = teclado.next();
        String rua = teclado.next();
        String bairro = teclado.next();
        String cep = teclado.next();
        String numero = teclado.next();

        return new Endereco(rua, pais, cidade, numero, bairro, cep);
    }

    private static Conta criarConta() throws LimiteSaldoContaFacilAlcancadoException {
        String tipoConta = teclado.next().toUpperCase();

        Cliente cliente = criarCliente();

        if (tipoConta.equals("C")) {
            return new Corrente(solicitarValor(), cliente);
        }

        if (tipoConta.equals("F")) {
            return new Facil(solicitarValor(), cliente);
        }

        return new Poupanca(solicitarValor(), cliente);
    }

    private static Cliente criarCliente() {
        String cpfCliente = teclado.next();
        String nomeCliente = teclado.next();
        Endereco endereco = criarEndereco();
        String dataNascimento = teclado.next();
        String tipoCliente = teclado.next().toUpperCase();

        if (tipoCliente.equals("PREMIUM"))
            return new Premium(nomeCliente, cpfCliente, dataNascimento, endereco);

        return new Cliente(nomeCliente, cpfCliente, dataNascimento, endereco);
    }

    private static Agencia encontarAgencia() throws AgenciaNaoEncontradaException {
        String numeroAgencia = teclado.next();

        return Agencia.getAgencia(numeroAgencia);
    }

    private static Conta encontrarConta(Agencia agencia) throws ContaNaoEncontradaException {
        String numeroConta = teclado.next();

        return agencia.consultarConta(numeroConta);
    }

    private static double solicitarValor() {
        return teclado.nextDouble();
    }

    private static void agenciaNaoEncontrada() {
        System.out.println("Agencia informada não foi encontrada!");
    }

    private static void contaNaoEncontrada() {
        System.out.println("Conta informada não foi encontrada!");
    }

    private static void solicitarOperacao() {
        System.out.println("Digite a operação desejada: ");
    }
}
