package operacoes;

public abstract class Operacao {
    private double valor;

    public abstract String consultarOperacao();

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return this.valor;
    }
}
