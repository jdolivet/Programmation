1)  a - Transformez la variable « recommander » en une variable binaire « recommander.b » où : « recommander.b » vaut 0 si « recommander » vaut 0 ou 1 ; « recommander.b » vaut 1 si « recommander » vaut 2.

setwd("~/Documents/Prof/2014-2015/MOOC Intro R/Semaine 2")
hop<-read.csv2("satisfaction_hopital.csv")
table(hop$recommander)
 0   1   2 
16 120 269 
hop$recommander.b<-factor(hop$recommander)
levels(hop$recommander.b)[1:2]<-0
levels(hop$recommander.b)[2]<-1
table(hop$recommander.b)
  0   1 
136 269 

ou

hop$recommander.b<-ifelse(hop$recommander>1,1,0)

b - Est-ce que le fait de recommander ou non un service (utiliser la variable binaire pour estimer la force de l’association) est associé au sexe ? Donnez Odds Ratio et l’intervalle de confiance.

table(subset(hop,sexe==0, recommander.b))
 0   1 
73 139 
table(subset(hop,sexe==1, recommander.b))
 0   1 
63 130 

(73/139)/(63/130)
[1] 1.083704

ou
twoby2(1-hop$recommander.b,1-hop$sexe)
2 by 2 table analysis: 
  ------------------------------------------------------ 
  Outcome   : 0 
Comparing : 0 vs. 1 

    0   1    P(0) 95% conf. interval
0 130 139  0.4833    0.4241   0.5429
1  63  73  0.4632    0.3812   0.5473

                                    95% conf. interval
Relative Risk:              1.0433    0.8380   1.2988
Sample Odds Ratio:          1.0837    0.7169   1.6383
Conditional MLE Odds Ratio: 1.0835    0.7021   1.6745
Probability difference:     0.0200   -0.0824   0.1211

Exact P-value: 0.7523 
Asymptotic P-value: 0.703 
------------------------------------------------------


Commentez le résultat.
Peu de différences

2)  Existe-t-il une corrélation entre le score de relation et :  
hop$score.relation.b<-ifelse(hop$score.relation>25,1,0)
a)  l’âge ? 
> twoby2(hop$score.relation.b,hop$age)
2 by 2 table analysis: 
  ------------------------------------------------------ 
  Outcome   : 18 
Comparing : 0 vs. 1 

18 19    P(18) 95% conf. interval
0  0  1        0         0      NaN
1  3  0        1       NaN        1

95% conf. interval
Relative Risk:     0         0      NaN
Sample Odds Ratio:     0         0      NaN
Conditional MLE Odds Ratio:     0         0   13.000
Probability difference:    -1        -1   -0.028

Exact P-value: 0.25 
Asymptotic P-value: NaN 
------------------------------------------------------

b) le fait de recommander ou non un service ? 
> twoby2(hop$score.relation.b,hop$recommander)
2 by 2 table analysis: 
  ------------------------------------------------------ 
  Outcome   : 0 
Comparing : 0 vs. 1 

0  1    P(0) 95% conf. interval
0 6  6  0.5000    0.2439   0.7561
1 7 91  0.0714    0.0344   0.1423

95% conf. interval
Relative Risk:  7.0000    2.8152  17.4056
Sample Odds Ratio: 13.0000    3.3099  51.0584
Conditional MLE Odds Ratio: 12.4252    2.6090  62.3304
Probability difference:  0.4286    0.1729   0.6775

Exact P-value: 5e-04 
Asymptotic P-value: 2e-04 
------------------------------------------------------
c) une amélioration morale ? 
> twoby2(hop$score.relation.b,hop$amelioration.moral)
2 by 2 table analysis: 
  ------------------------------------------------------ 
  Outcome   : 0 
Comparing : 0 vs. 1 

0   1    P(0) 95% conf. interval
0  5   6  0.4545    0.2028   0.7319
1 15 153  0.0893    0.0545   0.1428

95% conf. interval
Relative Risk: 5.0909    2.2701  11.4170
Sample Odds Ratio: 8.5000    2.3168  31.1859
Conditional MLE Odds Ratio: 8.3123    1.7864  37.3031
Probability difference: 0.3653    0.1177   0.6328

Exact P-value: 0.0032 
Asymptotic P-value: 0.0013 
------------------------------------------------------
d) une amélioration de santé ? (utiliser la corrélation de Pearson). Donnez les corrélations et les intervalles de confiance.
> twoby2(hop$score.relation.b,hop$amelioration.sante)
2 by 2 table analysis: 
  ------------------------------------------------------ 
  Outcome   : 0 
Comparing : 0 vs. 1 

0  1    P(0) 95% conf. interval
0 2  1  0.6667    0.1535   0.9566
1 1 55  0.0179    0.0025   0.1161

95% conf.  interval
Relative Risk:  37.3333    4.5683  305.0957
Sample Odds Ratio: 110.0000    4.9050 2466.8668
Conditional MLE Odds Ratio:  73.4410    2.4191 6110.2929
Probability difference:   0.6488    0.1835    0.9210

Exact P-value: 0.0052 
Asymptotic P-value: 0.0031 
------------------------------------------------------
Commentez le résultat.