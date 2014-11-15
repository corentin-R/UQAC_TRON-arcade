package ca.uqac.pat;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Vector;

public class IA_3 extends GameEntity {

	private int compteurDir;

	/**
	 * constructeur utilisé
	 * 
	 * @param dir
	 * @param couleur
	 * @param num
	 */
	public IA_3(TronGame game, int dir, Color couleur, int num, int x,
			int y) {
		xPos = x;
		yPos = y;
		xPos_init = xPos;
		yPos_init = yPos;
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
		int numRntityToFollow = 1;
		reset();
		while (true) {
			while (this.isVisible()) {
				updatePos();
				numRntityToFollow = entityToFollow();
				decideDirection(tron.getEntity(numRntityToFollow).getX(),
						tron.getEntity(numRntityToFollow).getY());

				// on ne dessine qu'une entitité à la fois sinon les couleurs se
				// mélangent
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
	 * choisit l'entité à suivre en fonction de celle(s) qui dont encore en vie
	 */
	private int entityToFollow() {
		if (tron.getEntity(0).isVisible()) {
			return 0;
		} else if (tron.getEntity(1).isVisible()) {
			return 1;
		} else
			return 2;
	}

	/**
	 * fonction utilisée pour décider de la nouvelle direction à donner à
	 * l'entité
	 */
	private void decideDirection(int xCible, int yCible) {
		if (isVisible())// si ce n'est pas le joueur
		{
			int dirCible = 0;
			Vector<Integer> dirPossibles = directionPossible();

			if (compteurDir > 10 || !peutAvancer()) {
				compteurDir = 0;
				if (Math.abs(xPos - xCible) > Math.abs(yPos - yCible)) {
					if (xPos - xCible > 5)
						dirCible = 180;
					else if (xPos - xCible < 5)
						dirCible = 0;
				} else {
					if (yPos - yCible > 5)
						dirCible = 90;
					else if (yPos - yCible < 5)
						dirCible = 270;
				}

				if (dirPossibles.contains(dirCible))
					direction = dirCible;
				else {
					if (dirCible == 180 || dirCible == 0) {
						if (yPos - yCible > 10)
							dirCible = 90;
						else if (yPos - yCible < 10)
							dirCible = 270;
					} else if (dirCible == 90 || dirCible == 270) {
						if (xPos - xCible > 5)
							dirCible = 180;
						else if (xPos - xCible < 5)
							dirCible = 0;
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
				&& !TronGame.isWhite(xPos, yPos - speed)
				|| direction == 90
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

			if (peutAvancer() && dir != (direction_initiale + 180) % 360) {
				tab.add(dir);
			}
		}
		direction = direction_initiale;
		return tab;
	}
}
