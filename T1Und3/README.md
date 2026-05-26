#T1Und3

## 1. Informações Gerais
* **Nome do Problema:** Dark Roads (Estradas Escuras) 
* **Link do Problema:** (https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=0&problem=2678&mosmsg=Submission+received+with+ID+31151009) *(Nota: Originalmente publicado como UVa 11631)* 
* **Linguagem Utilizada:** Java (Versão 17 ou superior)
* **Integrantes do Grupo:** * *Aaron Magno*
  * *Davi de Paula*
  * *Arthur Soares*

## 2. Como Executar a Solução

A solução foi desenvolvida de forma modular e centralizada em um único arquivo autônomo dentro do diretório `src/` para garantir total compatibilidade com ambientes de juízes online. Para executar o projeto localmente via terminal, siga as etapas abaixo:

1. Abra o terminal e navegue até a pasta raiz do projeto.
2. Compile a classe principal:
   ```bash
   javac src/Main.java
3.Localize o dado de entrada 
 ```
cd T1Und3
 java -cp src Main < dados/entradas_do_problema.txt
   ``` 
## 3. Engenharia e Modelagem do Problema como Grafo Ponderado
Vértices ($V$): Cada junção de estradas da cidade foi definida como um vértice do grafo. Arestas ($E$): Cada estrada bidirecional que conecta diretamente duas junções foi mapeada como uma aresta não-direcionada. O limite de estradas é dado por $m-1 \le n \le 200.000$.  Pesos/Custos: O comprimento $z$ em metros de cada estrada foi definido como o peso da sua respectiva aresta. Dado que o enunciado fixa o custo de iluminação em exatamente 1 Bytelandian Dollar por metro ao dia , o peso da aresta reflete de forma direta o seu custo financeiro operacional diário.  A restrição fundamental de que os cidadãos devem continuar trafegando por caminhos iluminados entre qualquer par de junções significa que o subgrafo resultante após os cortes deve obrigatoriamente permanecer conectado.  Relacionamento com a MST e Variação AplicadaPara maximizar a economia diária do governo, a meta é encontrar a estrutura de custo mínimo que interconecte todos os vértices da malha urbana sem a presença de ciclos (que representariam gastos e caminhos redundantes). Essa definição equivale ao conceito exato de uma Árvore Geradora Mínima (MST - Minimum Spanning Tree).  A variação do problema de MST padrão aplicada aqui reside na métrica de saída: o objetivo final não é reportar o custo da árvore geradora em si, mas sim a economia máxima obtida através da variação global de custos. Portanto, o algoritmo calcula a soma do peso de todas as arestas lidas na entrada (o custo total original do grafo) e subtrai o peso final da MST gerada, encontrando o montante total poupado. 
## Estratégia Algorítmica
O grupo implementou o Algoritmo de Kruskal, utilizando uma abordagem gulosa (greedy) baseada na análise e seleção global das arestas ordenadas pelo custo.O Papel do Union-Find / DSUComo o algoritmo de Kruskal examina as arestas em ordem crescente de peso, ele demanda um mecanismo de alto desempenho para verificar se a inclusão da aresta atual fechará um ciclo indesejado no subgrafo corrente. Para solucionar essa restrição, o grupo implementou manualmente a estrutura de dados Union-Find (Disjoint Set Union) com as seguintes operações:find(p): Identifica a qual componente conectado (conjunto disjunto) o vértice $p$ pertence. Para otimização de performance, empregou-se a técnica de Compressão de Caminho (Path Compression), que aponta todos os nós visitados na busca diretamente para a raiz do conjunto, achatando a árvore de busca.union(p, q): Compara as raízes de p e q. Se as raízes forem distintas, significa que os vértices estão em componentes isolados e a estrada pode ser iluminada com segurança, fundindo os dois conjuntos. Caso as raízes sejam iguais, os vértices já estão interconectados e a aresta é rejeitada por redundância.
## Análise de Complexidade
O limitante dominante da aplicação é o processo de ordenação das arestas, resultando em uma complexidade de O(E \log E), onde E é o número de estradas (n). As operações associadas ao Union-Find com compressão de caminho demandam tempo de O(E * alpha(V)), onde alpha representa a função inversa de Ackermann, um termo cujo crescimento é tão lento que assume valor menor ou igual a 4 para qualquer cenário do mundo real. O algoritmo executa em poucos milissegundos, operando com ampla folga abaixo do limite de tempo de 1 segundo determinado pelas plataformas de correção.Complexidade de Espaço (Estrutura Dominante): A estrutura de dados dominante em memória é o array nativo encarregado de armazenar os objetos representativos das E arestas da malha viária. A complexidade de espaço total é de O(V + E), configurando uma alocação estritamente linear e altamente eficiente para a volumetria estipulada pelo problema
## Casos Especiais Relevantes
Risco de Overflow Numérico: A especificação do problema pontua que a soma do comprimento total de todas as vias de um caso de teste é menor do que $2^{31}$. Uma vez que o tipo primitivo int em Java é sinalizado de 32 bits, seu limite superior termina rigorosamente em 2^{31}-1. Acumular a economia ou o custo total em variáveis comuns do tipo int dispararia estouros de bits (overflow). Solução: Uso obrigatório do tipo long (64 bits) para computar as somas e subtrações de custos.  Arestas com Pesos Iguais: Diante de estradas com comprimentos idênticos, a ordenação padrão as organiza de forma arbitrária. Esse comportamento pode induzir a construção de topologias de MST visualmente distintas caso existam caminhos alternativos equipotentes. Contudo, as propriedades matemáticas da MST garantem que o custo mínimo total e o cálculo de variação da economia permaneçam rigorosamente idênticos e invariáveis.Múltiplos Casos de Teste (Isolamento de Escopo): O fluxo de entrada submete múltiplos cenários de teste sequenciais, encerrando as atividades unicamente ao ler a flag de parada $m=n=0$. Para mitigar riscos de vazamento de memória ou contaminação de dados entre cidades distintas, o algoritmo força o reset completo do vetor de IDs do DSU e limpa as referências de arestas a cada nova iteração.  
## Comprovação de Sucesso 
https://github.com/davidias25/T1Und3/blob/main/T1Und3/Evidencias/Captura%20de%20tela%202026-05-26%20135656.png
