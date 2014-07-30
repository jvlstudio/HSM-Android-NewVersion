package br.com.ikomm.apps.HSM.database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.j256.ormlite.table.DatabaseTable;

/**
 * An abstract class for database objects.
 * 
 * @author Rodrigo Cericatto
 * @since Set 12, 2012
 */
@DatabaseTable
public abstract class Entity {
	
	/**
	 * Returns the mCurrentId of this entity.
	 * 
	 * @return
	 */
	public abstract int getId();
	
	/**
	 * Sets the mCurrentId of this entity.
	 * 
	 * @param mCurrentId
	 */
	public abstract void setId(int id);
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Field f : getClass().getFields()) {
        	if (!Modifier.isStatic(f.getModifiers())) {
	        	try {
	                sb.append(f.getName() + "=" + f.get(this) + " ");
	            } catch (IllegalAccessException e) {
	                // pass, don't print.
	            }
        	}
        }
        sb.append(']');
        return sb.toString();
	}
}