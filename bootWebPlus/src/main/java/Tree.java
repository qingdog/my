import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// 定义树节点
class TreeNode {
    int value;
    // 插入序号
    int index;
    TreeNode left;
    TreeNode right;

    public TreeNode(int value, int index) {
        this.value = value;
        this.index = index;
        this.left = null;
        this.right = null;
    }
}

// 定义二叉树类
class BinaryTree {
    TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    // 插入节点
    public void insert(int value, int index) {
        TreeNode newNode = new TreeNode(value, index);
        if (root == null) {
            root = newNode;
        } else {
            this.addChild(this.root, newNode);
        }
    }

    void addChild(TreeNode current, TreeNode newNode) {
        TreeNode parent = current;
        // 和父节点相等则放入左子树
        if (newNode.value <= current.value) {
            current = current.left;
            if (current == null) {
                parent.left = newNode;
                return;
            }
        } else {
            current = current.right;
            if (current == null) {
                parent.right = newNode;
                return;
            }
        }
        this.addChild(current, newNode);
    }

    // 搜索节点
    public List<TreeNode> search(int value) {
        TreeNode current = this.root;
        List<TreeNode> treeNodes = new ArrayList<>();

        this.searchNode(current, value, treeNodes);
        return treeNodes;
    }

    void searchNode(TreeNode current, int value, List<TreeNode> treeNodes) {
        if (value == current.value) {
            treeNodes.add(current);

            current = current.left;
        } else if (value < current.value) {
            current = current.left;
        } else {
            current = current.right;
        }
        if (current != null) {
            searchNode(current, value, treeNodes);
        }

    }
}

public class Tree {
    public static void main(String[] args) {
        int[] data = {5, 8, 1, 9, 6, 3, 2, 4, 8, 7};

        BinaryTree binaryTree = new BinaryTree();
        for (int i = 0; i < data.length; i++) {
            // 插入数据和插入顺序
            binaryTree.insert(data[i], i + 1);
        }

        while (true) {
            // 读取用户输入的数字
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please input data:");
            String nextLine = scanner.nextLine();

            if ("exit".equalsIgnoreCase(nextLine)) {
                return;
            }

            // 在二叉树中查找数字并输出位置
            List<TreeNode> treeNodes = null;
            if (nextLine.matches("\\d+")) {
                treeNodes = binaryTree.search(Integer.parseInt(nextLine));
            }

            if (treeNodes == null || treeNodes.isEmpty()) {
                System.out.println("data:" + nextLine + " not exist");
                return;
            }
            String collect = treeNodes.stream().map(treeNode -> treeNode.index + "").collect(Collectors.joining(","));
            System.out.println("data:" + nextLine + " position:" + collect);
        }

    }
}