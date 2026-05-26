import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// --- CLASSE UF (Union-Find / DSU) ---
class UF {
    private int[] id;

    public UF(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public int find(int p) {
        int raiz = p;
        while (raiz != id[raiz]) {
            raiz = id[raiz];
        }
        // Compressão de caminho (Path Compression)
        int atual = p;
        while (atual != raiz) {
            int proximo = id[atual];
            id[atual] = raiz;
            atual = proximo;
        }
        return raiz;
    }

    public boolean union(int p, int q) {
        int raizP = find(p);
        int raizQ = find(q);
        if (raizP == raizQ) {
            return false;
        }
        id[raizP] = raizQ;
        return true;
    }
}

// --- CLASSE ARESTA ---
class Aresta implements Comparable<Aresta> {
    private final int v;
    private final int w;
    private final int peso;

    public Aresta(int v, int w, int peso) {
        this.v = v;
        this.w = w;
        this.peso = peso;
    }

    public int umVertice() {
        return v;
    }

    public int outroVertice(int vertice) {
        if (vertice == v) return w;
        else if (vertice == w) return v;
        else throw new IllegalArgumentException("Vértice inválido");
    }

    public int peso() {
        return peso;
    }

    @Override
    public int compareTo(Aresta outra) {
        return Integer.compare(this.peso, outra.peso);
    }
}

// --- CLASSE KRUSKAL MST ---
class KruskalMST {
    private long custoMST;

    public KruskalMST(int numVertices, Aresta[] arestas) {
        custoMST = 0;
        
        // Ordenação padrão das arestas pelo peso
        Arrays.sort(arestas);

        UF uf = new UF(numVertices);
        int arestasColocadas = 0;

        for (Aresta aresta : arestas) {
            int v = aresta.umVertice();
            int w = aresta.outroVertice(v);

            if (uf.union(v, w)) {
                custoMST += aresta.peso();
                arestasColocadas++;
                
                if (arestasColocadas == numVertices - 1) {
                    break;
                }
            }
        }
    }

    public long custoTotal() {
        return custoMST;
    }
}

// --- CLASSE PRINCIPAL (Única Pública) ---
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String linha;

        while ((linha = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(linha);
            if (!st.hasMoreTokens()) continue;

            int m = Integer.parseInt(st.nextToken()); // Junções [cite: 7]
            int n = Integer.parseInt(st.nextToken()); // Estradas [cite: 7]

            // Condição de parada [cite: 8]
            if (m == 0 && n == 0) {
                break;
            }

            Aresta[] arestas = new Aresta[n];
            long custoTotalGrafo = 0;

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken()); // Vértice X [cite: 8]
                int v = Integer.parseInt(st.nextToken()); // Vértice Y [cite: 8]
                int peso = Integer.parseInt(st.nextToken()); // Comprimento Z [cite: 8]

                arestas[i] = new Aresta(u, v, peso);
                custoTotalGrafo += peso;
            }

            // Calcula a árvore geradora mínima
            KruskalMST mst = new KruskalMST(m, arestas);

            // Economia = Custo total inicial - Custo da MST
            long economia = custoTotalGrafo - mst.custoTotal();
            System.out.println(economia);
        }
    }
}