import agencias.Agencia;
import auxiliares.Endereco;
import clientes.Cliente;
import clientes.Premium;
import contas.Conta;
import contas.Corrente;
import contas.Facil;
import contas.Poupanca;
import exceptions.AgenciaJaExisteException;
import exceptions.AgenciaNaoEncontradaException;
import exceptions.ContaJaExisteException;
import exceptions.LimiteSaldoContaFacilAlcancadoException;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
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

            operacao = teclado.nextInt();
        }
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

            String numeroAgencia = teclado.next();
            Agencia agencia = Agencia.getAgencia(numeroAgencia);

            agencia.adicionarConta(novaConta);

        } catch (LimiteSaldoContaFacilAlcancadoException e) {
            System.out.println(e.getMessage());

        } catch (AgenciaNaoEncontradaException e) {
            System.out.println("Agência informada não foi encontrada!");

        } catch (ContaJaExisteException e) {
            System.out.println("A conta já existe na Agência!");
        }
    }

    private static void realizarSaque() {

    }

    private static void realizarDeposito() {
    }

    private static void realizarTransferencia() {
    }

    private static void solicitarEmprestimo() {
    }

    private static void gerarExtrato() {
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
        double valor = teclado.nextDouble();

        if (tipoConta.equals("C")) {
            return new Corrente(valor, cliente);
        }

        if (tipoConta.equals("F")) {
            return new Facil(valor, cliente);
        }

        return new Poupanca(valor, cliente);
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
}
