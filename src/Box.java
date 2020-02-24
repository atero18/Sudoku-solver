/**
 * 
 * @author ttassin
 * @version 0.0.2
 * Contient les informations relatives à une case (valeur actuelle, si la valeur est une valeur fixée par le jeu...)
 */
public class Box 
{
	private boolean fixed; //false par défaut
	private int value;  // -1 si la case ne contient aucune valeur.
	
	
	public Box()
	{
		this.value = -1;
		this.fixed = false;
	}
	
	public Box(int value)
	{
		this.setValue(value);
	}
	
	public Box(int value, boolean fixed)
	{
		this(value);
		this.fixed = fixed;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	/**
	 * 
	 * @param value valeur que la case doit posséder (doit être compris entre 1 et 9)
	 * Modifie la valeur de la case. Met à -1 si la valeur est incorrecte.
	 */
	public void setValue(int value)
	{
		if(fixed)
			System.out.println("Fixed box.");
		
		else if(value >= 1 && value <= 9)
			this.value = value;
		
		else
		{
			System.out.println("Box-Value uncorrect. Set to -1.");
			value = -1;
		}
	}
	/**
	 * 
	 * @return true si la case est statique et initialisée.
	 */
	public boolean isFixed()
	{
		return this.fixed && this.value != -1;
	}
}