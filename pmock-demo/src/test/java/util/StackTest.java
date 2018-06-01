package util;

import org.junit.Test;

import java.util.Stack;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-31
 * Time: 上午10:50
 */
public class StackTest {
    @Test
     public void popTest(){
        Stack<String> stack = new Stack<String>();
        stack.push("a");//将数据压入堆栈顶部，其作用于下面addElement一样
        stack.addElement("b");
        stack.push("c");
        stack.push("d");
        stack.push("e");
        stack.push("f");
        System.out.println(stack.pop());
        if(!stack.empty()){//判断堆栈是否为空
            System.out.println(stack.peek());//输出e。获取堆栈顶部的对象（Vector 对象的最后一项），但不从堆栈中移除该对象，如果堆栈为空，则抛出EmptyStackException异常
            System.out.println(stack.pop()); //输出e。获取堆栈顶部的对象（Vector 对象的最后一项），并从堆栈顶部移除该对象，如果堆栈为空，则抛出EmptyStackException异常
            System.out.println(stack.pop()); //输出d。
            System.out.println(stack.search("c"));//获取指定对象在堆栈中的位置，以 1 为基数，如果没有则返回-1
        }
    }
}
