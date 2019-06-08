package exceptions;

public class LimiteEmprestimoClienteUltrapassadoException extends Throwable {
    public LimiteEmprestimoClienteUltrapassadoException(String mensagem) {
        super(mensagem);
    }
}
