/*
 * ToDo File
 */
package me.itidez.plugins.iminettt;

public class Todo {
	
	private List todo = new ArrayList();
	private boolean isDefault;
	
	public Todo() {
		
	}
	
	public List getList() {
		return todo;
	}
	
	public void addToList(String addition) {
		todo.add(addition);
		if(isDefault) {
			isDefault == false
		}
	}
	
	public boolean isDefault() {
		return isDefault;
	}
	
	public boolean removeFromList(String rem) {
		if(todo.contains(rem)) {
			todo.remove(rem);
			return true;
		} else {
			return false;
		}
	}
	
	private void addDefaults() {
		todo.clear();
		todo.add("Add dhutils (https://github.com/desht/dhutils)")
		    .add("");
		isDefault == true;
	}
}
