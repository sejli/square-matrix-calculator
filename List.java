// Sean Li, sejli, pa3
public class List{
	// Fields
	private Node back;
	private Node front;
	private Node cursor;
	private int length;
	private int index;

	// List(): Creates an empty List
	public List(){
		back = null;
		front = null;
		cursor = null;
		index = -1;
		length = 0;
	}

	// Node: private node class
	private class Node{
		Object data;
		Node prev;
		Node next;

		Node(Object x){
			data = x;
			prev = null;
			next = null;
		}

		public String toString(){
			return String.valueOf(data);
		}

		public boolean equals(Object x){
			boolean eq = false;
			Node that;
			if(x instanceof Node){
				that = (Node) x;
				eq = (this.data.equals(that.data));
			}
			return eq;
		}
	}

	// length(): Returns the number of elements in this List.
	public int length(){
		return length;
	}

	// index(): If cursor is defined, returns the index of the cursor element, 
	// otherwise returns -1
	public int index(){
		if(cursor != null && length > 0){
			int i = 0;
			Node N = front;
			while(N != cursor){
				i++;
				N = N.next;
			}
			index = i;
			return i;
		}
		index = -1;
		return -1;
	}

	// front(): Returns front element. Pre: length()>0
	public Object front(){
		if(length == 0){
			throw new RuntimeException("List Error: Cannot call front() on empty List");
		}
		return front.data;
	}

	// back(): Returns back element. Pre: length()>0
	public Object back(){
		if(length == 0){
			throw new RuntimeException("List Error: Cannot call back() on empty List");
		}
		return back.data;
	}

	// get(): Returns cursor element. Pre: length()>0, index()>=0
	public Object get(){
		if(length == 0){
			throw new RuntimeException("List Error: Cannot call get() on empty List");
		}
		if(index < 0){
			throw new RuntimeException("List Error: Cannot call get() on an undefined cursor");
		}
		return cursor.data;
	}

	// equals(): Returns true if and only if this List and L are the same integer sequence
	public boolean equals(List x){
		boolean eq = false;
		Node N = this.front;
		Node M = x.front;
		eq = (this.length == x.length);
		while(eq && N != null){
			eq = N.equals(M);
			N = N.next;
			M = M.next;
		}
		return eq;
	}

	// clear(): Resets this List to its original empty state.
	public void clear(){
		front = null;
		back = null;
		length = 0;
	}

	// moveFront(): If List is non-empty, places the cursor under the front element,
	// otherwise do nothing.
	public void moveFront(){
		if(length > 0){
			cursor = front;
			index = 0;
		}
	}

	// moveBack(): If List is non-empty, places the cursor under the back element,
	// otherwise does nothing.
	public void moveBack(){
		if(length > 0){
			cursor = back;
			index = length - 1;
		}
	}

	// If cursor is defined and not at front, moves cursor one step toward
 	// front of this List, if cursor is defined and at front, cursor becomes
	// undefined, if cursor is undefined does nothing.
	public void movePrev(){
		if(index >= 0 && cursor != front){
			cursor = cursor.prev;
			index--;
		}
		else if(index >= 0 && cursor == front){
			cursor = null;
			index = -1;
		}
		else{

		}
	}

	// moveNext(): If cursor is defined and not at back, moves cursor one step toward
 	// back of this List, if cursor is defined and at back, cursor becomes
	// undefined, if cursor is undefined does nothing.
	public void moveNext(){ 
		if(index >= 0 && index != length - 1){
			cursor = cursor.next;
			index++;
		}
		else if(index >= 0 && index == length - 1){
			cursor = null;
			index = -1;
		}
		else{

		}
	}

	// prepend(): // Insert new element into this List. If List is non-empty,
 	// insertion takes place before front element.
	public void prepend(Object data){ 
		Node N = new Node(data);
		if(length == 0){
			front = back = N;
		}
		else{
			Node M = front;
			M.prev = N;
			N.next = M;
			front = N;
			if(index >= 0){
				index++;
			}
		}
		length++;
	}

