Ce programme s'utilise en ligne de commande.
Pour ce faire, il vous faut lancer le programme "App.java".

Quelques clients et consommations associées en électricité et en gaz ont été ajouté  dans une base de données simulée (les données sont écrites en dur dans le code).

La classe "BigDecimal" a été utilisé pour les calculs de facturation plutôt qu'un type primitif comme double ou float, afin de bénéficier d'une plus grande précision de calcul, notamment dans le cas où l'on manipule des sommes d'argent très précises.

Le choix a été prix de renvoyer une exception si une consommation d'énergie n'était pas trouvée en base de données, dans le cas où cette consommation serait réelle mais n'aurait pas été persistée en base de données.
Cette exception permet de gérer ce cas de la manière que l'on voudra.
En effet, si un client n'a rien consommé en gaz au mois de mai 2023, le choix a été pris de trouver en base de données pour cette période une consommation de gaz pour ce client de 0.
