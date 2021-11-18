# Exercice 9

Objectif : Gérer les erreurs de base de données
 - Nous avons dorénavant une contrainte d'unicité sur le nom du compte. Cette contrainte est portée par la base de données.
 - En cas de violation de la contrainte, la base de donnée va renvoyer une exception. Gérez cette exception afin que la contrainte d'unicité fasse partie des erreurs métiers

Certaines exceptions peuvent être envisagées et prises en compte. Elles deviennent alors erreurs qui vont être transformées et exposées en erreurs métiers, et donc être explicitées.
