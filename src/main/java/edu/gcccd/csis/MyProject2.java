package edu.gcccd.csis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Iterator;

public class MyProject2 implements Project2 {

    @Override
    public NodeList<Integer> addition(NodeList<Integer> nodeList1, NodeList<Integer> nodeList2) {

        Project2 p = new MyProject2();
        if(nodeList1==null && nodeList2!=null) {return nodeList2;}
        if(nodeList1!=null && nodeList2==null) {return nodeList1;}

        nodeList1 = p.removeLeadingZeros(nodeList1);
        nodeList2 = p.removeLeadingZeros(nodeList2);

        NodeList<Integer> revNodeList1 = p.reverse(nodeList1, nodeList1.iterator());
        NodeList<Integer> revNodeList2 = p.reverse(nodeList2, nodeList2.iterator());
        NodeList<Integer> combinedList = new NodeList<>();

        int carryover = 0;
        while(revNodeList1.iterator().hasNext() || revNodeList2.iterator().hasNext()) {
            int currentSum = 0;

            if(!revNodeList1.iterator().hasNext()) {
                revNodeList1.append(0);
                currentSum = carryover + revNodeList2.iterator().next();
            }

            if(!revNodeList2.iterator().hasNext()) {
                revNodeList2.append(0);
                currentSum = carryover + revNodeList1.iterator().next();
            }

            if(revNodeList1.iterator().hasNext() && revNodeList2.iterator().hasNext())
                currentSum = revNodeList1.iterator().next() + revNodeList2.iterator().next() + carryover;

            carryover=0;
            if(currentSum>9) {
                carryover = 1;
                currentSum -= 10;
            }
            combinedList.append(currentSum);

            revNodeList1.remove(revNodeList1.iterator().next());
            revNodeList2.remove(revNodeList2.iterator().next());
        }
        if(carryover!=0) combinedList.append(carryover);
        return p.reverse(combinedList, combinedList.iterator());
    }

    @Override
    public NodeList<Integer> addition(Iterator<NodeList<Integer>> iterator) {
        Project2 p = new MyProject2();
        NodeList<NodeList<Integer>> condensedListOfLists = new NodeList<>();
        int counter = 0;
        while(iterator.hasNext()) {
            counter++;
            condensedListOfLists.append(p.addition(iterator.next(), iterator.next()));
        }
        if(counter<2) return condensedListOfLists.iterator().next();
        addition(condensedListOfLists.iterator());
        return condensedListOfLists.iterator().next();
    }

    public NodeList<Integer> reverse(NodeList<Integer> appendedNodeList, Iterator<Integer> iterator) {
        if(iterator.hasNext()) {
            int k = iterator.next();
            appendedNodeList.remove(appendedNodeList.iterator().next());
            reverse(appendedNodeList, iterator);
            appendedNodeList.append(k);
        } else {return null;}
        return appendedNodeList;
    }

    public NodeList<Integer> removeLeadingZeros(NodeList<Integer> nodeList) {
        if(nodeList==null) return null;
        while(nodeList.iterator().next()==0) {
            nodeList.remove(nodeList.iterator().next());
            if (nodeList.iterator().next()==0) break;
        } return nodeList;
    }

    @Override
    public void save(NodeList<Integer> nodeList, String fileName) throws Exception {

    }


    @Override
    public NodeList<Integer> load(String fileName) {

        return null;
    }


    public static void main(final String[] args) {
        final int L = 32;

        final NodeList<Integer> n1 = Project2.generateNumber(L); // (head 1st) e.g. 3457
        final NodeList<Integer> n2 = Project2.generateNumber(L); // (head 1st) e.g. 682

        final Project2 p = new MyProject2();

        Project2.print(p.addition(n1, n2)); //  n1+n2, e.g. 4139

        final NodeList<NodeList<Integer>> listOfLists = new NodeList<>();
        for (int i = 0; i < L; i++) {
            listOfLists.append(Project2.generateNumber(L));
        }
        p.addition(listOfLists.iterator());
        //p.save(p.addition(listOfLists.iterator()), "result.bin");
        //Project2.print(p.load("result.bin"));
    }
}