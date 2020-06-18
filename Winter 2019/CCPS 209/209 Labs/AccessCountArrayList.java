import java.util.*;

public class AccessCountArrayList<E> extends ArrayList<E> {
    
    //Subclass intstance variable
    //Available to any extensions of this class
    protected int count;
    
    AccessCountArrayList(){
        super();
        count = 0;
    }
    
    public int getAccessCount(){
        return count;
    }
    
    public int resetCount(){
        return count = 0;
    }
    
    @Override
    public E get(int index){
        count++;
        return super.get(index);
    }
    
    @Override
    public E set(int index, E element){
        count++;
        return super.set(index, element);
    }
}
