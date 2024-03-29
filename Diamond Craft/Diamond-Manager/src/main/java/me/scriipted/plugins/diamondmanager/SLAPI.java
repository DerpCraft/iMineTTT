/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriipted.plugins.diamondmanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author tjs238
 */
/** SLAPI = Saving/Loading API
 * API for Saving and Loading Objects.
 * @author Tomsik68
 */
public class SLAPI
{
	public static void save(Object obj,String path) throws Exception
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}
	public static Object load(String path) throws Exception
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		Object result = ois.readObject();
		ois.close();
		return result;
	}
}