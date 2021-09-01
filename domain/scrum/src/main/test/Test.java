import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {


    public static void main(String[] args) {

        int a = 2;
        if(a==1){
            a=2;
        }else{
            a=3;
        }
        System.out.println(a);

    }

}
