package operacoes;

import com.sun.javafx.binding.StringFormatter;

public class Emprestimo extends Operacao {
    public Emprestimo(double valor) {
        setValor(valor);
    }

    @Override
    public String consultarOperacao() {
        return StringFormatter.format("EMPRESTIMO %d.2", getValor()).toString();
    }
}
