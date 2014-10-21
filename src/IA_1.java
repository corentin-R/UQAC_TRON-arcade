import java.awt.Color;
import java.util.Random;
import java.util.Vector;


public class IA_1 extends GameEntity {
	 
   public IA_1(int dir, Color couleur, int identifiant){
	   super(dir,couleur,identifiant);
		sideLength=6;
		couleur_init = couleur;
		speed=2;
		score = 0;       
    }
   
  
	public void decideDirection(){
		if(id!=0)//si ce n'est pas le joueur
		{
			if(!peutAvancer())
			{
				Random dir = new Random();
				int newDir;
				Vector<Integer> dirPossibles = directionPossible();
				int nb_essai =0;
				//System.out.println(dirPossibles);
				do{
					newDir =dir.nextInt(4)*90;

					nb_essai++;
				}			
				while((newDir==direction
						|| newDir==(direction+180)%360 
						|| !dirPossibles.contains(newDir))
						&& nb_essai<100);
				
				direction=newDir;				
				//System.out.println("new finale="+direction);
			}
		}    	
	}

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
	
	private Vector<Integer> directionPossible(){
		Vector<Integer> tab =new Vector<Integer>();
		int direction_initiale=direction;
		for(int dir=0;dir<360;dir+=90){
			direction = dir;
			if(peutAvancer()){
				tab.add(dir);
			}
		}
		direction=direction_initiale;
		return tab;
	}
}
