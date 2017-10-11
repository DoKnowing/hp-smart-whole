package hp.smart.whole.analyzer;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

public class RBTree {

    private  static final int RED=1;
    private static final int BLACK=0;
    private TreeNode nil=new TreeNode();
    private TreeNode root=nil;

    public void insert(TreeNode node){
        TreeNode p=nil;
        TreeNode cur=root;

        while(cur!=nil){
            p=cur;
            if(node.value<cur.value){
                cur=cur.left;
            }else if(node.value>cur.value){
                cur=cur.right;
            }
        }
        node.parent=p;
        if (p == nil) {
            root=node;
        }else if(node.value<p.value){
            p.left=node;
        }else{
            p.right=node;
        }
        insertFixup(node);
    }

    public void insertFixup(TreeNode x){
        while(x.parent.color==RED){
            if(x.parent==x.parent.parent.left){
                TreeNode y=x.parent.parent.right;
                if(y.color==RED){
                    x.parent.color=BLACK;
                    y.color=BLACK;
                    x.parent.parent.color=RED;
                    x=x.parent.parent;
                }else{
                    if(x==x.parent.right){
                        x=x.parent;
                        leftRotate(x);
                    }
                    x.parent.color=BLACK;
                    x.parent.parent.color=RED;
                    rightRotate(x.parent.parent);
                }
            }else {
                TreeNode y=x.parent.parent.left;
                if(y.color==RED){
                    y.color=BLACK;
                    x.parent.color=BLACK;
                    x.parent.parent.color=RED;
                    x=x.parent.parent;
                }else{
                    if(x==x.parent.left){
                        x=x.parent;
                        rightRotate(x);
                    }
                    x.parent.color=BLACK;
                    x.parent.parent.color=RED;
                    leftRotate(x.parent.parent);
                }
            }
        }
        root.color=BLACK;
}

    public void delete(TreeNode deleteNode){
        TreeNode replaceNode;
        int originalColor=deleteNode.color;
        if(deleteNode.left!=nil && deleteNode.right!=nil){
            TreeNode temp=successor(deleteNode.right);
            deleteNode.value=temp.value;
            delete(temp);
        }else{
            if(deleteNode.left!=nil){
                replaceNode=deleteNode.left;
            }else {
                replaceNode = deleteNode.right;
            }
            transplante(deleteNode,replaceNode);
            if(originalColor==BLACK)
                deleteFixup(replaceNode);
        }
    }

    public TreeNode successor(TreeNode node){
        while(node.left!=nil){
            node=node.left;
        }
        return node;
    }
    public void transplante(TreeNode deleteNode,TreeNode replaceNode){
        replaceNode.parent=deleteNode.parent;
        if(deleteNode==root){
            root=replaceNode;
        }
        if(deleteNode==deleteNode.parent.left){
            deleteNode.parent.left=replaceNode;
        }else {
            deleteNode.parent.right=replaceNode;
        }
    }

    public void deleteFixup(TreeNode replaceNode){
        while(replaceNode!=root && replaceNode.color==BLACK) {
            if (replaceNode == replaceNode.parent.left) {
                TreeNode brother = replaceNode.parent.right;
                if (brother.color == RED) {
                    //将情况变为兄弟为黑色
                    brother.color = BLACK;
                    replaceNode.parent.color = RED;
                    leftRotate(replaceNode.parent);
                } else if (brother.left.color == BLACK && brother.right.color == BLACK) {
                    //将当前需要去双黑的节点改为父节点
                    brother.color=RED;
                    replaceNode=replaceNode.parent;
                } else if (brother.right.color == BLACK) {
                    //将情况变为兄弟的右孩子为红色
                    brother.left.color=BLACK;
                    brother.color=RED;
                    rightRotate(brother);
                }else{
                    //给当前双黑节点去重
                    brother.right.color=brother.color;
                    brother.color=replaceNode.parent.color;
                    replaceNode.parent.color=BLACK;
                    leftRotate(replaceNode.parent);
                    replaceNode=root;
                }
            } else {
                TreeNode brother=replaceNode.parent.left;
                if(brother.color==RED){
                    brother.color=BLACK;
                    replaceNode.parent.color=RED;
                    rightRotate(replaceNode.parent);
                }else if(brother.left.color==BLACK && brother.right.color==BLACK){
                    brother.color=RED;
                    replaceNode=replaceNode.parent;
                }else if(brother.left.color==BLACK){
                    brother.color=RED;
                    brother.right.color=BLACK;
                    leftRotate(brother);
                }else{
                    brother.left.color=brother.color;
                    brother.color=replaceNode.parent.color;
                    replaceNode.parent.color=BLACK;
                    rightRotate(replaceNode.parent);
                    replaceNode=root;
                }
            }
        }
        replaceNode.color=BLACK;
    }

    public void leftRotate(TreeNode x){
        TreeNode y=x.right;
        y.parent=x.parent;
        if(x.parent==nil){
            root=y;
        }else if(x==x.parent.left){
            x.parent.left=y;
        }else{
            x.parent.right=y;
        }
        x.right=y.left;
        if(y.left!=nil){
            y.left.parent=x;
        }

        x.parent=y;
        y.left=x;
    }

    public void rightRotate(TreeNode x){
        TreeNode y=x.left;
        y.parent=x.parent;
        if(x.parent==nil){
            root=y;
        }else if(x==x.parent.left){
            x.parent.left=y;
        }else{
            x.parent.right=y;
        }
        x.left=y.right;
        if(y.right!=nil){
            y.right.parent=x;
        }

        x.parent=y;
        y.right=x;
    }

    public TreeNode search(int value){
        TreeNode p=root;
        while(p!=nil && p.value!=value){
            if(value<p.value){
                p=p.left;
            }else{
                p=p.right;
            }
        }
        return p;
    }

    class TreeNode{
        private TreeNode parent;
        private TreeNode left;
        private TreeNode right;
        private int color;
        private int value;

        public TreeNode(){
            //叶节点
            this.color=BLACK;
            this.value=-1;
            this.left=this.right=this.parent= null;
        }

        public TreeNode(int value){
            //中间节点
            this.color=RED;
            this.value=value;
            this.left=this.right=this.parent= nil;
        }

    }

    @Test
    public void test(){
        System.out.println("-----------");
        int[] arr={12,1,9,2,0,11,7, 19, 4, 15 ,18, 5 ,14, 13 ,10, 16, 6, 3, 8, 17};
        for(int i=0;i<arr.length;i++){
            insert(new TreeNode(arr[i]));
        }
        for(int i=0;i<arr.length;i++){
            TreeNode node=search(arr[i]);
            if(node!=nil){
                delete(node);
            }

        }
    }

}
