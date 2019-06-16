package contas;

import clientes.Cliente;
import exceptions.LimiteSaldoContaFacilAlcancadoException;

public class Poupanca extends Conta {
    private static final double RENDIMENTO_MENSAL = 1.4d;

    public Poupanca(double valor, Cliente cliente) throws LimiteSaldoContaFacilAlcancadoException {
        adicionarSaldo(valor);
        setCodigoUnico(criarCodigoUnico());
        this.cliente = cliente;
        contaCriada();
    }

    @Override
    public void aplicarTaxasMensais() throws LimiteSaldoContaFacilAlcancadoException {
        adicionarSaldo(getSaldo() * RENDIMENTO_MENSAL);
    }

    @Override
    public void aplicarTaxasAnuais() {
        // Método não implementado pois conta poupança não tem taxas anuais
    }
}
