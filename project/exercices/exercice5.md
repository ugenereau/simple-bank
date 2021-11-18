# Exercice 5

Objectif : Différencier les erreurs
 - Changer la sortie de Option à Either



Le Either permet de conserver le détail du cas non-passant. Ce type représente spécifiquement le fait qu'il y a une erreur s'il n'y a pas de donnée, contrairement à l’option qui représente simplement le fait qu'il peut ne pas y avoir de donnée, sans préciser s'il s'agit d'une erreur ou non.

Pourquoi : donner  un retour précis sur l’erreur que l’on a rencontré, quelle règle on n'a pas respecté, etc… donne à son destinataire (machine, développeur…) la possibilité de réagir en fonction de l'erreur rencontrée.
