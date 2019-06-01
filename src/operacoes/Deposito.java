package operacoes;

import com.sun.javafx.binding.StringFormatter;

public class Deposito extends Operacao {
    public Deposito(double valor) {
        setValor(valor);
    }

    @Override
    public String consultarOperacao() {
        return StringFormatter.format("DEPOSITO %d.2", getValor()).toString();
    }
}
