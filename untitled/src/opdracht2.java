import java.util.*;

public class opdracht2 {
    public static void main( String args[] ) {
        BKnoop_1<String> root, knoopB, knoopC, knoopD,
                knoopE, knoopF, knoopG;

        root   = new BKnoop_1<String>( "A" );
        knoopB = new BKnoop_1<String>( "B" );
        knoopC = new BKnoop_1<String>( "C" );
        knoopD = new BKnoop_1<String>( "D" );
        knoopE = new BKnoop_1<String>( "E" );
        knoopF = new BKnoop_1<String>( "F" );
        knoopG = new BKnoop_1<String>( "G" );

        root.add( knoopB );
        root.add( knoopC );

        knoopB.add( knoopD );
        knoopB.add( knoopE );

        knoopE.add( knoopF );
        knoopE.add( knoopG );

        knoopC.insert( knoopE, BKnoop_1.LEFT );

        System.out.println( root.preOrderToString());
        System.out.println(root.postOrdertoString());
        System.out.println(root.inOrderToString());
        System.out.println( root.levelOrderToString());
    }
}









class BKnoop_1<E> {
    private BKnoop_1<E> parent, leftChild, rightChild;
    private E userObject;
    private static StringBuffer buffer;
    private Queue<BKnoop_1<E>> q;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;


    public BKnoop_1() {
        this( null );
    }

    public BKnoop_1( E userObject ) {
        parent = null;
        leftChild = null;
        rightChild = null;
        this.userObject = userObject;
    }

    public String preOrderToString() {
        buffer = new StringBuffer();
        preOrder();
        return buffer.toString();
    }

    private void preOrder() {
        buffer.append( userObject.toString() );
        if( leftChild != null )
            leftChild.preOrder();
        if( rightChild != null )
            rightChild.preOrder();
    }





    public String levelOrderToString() {
        buffer = new StringBuffer();

        q = new LinkedList<BKnoop_1<E>>();
        q.add( this );
        levelOrder();
        return buffer.toString();
    }

    private void levelOrder() {
        while( !q.isEmpty() ) {
            BKnoop_1<E> knoop = q.remove();
            buffer.append( knoop.userObject.toString() );
            if( knoop.leftChild != null )
                q.add( knoop.leftChild );
            if( knoop.rightChild != null )
                q.add( knoop.rightChild );
        }
    }
    public String inOrderToString(){
        buffer= new StringBuffer();
        inOrder();
        return buffer.toString();
    }
    private void inOrder(){
        if (leftChild!=null)
            leftChild.inOrder();
        buffer.append(userObject.toString());
        if (rightChild!=null)
            rightChild.inOrder();
    }

    public String postOrdertoString(){
        buffer = new StringBuffer();
        postOrder();
        return buffer.toString();
    }

    private void postOrder(){

        if( leftChild != null )
            leftChild.postOrder();
        if( rightChild != null )
            rightChild.postOrder();
        buffer.append( userObject.toString() );
    }







    public void add( BKnoop_1<E> newChild ) {
        if( leftChild == null )
            insert( newChild, LEFT );
        else
        if( rightChild == null )
            insert( newChild, RIGHT );
        else
            throw new IllegalArgumentException(
                    "Meer dan 2 kinderen" );
    }

    public E get() {
        return userObject;
    }

    public BKnoop_1<E> getLeftChild() {
        return leftChild;
    }

    public BKnoop_1<E> getRightChild() {
        return rightChild;
    }

    public BKnoop_1<E> getParent() {
        return parent;
    }

    public void insert( BKnoop_1<E> newChild, int childIndex ) {
        if( isAncestor( newChild ) )
            throw new IllegalArgumentException(
                    "Nieuw kind is voorouder" );
        if( childIndex != LEFT &&
                childIndex != RIGHT )
            throw new IllegalArgumentException(
                    "Index moet 0 of 1 zijn" );

        if( newChild != null ) {
            BKnoop_1<E> oldParent = newChild.getParent();
            if( oldParent != null )
                oldParent.remove( newChild );
        }

        newChild.parent = this;
        if( childIndex == LEFT )
            leftChild = newChild;
        else
            rightChild = newChild;
    }

    public boolean isChild( BKnoop_1<E> aNode ) {
        return aNode == null?
                false :
                aNode.getParent() == this;
    }

    public boolean isAncestor( BKnoop_1<E> aNode ) {
        BKnoop_1<E> ancestor = this;
        while( ancestor != null && ancestor != aNode )
            ancestor = ancestor.getParent();
        return ancestor != null;
    }

    public void remove( BKnoop_1<E> aChild ) {
        if( aChild == null )
            throw new IllegalArgumentException(
                    "Argument is null" );

        if( !isChild( aChild ) )
            throw new IllegalArgumentException(
                    "Argument is geen kind" );

        if( aChild == leftChild ) {
            leftChild.parent = null;
            leftChild = null;
        }
        else {
            rightChild.parent = null;
            rightChild = null;
        }
    }

    public String toString() {
        return userObject.toString();
    }
}
