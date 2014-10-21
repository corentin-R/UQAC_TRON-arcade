import java.awt.*;

public class GameEntity {

	protected int xPos, yPos;//position
	protected int sideLength;// which is the integer length of a side (of the square shaped body of our Cycle)
	protected int direction;// 0,90,180,270 (right, up, left, down)
	protected Color color;
	protected Color couleur_init;
	protected int speed;
	protected  int score;
	protected boolean visible;
	protected int id;
	
	/**
	 * constructeur par d�fault, pas utilis�
	 */
	public GameEntity(){
		this(0,0,0,null, 0);
		sideLength=0;
		speed=0;
		score = 0;
	}
	/**
	 * constructeur utilis�
	 * @param dir
	 * @param couleur
	 * @param num
	 */
	public GameEntity(int dir, Color couleur, int num){
		this(0,0,0,null, num);
		sideLength=6;
		direction=dir;
		color=couleur  ;
		couleur_init = couleur;
		speed=2;
		score = 0;
	}
	/**
	 * constructeur plus complet pas utilis�
	 * @param x
	 * @param y
	 * @param dir
	 * @param couleur
	 * @param num
	 */
	public GameEntity(int x, int y, int dir, Color couleur, int num){
		xPos=x;
		yPos=y;
		sideLength=6;
		direction=dir;
		color=couleur;
		couleur_init = couleur;
		speed=2;
		score = 0;
		visible=true;
		id=num;
	}

	/**
	 * dessine un carr� et affiche le score de l'entit�
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(xPos,yPos,sideLength,sideLength);
		Font f1 = new Font("Arial",Font.BOLD,20);
		g.setFont(f1);
		if(id!=0)
			g.drawString("IA_"+id+": "+score ,40+120*id,40);
		else 
			g.drawString("Joueur: "+score ,40+80*id,40);
	}

	/**
	 * donne la direction courante de l'entit�
	 * @return direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * donne une nouvelle direction � l'entit�
	 * @param direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * mets � jour les coordonn�es de l'entit� en
	 *  fonction de sa direction actuelle
	 */
	public void updatePos(){
		switch(direction){
		case 0:
			xPos += speed;
			break;
		case 90:
			yPos -= speed;
			break;
		case 180:
			xPos -= speed;
			break;
		case 270:
			yPos += speed;
			break;
		default:
			break;
		}
		if(willDie()){
			setInvisible();
			System.out.println("entities[" + id + "] a perdu");
		}
		//System.out.println("pos f  "+yPos +" "+xPos+" "+speed+" "+direction);
	}

	/**
	 * v�rifit si l'entit� va mourir en regardant si les pixels
	 * devant elle sont blanc ou pas
	 * @return true si mort
	 */
	public boolean willDie(){
		if (direction == 0 && !TronGame.isWhite(xPos  + sideLength , yPos)//haut droite
				|| direction == 0 && !TronGame.isWhite(xPos + sideLength, yPos+ sideLength)//bas droite
				|| direction == 180 && !TronGame.isWhite(xPos, yPos+ sideLength)//bas gauche
				|| direction == 180 && !TronGame.isWhite(xPos, yPos)//haut gauche
				|| direction == 270 && !TronGame.isWhite(xPos, yPos  + sideLength)//bas gauche
				|| direction == 270 && !TronGame.isWhite(xPos+ sideLength , yPos  + sideLength)//bas droite
				|| direction == 90 && !TronGame.isWhite(xPos, yPos)//haut gauche
				|| direction == 90 && !TronGame.isWhite(xPos+ sideLength , yPos))//hut droit
			return true;
		else
			return  false;
	}

	/**
	 * donne le score de l'entit�
	 * @return score actuel
	 */
	public int getScore() {
		return score;
	}

	/**
	 * d�finit la nouvelle valeur du score de l'entit�
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * remets l'entit� aux coordonn�es voulues
	 * @param x
	 * @param y
	 */
	public void resetPos(int x, int y){
		xPos=x;
		yPos=y;
	}
	
	/**
	 * 
	 * @return position en x
	 */
	public int getX(){ return xPos;}
	
	/**
	 * 	
	 * @return position en y
	 */
	public int getY(){ return yPos;}

	/**
	 * qd l'entit� est morte on s'assure de ne plus l'affichher
	 */
	public void setInvisible(){
		visible=false;
		color=Color.white;
	}

	/**
	 * d�termine si l'entit� est morte ou pas
	 * @return true si vivante
	 */
	public boolean isVisible(){ return visible;}

	/**
	 * remet l'ntit� vivante et visible en d�but de round
	 */
	public void setVisible(){
		visible=true;
		color=couleur_init;
	}


}
