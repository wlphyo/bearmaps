package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{
    private ArrayList<PriorityNode> items; //= new ArrayList<PriorityNode>();
    private Map<Object,Object> hashMap;// = new HashMap<Object,Object>(); //generic map takes in Priority Node and pos
    private int size;
    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    ArrayHeapMinPQ(){
        items = new ArrayList<PriorityNode>();
        hashMap = new HashMap<Object,Object>();
    }
    @Override
    public void add(T item, double priority){
        if(hashMap.containsKey(new PriorityNode(item,priority))){
            throw new IllegalArgumentException("Cannot add. "+item+ " exists");
        }else{
            items.add(new PriorityNode(item,priority));
            System.out.print(items.get(items.size()-1).getItem()+"\n");
            hashMap.put(items.get(items.size()-1).getItem(),size());
        }


    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item){
        System.out.print(hashMap.containsKey(item)+"\n");

        if(hashMap.containsKey(item)) return true;
        else return false;
    }
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest(){
        if(size()==0) throw new NoSuchElementException("get Smallest cannot return. PQ is empty");
        return items.get(0).getItem();

    }
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest(){
        if(size()==0){
            throw new NoSuchElementException("Cannot remove Smallest, PQ is empty");
        }
        T ret = items.get(0).getItem();
        /*need to remove */
        return ret;
    }
    /* Returns the number of items in the PQ. */
    @Override
    public int size(){
        return items.size();
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority){
        if(contains(item)){
            int pos = (int)hashMap.get(item);
            items.get(pos).setPriority(priority);


        }else throw new NoSuchElementException("Cannot change "+ item +" priority.");
    }


    /*returns true if passed node is a leaf node*/
    public boolean isLeaf(int pos){
        if (pos >= (size() / 2) && pos <= size()) {
            return true;
        }
        return false;
    }
    /*reheap*/
    public void minHeapify(int k){
        if(!isLeaf(k)){
            System.out.println();
            if(items.get(k).compareTo(items.get(leftChild(k)))>0
                    || items.get(k).compareTo(items.get(rightChild(k)))>0){
                if(items.get(leftChild(k)).compareTo(items.get(rightChild(k)))<0){
                    swap(items.get(leftChild(k)).getItem(),items.get(k).getItem());
                    minHeapify(leftChild(k));
                }else{
                    swap(items.get(k).getItem(),items.get(rightChild(k)).getItem());
                    minHeapify((rightChild(k)));
                }
            }
        }
    }
//    // Function to build the min heap using
//    // the minHeapify
//    public void minHeap()
//    {
//        for (int pos = (size() / 2); pos >= 1; pos--) {
//            minHeapify(pos);
//        }
//    }
    /*return parent pos of children*/
    public int parent(int pos){
        return (pos-1)/2;
    }

    /*return left child pos*/
    public int leftChild(int pos){
        return (pos*2)+1;
    }
    /*return right child pos*/
    public int rightChild(int pos){
        return (pos*2)+2;
    }
    /* swap two items*/
    public void swap(T item1,T item2){
        int pos1 = (int)hashMap.get(item1);
        int pos2 = (int)hashMap.get(item2);
        items.set(pos1,new PriorityNode(item2,items.get(pos1).getPriority()));
        items.set(pos2,new PriorityNode(item1,items.get(pos2).getPriority()));
        hashMap.replace(item1,pos2);
        hashMap.replace(item2,pos1);
    }
    public void print()
    {
        //System.out.print("hello");
        for (int i = 0; i < size() / 2; i++) {
            System.out.println("i is "+i);
            System.out.print(" PARENT : " + items.get(parent(i)).getItem()
                    + " LEFT CHILD : " + items.get(leftChild(i)).getItem()
                    + " RIGHT CHILD :" + items.get(rightChild(i)).getItem());
            System.out.println();
        }
    }
    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
