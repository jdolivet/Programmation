1)  a - À l’aide d’un modèle de régression, étudiez l’association entre le score de la relation et les variables « age ». (Le script doit inclure la vérification éventuelle des conditions de validité de la méthode utilisée).
> ass1<-lm(score.relation ~ age, data=hop)
> ass1

Call:
  lm(formula = score.relation ~ age, data = hop)

Coefficients:
  (Intercept)          age  
33.76634      0.02668  
> summary(ass1)
Commentez le résultat.
Le score de relation evolue tres peu en fonction de l age.
b - Étudiez cette association en prenant en compte le « sexe », « score.information », « amelioration.sante », « amelioration.moral », « profession », « service ». (Le script doit inclure la vérification éventuelle des conditions de validité de la méthode utilisée).
> ass2<-lm(score.relation ~ age+sexe+score.information+amelioration.sante+amelioration.moral+profession+service, data=hop)
> ass2

Call:
  lm(formula = score.relation ~ age + sexe + score.information + 
       amelioration.sante + amelioration.moral + profession + service, 
     data = hop)

Coefficients:
  (Intercept)                 age                sexe  
20.74420             0.04276            -0.49000  
score.information  amelioration.sante  amelioration.moral  
0.27390             0.65586             0.74913  
profession             service  
0.07315             0.10190  
> summary(ass2)
Commentez le résultat.

2)  a - À l’aide d’un modèle de régression logistique, étudiez l’association entre le fait de recommander ou non un service (« recommander.b ») et le sexe. (Le script doit inclure la vérification éventuelle des conditions de validité de la méthode utilisée).
> ass3<-glm(recommander~sexe,data=hop)
> ass3

Call:  glm(formula = recommander ~ sexe, data = hop)

Coefficients:
  (Intercept)         sexe  
1.608        0.034  

Degrees of Freedom: 404 Total (i.e. Null);  403 Residual
(129 observations deleted due to missingness)
Null Deviance:      127 
Residual Deviance: 126.8 	AIC: 685.1
> summary(ass2)

Call:
  lm(formula = score.relation ~ age + sexe + score.information + 
       amelioration.sante + amelioration.moral + profession + service, 
     data = hop)

Residuals:
  Min       1Q   Median       3Q      Max 
-16.7892  -2.1343   0.6682   2.7140  10.1691 

Coefficients:
  Estimate Std. Error t value Pr(>|t|)    
(Intercept)        20.74420    1.79158  11.579  < 2e-16 ***
  age                 0.04276    0.01468   2.914  0.00386 ** 
  sexe               -0.49000    0.49332  -0.993  0.32143    
score.information   0.27390    0.03788   7.230 4.59e-12 ***
  amelioration.sante  0.65586    0.34225   1.916  0.05634 .  
amelioration.moral  0.74913    0.28617   2.618  0.00933 ** 
  profession          0.07315    0.14513   0.504  0.61464    
service             0.10190    0.10819   0.942  0.34707    
---
  Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 4.103 on 281 degrees of freedom
(245 observations deleted due to missingness)
Multiple R-squared:  0.2733,	Adjusted R-squared:  0.2552 
F-statistic:  15.1 on 7 and 281 DF,  p-value: < 2.2e-16
Commentez le résultat.

b - Étudiez cette association, en prenant en compte les variables « age », «score.information », « amelioration.sante », « amelioration.moral », « profession », « service ». (Le script doit inclure la vérification éventuelle des conditions de validité de la méthode utilisée).

R version 3.0.2 (2013-09-25) -- "Frisbee Sailing"
Copyright (C) 2013 The R Foundation for Statistical Computing
Platform: x86_64-pc-linux-gnu (64-bit)

R est un logiciel libre livré sans AUCUNE GARANTIE.
Vous pouvez le redistribuer sous certaines conditions.
Tapez 'license()' ou 'licence()' pour plus de détails.

R est un projet collaboratif avec de nombreux contributeurs.
Tapez 'contributors()' pour plus d'information et
'citation()' pour la façon de le citer dans les publications.

Tapez 'demo()' pour des démonstrations, 'help()' pour l'aide
en ligne ou 'help.start()' pour obtenir l'aide au format HTML.
Tapez 'q()' pour quitter R.

