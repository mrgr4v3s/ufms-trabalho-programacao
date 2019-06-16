package contas;

import clientes.Cliente;
import exceptions.LimiteSaldoContaFacilAlcancadoException;
import exceptions.LimiteSaqueContaFacilAlcancadoException;
import exceptions.LimiteTransferenciaContaFacilAlcancadoException;
import exceptions.SaldoInsuficienteException;

public class Facil extends Conta {
    private static final int LIMITE_TRANSFERENCIA_MENSAL = 1;
    private static final int LIMITE_SAQUE_MENSAL = 1;
    private static final double LIMITE_TOTAL_SALDO = 5000.00d;
    private static final double ANUIDADE = 10.00d;

    private int quantidadeTransferenciaRealizada;
    private int quantidadeSaqueRealizado;

    public Facil(double valor, Cliente cliente) throws LimiteSaldoContaFacilAlcancadoException {
        adicionarSaldo(valor);
        setCodigoUnico(criarCodigoUnico());
        this.cliente = cliente;
        contaCriada();
        zerarLimiteDeSaque();
        zerarLimiteTransferencia();
    }

    @Override
    public void realizarSaque(double valor) throws SaldoInsuficienteException, LimiteSaqueContaFacilAlcancadoException {
        if (quantidadeSaqueRealizado == LIMITE_SAQUE_MENSAL)
            throw new LimiteSaqueContaFacilAlcancadoException("Limite de saques de " + LIMITE_SAQUE_MENSAL + " foi alcançado.");

        super.realizarSaque(valor);

        quantidadeSaqueRealizado++;
    }

    @Override
    public void realizarTransferencia(Conta conta, double valor) throws SaldoInsuficienteException, LimiteTransferenciaContaFacilAlcancadoException, LimiteSaldoContaFacilAlcancadoException {
        if (quantidadeTransferenciaRealizada == LIMITE_TRANSFERENCIA_MENSAL)
            throw new LimiteTransferenciaContaFacilAlcancadoException("Limite de transferencias " + LIMITE_TRANSFERENCIA_MENSAL + " foi alcançado.");

        super.realizarTransferencia(conta, valor);

        quantidadeTransferenciaRealizada++;
    }

    @Override
    void adicionarSaldo(double valor) throws LimiteSaldoContaFacilAlcancadoException {
        if (getSaldo() + valor >= LIMITE_TOTAL_SALDO)
            throw new LimiteSaldoContaFacilAlcancadoException("Limite de saldo de " + LIMITE_TOTAL_SALDO + " foi alcançado.");

        super.adicionarSaldo(valor);
    }

    @Override
    public void aplicarTaxasMensais() {
        zerarLimiteDeSaque();
        zerarLimiteDeSaque();
    }

    @Override
    public void aplicarTaxasAnuais() {
        removerSaldo(ANUIDADE);
    }

    private void zerarLimiteDeSaque() {
        this.quantidadeSaqueRealizado = 0;
    }

    private void zerarLimiteTransferencia() {
        this.quantidadeTransferenciaRealizada = 0;
    }
}
