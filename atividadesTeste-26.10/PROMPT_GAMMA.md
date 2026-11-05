# Prompt para o Gamma – Apresentação Testes Unitários com JUnit

> **Como usar:** no Gamma, escolha **Criar → Colar texto**. Cole a **PARTE 1** no campo de instruções
> adicionais e a **PARTE 2** no campo de texto. Escolha a opção que **mantém o texto** (preservar), não a que gera texto novo.
> Cada `---` vira um slide.

---

## PARTE 1 – Instruções para o Gamma

Crie uma apresentação acadêmica em português do Brasil, com visual limpo e tema escuro ou neutro.
Cada bloco separado por `---` é um slide. Mantenha a ordem dos slides e os textos como estão.
**Use exatamente as imagens dos links**, sem trocar por imagens geradas ou banco de imagens, e sem cortar o conteúdo delas
(são prints de código e de resultados, que precisam ficar legíveis). Coloque cada imagem grande no slide.
Não invente dados, números ou porcentagens além dos que estão no texto.

---

## PARTE 2 – Conteúdo dos slides

# Testes Unitários com JUnit

Qualidade e Teste de Software – Atividade de Testes Unitários

João Ritter

---

# Critérios usados

Para cada classe usamos pelo menos um critério de cada lado:

- **Caixa-preta (funcional):** partição de equivalência, valor-limite, estados e regras de negócio
- **Caixa-branca (estrutural):** ramos, condições, caminhos independentes e tratamento de erros

Em todos os exercícios: entradas válidas e inválidas, exceções, testes parametrizados e `@BeforeEach`.

---

# Exercício 1 – Calculadora de Desconto

Método testado: `calcular(valorCompra, percentual)`

- Valor da compra não pode ser negativo
- Percentual tem que estar entre 0 e 100

---

# Exercício 1 – Classe de teste

![Trecho da classe CalculadoraDescontoTest](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/prints-apresentacao/ex1-classe-de-teste.png)

---

# Exercício 1 – Como os testes foram escolhidos

- **Partições:** valorCompra < 0 (inválido) e >= 0 (válido); percentual < 0, 0 a 100 e > 100
- **Valores-limite:** -0,01 / 0 / 0,01 e 99,99 / 100 / 100,01. O 0 e o 100 são aceitos porque o código usa `<` e `>`
- **Grafo de fluxo:** a condição com `||` foi separada em dois nós. V(G) = 10 − 8 + 2 = **4 caminhos**, todos testados
- **Caso extra:** os dois valores inválidos juntos, para ver qual mensagem aparece primeiro

![Grafo de fluxo de controle da CalculadoraDesconto](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/gfc-calculadora-desconto.png)

---

# Exercício 1 – Resultado da execução

**12 testes executados, 0 falhas**

![Resultado da execução da CalculadoraDescontoTest](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/prints-apresentacao/ex1-resultado-execucao.png)

---

# Exercício 1 – Cobertura

Linhas: **100%** | Ramos: **100%**

![Cobertura da classe CalculadoraDesconto](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/image-1.png)

![Porcentagem de cobertura da CalculadoraDesconto](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/image.png)

---

# Exercício 1 – O conjunto é suficiente?

**Sim, para esse método.** Ele não guarda estado e não tem laço: os 4 caminhos foram executados e os limites foram testados dos dois lados.

Só para cobrir os ramos, 4 testes bastavam (um por caminho). Os testes extras são os das **fronteiras**, que é onde o erro costuma aparecer.

---

# Exercício 2 – Conta Bancária

Regras testadas:

- Saldo inicial não pode ser negativo (0 é aceito)
- Depósito precisa ser maior que 0
- Saque precisa ser maior que 0 e no máximo o saldo

---

# Exercício 2 – Classe de teste

![Trecho da classe ContaBancariaTest](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/prints-apresentacao/ex2-classe-de-teste.png)

---

# Exercício 2 – Como os testes foram escolhidos

- **Limites:** no construtor o 0 é válido; no depósito e no saque o 0 é inválido. Sacar **exatamente o saldo** é válido
- **Mudança de estado:** depósito aumenta e saque diminui o saldo no valor exato. **Quando dá erro, o saldo não muda** (todo teste de exceção confere isso)
- **Sequência de operações:** confirma que o saque usa sempre o saldo atual
- **JUnit:** `@BeforeEach` cria uma conta com 100 antes de cada teste; `@ParameterizedTest` nos casos inválidos; `assertThrows` para conferir a mensagem do erro
- **Grafo:** construtor e depositar com V(G) = 2, sacar com V(G) = 3

