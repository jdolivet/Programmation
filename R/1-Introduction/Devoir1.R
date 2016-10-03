1)  a - Décrivez les données quantitatives (moyenne, écart-type, médiane, NA, minimum et maximum).
> setwd("~/Documents/Prof/2014-2015/MOOC Intro R/Semaine 1")
> hop<-read.csv2("satisfaction_hopital.csv")
> summary(hop$age)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max.    NA s 
  18.00   45.00   60.00   58.21   72.00   97.00       6  
> summary(hop$amelioration.sante)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max.    NA s 
  0.000   2.000   2.000   2.231   3.000   3.000     158 
> summary(hop$amelioration.moral)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max.    NA s 
  0.000   1.000   1.000   1.679   3.000   3.000     151 
> summary(hop$score.information)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max.    NA s 
  13.00   28.00   33.00   31.91   38.00   40.00     176 
> summary(hop$score.relation)
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max.    NA s 
  13.00   33.00   36.00   35.22   39.00   40.00     185 


b - Décrivez la répartition des données qualitatives (pourcentage et effectif).
> sexe<-table(hop$sexe, useNA = "always")
> sexe
    0    1 <NA> 
  268  266    0 
> sexe/sum(sexe)
          0         1      <NA> 
  0.5018727 0.4981273 0.0000000 

> profession<-table(hop$profession, useNA = "always")
> profession
  1    2    3    4    5    6    7    8 <NA> 
  1   38  124   88   69   44   22   41  107 
> profession/sum(profession)
          1           2           3           4           5           6           7           8        <NA>  
0.001872659 0.071161049 0.232209738 0.164794007 0.129213483 0.082397004 0.041198502 0.076779026 0.200374532 

> recommander<-table(hop$recommander, useNA = "always")
> recommander
   0    1    2 <NA> 
  16  120  269  129 
> round(prop.table(recommander),3)
      0     1     2  <NA> 
  0.030 0.225 0.504 0.242 


2)  a - Décrivez la distribution du score de relation.
> table(hop$score.relation, useNA = "always")
13   21   22   23   24   25   26   27   28   29   30   31   32   33   34   35   36   37   38   39   40 <NA> 
 1    4    1    1    2    7    5    3   11    7   14   10   15   28   19   23   24   25   45   40   64  185 

Commentez les résultats.
Les patients ont de très bonnes relations avec le personnel de l hopital.

b - Comparez et commentez la distribution du score de relation chez les hommes et chez les femmes à l'aide d'une représentation graphique.
> homme<-factor(subset(hop$score.relation,sexe==0))
> table(homme, useNA = "always")
homme
21 22 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 <NA> 
 1  1  6  3  2  5  2  5  2  4  9  8  4  7  8 16 13 24  58 
plot(homme)

> femme<-factor(subset(hop$score.relation,sexe!=0))
> table(femme, useNA = "always")
femme
13   21   23   24   25   26   27   28   29   30   31   32   33   34   35   36   37   38   39   40 <NA> 
 1    3    1    2    1    2    1    6    5    9    8   11   19   11   19   17   17   29   27   40  127 
> plot(femme)

Commentaire : les distributions sont semblables
