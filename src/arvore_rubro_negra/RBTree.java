package arvore_rubro_negra;

public class RBTree<T extends Comparable<T>> {
    private enum Color {
        RED, BLACK
    }

    private class RBNode {
        T data;
        RBNode esq;
        RBNode dir;
        RBNode parent;
        RBNode sibling;
        Color color;

        public RBNode(T data) {
            this.data = data;
            this.color = Color.RED;
            this.esq = null;
            this.dir = null;
            this.parent = null;
            this.sibling = null;
        }
    }

    private RBNode root;

    public RBTree() {
        this.root = null;
    }

    // Implementação do slide, mas com a atualização das referências do irmão
    public void rbInsert(T data) {
        RBNode z = new RBNode(data);

        RBNode y = null;
        RBNode x = this.root;

        while (x != null) {
            y = x;

            if (z.data.compareTo(x.data) < 0) {
                x = x.esq;
            } else {
                x = x.dir;
            }
        }

        z.parent = y;

        if (y == null) {
            this.root = z;
        }
        else if (z.data.compareTo(y.data) < 0) {
            y.esq = z;

            z.sibling = y.dir;

            if (y.dir != null) {
                y.dir.sibling = z;
            }
        } else {
            y.dir = z;

            z.sibling = y.esq;

            if (y.esq != null) {
                y.esq.sibling = z;
            }
        }

        rbInsertFixUp(z);
    }

    private void rbInsertFixUp(RBNode z) {
        RBNode y = null;

        while (z.parent != null && z.parent.color == Color.RED) {
            RBNode avo = z.parent.parent;
            y = z.parent.sibling; // Agora posso pegar o irmão sem precisar verificar se é o direito ou o esquerdo

            // Caso 1: Tio vermelho
            if (y != null && y.color == Color.RED) {
                z.parent.color = Color.BLACK;
                y.color = Color.BLACK;
                avo.color = Color.RED;
                z = avo;
            } else {
                if (z.parent == avo.esq) {
                    // Caso 2: Tio preto e nó faz zigue-zague
                    if (z == z.parent.dir) {
                        z = z.parent;
                        leftRotate(z);
                    }

                    // Caso 3: Tio preto e nó faz linha reta, o caso 2 termina aqui
                    z.parent.color = Color.BLACK;
                    avo.color = Color.RED;
                    rightRotate(avo);
                } else {
                    // Simétricos
                    if (z == z.parent.esq) {
                        z = z.parent;
                        rightRotate(z);
                    }

                    z.parent.color = Color.BLACK;
                    avo.color = Color.RED;
                    leftRotate(avo);
                }
            }
        }
        this.root.color = Color.BLACK;
    }

    public void rbDelete(T data) {
        RBNode z = findNode(this.root, data);

        if (z == null) return;

        RBNode y;
        RBNode x;

        if (z.esq == null || z.dir == null) {
            y = z;
        } else {
            y = treeSucessor(z);
        }

        if (y.esq != null) {
            x = y.esq;
        } else {
            x = y.dir;
        }

        if (x != null) {
            x.parent = y.parent;
        }

        if (y.parent == null) {
            this.root = x;
            if (x != null) {
                x.sibling = null;
            }
        }
        else if (y == y.parent.esq) {
            y.parent.esq = x;

            if (x != null) {
                x.sibling = y.parent.dir;
            }

            if (y.parent.dir != null) {
                y.parent.dir.sibling = x;
            }
        }
        else {
            y.parent.dir = x;

            if (x != null) {
                x.sibling = y.parent.esq;
            }

            if (y.parent.esq != null) {
                y.parent.esq.sibling = x;
            }
        }

        if (y != z) {
            z.data = y.data;
        }

        if (isBlack(y)) {
            rbDeleteFixUp(x, y.parent);
        }
    }

