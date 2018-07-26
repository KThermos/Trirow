package trirow;
/***
 * Class to represent a Stack
 * @author thurm018
 *
 * @param <T>
 */
public class Stack<T> {
	private T[] dStuff;
	private int top;

	/***
	 * contructor that makes an empty stack
	 */
	public Stack (){
		top = -1;
		dStuff = (T[]) new Object[0];
	}
	/*****
	 * method to add an element to the stack
	 * @param newStuff
	 */
	public void push(T newStuff){
		if(top >= dStuff.length-1){
			T[] temp = (T[]) new Object[dStuff.length+1];
			for(int i = 0; i<dStuff.length;i++){
				temp[i] = dStuff[i];
			}
			dStuff = temp;
		}
		top++;
		dStuff[top]= newStuff;
	}
	/***
	 * method to take an element off the top of the stack
	 * @return the top element
	 */
	public T pop(){
		top--;
		return dStuff[top+1];
	}
/**
 * 
 * @return true if the stack is empty
 */
	public boolean isEmpty(){
		if(top ==0){
			return true;
		}
		return false;
	}
	/**
	 * method to return top element without removing it
	 * @return the top element in the stack
	 */
	public T peek(){
		if(this.size()<0){
			return null;
		}
		return dStuff[top];
	}
	/**
	 * method to check the size of the stack
	 * @return number of elements in the stack
	 */
	public int size(){
		return top+1;
	}
}
