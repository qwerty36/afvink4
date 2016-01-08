import java.util.*;
import java.io.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class opdracht3 extends Applet implements ActionListener {

    public static Node tree;
    static String text;
    final boolean gui = true;
    private TextField inputField, outputField, outputResult, inputResult;
    private Button getOutputButton, getInputButton, getFreq, showTree;
    public static String freqs = "";
    public static Hashtable ht;

    public void init() {

        if (gui) {

            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            inputField = new TextField("", 20);
            add(inputField);
            getOutputButton = new Button("HuffEncode");
            add(getOutputButton);
            getOutputButton.addActionListener(this);
            outputResult = new TextField("", 40);
            add(outputResult);

            getFreq = new Button("Get Frequencies");
            add(getFreq);
            getFreq.addActionListener(this);
            showTree = new Button("Show Huffman Tree");
            add(showTree);
            showTree.addActionListener(this);


        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getOutputButton
                && !(inputField.getText().equals(""))) {

            tree = null;
            freqs = "";
            text = inputField.getText();
            outputResult.setText(huffEncode(text));
        }

        if (e.getSource() == getFreq && (tree != null)) {
            new showFreq(freqs);
        }
        if (e.getSource() == showTree && (tree != null)) {
            new showTree(tree, ht);
        }
    }

    public static Hashtable calcFreq(String s) {
        Hashtable alpha = new Hashtable(28);
        for (int i = 0; i < s.length(); i++) {
            int currentValue;
            if (alpha.containsKey("" + s.charAt(i))) {
                currentValue = Integer.valueOf(alpha.get(""
                        + s.charAt(i)).toString()).intValue();
                currentValue++;
            } else {
                currentValue = 1;
            }
            alpha.put("" + s.charAt(i), "" + currentValue++);
        }
        return alpha;
    }

    public static String huffEncode(String startText) {

        Hashtable h = calcFreq(startText);
        ht = h;
        Hashtable code = new Hashtable(h.size());
        int i = 0;
        int i2;
        Node n1, n2, n3;
        String returnString = "";
        Node forest[] = new Node[26];

        for (Enumeration e = h.keys(); e.hasMoreElements();) {
            char ne = e.nextElement().toString().charAt(0);
            forest[i] = new Node(ne, Integer.valueOf(h.get(""
                    + ne).toString()).intValue());
            i++;
        }

        while (populated(forest) != 1) {
            i2 = getSmallest1(forest);
            n1 = forest[i2];
            forest[i2] = null;
            i2 = getSmallest1(forest);
            n2 = forest[i2];
            forest[i2] = null;
            forest[i2] = new Node('-', n1, n2);
            tree = forest[i2];
        }


        for (Enumeration e = h.keys(); e.hasMoreElements();) {
            char ne = e.nextElement().toString().charAt(0);
            freqs = freqs + ne + " (" + h.get("" + ne) + "): ";
            NodeSearch.lookFor(tree, ne);
            code.put("" + ne, invertString(NodeSearch.result));
            freqs = freqs + invertString(NodeSearch.result) + "\n";
        }


        for (int j = 0; j < startText.length(); j++) {
            returnString = returnString + code.get(""
                    + startText.charAt(j)).toString();
        }

        return returnString;
    }


    public static int getSmallest1(Node[] n) {
        int smallest1 = 100;
        int returnNode = -1;
        for (int i = 0; i < n.length; i++) {

            if (n[i] != null) {
                if (n[i].frequency < smallest1) {
                    smallest1 = n[i].frequency;
                    returnNode = i;
                }
            }
        }
        return returnNode;
    }

    public static int populated(Node[] n) {
        int count = 0;
        for (int i = 0; i < n.length; i++) {
            if (n[i] != null) {
                count++;
            }
        }
        return count;
    }

    public static String invertString(String s) {
        String returnString = "";
        for (int i = 1; i <= s.length(); i++) {
            returnString = returnString + s.charAt(s.length() - i);
        }
        return returnString;
    }
}

