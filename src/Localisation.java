/*
 *   0 1 2 3 4 5 6 7 8    (column)
 *  +-+-+-+-+-+-+-+-+-+
 * 0|0|1|2|3|4|5|6|7|8|
 *  +-+-+-+-+-+-+-+-+-+
 * 1|9| | | | | | | | |
 *  +-+-+-+-+-+-+-+-+-+
 * 2| | | | | | | | | |
 *  +-+-+-+-+-+-+-+-+-+
 * 3| | | | | | | | | |
 *  +-+-+-+-+-+-+-+-+-+
 * 4| | | | | | | | | |
 *  +-+-+-+-+-+-+-+-+-+
 * 5| | | | | | | | | |
 *  +-+-+-+-+-+-+-+-+-+
 * 6| | | | | | | | | |
 *  +-+-+-+-+-+-+-+-+-+
 * 7| | | | | | | | | |
 *  +-+-+-+-+-+-+-+-+-+
 * 8| | | | | | | | | |
 *  +-+-+-+-+-+-+-+-+-+
 *(line) 
 *  
 */

/**
 * 
 * @author ttassin
 * @version 0.0.2
 * Classe vectorielle qui indique la position de la case dans la grille.
 * Classe non utilisé dans la version 0.0.2 *
 */
public class Localisation
{
	int line;
	int column;
	
	public Localisation(int line, int column)
	{
		if(line < 1 || line > 9 || column < 1 || column > 9)
			System.out.println("Wrong line-column values : (" + line + "," + column + ")");
		
		else
		{
			this.line = line;
			this.column = column;
		}
	}
	
	public Localisation(int key)
	{
		this(key / 9, key % 9);
		
	}
	
	public int getLine()
	{
		return this.line;
	}
	
	public int getColumn()
	{
		return this.column;
	}
	
	public int getKey()
	{
		return this.line * 9 + this.column;
	}
	
	public boolean equals(Object o)
	{
		
		if(o instanceof Localisation)
		{
			Localisation p = (Localisation)o;
			return this.getLine() == p.getLine() && this.getColumn() == p.getColumn();
		}
		else
			return false;
	}
}
