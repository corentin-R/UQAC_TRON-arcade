
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.*;

/**
 * ---------------------------- Tron POO ---------------------------
 */

public class TronGame extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private final int nb_Entities = 4;
	private GameEntity[] entities = new GameEntity[nb_Entities];
	private Timer timer;// sert à  gérer le framerate
	private static Image offScreenBuffer;// needed for double buffering graphics
	public Graphics offScreenGraphics;// needed for double buffering graphics
	private boolean roundOver, partieOver;

	/**
	 * main()
	 */
	public static void main(String[] args) {

		JFrame fenetreJeu = new JFrame("TRON - LAB2 POO");
		fenetreJeu.setBounds(0, 0, 1010, 730);// almost 1024x768 fenetreJeu
		fenetreJeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetreJeu.setResizable(false);

		TronGame game = new TronGame();
		fenetreJeu.getContentPane().add(game);
		fenetreJeu.setBackground(Color.BLACK);
		fenetreJeu.setVisible(true);
		game.initPartie();
		fenetreJeu.addKeyListener(game);

	}

	/**
	 * initialise un round
	 */
	public void initPartie() {
		System.out.println("--------->Nouvelle Partie");
		partieOver=false;
		offScreenBuffer = createImage(getWidth(), getHeight());// should be 1016x736
		offScreenGraphics = offScreenBuffer.getGraphics();
		timer = new Timer(2, this);//génere des impulsions tous les x ms, créer un framerate


		offScreenGraphics.clearRect(0, 0, 1016, 736);
		offScreenGraphics.setColor(Color.white);
		offScreenGraphics.fillRect(10,10+60,getWidth()-20,getHeight()-60-20);

		Random direction = new Random();
		direction.nextInt(4);
		
		// initiatlise les GameEntity
		entities[0] = new  GameEntity(this, direction.nextInt(4)*90 ,Color.BLUE,0,getWidth()/6,getHeight()/2 );
		entities[1] = new  IA_1(this, direction.nextInt(4)*90 ,Color.RED,1,5*getWidth()/6,getHeight()/2);
		entities[2] = new  GameEntity(this, direction.nextInt(4)*90,Color.GREEN,2,getWidth()/2,getHeight()/4);
		entities[3] = new  IA_3(this, direction.nextInt(4)*90,Color.YELLOW,3,getWidth()/2,3*getHeight()/4);

		entities[0].start();
		entities[1].start();
		entities[2].start();
		entities[3].start();

		initRound();
	}

	/**
	 * initialise tout les champs pour un nouveau round
	 */
	public void initRound() {

		System.out.println("---->Nouveau Round");
		setRoundOver(false);

		//sert pour le double buffering
		offScreenGraphics.clearRect(0, 0, 1016, 736);
		offScreenGraphics.setColor(Color.white);
		offScreenGraphics.fillRect(10,10+60,getWidth()-20,getHeight()-60-20);
		
		timer.start();

		for(GameEntity it : entities)
		{
			if(!it.isAlive()){
				it.setVisible();
				it.notify();
			}
			it.reset();			
		}
	}

	/**
	 * paint() est appellée directement par repaint()
	 * @param g
	 */
	public void paint(Graphics g) {

		g.setColor(Color.BLACK);
		g.drawImage(offScreenBuffer, 0, 0, this);

		if(isRoundOver())
			afficherVictoire(g);
	}


	/**
	 * affiche un message de victoire en haut à droite de l'écran si victoire
	 * d'un round ou de la partie
	 * @param g
	 */
	public void afficherVictoire(Graphics g) {
		whoWon();//obligé de vérifier pour mettre partieOver à true avnt de le vérifier
		Font f1 = new Font("Arial",Font.BOLD,30);
		Font f2 = new Font("Arial",Font.PLAIN,10);
		g.setFont(f1);
		g.setColor(Color.white);

		if(!partieOver){//si seulement le round est finit
			if(whoWon()==0)
				g.drawString("Joueur a gagné le round",getWidth()-450, 40);

			else
				g.drawString("IA_"+ whoWon()+" a gagné le round",getWidth()-450, 40);			
		}
		else if(partieOver){//si en plus la partie est finie
			if(whoWon()==0)
				g.drawString("Joueur a gagné la partie",getWidth()-450, 40);
			else
				g.drawString("IA_"+ whoWon()+" a gagné la partie",getWidth()-450, 40);
		}
		g.setFont(f2);
		g.drawString("appuyez sur SPACE pour continuer",getWidth()-450, 60);
	}

	/**
	 * est appellée automatiquement par le timer
	 * @param ActionEvent e l'évenement 
	 */
	public void actionPerformed(ActionEvent e) {
		// update
		repaint();

		if(nbEntityAlive()==1 && !isRoundOver()){
			timer.stop();
			incScore(whoWon());
		}
		//match nul-> nouveau round au bout de 1.5 sec
		else if(nbEntityAlive()==0 && !isRoundOver()){
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			initRound();
		}
	}

	/**
	 * vérifie si chacun des GameEntity va mourir
	 * et vérifie combien d'entités sont vivantes
	 * @return nb d'entités encore en vie
	 */
	public int nbEntityAlive(){
		int nb_en_vie = 0;
		for(int i=0; i<nb_Entities;i++) 
			if (entities[i].isVisible()) 
				nb_en_vie++;

		return nb_en_vie;
	}

	/**
	 * décide si c'est la fin d'un round ou de la partie
	 * et qui a gagné
	 * @return quelle entité a gagné
	 */
	@SuppressWarnings("deprecation")
	public int whoWon(){
		int entite_gagnante=0;
		for(int j=0; j<nb_Entities;j++){
			if(entities[j].isVisible()) {
				System.out.println("entities[" + j + "] a gagné le round");
				setRoundOver(true);
				entite_gagnante =j;
			}
			if(entities[j].getScore()==3){
				System.out.println("entities[" + j + "] a gagné la partie");
				entities[j].stop();
				partieOver=true;
			}
		}
		return entite_gagnante;
	}

	/**
	 * incrémente le score  de l'entité gagnante
	 * @param numEntity id de l'entité
	 */
	public void incScore(int numEntity){
		entities[numEntity].setScore(entities[numEntity].getScore() + 1);
		//System.out.println("score entities["+numEntity+"] = "+entities[numEntity].getScore());
	}

	/**
	 * gère les touches appuyées sur la fenêtre
	 * @param KeyEvent e la touche appuyée
	 */
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT)
			entities[0].setDirection(180);
		else if (keyCode == KeyEvent.VK_RIGHT)
			entities[0].setDirection(0);
		else if (keyCode == KeyEvent.VK_UP)
			entities[0].setDirection(90);
		else if (keyCode == KeyEvent.VK_DOWN)
			entities[0].setDirection(270);

		else if (keyCode == KeyEvent.VK_SPACE) {
			if (!timer.isRunning()) {
				if(isRoundOver() && !partieOver)
					initRound();
				else if( partieOver)
				{
					initPartie();
				}
					
			}
		} 
		else if (keyCode == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}


	/**
	 * retourne si un pixel est blanc ou pas
	 * @param x, position horizontale du pixel à tester
	 * @param y, position verticale du pixel à tester
	 */
	public static boolean isWhite(int x, int y) {
		BufferedImage bi = (BufferedImage) offScreenBuffer;
		if (bi == null)
			return true;
		try {
			int colorVal = bi.getRGB(x, y);
			return (colorVal == -1);
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public boolean isRoundOver() {
		return roundOver;
	}

	public void setRoundOver(boolean roundOver) {
		this.roundOver = roundOver;
	}
	
	public GameEntity getEntity(int numEntity){
		if (numEntity<nb_Entities);
			GameEntity ge = entities[numEntity];
		return ge;
	}

}