> hop <- read.csv2("satisfaction_hopital.csv")
Erreur dans file(file, "rt") : impossible d'ouvrir la connexion
De plus : Message d'avis :
In file(file, "rt") :
impossible d'ouvrir le fichier 'satisfaction_hopital.csv' : Aucun fichier ou dossier de ce type
> setwd("~/Documents/Prof/2014-2015/MOOC Intro R/Semaine 4")
> hop <- read.csv2("satisfaction_hopital.csv")
> ass1<-lm(score.relation ~ age, data=hop)
> ass1

Call:
  lm(formula = score.relation ~ age, data = hop)

Coefficients:
  (Intercept)          age  
33.76634      0.02668  

> boxplot(score.relation ~ age, data=hop)
> summary(ass1)

Call:
  lm(formula = score.relation ~ age, data = hop)

Residuals:
  Min      1Q  Median      3Q     Max 
-22.047  -2.447   1.339   3.620   5.673 

Coefficients:
  Estimate Std. Error t value Pr(>|t|)    
(Intercept) 33.76634    0.84496  39.962   <2e-16 ***
  age          0.02668    0.01485   1.796   0.0734 .  
---
  Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 4.609 on 347 degrees of freedom
(185 observations deleted due to missingness)
Multiple R-squared:  0.00921,  Adjusted R-squared:  0.006355 
F-statistic: 3.226 on 1 and 347 DF,  p-value: 0.07336

> aggregate(score.relation ~ age, data=hop, mean)
age score.relation
1   18       32.33333
2   19       25.00000
3   20       33.00000
4   21       35.00000
5   22       37.50000
6   23       34.66667
7   25       34.50000
8   26       34.00000
9   27       34.66667
10  28       37.50000
11  29       34.16667
12  30       36.00000
13  31       33.50000
14  32       33.28571
15  33       37.00000
16  34       31.25000
17  35       35.33333
18  36       35.60000
19  37       36.00000
20  38       32.50000
21  39       37.33333
22  40       36.14286
23  41       34.00000
24  42       33.00000
25  43       33.80000
26  44       35.42857
27  45       36.87500
28  46       34.28571
29  47       32.80000
30  48       31.00000
31  49       36.10000
32  50       35.83333
33  51       38.40000
34  52       33.28571
35  53       34.80000
36  54       35.80000
37  55       35.00000
38  56       38.66667
39  57       38.00000
40  58       37.66667
41  59       29.62500
42  60       35.15385
43  61       35.66667
44  62       36.14286
45  63       37.33333
46  64       35.28571
47  65       35.16667
48  66       35.90000
49  67       36.44444
50  68       36.75000
51  69       35.87500
52  70       35.00000
53  71       39.00000
54  72       33.00000
55  73       34.00000
56  74       35.20000
57  75       37.55556
58  76       34.66667
59  77       38.50000
60  78       39.00000
61  79       31.50000
62  80       31.00000
63  81       27.50000
64  82       32.00000
65  83       38.33333
66  84       37.28571
67  85       40.00000
68  86       33.00000
69  87       40.00000
70  88       33.00000
71  89       39.00000
> ass2<-lm(score.relation ~ age+sexe+score.information+amelioration.sante+amelioration.moral+profession+service, data=hop)
> ass2

Call:
  lm(formula = score.relation ~ age + sexe + score.information + 
       amelioration.sante + amelioration.moral + profession + service, 
     data = hop)

Coefficients:
  (Intercept)                 age                sexe  
20.74420             0.04276            -0.49000  
score.information  amelioration.sante  amelioration.moral  
0.27390             0.65586             0.74913  
profession             service  
0.07315             0.10190  

> summary(ass2)

Call:
  lm(formula = score.relation ~ age + sexe + score.information + 
       amelioration.sante + amelioration.moral + profession + service, 
     data = hop)

Residuals:
  Min       1Q   Median       3Q      Max 
-16.7892  -2.1343   0.6682   2.7140  10.1691 

Coefficients:
  Estimate Std. Error t value Pr(>|t|)    
(Intercept)        20.74420    1.79158  11.579  < 2e-16 ***
  age                 0.04276    0.01468   2.914  0.00386 ** 
  sexe               -0.49000    0.49332  -0.993  0.32143    
score.information   0.27390    0.03788   7.230 4.59e-12 ***
  amelioration.sante  0.65586    0.34225   1.916  0.05634 .  
