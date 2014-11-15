package ca.uqac.pat;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Vector;

public class IA_2 extends GameEntity {

	private int compteurDir;
	private double range[] = 
		{ 100, 500, 200, 210, 680, 100, 550, 900, 320, 640 };

	/**
	 * constructeur utilisé
	 * 
	 * @param dir
	 * @param couleur
	 * @param num
	 */
	public IA_2(TronGame game, int dir, Color couleur, int num, int x, int y) {
		xPos = x;
		yPos = y;
		xPos_init = xPos;
		;
		yPos_init = yPos;
		;
		sideLength = 6;
		direction = dir;
		color = couleur;
		couleur_init = couleur;
		speed = 2;
		score = 0;
		id = num;
		tron = game;
	}

	/**
	 * méthode run appellée lors du start()
	 */
	public void run() {
		reset();
		while (true) {
			while (this.isVisible()) {
				updatePos();
				decideDirection();

				synchronized (tron.offScreenGraphics) {
					draw((Graphics2D) tron.offScreenGraphics);
				}
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * fonction utilisée pour décider de la nouvelle direction à donner à
	 * l'entité
	 */

	public void decideDirection() {
		if (isVisible()) {// si ce n'est pas le joueur
			int dirCible = 0;
			Vector<Integer> dirPossibles = directionPossible();
			if (compteurDir > 175 || !peutAvancer()) {
				compteurDir = 0;

				int xPosini = xPos;
				int yPosini = yPos;
				dirCible = changeDirection();
				double rangeCible = range[changeRange()];
				if ((rangeCible == Math.abs((double) xPos - xPosini))
						&& (direction == 0 || direction == 180)) {
					xPosini = xPos;
					dirCible = changeDirection();
					rangeCible = range[changeRange()];
				}
				if (dirPossibles.contains(dirCible))
					direction = dirCible;
				else {
					if ((rangeCible == Math.abs((double) yPos - yPosini))
							&& (direction == 90 || direction == 270)) {
						yPosini = yPos;
						dirCible = changeDirection();
						rangeCible = range[changeRange()];
					}

					if (dirPossibles.contains(dirCible))
						direction = dirCible;
					else {
						Random dir = new Random();
						int nb_essai = 0;
						do {
							dirCible = dir.nextInt(4) * 90;
							nb_essai++;
						} while (!dirPossibles.contains(dirCible)
								&& nb_essai < 100);
						direction = dirCible;
					}
				}
			}
			compteurDir++;
		}
	}

	/**
	 * vérifit si au coup suivant l'ia pourra avancer
	 * 
	 * @return true si peut avancer
	 */
	private boolean peutAvancer() {
		if (direction == 0
				&& !TronGame.isWhite(xPos + sideLength + speed, yPos)
				|| direction == 0
				&& !TronGame.isWhite(xPos + sideLength + speed, yPos
						+ sideLength)
				|| direction == 180
				&& !TronGame.isWhite(xPos - speed, yPos + sideLength)
				|| direction == 180
				&& !TronGame.isWhite(xPos - speed, yPos)
				|| direction == 270
				&& !TronGame.isWhite(xPos, yPos + sideLength + speed)
				|| direction == 270
				&& !TronGame.isWhite(xPos + sideLength, yPos + sideLength
						+ speed) || direction == 90
				&& !TronGame.isWhite(xPos, yPos - speed) || direction == 90
				&& !TronGame.isWhite(xPos + sideLength, yPos - speed))

			return false;
		else
			return true;
	}

	/**
	 * donne toutes les direction possibles à un moment t
	 * 
	 * @return vector des directions possibles
	 */
	private Vector<Integer> directionPossible() {
		Vector<Integer> tab = new Vector<Integer>();
		int direction_initiale = direction;
		for (int dir = 0; dir < 360; dir += 90) {
			direction = dir;
			if (peutAvancer()) {
				tab.add(dir);
			}
		}
		direction = direction_initiale;
		return tab;
	}

	/**
	 * donne une nouvelle distance à parcourir pour l'IA
	 * 
	 * @return la valeur de la distance
	 */
	private int changeRange() {
		Random r = new Random();
		int newRange;
		newRange = r.nextInt(range.length);
		// System.out.println("newRange "+newRange);
		return newRange;
	}

	/**
	 * donne une nouvelle direction l'IA
	 * 
	 * @return la direction
	 */
	private int changeDirection() {
		Random r = new Random();
		int newD;
		newD = r.nextInt(4);
		// System.out.println("newDirection "+newD);
		return newD;
	}

}
