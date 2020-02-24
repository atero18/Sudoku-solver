import java.util.*;
import java.io.*;


/**
 * 
 * @author ttassin
 * @version 0.0.2
 * Contient les informations relatives à toute la grille (valeurs des différentes cases ; algorithme de résolution...)
 *
 */
public class Grid
{
	private Map<Integer, Box> grid; // Ensemble de la grille (voir Localisation.java pour comprendre l'assignation)
	
	public Grid()
	{
		this.grid = new HashMap<Integer, Box>(81);
	}
	
	
	/**
	 * 
	 * @param showOnlyStatic true si on souhaite voir uniquement la grille de départ.
	 * Affiche la grille sur la console java.
	 */
	public void display(boolean showOnlyStatic)
	{
		final String line = "+-+-+-++-+-+-++-+-+-+";
		
		for(int i = 0 ; i < 81 ;i++)
		{
			if(i % 3 == 0 && i % 9 != 0)
				System.out.print("|");
			
			if(i % 27 == 0)
				System.out.print("\n" + line);
			
			if(i % 9 == 0)
			{
				System.out.print("\n" + line);
				System.out.print("\n|");
			}
				
			if(grid.get(i) != null)
			{
				int value = grid.get(i).getValue();
				if(value != -1 && (showOnlyStatic ? grid.get(i).isFixed() : true))
					System.out.print(value + "|");
				else
					System.out.print(" |");
			}
			else
				System.out.print(" |");
		}
		
		System.out.print("\n" + line);	
	}
	
	/**
	 * Affiche la grille dans son état actuel sur la console java.
	 */
	public void display()
	{
		this.display(false);
	}
	
	/**
	 * 
	 * @param path Chemin d'accès au fichier .txt (ex : "grid/easy_015.txt").
	 * Permet de charger un fichier txt contenant une grille de départ.
	 * Une ligne du fichier correspond à une ligne de la grille.
	 * Les cases vides sont représentées par un "X".
	 * Le fichier n'est pas vérifié et est considéré comme valide.
	 */
	public void load(String path)
	{
		try
		{
			FileReader file = new FileReader(path);
			BufferedReader buff = new BufferedReader(file);
			
			for(int i = 0; i < 9; i++)
			{
				String s = buff.readLine();
				for(int j = 0; j < 9; j++)
				{
					char c = s.charAt(j);
					if(c == 'X')
					{
						this.grid.put(i*9 + j, new Box());
					}
					else
						this.grid.put(i*9 + j,  new Box(Character.getNumericValue(c), true));
				}
				
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Fichier inexistant");
		}
		catch(IOException e)
		{
			System.out.println("Impossible de lire le fichier");
		}
	}
	
	/**
	 * 
	 * @param key la position de la case dans la grille (ex : 65).
	 * @param value la valeur qui doit être testée (ex : 4).
	 * @return true si {value} peut être inséré dans {key}.
	 */
	public boolean possibleValue(int key, int value)
	{
		
		if(this.grid.get(key).isFixed())
			return false;
		
		int column = key % 9;
		int line = key / 9;
		int pos;
		
		// Vérification de la ligne
		for(int i = 0; i < 9; i++)
		{
			pos = line * 9 + i;
			if(pos != key && this.grid.get(pos).getValue() == value)
				return false;
			
		}
		
		//Vérification de la colonne
		for(int i = 0; i < 9; i++)
		{
			pos = i * 9 + column;
			if(pos != key && this.grid.get(pos).getValue() == value)
				return false;
		}
		
		//Vérification du groupe
		int lineGroup = line - line % 3;
		int columnGroup = column - column % 3;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				pos = (lineGroup + i) * 9 + columnGroup + j; 
				if(pos != key && this.grid.get(pos).getValue() == value)
					return false;
			}
		}
		return true;
		
	}
	
	/**
	 * 
	 * @param key la position de la case dans la grille (ex : 65).
	 * @return l'ensemble des valeurs possibles pour la case {key}.
	 */
	public int[] availableValues(int key)
	{
		if(this.grid.get(key).isFixed())
			return null;
		
		int size = 0;
		for(int i = 1; i <= 9; i++)
		{
			if(possibleValue(key, i))
				size++;
		}
		
		if(size == 0)
			return null;
		
		int tab[] = new int[size], j = 0;
		for(int i = 1; i <= 9; i++)
		{
			if(possibleValue(key, i))
			{
				tab[j] = i;
				j++;
			}
		}
		return tab;
	}

	/**
	 * 
	 * @param key La case à partir de laquelle il test les différentes possibilités
	 * @return true si l'algorithme a trouvé une valeur à cette case et aux cases suivantes.
	 */
	public boolean dfs (int key)
	{
		// Si la grille est terminé
		if(key > 80)
			return true;
		
		// Si la case est statique on passe à la case suivante
		if(this.grid.get(key).isFixed())
			return this.dfs(key + 1);
		
		int[] tab = availableValues(key);

		// Si aucune valeur n'est possible pour cette case
		if(tab == null)
			return false;

		
		//Test des différentes possibilités pour cette case et pour les suivantes
		int i = 0;
		boolean result;
		do
		{
			this.grid.put(key, new Box(tab[i]));
			result = this.dfs(key + 1);
			i++;
		}while(i < tab.length && !result);
		
		if(!result)
			this.grid.put(key, new Box());
		
		return result;
	}
	
	/**
	 * 
	 * @return true si la grille a été résolue
	 * Démarre l'algorithme de résolution de grille.
	 */
	public boolean resolve()
	{
		System.out.println("GRILLE DE DEPART");
		this.display();
		long time = System.currentTimeMillis();
		
		if(this.dfs(0))
		{
			time = System.currentTimeMillis() - time;
			System.out.println("\n\n\nGRILLE RESOLUE");
			this.display();
			System.out.println("\n\nDurée nécéssaire à la résolution : " + time + " ms");
			return true;
		}
		else
		{
			System.out.println("\n\n Résolution impossible.");
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		Grid grille = new Grid();
		grille.load("grids/sup_515.txt");
		grille.resolve();
	}
}
