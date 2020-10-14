package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KDCore {

    Node root=null;
    int k;
    List<Integer> points= new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    String[] dp= new String[3];

    public void expandTree(Node pNode, Node nNode, int depth){

        if(root==null){ root= nNode;}

        else {
            if (pNode.co_ord.elementAt(depth % k) < nNode.co_ord.elementAt(depth % k)) {
                if (pNode.right == null) {
                    pNode.right = nNode;
                    return;
                } else
                    expandTree(pNode.right, nNode, depth += 1);
            } else {
                if (pNode.left == null) {
                    pNode.left = nNode;
                    return;
                } else
                    expandTree(pNode.left, nNode, depth += 1);
            }
        }
    }

    public void searchPoint(Node parentPoint, Node targetpoint, int depth){

        boolean found=true;

        for(int i=0;i<k;i++){
            if(parentPoint.co_ord.get(i)!=targetpoint.co_ord.get(i)){
                found = false;
                break;
            }
        }
        if(!found){
            if(parentPoint.co_ord.elementAt(depth%k) < targetpoint.co_ord.elementAt(depth%k)){
                if(root.right==null){
                    System.out.println("No such point found in the space!");
                    return;
                }
                else
                    searchPoint(parentPoint.right,targetpoint,depth+=1);
            }
            else{
                if(parentPoint.left==null){
                    System.out.println("No such point found in the space!");
                    return;
                }
                else
                    searchPoint(parentPoint.left,targetpoint,depth+=1);
            }
        }
        else{
            System.out.println("Search point found at depth "+depth+"!");
            if (parentPoint.left!=null)
                searchPoint(parentPoint.left,targetpoint,depth+1);
        }
    }
    public void printTree(Node p, int tab, char childMark){

      if(p==null) return;

        System.out.print("lvl= "+tab);

        for(int i=0;i<tab*2;i++) System.out.print("   ");

        if(childMark!='0') {
            System.out.print(childMark+"--> { ");

            for (int i = 0; i < p.co_ord.size() - 1; i++)
                System.out.print(p.co_ord.get(i) + ", ");
            System.out.println(p.co_ord.get(p.co_ord.size() - 1) + " }");
        }
        else{
             System.out.print("  { ");
            for (int i = 0; i < p.co_ord.size() - 1; i++)
                System.out.print(p.co_ord.get(i) + ", ");
            System.out.println(p.co_ord.get(p.co_ord.size() - 1) + " }");

        }
        tab++;
        printTree(p.left, tab,'L');
        printTree(p.right,tab,'R');

    }
    public Node inputForAPoint(){

        String s;
        Node n= new Node();
        System.out.println("Enter point's co-ordinates: ");
        sc.nextLine();
        s=sc.nextLine();
        dp=s.split(",");
        for(int j=0;j<k;j++) n.co_ord.add(Integer.parseInt(dp[j]));

        return n;
    }

    public void initial(){

        int pc;
        String input;

        System.out.print("Enter K: ");
        k= sc.nextInt();
       System.out.print("Initial number of points: ");
        pc=sc.nextInt();
        System.out.println("Enter point's co-ordinates(*comma seperated):");
        sc.nextLine();
        for(int i=0;i<pc;i++) {
            input=sc.nextLine();
            dp=input.split(",");

            for(int j=0;j<k;j++) points.add(Integer.parseInt(dp[j]));
        }

        int[] initialPoints= { 3,6, 17,15, 13,15, 6,12, 9,1, 2,7, 10,19,2,7};

        for(int i=0;i<=points.size()-k;i+=k){

            Node n= new Node();
            for(int j = i; j < i + k; j++){

                //n.co_ord.add(initialPoints[j]);
                n.co_ord.add(points.get(j));
            }
            int depth=0;

            expandTree(root,n,depth);
        }
        int optln=0;

        while(optln!=4){

            System.out.print("\n1.Print tree\t2.Insert\n3.Search\t\t4.Close\n");
            optln=sc.nextInt();

            switch (optln){
                case 1: printTree(root,0,'0');
                        break;
                case 2: Node newPoint = inputForAPoint();
                        expandTree(root,newPoint,0);
                        System.out.println("A new point inserted!");
                        break;
                case 3: Node searchPoint= inputForAPoint();
                        searchPoint(root,searchPoint,0);
                        break;
                default: break;
            }
        }
    }
}
