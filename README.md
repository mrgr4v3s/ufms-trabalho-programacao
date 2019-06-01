Descrição
---
Você deve implementar um sistema de gerenciamento para um banco. Seu sistema deve permitir a criação e o fechamento de contas, transferências, depósitos e saques. 
***
Uma conta pode ser corrente, fácil, ou poupança. Toda conta pertence a uma única agência e possui um código único que representa a conta e um cliente.  Um cliente Tradicional paga uma taxa anual de R$ 15,00 enquanto um cliente Premium não paga anuidade alguma. 
***
Uma conta fácil pode ser aberta pelo próprio cliente. Uma conta fácil possui direito a apenas um saque por mês, e pode fazer também apenas uma transferência por mês. A conta deve saber quem é o cliente dono da conta, cliente que é representado através de seu nome, endereço, data nascimento, cpf e tipo de cliente. No caso da conta fácil, o cliente é cadastrado numa agência especial "Online" de número 22222, sem endereço.  Por fim, uma conta fácil possui uma anuidade de R$ 10,00 e o cliente pode ter no máximo R$5.000,00 em sua conta.
***
Uma conta corrente não possui limitações a cerca da quantidade de saques, transferências ou valores guardados. Porém, uma conta corrente tem uma taxa anual de R$ 5,00. A conta corrente também permite um empréstimo de até R$200,00 para clientes tradicionais, ou R$2.500,00 para clientes premium, com juros simples de 2% por mês. 
***
Finalmente, uma conta poupança não possui taxas anuais e permite apenas transferências e depósitos. No caso de uma conta poupança, ao ano aplica-se um rendimento de 1,4% no valor armazenado na conta. 
***
Seu sistema deve ler um evento e respondê-lo de alguma forma. Seguem os eventos lidos como inteiros:
***
Seu sistema deve possuir uma classe Endereço, que contém país, estado, cidade, endereço, bairro, cep e número;
***
### 1 - Virada do Mês. 

Executa todas as ações que devem ser realizadas quando um novo mês é iniciado. Clientes  que possuem uma conta há 1 ano deverão pagar taxas por exemplo. Clientes com saldo devedor pagam este valor, que é descontado dos seus saldos atuais.
``` 
1
```

### 2 - Cadastrar Agência 

Recebe nome da Agência, seguido do seu número e endereço (País (String), Cidade (String), endereço (String), bairro (String), cep (String) e número (String)),
```
2
Agência do Pantanal
12345
Brasil
Campo Grande
Avenida Afonso Pena
Centro
79088170
2516
```
***
### 3 - Abertura de Conta. 

Deve ler o tipo de conta a ser aberta (C:corrente, P:Poupança, F:Fácil) e ler o cpf do cliente.
 
Caso o cpf do cliente já esteja cadastrado, vincule a nova conta a este cliente. Caso contrário crie e cadastre um novo cliente. Em ambos os casos, as informações sobre o cliente deve ser lidas e atualizadas quando for necessário (incluindo o tipo de cliente). 

Sobre os dados do cliente, leia seu nome, endereço (completo como na agência), data de nascimento e tipo de cliente. Por fim, leia a Agência e valor inicial depositado. 

Contas devem ser criadas de forma incremental, iniciando na conta 0001 e devem ser representadas utilizando 4 dígitos. 

```
3

C

12341234

Antônio Bandeiras

Brasil

Campo Grande

Avenida Afonso Pena

Centro

79088170

1250

01/01/1970

TRADICIONAL

12345

15000,00
```
***
### 4 - Saque Leia a agência, conta e o valor.

```
4

12345

0001

15,00
```
***
### 5 - Depósito em conta 
Leia agência, conta e o valor.
```
5
12345
0001
15,00
```
***
### 6 - Transferência 
Leia agência e conta origem, agência e conta de destino e o valor.
```
6
12345
0001
12345
0002
15,00
```
***
### 7 - Solicitar Empréstimo 
Leia agência, conta e valor.
```
7
12345
0001
15,00
```
***
### 8 - Gerar Extratos 
Leia agência e conta. Um Extrato lista até as últimas 10 operações realizadas na conta de um cliente (Saques, Depósitos, Transferências, Pagamentos), seu saldo atual e o quanto ele deve ao banco.
```
8
12345
0001
```

**Saida**
```
DEPOSITO 15000,00
SAQUE 15,00
DEPOSITO 15,00
TRANSFERENCIA 15,00
EMPRESTIMO 15,00

SALDO ATUAL: 14985,00
SALDO DEVEDOR: 15,00
```
***
### 9 - Gerar Relatórios 
Leia o  tipo de relatório: (Uma conta é listada mostrando sua agência, conta e cpf do dono).
* A: Listar todas as contas do banco ordenadas pela agência e então pelo número da conta
* B: Listar todas as contas do banco ordenadas pelo nome do titular da conta. Para múltiplas contas, ordená-las pelo tipo de conta nesta ordem: Fácil, Corrente, Poupança
* C: Listar todas as poupanças
* D: Listar todas as contas correntes
* E: Listar todas as contas fáceis
* F: Listar todos os clientes que possuem mais do que uma conta
* G: Listar todos clientes ordenados alfabeticamente.
```
9
A
12345
0001
12341234
```
***
**SEMPRE** que uma operação for solicitada, porém não pode ser atendida por problemas relacionados ao limite do usuário (sacar 25 de uma conta de 20), uma exceção sua deve ser disparada e u novo valor deve ser solicitado. Um novo valor será informado ou -1 será utilizado  