amelioration.moral  0.74913    0.28617   2.618  0.00933 ** 
  profession          0.07315    0.14513   0.504  0.61464    
service             0.10190    0.10819   0.942  0.34707    
---
  Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 4.103 on 281 degrees of freedom
(245 observations deleted due to missingness)
Multiple R-squared:  0.2733,	Adjusted R-squared:  0.2552 
F-statistic:  15.1 on 7 and 281 DF,  p-value: < 2.2e-16

> ass3<-glm(recommander.b~sexe,data=hop)
Erreur dans eval(expr, envir, enclos) : objet 'recommander.b' introuvable
> ass3<-glm(recommander~sexe,data=hop)
> ass3

Call:  glm(formula = recommander ~ sexe, data = hop)

Coefficients:
  (Intercept)         sexe  
1.608        0.034  

Degrees of Freedom: 404 Total (i.e. Null);  403 Residual
(129 observations deleted due to missingness)
Null Deviance:	    127 
Residual Deviance: 126.8 	AIC: 685.1
> summary(ass2)

Call:
  lm(formula = score.relation ~ age + sexe + score.information + 
       amelioration.sante + amelioration.moral + profession + service, 
     data = hop)

Residuals:
  Min       1Q   Median       3Q      Max 
-16.7892  -2.1343   0.6682   2.7140  10.1691 

Coefficients:
  Estimate Std. Error t value Pr(>|t|)    
(Intercept)        20.74420    1.79158  11.579  < 2e-16 ***
  age                 0.04276    0.01468   2.914  0.00386 ** 
  sexe               -0.49000    0.49332  -0.993  0.32143    
score.information   0.27390    0.03788   7.230 4.59e-12 ***
  amelioration.sante  0.65586    0.34225   1.916  0.05634 .  
amelioration.moral  0.74913    0.28617   2.618  0.00933 ** 
  profession          0.07315    0.14513   0.504  0.61464    
service             0.10190    0.10819   0.942  0.34707    
---
  Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 4.103 on 281 degrees of freedom
(245 observations deleted due to missingness)
Multiple R-squared:  0.2733,	Adjusted R-squared:  0.2552 
F-statistic:  15.1 on 7 and 281 DF,  p-value: < 2.2e-16

> ass3<-glm(recommander~sexe+age+score.information+amelioration.sante+amelioration.moral+profession+service,data=hop)
> ass4<-glm(recommander~sexe+age+score.information+amelioration.sante+amelioration.moral+profession+service,data=hop)
> ass4

Call:  glm(formula = recommander ~ sexe + age + score.information + 
             amelioration.sante + amelioration.moral + profession + service, 
           data = hop)

Coefficients:
  (Intercept)                sexe                 age  
0.377193            0.120777            0.002899  
score.information  amelioration.sante  amelioration.moral  
0.025724            0.095500            0.060625  
profession             service  
-0.010855           -0.006889  

Degrees of Freedom: 313 Total (i.e. Null);  306 Residual
(220 observations deleted due to missingness)
Null Deviance:	    93.46 
Residual Deviance: 76.02 	AIC: 463.7
> summary(ass4)

Call:
  glm(formula = recommander ~ sexe + age + score.information + 
        amelioration.sante + amelioration.moral + profession + service, 
      data = hop)

Deviance Residuals: 
  Min       1Q   Median       3Q      Max  
-1.5674  -0.3541   0.1188   0.3694   0.8174  

Coefficients:
  Estimate Std. Error t value Pr(>|t|)    
(Intercept)         0.377193   0.208008   1.813   0.0708 .  
sexe                0.120777   0.057426   2.103   0.0363 *  
  age                 0.002899   0.001695   1.711   0.0881 .  
score.information   0.025724   0.004495   5.723  2.5e-08 ***
  amelioration.sante  0.095500   0.039929   2.392   0.0174 *  
  amelioration.moral  0.060625   0.033212   1.825   0.0689 .  
profession         -0.010855   0.016627  -0.653   0.5143    
service            -0.006889   0.012534  -0.550   0.5830    
---
  Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

(Dispersion parameter for gaussian family taken to be 0.2484297)

Null deviance: 93.465  on 313  degrees of freedom
Residual deviance: 76.019  on 306  degrees of freedom
(220 observations deleted due to missingness)
AIC: 463.71

Number of Fisher Scoring iterations: 2
Commentez le résultat.