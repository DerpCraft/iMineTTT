/*
 * ToDo File
 */
package me.itidez.plugins.iminettt;

public class Todo {
	
	private List todo = new ArrayList();
	
	public Todo() {
		
	}
	
	public List getList() {
		return todo;
	}
	
	public void addToList(String addition) {
		todo.add(addition);
	}
	
	public boolean removeFromList(String rem) {
		if(todo.contains(rem)) {
			todo.remove(rem);
			return true;
		} else {
			return false;
		}
	}
}
