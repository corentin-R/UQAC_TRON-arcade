import java.awt.Color;
import java.util.Random;
import java.util.Vector;


public class IA_3 extends GameEntity {

	private int compteurDir;

	/**
	 * constructeur avec paramètres
	 * @param dir
	 * @param couleur
	 * @param identifiant
	 */
	public IA_3(int dir, Color couleur, int identifiant){
		super(dir,couleur,identifiant);
		sideLength=6;
		couleur_init = couleur;
		speed=2;
		score = 0;       
		compteurDir=0;
	}


	/**
	 * fonction utilisée pour décider de la nouvelle 
	 * direction à donner à l'entité
	 */
	public void decideDirection(int xCible, int yCible){
		if(isVisible())//si ce n'est pas le joueur
		{
			int dirCible=0;
			Vector<Integer> dirPossibles = directionPossible();
			System.out.println("--------------\n"+dirPossibles);
			System.out.println(direction);
			System.out.println("cpt "+compteurDir);

			if(compteurDir>3 || !peutAvancer())
			{
				compteurDir=0;
				
				if(Math.abs(xPos - xCible )>Math.abs(yPos - yCible))
				{
					if(xPos - xCible >5)
						dirCible= 180;
					else if (xPos - xCible <5)
						dirCible=0;

					if(yPos - yCible >5)
						dirCible= 90;
					else if (yPos - yCible <5)
						dirCible=270;
				}


				System.out.println("dirCible1 "+dirCible);
				System.out.println("cpt "+compteurDir);

				if(dirPossibles.contains(dirCible))
					direction=dirCible;
				else
				{
					if(dirCible == 180 || dirCible == 0)
					{
						if(yPos - yCible >10)
							dirCible= 90;
						else if (yPos - yCible <10)
							dirCible=270;

						System.out.println("en y");
						System.out.println("dirCible1/2 "+dirCible);
					}
					else if(dirCible == 90 || dirCible == 270)
					{
						if(xPos - xCible >5)
							dirCible= 180;
						else if (xPos - xCible <5)
							dirCible=0;

						System.out.println("en x");
						System.out.println("dirCible2/2 "+dirCible);
					}
					
					if(dirPossibles.contains(dirCible))
						direction=dirCible;
					else
					{
						Random dir = new Random();
						int nb_essai =0;
						do{
							dirCible =dir.nextInt(4)*90;
							nb_essai++;
						}			
						while(!dirPossibles.contains(dirCible) && nb_essai<100);
						System.out.println("--------------random ");
						System.out.println("dirCible2 "+dirCible);

						direction=dirCible;
					}
				}


			}

			compteurDir++;
			
		}

	}

	/**
	 * vérifit si au coup suivant l'ia pourra avancer
	 * @return true si peut avancer
	 */
	private boolean peutAvancer(){
		if(direction == 0 && !TronGame.isWhite(xPos  + sideLength +speed, yPos)
				|| direction == 0 && !TronGame.isWhite(xPos + sideLength+speed , yPos+ sideLength)
				|| direction == 180 && !TronGame.isWhite(xPos-speed, yPos+ sideLength)
				|| direction == 180 && !TronGame.isWhite(xPos-speed, yPos)
				|| direction == 270 && !TronGame.isWhite(xPos, yPos  + sideLength +speed )
				|| direction == 270 && !TronGame.isWhite(xPos+ sideLength , yPos  + sideLength +speed )
				|| direction == 90 && !TronGame.isWhite(xPos, yPos-speed)
				|| direction == 90 && !TronGame.isWhite(xPos+ sideLength , yPos-speed))

			return false;
		else
			return true;
	}

	/**
	 * donne toutes les direction possibles à un moment t
	 * @return vector des directions possibles
	 */
	private Vector<Integer> directionPossible(){
		Vector<Integer> tab =new Vector<Integer>();
		int direction_initiale=direction;
		for(int dir=0;dir<360;dir+=90){
			direction = dir;

			if(peutAvancer() && dir!=(direction_initiale+180)%360){
				tab.add(dir);
			}
		}
		direction=direction_initiale;
		//System.out.println("-------------->dir "+direction);
		return tab;
	}
}