    // A modificação dessa é passar o pai como parâmetro para quando remover o filho ter uma referência
    private void rbDeleteFixUp(RBNode x, RBNode xPai) {
        RBNode w = null;

        while (x != this.root && isBlack(x)) {
            if (x == xPai.esq) {
                w = xPai.dir;

                if (!isBlack(w)) {
                    w.color = Color.BLACK;
                    xPai.color = Color.RED;
                    leftRotate(xPai);
                    w = xPai.dir;
                }

                if (isBlack(w.esq) && isBlack(w.dir)) {
                    w.color = Color.RED;
                    x = xPai;
                    xPai = x.parent;
                }
                else {
                    if (isBlack(w.dir)) {
                        if (w.esq != null) {
                            w.esq.color = Color.BLACK;
                            w.color = Color.RED;
                            rightRotate(w);
                            w = xPai.dir;
                        }

                        w.color = xPai.color;
                        xPai.color = Color.BLACK;
                        if (w.dir != null) {
                            w.dir.color = Color.BLACK;
                        }
                        leftRotate(xPai);

                        x = this.root;
                        xPai = null;
                    }
                }
            }
            else {
                w = xPai.esq;

                if (!isBlack(w)) {
                    w.color = Color.BLACK;
                    xPai.color = Color.RED;
                    rightRotate(xPai);
                    w = xPai.esq;
                }

                if (isBlack(w.dir) && isBlack(w.esq)) {
                    w.color = Color.RED;
                    x = xPai;
                    xPai = x.parent;
                }
                else {
                    if (isBlack(w.esq)) {
                        if (w.dir != null) {
                            w.dir.color = Color.BLACK;
                            w.color = Color.RED;
                            leftRotate(w);
                            w = xPai.esq;
                        }

                        w.color = xPai.color;
                        xPai.color = Color.BLACK;
                        if (w.esq != null) {
                            w.esq.color = Color.BLACK;
                        }
                        rightRotate(xPai);

                        x = this.root;
                        xPai = null;
                    }
                }
            }
        }

        if (x != null) {
            x.color = Color.BLACK;
        }
    }

    private void leftRotate(RBNode x) {
        RBNode y = x.dir;
        x.dir = y.esq;

        if (y.esq != null) {
            y.esq.parent = x;
        }

        if (x.esq != null) {
            x.esq.sibling = x.dir;
        }
        if (x.dir != null) {
            x.dir.sibling = x.esq;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y;
            y.sibling = null;
        }
        else if (x == x.parent.esq) {
            x.parent.esq = y;

            y.sibling = x.parent.dir;
            if (x.parent.dir != null) {
                x.parent.dir.sibling = y;
            }
        }
        else {
            x.parent.dir = y;

            y.sibling = x.parent.esq;
            if (x.parent.esq != null) {
                x.parent.esq.sibling = y;
            }
        }

        y.esq = x;
        x.parent = y;

        x.sibling = y.dir;
        if (y.dir != null) {
            y.dir.sibling = x;
        }
    }

    private void rightRotate(RBNode x) {
        RBNode y = x.esq;
        x.esq = y.dir;

        if (y.dir != null) {
            y.dir.parent = x;
        }

        if (x.dir != null) {
            x.dir.sibling = x.esq;
        }
        if (x.esq != null) {
            x.esq.sibling = x.dir;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y;
            y.sibling = null;
        }
        else if (x == x.parent.dir) {
            x.parent.dir = y;

            y.sibling = x.parent.esq;
            if (x.parent.esq != null) {
                x.parent.esq.sibling = y;
            }
        }
        else {
            x.parent.esq = y;

            y.sibling = x.parent.dir;
            if (x.parent.dir != null) {
                x.parent.dir.sibling = y;
            }
        }

        y.dir = x;
        x.parent = y;

        x.sibling = y.esq;
        if (y.esq != null) {
            y.esq.sibling = x;
        }
    }

    // Pra não usar a mesma lógica de encontrar o nó toda vez que precisar
    private RBNode findNode(RBNode atual, T data) {
        while (atual != null) {
            int cmp = atual.data.compareTo(data);

            if (cmp == 0) {
                return atual;
            }
            else if (cmp > 0) {
                atual = atual.esq;
            }
            else {
                atual = atual.dir;
            }
        }
        return null;
    }

    // A função de achar o sucessor de um nó (tá no slide)
    private RBNode treeSucessor(RBNode x) {
        RBNode atual = x.dir;

        while (atual.esq != null) {
            atual = atual.esq;
        }

        return atual;
    }

    // Essa é pra buscar as chaves ao invés do nó, já que o findNode é privado
    public T buscarValor(T chave) {
        RBNode noEncontrado = findNode(this.root, chave);

        if (noEncontrado != null) {
            return noEncontrado.data;
        }

        return null;
    }

    public void imprimirArvore() {
        if (this.root == null) {
            System.out.println("Árvore vazia!");
            return;
        }
        imprimirRecursivo(this.root, "", true);
    }

    private void imprimirRecursivo(RBNode no, String prefixo, boolean isTail) {
        if (no != null) {
            // Testando se a referência do irmão funciona
            String infoIrmao = (no.sibling != null) ? no.sibling.data.toString() : "Nenhum";

            // Nó atual
            System.out.println(prefixo + (isTail ? "└── " : "├── ")
                    + no.data + " (" + no.color + ") [Irmão: " + infoIrmao + "]");

            // Filhos
            imprimirRecursivo(no.dir, prefixo + (isTail ? "    " : "│   "), false);
            imprimirRecursivo(no.esq, prefixo + (isTail ? "    " : "│   "), true);
        }
    }

    // Essa serve pra evitar NullPointerException na hora de verificar se é preto
    private boolean isBlack(RBNode n) {
        return n == null || n.color == Color.BLACK;
    }
}
