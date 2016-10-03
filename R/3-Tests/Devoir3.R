1)   Le pourcentage de sujets recommandant le service dans lequel ils sont passés est-il significativement différent chez les hommes et chez les femmes ? (le script doit inclure la vérification éventuelle des conditions de validité de la méthode utilisée)
> tab<-table(hop$recommander,hop$sexe)
> prop.table(tab)
0          1
0 0.02469136 0.01481481
1 0.15555556 0.14074074
2 0.34320988 0.32098765
> chisq.test(tab)
Pearson s Chi-squared test
data:  tab
X-squared = 0.7113, df = 2, p-value = 0.7007


2)   La moyenne du score de relation est-il significativement différent chez les hommes et chez les femmes ? (le script doit inclure la vérification éventuelle des conditions de validité de la méthode utilisée)
> tapply(hop$score.relation, hop$sexe, mean, na.rm=TRUE)
       0        1 
35.48087 34.92771 
> t.test(hop$score.relation[hop$sexe == 0], hop$score.relation[hop$sexe == 1])
Welch Two Sample t-test
data:  hop$score.relation[hop$sexe == 0] and hop$score.relation[hop$sexe == 1]
t = 1.1149, df = 341.308, p-value = 0.2657
alternative hypothesis: true difference in means is not equal to 0
95 percent confidence interval:
  -0.4227631  1.5290901
sample estimates:
  mean of x mean of y 
35.48087  34.92771 

3)   Le score de relation est-il significativement corrélé à l’âge? (le script doit inclure la vérification éventuelle des conditions de validité de la méthode utilisée)
> cor(hop$score.relation, hop$age, use="pairwise")
[1] 0.09596955
> cor.test(hop$score.relation, hop$age)

Pearson s product-moment correlation

data:  hop$score.relation and hop$age
t = 1.796, df = 347, p-value = 0.07336
alternative hypothesis: true correlation is not equal to 0
95 percent confidence interval:
 -0.009102243  0.198945290
sample estimates:
       cor 
0.09596955 