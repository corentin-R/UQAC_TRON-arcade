Groupe : Davy TAPSOBA, Corentin RAOULT Sylvain KUOCH

--- Pr�sentation :

Modelisation: temps pr�vu: ~2h, temps efectif: ~1h
d�veloppement: temps pr�vu: ~h, temps efectif: ~h
d�buggage: temps pr�vu: ~h, temps efectif: ~h


Ce laboratoire a pour but de d�velopper un jeu TRON comprenant 4 motos: 3 IA diff�rentes et un joueur.
La partie graphique � �t� d�velopp� gr�ce � la librairie awt fournie par java.
La gestion des collisions se fait avec les couleurs. En effet on choisit un fond blanc, et on v�rifit si les pixels devant
 la moto sont ne sont pas blanc (bord du terrain, autre moto ou elle-m�me) alors il y a collision et la moto a perdu.  
Pour bouger les motos on affiche des carr�es de coouleurs correspondant � la moto sans effacer les anciens cela permert de garder la trace de la motos.
Les IA h�ritent de la classe GameEntity, la seule diff�rence avec la classe parente est qu'on a ahouter une m�thode pour d�cider quelle direction doit prendre la moto.
La classe principale TRonGame h�rite de  JPanel (pour l'interface graphique) et utilise les intrefaces KeyListener, ActionListener pour la gestion des inputs. 


