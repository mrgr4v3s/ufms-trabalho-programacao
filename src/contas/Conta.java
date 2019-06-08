package contas;

import clientes.Cliente;
import com.sun.javafx.binding.StringFormatter;
import exceptions.LimiteSaldoContaFacilAlcancadoException;
import exceptions.LimiteSaqueContaFacilAlcancadoException;
import exceptions.LimiteTransferenciaContaFacilAlcancadoException;
import exceptions.SaldoInsuficienteException;
import operacoes.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private static int quantidadeDeContasCriadas = 0;
    private double saldo = 0;
    private double saldoDevedor = 0;
    private String codigoUnico;
    private List<Operacao> operacoesRealizadas = new ArrayList<>();

    public void realizarSaque(double valor) throws SaldoInsuficienteException, LimiteSaqueContaFacilAlcancadoException {
        if (this.saldo < valor)
            throw new SaldoInsuficienteException("Saldo da conta não é suficiente para realizar a operação");

        removerSaldo(valor);

        lancarHistoricoOperacoes(new Saque(valor));
    }

    public void realizarDeposito(double valor) throws LimiteSaldoContaFacilAlcancadoException {
        adicionarSaldo(valor);

        lancarHistoricoOperacoes(new Deposito(valor));
    }

    public void realizarTransferencia(Conta conta, double valor) throws SaldoInsuficienteException, LimiteTransferenciaContaFacilAlcancadoException, LimiteSaldoContaFacilAlcancadoException {
        if (getSaldo() < valor)
            throw new SaldoInsuficienteException("Saldo da conta origem não é suficiente para realizar a operação");

        this.saldo -= valor;
        conta.adicionarSaldo(valor);

        lancarHistoricoOperacoes(new Transferencia(valor));
        conta.lancarHistoricoOperacoes(new Deposito(valor));
    }

    public void realizarEmprestimo(double valor) throws LimiteSaldoContaFacilAlcancadoException {
        adicionarSaldoDevedor(valor);
        adicionarSaldo(valor);

        lancarHistoricoOperacoes(new Emprestimo(valor));
    }

    public void realizarPagamento(double valor) throws SaldoInsuficienteException {
        if (getSaldo() < valor)
            throw new SaldoInsuficienteException("Saldo da conta não é suficiente para realizar a operação");

        removerSaldo(valor);

        lancarHistoricoOperacoes(new Pagamento(valor));
    }

    public void solicitarExtrato() {
        for (Operacao operacao : operacoesRealizadas) {
            System.out.println(operacao.consultarOperacao());
        }

        System.out.println();

        System.out.println(StringFormatter.format("SALDO ATUAL: %d.2", getSaldo()));
        System.out.println(StringFormatter.format("SALDO DEVEDOR: %d.2", getSaldo()));
    }

    private void lancarHistoricoOperacoes(Operacao operacao) {
        if (operacoesRealizadas.size() >= 10){
            operacoesRealizadas.remove(0);
        }

        operacoesRealizadas.add(operacao);
    }

    String criarCodigoUnico() {
        StringBuilder novoCodigoUnico = new StringBuilder(String.valueOf(quantidadeDeContasCriadas));

        int i = novoCodigoUnico.length();

        for (;i <= 4; i++) {
            novoCodigoUnico.insert(0, "0");
        }

        return novoCodigoUnico.toString();
    }

    void contaCriada() {
        Conta.quantidadeDeContasCriadas++;
    }

    public abstract void aplicarTaxasMensais(Cliente cliente) throws LimiteSaldoContaFacilAlcancadoException;

    public abstract void aplicarTaxasAnuais(Cliente cliente);

    double getSaldo() {
        return saldo;
    }

    void adicionarSaldo(double valor) throws LimiteSaldoContaFacilAlcancadoException {
        this.saldo += valor;
    }

    protected void removerSaldo(double valor) {
        this.saldo -= valor;
    }

    protected double getSaldoDevedor() {
        return this.saldoDevedor;
    }

    protected void adicionarSaldoDevedor(double valor) {
        this.saldoDevedor += valor;
    }

    protected void removerSaldoDevedor(double valor) {
        this.saldoDevedor -= valor;
    }

    public String getCodigoUnico() {
        return this.codigoUnico;
    }

    void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }


}
