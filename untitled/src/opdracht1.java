import javax.swing.*;
import javax.swing.tree.*;


public class opdracht1 extends JFrame {


    public static void main(String[] args) {
        JFrame frame=new opdracht1();
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tree");
        frame.setContentPane(new Boom() );
        frame.setVisible(true);
    }

}

class Boom extends JTree{
    private DefaultTreeModel treeModel;


    public Boom(){
        DefaultMutableTreeNode Derde_Jaar=new DefaultMutableTreeNode("Derde jaar");
        DefaultMutableTreeNode Stage =new DefaultMutableTreeNode("Stage");
        DefaultMutableTreeNode Minor=new DefaultMutableTreeNode("Minor");
        DefaultMutableTreeNode Buitenland=new DefaultMutableTreeNode("Buitenland");
        DefaultMutableTreeNode Nederland=new DefaultMutableTreeNode("Nederland");
        DefaultMutableTreeNode Nijmegen=new DefaultMutableTreeNode("Nijmegen");
        DefaultMutableTreeNode Radboud_UMC=new DefaultMutableTreeNode("Radboud_UMC");
        DefaultMutableTreeNode Radboud_Universiteit=new DefaultMutableTreeNode("Radboud_Universiteit");
        DefaultMutableTreeNode Wageningen=new DefaultMutableTreeNode("Wageningen");
        DefaultMutableTreeNode Europa=new DefaultMutableTreeNode("Europa");
        DefaultMutableTreeNode Utrecht=new DefaultMutableTreeNode("Utrecht");
        DefaultMutableTreeNode WUR=new DefaultMutableTreeNode("WUR");
        DefaultMutableTreeNode KeyGene=new DefaultMutableTreeNode("KeyGene");
        DefaultMutableTreeNode GeneTwister=new DefaultMutableTreeNode("GeneTwister");
        DefaultMutableTreeNode HAN=new DefaultMutableTreeNode("HAN");
        DefaultMutableTreeNode AndereHG=new DefaultMutableTreeNode("Andere hoge school");
        DefaultMutableTreeNode Universiteit_pm=new DefaultMutableTreeNode("Universiteit premaster");


        Derde_Jaar.add(Stage);
        Derde_Jaar.add(Minor);

        Stage.add(Nederland);
        Stage.add(Buitenland);
        Buitenland.add(Europa);

        Nederland.add(Nijmegen);
        Nederland.add(Wageningen);
        Nederland.add(Utrecht);

        Nijmegen.add(Radboud_UMC);
        Nijmegen.add(Radboud_Universiteit);

        Wageningen.add(WUR);
        Wageningen.add(KeyGene);
        Wageningen.add(GeneTwister);

        Minor.add(HAN);
        Minor.add(AndereHG);
        Minor.add(Universiteit_pm);




        treeModel =new DefaultTreeModel(Derde_Jaar);
        setModel(treeModel);
    }

}
