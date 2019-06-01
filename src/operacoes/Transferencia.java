package operacoes;

import com.sun.javafx.binding.StringFormatter;

public class Transferencia extends Operacao {
    public Transferencia(double valor) {
        setValor(valor);
    }

    @Override
    public String consultarOperacao() {
        return StringFormatter.format("TRANSFERENCIA %d.2", getValor()).toString();
    }
}
