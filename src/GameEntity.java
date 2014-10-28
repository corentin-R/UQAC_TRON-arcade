import java.awt.*;

public class GameEntity extends Thread {

	protected int xPos, yPos;//position
	protected int xPos_init, yPos_init;
	protected int sideLength;// which is the integer length of a side (of the square shaped body of our Cycle)
	protected int direction;// 0,90,180,270 (right, up, left, down)
	protected Color color;
	protected Color couleur_init;
	protected int speed;
	protected int score;
	protected boolean visible;
	protected int id;
	protected TronGame tron;

	public GameEntity()
	{
		this(null, 0, Color.black, 99,0,0);
	}

	/**
	 * constructeur utilisé
	 * @param dir
	 * @param couleur
	 * @param num
	 */
	public GameEntity(TronGame game, int dir, Color couleur, int num, int x, int y){
		xPos=x;
		yPos=y;
		xPos_init=xPos;;
		yPos_init=yPos;;
		sideLength=6;
		direction=dir;
		color=couleur  ;
		couleur_init = couleur;
		speed=2;
		score = 0;
		id=num;
		tron=game;				
	}

	public void run(){
		reset();
		while(true)
		{
			while(this.isVisible()){
				updatePos();

				//System.out.println("id " + id +" num thread =  "+this.getName() +" = " +this.getState());
				synchronized(tron.offScreenGraphics) {
					draw((Graphics2D)tron.offScreenGraphics);
				}
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
			/*synchronized(tron)
			{
				if(tron.isRoundOver())
				{					
					reset();
				}
					
			}*/
		}
	}



	public void reset(){
		xPos=xPos_init;
		yPos=yPos_init;
		speed=2;
		setVisible();
		//System.out.println(id + " ------------> " + tron.isRoundOver());
		synchronized(this){
			notify();
		}
	}


	/**
	 * dessine un carré et affiche le score de l'entité
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
	 * donne la direction courante de l'entité
	 * @return direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * donne une nouvelle direction à l'entité
	 * @param direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * mets à jour les coordonnées de l'entité en
	 *  fonction de sa direction actuelle
	 */
	synchronized public void updatePos(){
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
			visible=false;
			System.out.println("entities[" + id + "] a perdu " );			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
	}

	/**
	 * vérifit si l'entité va mourir en regardant si les pixels
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
	 * donne le score de l'entité
	 * @return score actuel
	 */
	public int getScore() {
		return score;
	}

	/**
	 * définit la nouvelle valeur du score de l'entité
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
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
	 * détermine si l'entité est morte ou pas
	 * @return true si vivante
	 */
	public boolean isVisible(){ return visible;}

	/**
	 * remet l'ntité vivante et visible en début de round
	 */
	public void setVisible(){
		visible=true;
	}




}