	// append(): Insert new element into this List. If List is non-empty,
 	// insertion takes place before front element.
	public void append(Object data){
		Node N = new Node(data);
		if(length == 0){
			front = back = N;
		}		
		else{
			back.next = N;
			N.prev = back;
			back = N;
		}
		length++;
	}

	// insertBefore(): Insert new element before cursor.
	// Pre: length()>0, index()>=0
	public void insertBefore(Object data){
		if(length == 0){
			throw new RuntimeException("List Error: Cannot call insertBefore() on empty List");
		}
		if(index < 0){
			throw new RuntimeException("List Error: Cannot call insertBefore() on undefined cursor");
		}
		if(cursor == front){
			this.prepend(data);
			index++;
		}
		else{
			Node N = new Node(data);
			Node M = cursor.prev;
			M.next = N;
			N.prev = M;
			N.next = cursor;
			cursor.prev = N;
			index++;
			length++;
		}
	}

	// insertAfter(): Inserts new element after cursor.
	// Pre: length()>0, index()>=0
	public void insertAfter(Object data){
		if(length == 0){
			throw new RuntimeException("List Error: Cannot call insertAfter() on empty List");
		}
		if(index < 0){
			throw new RuntimeException("List Error: Cannot call insertAfter() on undefined cursor");
		}
		if(cursor == back){
			this.append(data);
		}
		else{
			Node N = new Node(data);
			Node M = cursor.next;
			N.next = M;
			cursor.next = N;
			N.prev = cursor;
			M.prev = N;
			length++;
		}
	}

	// deleteFront(): Deletes the front element. Pre: length()>0
	public void deleteFront(){
		if(length == 0){
			throw new RuntimeException("List Error: Cannot call deleteFront() on empty List");
		}
		if(cursor == front){
			front = front.next;
			front.prev = null;
			cursor = null;
			index = -1;
		}
		else if(length == 1){
			cursor = null;
			index = -1;
			front = back = null;
		}
		else if(cursor != front && length > 1){
			front = front.next;
			front.prev = null;
			index--;
		}
		length--;
	}

	// deleteBack(): Deletes the back element. Pre: length()>0
	public void deleteBack(){
		if(length == 0){
			throw new RuntimeException("List Error: Cannot call deleteBack() on empty List");
		}
		if(cursor == back){
			cursor = null;
			back = back.prev;
			back.next = null;
			index = -1;
		}
		else if(length == 1){
			cursor = null;
			index = -1;
			front = back = null;
		}
		else if(cursor != back){
			back = back.prev;
			back.next = null;
		}
		length--;
	}

	// delete(): Deletes cursor element, making cursor undefined.
	// Pre: length()>0, index()>=0
	public void delete(){
		if(length == 0){
			throw new RuntimeException("List Error: Cannot call delete() on empty List");
		}
		if(index < 0){
			throw new RuntimeException("List Error: Cannot call delete() on undefined cursor");
		}
		if(cursor == front && length > 1){
			front = cursor.next;
			cursor = null;
			index = -1;
		}
		else if(cursor == back && length > 1){
			back = cursor.prev;
			cursor = null;
			index = -1;
		}
		else if(cursor != front && cursor != back && length > 1){
			cursor.prev.next = cursor.next;
			cursor = null;
			index = -1;
		}
		else{
			front = back = null;
			cursor = null;
			index = -1;
		}
		length--;
	}

	// Overrides Object's toString method. Returns a String
	// representation of this List consisting of a space
	// separated sequence of integers, with front on left.
	public String toString(){
		StringBuffer sb = new StringBuffer();
		Node N = front;
		while(N != null){
			sb.append(N.toString());
			sb.append(" ");
			N = N.next;
		}
		String s = sb.toString();
		return s;
	}

}