class showFreq extends Frame {

    public showFreq(String f) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
//add(new TextArea(f,40,20));

        StringTokenizer T = new StringTokenizer(f, "\n", false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
        while (T.hasMoreTokens()) {
            add(new Label(T.nextToken()));
        }
        setTitle("Frequencies / Codes");
        setSize(100, 5 * f.length());
        setVisible(true);
    }
}

class showTree extends Frame {

    private Node tree;
    private Hashtable ht;

    public showTree(Node N, Hashtable h) {
        tree = N;
        ht = h;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
        setTitle("Showing Huffman Tree");
        setSize(400, 400);
        setVisible(true);
        repaint();
    }

    public void paint(Graphics g) {
        for (Enumeration e = ht.keys(); e.hasMoreElements();) {
            g.setFont(new Font("SansSerif", Font.PLAIN, 8));
            char c = e.nextElement().toString().charAt(0);
            NodeSearch.lookFor(tree, c);
            String s = opdracht3.invertString(NodeSearch.result);
            int ypos = 50;
            int xpos = getSize().width / 2;
            int level = 2;
            for (int i = 0; i < s.length(); i++) {
                int newxpos = (int) (getSize().width / (Math.pow(2, level)));

                if (s.charAt(i) == '0') {
                    g.drawLine(xpos, ypos, xpos - newxpos, ypos + 40);
                    g.drawString("0", xpos - newxpos / 2, ypos + 20);
                    xpos = xpos - newxpos;
                } else if (s.charAt(i) == '1') {
                    g.drawLine(xpos, ypos, xpos + newxpos, ypos + 40);
                    g.drawString("1", xpos + newxpos / 2, ypos + 20);
                    xpos = xpos + newxpos;
                }
                ypos += 40;
                level++;
            }
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            g.drawString("" + c, xpos, ypos);
        }
    }
}

class Node {

    public char letter;
    public int frequency;
    public Node child1, child2;

    public Node(char l, int f) {
        letter = l;
        frequency = f;
    }

    public Node(char l, Node c1, Node c2) {
        letter = l;
        frequency = c1.frequency + c2.frequency;
        child1 = c1;
        child2 = c2;
    }

    public void newChild1(Node n) {
        child1 = n;
    }

    public void newChild2(Node n) {
        child2 = n;
    }

    public String toString() {
        String c1 = "";
        String c2 = "";
        if (child1 != null) {
            c1 = child1.toString();
        }
        if (child2 != null) {
            c2 = child1.toString();
        }
        return letter + "\n" + c1 + " " + c2;
    }

    public void lookFor(char c) {
        if (letter == c) {
            NodeSearch.found = true;
        }
        if (!NodeSearch.found) {
            if (child1 != null) {
                child1.lookFor(c);
                if (NodeSearch.found) {
                    NodeSearch.result = NodeSearch.result + "0";
                    return;
                }
            }
            if (child2 != null) {
                child2.lookFor(c);
                if (NodeSearch.found) {
                    NodeSearch.result = NodeSearch.result + "1";
                    return;
                }
            }
        }
    }

    public void toChar(String s) {
        if (NodeSearch.charAt == s.length()) {
            NodeSearch.result = "" + letter;
            NodeSearch.found = true;
        } else {
            if (s.charAt(NodeSearch.charAt) == '0') {
                NodeSearch.charAt++;
                child1.toChar(s);
            } else {
                NodeSearch.charAt++;
                child2.toChar(s);
            }
        }
    }

}

class NodeSearch {

    static String result;
    static boolean found;
    static int charAt;

    public static void lookFor(Node n, char c) {
        result = "";
        found = false;
        n.lookFor(c);
    }

    public static void toChar(Node n, String bitString) {
        result = "";
        found = false;
        charAt = 0;
        n.toChar(bitString);
    }
}
