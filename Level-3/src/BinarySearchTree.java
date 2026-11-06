
import java.util.Scanner;

public class BinarySearchTree {

    static class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    Node root;

    // Insert a node
    Node insert(Node root, int value) {
        if (root == null) {
            return new Node(value);
        }

        if (value < root.data) {
            root.left = insert(root.left, value);
        } else if (value > root.data) {
            root.right = insert(root.right, value);
        } else {
            System.out.println("Duplicate values are not allowed.");
        }

        return root;
    }

    // Search a value
    boolean search(Node root, int key) {
        if (root == null) {
            return false;
        }

        if (root.data == key) {
            return true;
        }

        if (key < root.data) {
            return search(root.left, key);
        }

        return search(root.right, key);
    }

    // Find minimum value node
    Node findMin(Node root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // Delete a node
    Node delete(Node root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.data) {
            root.left = delete(root.left, key);
        } else if (key > root.data) {
            root.right = delete(root.right, key);
        } else {

            // Case 1: No child
            if (root.left == null && root.right == null) {
                return null;
            }

            // Case 2: One child
            if (root.left == null) {
                return root.right;
            }

            if (root.right == null) {
                return root.left;
            }

            // Case 3: Two children
            Node successor = findMin(root.right);
            root.data = successor.data;
            root.right = delete(root.right, successor.data);
        }

        return root;
    }

    // Inorder: Left, Root, Right
    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    // Preorder: Root, Left, Right
    void preorder(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    // Postorder: Left, Right, Root
    void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== BINARY SEARCH TREE =====");
            System.out.println("1. Insert");
            System.out.println("2. Search");
            System.out.println("3. Delete");
            System.out.println("4. Inorder Traversal");
            System.out.println("5. Preorder Traversal");
            System.out.println("6. Postorder Traversal");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter value: ");
                    int value = sc.nextInt();
                    bst.root = bst.insert(bst.root, value);
                    System.out.println("Insertion operation completed.");
                    break;

                case 2:
                    System.out.print("Enter value to search: ");
                    int key = sc.nextInt();

                    if (bst.search(bst.root, key)) {
                        System.out.println("Value found!");
                    } else {
                        System.out.println("Value not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter value to delete: ");
                    int del = sc.nextInt();

                    if (!bst.search(bst.root, del)) {
                        System.out.println("Value not found!");
                    } else {
                        bst.root = bst.delete(bst.root, del);
                        System.out.println("Value deleted successfully!");
                    }
                    break;

                case 4:
                    System.out.print("Inorder: ");
                    bst.inorder(bst.root);
                    System.out.println();
                    break;

                case 5:
                    System.out.print("Preorder: ");
                    bst.preorder(bst.root);
                    System.out.println();
                    break;

                case 6:
                    System.out.print("Postorder: ");
                    bst.postorder(bst.root);
                    System.out.println();
                    break;

                case 7:
                    System.out.println("Exiting BST...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