![Grafo de fluxo de controle da ContaBancaria](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/gfc-conta-bancaria.png)

---

# Exercício 2 – Resultado da execução

**20 testes executados, 0 falhas**

![Resultado da execução da ContaBancariaTest](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/prints-apresentacao/ex2-resultado-execucao.png)

---

# Exercício 2 – Cobertura

Linhas: **100%** | Ramos: **100%**

![Cobertura da classe ContaBancaria](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/image-3.png)

![Porcentagem de cobertura da ContaBancaria](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/image-2.png)

---

# Exercício 2 – O conjunto é suficiente?

A cobertura de 100% já aparecia com uns 6 testes. Os outros não mudam a porcentagem, mas **são os que pegam erro de verdade**:

- **Sacar exatamente o saldo:** pega se alguém trocar `>` por `>=`
- **Conferir o saldo nos testes de erro:** pega o erro de "fez a conta antes de validar"
- **Sequência de operações:** pega se o método usar o saldo inicial em vez do atual

---

# Exercício 3 – Reserva de Hotel

Regras de negócio:

- **R1:** diárias > 0 e valor da diária > 0
- **R2:** café da manhã custa R$ 30 por diária
- **R3:** 7 diárias ou mais têm 10% de desconto (o café também entra no desconto)
- **R4:** reserva cancelada vale R$ 0
- **R5:** reserva cancelada não aceita café e não pode ser cancelada de novo

---

# Exercício 3 – Classe de teste

![Trecho da classe ReservaHotelTest](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/prints-apresentacao/ex3-classe-de-teste.png)

---

# Exercício 3 – Como os testes foram escolhidos

- **Limites:** 0 e 1 diária; valor da diária 0, -0,01 e 0,01; **6 e 7 diárias** (onde começa o desconto)
- **Estados:** ATIVA (com ou sem café) → CANCELADA, sem volta. Testamos o que pode e o que não pode em cada estado
- **Grafo do `calcularValorTotal()`:** V(G) = 12 − 10 + 2 = **4**, com 5 caminhos possíveis, todos testados
- **Caminho mais importante:** 7 diárias com café → 7 × (100 + 30) = 910, menos 10% = **819**. Mostra que o desconto vale em cima do café

![Grafo de fluxo de controle do calcularValorTotal](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/gfc-reserva-hotel-calcular-valor-total.png)

---

# Exercício 3 – Resultado da execução

**19 testes executados, 0 falhas**

![Resultado da execução da ReservaHotelTest](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/prints-apresentacao/ex3-resultado-execucao.png)

---

# Exercício 3 – Cobertura

Linhas: **100%** | Ramos: **100%**

![Cobertura da classe ReservaHotel – parte 1](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/image-5.png)

![Cobertura da classe ReservaHotel – parte 2](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/image-6.png)

![Porcentagem de cobertura da ReservaHotel](https://raw.githubusercontent.com/joaovritter/Qualidade-Teste-de-Software/refs/heads/claude/zen-bardeen-je3g16/atividadesTeste-26.10/image-4.png)

---

# Exercício 3 – O conjunto é suficiente?

A cobertura deu 100%, mas **com uns 6 testes já chegava nisso** e ficaria de fora:

- o limite 6/7 do desconto (um `>= 7` virando `> 7` passaria);
- café sem desconto e desconto sem café, testados separados;
- reserva com café sendo cancelada, que também tem que dar 0.

Por isso os testes saíram dos **caminhos do grafo e dos estados**, e não da porcentagem.

---

# Conclusão

- **51 testes, 0 falhas.** As três classes ficaram com 100% de cobertura de linhas e ramos
- **Cobertura alta não quer dizer que não há defeito:** nos três exercícios ela chegou a 100% bem antes de terminar os testes
- Os testes que mais fazem diferença vieram dos **valores-limite** (0, 100, sacar o saldo exato, 6/7 diárias) e da **verificação de estado** (saldo não muda no erro, reserva cancelada)
- A parte estrutural (ramos, condições e grafo) serviu para garantir que **nenhum caminho ficou sem teste**
