1)  Faites une analyse en composantes principales. Commentez le résultat.
> smp2<-smp[8:23]
> library(psy)
> mdspca(smp2)

2)  Comparez avec les résultats obtenus à l’aide d’une représentation sphérique.
> sphpca(smp2)

3)  Comparez avec les résultats obtenus à l’aide d’une classification hiérarchique.
> cah<-hclust(dist(t(scale(smp2))),method="ward")
> plot(cah,main="Santé mentale en prison")