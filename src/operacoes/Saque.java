package operacoes;

import com.sun.javafx.binding.StringFormatter;

public class Saque extends Operacao {
    public Saque(double valor) {
        this.setValor(valor);
    }

    @Override
    public String consultarOperacao() {
        return StringFormatter.format("SAQUE %d.2", getValor()).toString();
    }
}
