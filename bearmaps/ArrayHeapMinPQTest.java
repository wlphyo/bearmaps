package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
    public void testBasic(){
        ArrayHeapMinPQ a = new ArrayHeapMinPQ();
        a.add(1,1);
        a.add(2,2);
        a.add(3,3);
        a.add(4,4);
        a.add(5,5);
        a.add(6,6);
        assertEquals(0,a.parent(1));
        assertEquals(1,a.getSmallest());
        assertEquals(false,a.contains(7));
        assertEquals(true, a.contains(3));
//        for(int i = (a.size()/2)-1;i>=0;i--){
//            System.out.println("size is "+ a.size());
//            a.minHeapify(i);
//        }
        a.print();

    }
}
