# Shortest-Path


Application permettant de calculer le lieu de rendez-vous optimal pour deux amis selon deux algorithmes.

Le premier algorithme calcul le lieu de rendez-vous minimisant la somme des temps de parcours des deux amis
et le deuxième le lieu de rendez-vous minimisant la somme du nombre d’arcs parcourus.

Vous devez commencez par séléctionner le fichier pour construire le graphe (attention, le contenu du fichier doit être au bon format)**
Vous pouvez séléctionner le fichier voulu en parcourant les dossiers ou entrer le chemin absolut du fichier.

Dès que vous séléctionez un nouveau fichier, il faut construire le graphe. Pour cela vous devez choisir "Graphe" dans le menu déroulant.
Après vous pouvez afficher le graphe transformé en choissisant "TGraphe" dans le menu déroulant.

Vous pouvez ensuite afficher le résultat des différents algorithmes (l'un ou l'autre ou les deux en même temps).

Vous pouvez aussi éffacer le contenu de la zone de texte en cliquant sur le bouton "Effacer".


**Le format du fichier:  (Une information par ligne)

- le nombre de nœuds du graphe (un entier).
- le nom de chaque sommet (un caractère alphanumérique uniquement) avec ";" entre les sommets.
- le nombre de lieux de rendez-vous (un entier).
- le nom de chaque sommet étant un lieu de rendez-vous (un caractère alphanumérique uniquement) avec ";" entre les sommets.
- les deux noms des sommets initiaux avec ";" entre les sommets.
- une liste de triplets représentant chaque arc du graphe,
	un triplet doit être de la forme "SommetInitial, SommetTerminal, Durée", il faut ajouter ";" entre les triplets.
	chaque sommet est identifié par son caractère alphanumérique et la durée est un entier.
