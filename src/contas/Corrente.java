package contas;

import clientes.Cliente;
import clientes.Premium;
import exceptions.LimiteEmprestimoClientePremiumUltrapassadoException;
import exceptions.LimiteEmprestimoClienteUltrapassadoException;
import exceptions.LimiteSaldoContaFacilAlcancadoException;

public class Corrente extends Conta {
    private static final double TAXA_ANUAL = 5.00D;
    private static final double LIMITE_EMPRESTIMO_CLIENTE = 2000.00D;
    private static final double LIMITE_EMPRESTIMO_CLIENTE_PREMIUM = 2500.00D;
    private static final double JUROS_SIMPLES_MENSAL_EMPRESTIMO = 2.00;
    private static final String MENSAGEM_VALIDACAO = "O valor solicitado no emprestimo excede o limite da conta.";

    public Corrente(double valor) throws LimiteSaldoContaFacilAlcancadoException {
        adicionarSaldo(valor);
        setCodigoUnico(criarCodigoUnico());
        contaCriada();
    }

    public void realizarEmprestimo(Cliente cliente, double valor) throws LimiteEmprestimoClientePremiumUltrapassadoException, LimiteEmprestimoClienteUltrapassadoException, LimiteSaldoContaFacilAlcancadoException {
        validarLimiteClientePremium(cliente, valor);
        validarLimiteCliente(valor);

        realizarEmprestimo(valor);
    }

    private void validarLimiteCliente(double valor) throws LimiteEmprestimoClienteUltrapassadoException {
        if (valor > LIMITE_EMPRESTIMO_CLIENTE)
            throw new LimiteEmprestimoClienteUltrapassadoException(MENSAGEM_VALIDACAO);

        if (getSaldoDevedor() + valor > LIMITE_EMPRESTIMO_CLIENTE)
            throw new LimiteEmprestimoClienteUltrapassadoException(MENSAGEM_VALIDACAO);
    }

    private void validarLimiteClientePremium(Cliente cliente, double valor) throws LimiteEmprestimoClientePremiumUltrapassadoException {
        if (cliente instanceof Premium && valor > LIMITE_EMPRESTIMO_CLIENTE_PREMIUM)
            throw new LimiteEmprestimoClientePremiumUltrapassadoException(MENSAGEM_VALIDACAO);

        if (cliente instanceof Premium && getSaldoDevedor() + valor > LIMITE_EMPRESTIMO_CLIENTE_PREMIUM)
            throw new LimiteEmprestimoClientePremiumUltrapassadoException(MENSAGEM_VALIDACAO);
    }

    @Override
    public void aplicarTaxasMensais(Cliente cliente) {
        adicionarSaldoDevedor(getSaldoDevedor() * JUROS_SIMPLES_MENSAL_EMPRESTIMO);
    }

    @Override
    public void aplicarTaxasAnuais(Cliente cliente) {
        removerSaldo(TAXA_ANUAL);

        if (cliente instanceof Premium)
            return;

        removerSaldo(cliente.getTaxaAnual());
    }
}
