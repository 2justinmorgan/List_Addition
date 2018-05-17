package edu.gcccd.csis;

import java.util.Iterator;

public class MyProject2 implements Project2 {

    @Override
    public NodeList<Integer> addition(NodeList<Integer> nodeList1, NodeList<Integer> nodeList2) {
        //if(!nodeList1.iterator().hasNext() || !nodeList2.iterator().hasNext()) return null;
        if(!nodeList1.iterator().hasNext() && nodeList2.iterator().hasNext()) {return nodeList2;}
        if(nodeList1.iterator().hasNext() && !nodeList2.iterator().hasNext()) {return nodeList1;}

        Project2 p = new MyProject2();
        boolean nodeList1HasLeadingZeros = nodeList1.iterator().next()==0;
        while(nodeList1HasLeadingZeros) {
            nodeList1.remove(nodeList1.iterator().next());
            nodeList1HasLeadingZeros = nodeList1.iterator().next()==0;
        }
        boolean nodeList2HasLeadingZeros = nodeList2.iterator().next()==0;
        while(nodeList2HasLeadingZeros) {
            nodeList2.remove(nodeList2.iterator().next());
            nodeList2HasLeadingZeros = nodeList2.iterator().next()==0;
        }

        NodeList<Integer> revNodeList1 = p.reverse(nodeList1, nodeList1.iterator());
        NodeList<Integer> revNodeList2 = p.reverse(nodeList2, nodeList2.iterator());
        NodeList<Integer> combinedList = new NodeList<>();

        //Project2.print(revNodeList1);
        //Project2.print(revNodeList2);

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


    public NodeList<Integer> reverse(NodeList<Integer> appendedNodeList, Iterator<Integer> iterator) {
        if(iterator.hasNext()) {
            int k = iterator.next();
            appendedNodeList.remove(appendedNodeList.iterator().next());
            reverse(appendedNodeList, iterator);
            appendedNodeList.append(k);
        } else {return null;}
        return appendedNodeList;
    }


    @Override
    public NodeList<Integer> addition(Iterator<NodeList<Integer>> iterator) {
        Project2 p = new MyProject2();
        NodeList<NodeList<Integer>> condensedListOfLists = new NodeList<>();
        int countLimit = 0;
        while(iterator.hasNext()) {
            countLimit++;
            condensedListOfLists.append(p.addition(iterator.next(),iterator.next()));
        }
        if(countLimit<2) {
            System.out.println("\nresult");
            Project2.print(condensedListOfLists.iterator().next());
            return condensedListOfLists.iterator().next();
        }
        System.out.println("result so far with countLimit "+countLimit);
        addition(condensedListOfLists.iterator());
        Project2.print(condensedListOfLists.iterator().next());
        return condensedListOfLists.iterator().next();

//        NodeList<Integer> nodeList = new NodeList<>();
//        NodeList<Integer> additionNodeList = new NodeList<>();
//        NodeList<Integer> resultNodeList = new NodeList<>();/
//        int i;
//        while(iterator.hasNext()) {
//            nodeList = iterator.next();
//            iterator.remove();
//
//            //  add items from first two node lists
//
//            additionNodeList = addition(iterator);
//
//
//            while(additionNodeList.iterator().hasNext()){
//                i = additionNodeList.iterator().next();
//                additionNodeList.iterator().remove();
//
//                resultNodeList.append(i);
//            }
//        }
    }


    @Override
    public void save(NodeList<Integer> nodeList, String fileName) {

    }


    @Override
    public NodeList<Integer> load(String fileName) {

        return null;
    }


    public static void main(final String[] args) {
        final int L = 16;

        final NodeList<Integer> n1 = Project2.generateNumber(L); // (head 1st) e.g. 3457
        final NodeList<Integer> n2 = Project2.generateNumber(L); // (head 1st) e.g. 682

        final Project2 p = new MyProject2();

        Project2.print(p.addition(n1, n2)); //  n1+n2, e.g. 4139
        System.out.println("---before for loop---");
        final NodeList<NodeList<Integer>> listOfLists = new NodeList<>();
        for (int i = 0; i < L; i++) {
            listOfLists.append(Project2.generateNumber(L));
        }
        System.out.println("---after for loop---");
        p.addition(listOfLists.iterator());
        //p.save(p.addition(listOfLists.iterator()), "result.bin");
        //Project2.print(p.load("result.bin"));
    }
}