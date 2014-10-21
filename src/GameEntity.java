import java.awt.*;
import java.util.Random;

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

	public GameEntity(){
		this(0,0,0,null, 0);
		sideLength=0;
		speed=0;
		score = 0;
	}
	public GameEntity(int dir, Color couleur, int num){
		this(0,0,0,null, num);
		sideLength=6;
		direction=dir;
		color=couleur  ;
		couleur_init = couleur;
		speed=2;
		score = 0;
	}
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

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

	public boolean willDie(){
		if (direction == 0 && !TronGame.isWhite(xPos  + sideLength-speed , yPos)
				|| direction == 0 && !TronGame.isWhite(xPos + sideLength-speed , yPos+ sideLength)
				|| direction == 180 && !TronGame.isWhite(xPos, yPos+ sideLength)
				|| direction == 180 && !TronGame.isWhite(xPos, yPos)
				|| direction == 270 && !TronGame.isWhite(xPos, yPos  + sideLength -speed )
				|| direction == 270 && !TronGame.isWhite(xPos+ sideLength , yPos  + sideLength  -speed)
				|| direction == 90 && !TronGame.isWhite(xPos, yPos)
				|| direction == 90 && !TronGame.isWhite(xPos+ sideLength , yPos))
			return true;
		else
			return  false;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void resetPos(int x, int y){
		xPos=x;
		yPos=y;
	}

	public void setInvisible(){
		visible=false;
		color=Color.white;
	}

	public boolean isVisible(){ return visible;}

	public void setVisible(){
		visible=true;
		color=couleur_init;
	}


}
