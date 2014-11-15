#Tron Light Cycle


####Groupe : Davy TAPSOBA, Corentin RAOULT, Sylvain KUOCH

Clone du jeu d'arcade r�alis� lors du TP 3 de POO � l'UQAC.

### Pr�sentation :

Ce laboratoire a pour but de d�velopper un jeu TRON comprenant 4 motos: 3 IA diff�rentes et un joueur.
La partie graphique � �t� d�velopp� gr�ce � la librairie awt fournie par java.
La gestion des collisions se fait avec les couleurs. En effet on choisit un fond blanc, et on v�rifit si les pixels devant
 la moto sont ne sont pas blanc (bord du terrain, autre moto ou elle-m�me) alors il y a collision et la moto a perdu.  
Pour bouger les motos on affiche des carr�es de coouleurs correspondant � la moto sans effacer les anciens cela permert de garder la trace de la motos.
Les IA h�ritent de la classe GameEntity, la seule diff�rence avec la classe parente est qu'on a ajouter une m�thode pour d�cider quelle direction doit prendre la moto.
La classe principale TRonGame h�rite de  JPanel (pour l'interface graphique) et utilise les intrefaces KeyListener, ActionListener pour la gestion des inputs. 


###Comportement des IAs

####IA_1:
C'est l'IA la moins intelligente, elle regarde si au coup suivant elle sera en collision. Si oui elle tourne, sinon elle continue dans la m�me direction.

####IA_2:
Cette IA change de choisit sa direction al�atoirement (comme l'IA-1) mais sans forc�ment �tre en collision.

#####IA_3:
Cette IA essaye de suivre le joueur ou une des autre IA si ce-dernier est mort.

Au final aucume IA n'est tr�s dure � battre.

###Screenshot
![tron](screenshot-tron.png)


