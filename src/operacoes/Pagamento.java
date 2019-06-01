package operacoes;

import com.sun.javafx.binding.StringFormatter;

public class Pagamento extends Operacao {
    public Pagamento(double valor) {
        setValor(valor);
    }

    @Override
    public String consultarOperacao() {
        return StringFormatter.format("PAGAMENTO %d.2", getValor()).toString();
    }
}
