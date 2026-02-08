public class Main {
    public static void main(String[] args) {
        BinNode<Integer> t = createSampleTree();
        BinNode<Integer> t2 = createBigTree();
        BinNode<Integer> t3 = createBigNonSearchTree();
        t.display();
        t2.display();
        t3.display();
        System.out.printf("ex_14:the tree has %s leaves\n", ex_14(t));
        System.out.printf("ex_18:the tree t2 %s t\n", ex_18(t2, t2) ? "contains" : "does not contain");
        System.out.printf("ex_19:the positive numbers sum - the negative numbers sum = %s\n", ex_19(t3));
        System.out.print("ex_1:");
        printSome(t2);
        System.out.printf("\nhas %s evens without odd sons\n", sumEm(t2));
        System.out.printf("%s evens without odd sons\n", exists(t2) ? "contains" : "does not contain");
        System.out.printf("%s is evens without odd sons\n", all(t2) ? "all of the tree " : "not all the tree");
        System.out.printf("%s a balanced tree\n", isBalanced(t) ? "is" : "is not");
        System.out.printf("tree height is : %s\n", height(t));
        System.out.printf("ex_20: %s\n",ex_20(t,20));
        System.out.printf("ex_26:%s a perfect tree\n",perfectTree(t3) ? "is" : "is not");
        System.out.printf("ex_23: %s\n",ex_23(t2));
    }

    public static BinNode<Integer> createSampleTree() {
        // 1. Create the bottom-most layer (Level 4)
        BinNode<Integer> deepLeaf1 = new BinNode<>(1); // Child for 2
        BinNode<Integer> deepLeaf2 = new BinNode<>(6); // Left child for 7
        BinNode<Integer> deepLeaf3 = new BinNode<>(8); // Right child for 7

        // 2. Create the middle layer (Level 3)
        // Node 2: Has '1' as left child, no right child
        BinNode<Integer> node2 = new BinNode<>(deepLeaf1, 2, null);

        // Node 7: Has '6' and '8' as children
        BinNode<Integer> node7 = new BinNode<>(deepLeaf2, 7, deepLeaf3);

        // These remain leaves for now
        BinNode<Integer> leaf12 = new BinNode<>(12);
        BinNode<Integer> leaf20 = new BinNode<>(20);

        // 3. Create the sub-parents (Level 2)
        BinNode<Integer> subLeft = new BinNode<>(node2, 5, node7);
        BinNode<Integer> subRight = new BinNode<>(leaf12, 15, leaf20);

        // 4. Create the root (Level 1)
        return new BinNode<>(subLeft, 10, subRight);
    }

    public static BinNode<Integer> createBigTree() {
        BinNode<Integer> leaf1 = new BinNode<>(1);
        BinNode<Integer> leaf3 = new BinNode<>(3);
        BinNode<Integer> leaf6 = new BinNode<>(6);
        BinNode<Integer> leaf8 = new BinNode<>(8);
        BinNode<Integer> leaf30 = new BinNode<>(30);
        BinNode<Integer> node2 = new BinNode<>(leaf1, 2, leaf3);
        BinNode<Integer> node7 = new BinNode<>(leaf6, 7, leaf8);
        BinNode<Integer> node25 = new BinNode<>(null, 25, leaf30);
        BinNode<Integer> leaf12 = new BinNode<>(12);
        BinNode<Integer> subLeft = new BinNode<>(node2, 5, node7);
        BinNode<Integer> node20 = new BinNode<>(null, 20, node25);
        BinNode<Integer> subRight = new BinNode<>(leaf12, 14, node20);
        return new BinNode<>(subLeft, 10, subRight);
    }

    public static BinNode<Integer> createBigNonSearchTree() {
        // --- Step 1: Create new "Bad" Nodes (breaking BST rules) ---

        // A negative number (-99).
        // We will put this on the RIGHT of 20. (Error: Right child must be larger than
        // parent)
        BinNode<Integer> badRightChild = new BinNode<>(-99);

        // A huge number (1000).
        // We will put this on the LEFT of 2. (Error: Left child must be smaller than
        // parent)
        BinNode<Integer> badLeftChild = new BinNode<>(12);

        // --- Step 2: Create the Original Leaves (with attached bad nodes) ---

        // Original leaf '2' gets '1000' on its left
        BinNode<Integer> node2 = new BinNode<>(badLeftChild, 2, null);

        // Original leaf '20' gets '-99' on its right
        BinNode<Integer> node20 = new BinNode<>(null, 20, badRightChild);

        // These original leaves remain untouched
        BinNode<Integer> node7 = new BinNode<>(7);
        BinNode<Integer> node12 = new BinNode<>(12);

        // --- Step 3: Rebuild the Original Parents ---

        // Recreating the original '5' (Left subtree)
        BinNode<Integer> subLeft = new BinNode<>(node2, 5, node7);

        // Recreating the original '15' (Right subtree)
        BinNode<Integer> subRight = new BinNode<>(node12, 15, node20);

        // --- Step 4: The Original Root ---
        return new BinNode<>(subLeft, 10, subRight);
    }

    public static <T> int ex_14(BinNode<T> t) {
        if (t == null)
            return 0;
        if (!t.hasLeft() && !t.hasRight())
            return 1;
        return ex_14(t.getLeft()) + ex_14(t.getRight());
    }

    private static boolean isIn(BinNode<Integer> t, int num) {
        if (t == null)
            return false;
        if (t.getValue() == num)
            return true;
        return isIn(t.getLeft(), num) || isIn(t.getRight(), num);
    }

    public static boolean ex_18(BinNode<Integer> t1, BinNode<Integer> t2) {
        if (t2 == null)
            return true;
        if (!isIn(t1, t2.getValue()))
            return false;
        return ex_18(t1, t2.getLeft()) && ex_18(t1, t2.getRight());
    }
    public static int ex_19(BinNode<Integer> t) {
        return posSum(t) - negSum(t);
    }

    private static int posSum(BinNode<Integer> t) {
        if (t == null)
            return 0;
        if (t.getValue() > 0)
            return t.getValue() + posSum(t.getLeft()) + posSum(t.getRight());
        return posSum(t.getLeft()) + posSum(t.getRight());
    }

    private static int negSum(BinNode<Integer> t) {
        if (t == null)
            return 0;
        if (t.getValue() < 0)
            return -t.getValue() + negSum(t.getLeft()) + negSum(t.getRight());
        return negSum(t.getLeft()) + negSum(t.getRight());
    }

    public static void printSome(BinNode<Integer> t) {
        if (t == null)
            return;
        if (t.getValue() % 2 == 0
                && (t.hasLeft() && t.hasRight())
                && (t.getLeft().getValue() % 2 == 0 && t.getRight().getValue() % 2 == 0)) {
            System.out.print(t.getValue() + " ");
        }
        if (t.getValue() % 2 == 0
                && (!t.hasLeft() && t.hasRight())
                && (t.getRight().getValue() % 2 == 0))
            System.out.print(t.getValue() + "");
        if (t.getValue() % 2 == 0
                && (t.hasLeft() && !t.hasRight())
                && (t.getLeft().getValue() % 2 == 0))
            System.out.print(t.getValue() + " ");
        printSome(t.getLeft());
        printSome(t.getRight());
    }

    public static int sumEm(BinNode<Integer> t) {
        if (t == null)
            return 0;
        int c = 0;
        if (t.getValue() % 2 == 0
                && (t.hasLeft() && t.hasRight())
                && (t.getLeft().getValue() % 2 == 0 && t.getRight().getValue() % 2 == 0))
            c = 1;

        if (t.getValue() % 2 == 0
                && (!t.hasLeft() && t.hasRight())
                && (t.getRight().getValue() % 2 == 0))
            c = 1;
        if (t.getValue() % 2 == 0
                && (t.hasLeft() && !t.hasRight())
                && (t.getLeft().getValue() % 2 == 0))
            c = 1;
        return sumEm(t.getLeft()) + sumEm(t.getRight()) + c;
    }

    public static boolean exists(BinNode<Integer> t) {
        if (t == null)
            return false;
        if (t.getValue() % 2 == 0
                && (t.hasLeft() && t.hasRight())
                && (t.getLeft().getValue() % 2 == 0 && t.getRight().getValue() % 2 == 0))
            return true;

        if (t.getValue() % 2 == 0
                && (!t.hasLeft() && t.hasRight())
                && (t.getRight().getValue() % 2 == 0))
            return true;
        if (t.getValue() % 2 == 0
                && (t.hasLeft() && !t.hasRight())
                && (t.getLeft().getValue() % 2 == 0))
            return true;
        return exists(t.getLeft()) || exists(t.getRight());
    }

    public static boolean all(BinNode<Integer> t) {
        if (t == null)
            return true;
        if (t.getValue() % 2 == 1)
            return false;
        return all(t.getLeft()) && all(t.getRight());
    }

    public static boolean isBalanced(BinNode<Integer> t) {
        if (t == null)
            return true;
        int leftHeight = height(t.getLeft());
        int rightHeight = height(t.getRight());
        if (Math.abs(leftHeight - rightHeight) > 1)
            return false;
        return isBalanced(t.getLeft()) && isBalanced(t.getRight());
    }

    public static <T> int height(BinNode<T> t) {
        if (t == null)
            return -1;
        return 1 + Math.max(height(t.getLeft()), height(t.getRight()));
    }
    public static boolean ex_20(){
        return true;
    }
    public static int nodec(BinNode<Integer> t){
        if (t==null)
            return 0;
        return 1+nodec(t.getLeft())+nodec(t.getRight());
    }
    public static boolean ex_20(BinNode<Integer> t,int n){
        if(n!=nodec(t))
            return false;
        for(int i=1;i<=n;i++){
            if(countisIn(t, i)!=1)
                return false;
        }
        return true;
    }
    public static int countisIn(BinNode<Integer> t, int num) {
        if (t == null)
            return 0;
        int c=0;
        if (t.getValue() == num)
            c=1;
        return c+countisIn(t.getLeft(), num) + countisIn(t.getRight(), num);
    }
    public static boolean perfectTree(BinNode<Integer> t){
        return nodec(t)==Math.pow(2, height(t));
    }
    public static int ex_23(BinNode<Integer> t){
        if(t==null)
            return Integer.MIN_VALUE;
        return Math.max(t.getValue(), Math.max(ex_23(t.getRight()),ex_23(t.getLeft())));
    }
    
}
