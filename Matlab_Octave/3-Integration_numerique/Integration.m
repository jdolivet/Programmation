function [err_trap,err_gau2,err_simp]=inflag(N)
% ---------------------------
% Etant donne un entier N, on integre numeriquement la fonction f
% definie ci-apres sur l'intervalle [a,b] dont les bornes sont 
% definies ci-dessous.
% 
% parametres
% ----------
%  N        : nombre d'intervalles pour la discretisation de [a,b]
% sorties

% Bornes de l'intervalle
a = 0.;
b = 1.;

% Fonction f a integrer
% voir fin du fichier 

% Valeur exacte de l'integrale de f sur [a,b] = [0,1]
  exact = -1./(10*pi);

% Pas d'espace
h = (b-a)/N;

% ------------------------------------------------------------
% Formules de quadrature
% -----------------------------------------------------------

% Formule du trapeze 
% ------------------

Lhtrap = 0.;
for i=0:N-1,
	xi=a+i*h;
	Lhtrap = Lhtrap + h/2*(f(xi)+f(xi+h));
end

% Formule de Gauss a deux points 
% ------------------------------

Lhgau2 = 0.;
alpha1 = (1-1/sqrt(3))/2.;
beta1 = (1+1/sqrt(3))/2.;

for i=0:N-1,
	xi=a+i*h;
	Lhgau2 = Lhgau2 + h/2*(f(xi+alpha1*h)+f(xi+beta1*h));
end

% Formule de Simpson
% ------------------

Lhsimp= 0.;

for i=0:N-1,
	xi=a+i*h;
	Lhsimp =  Lhsimp + h/6*(f(xi)+4*f(xi+h/2)+f(xi+h));
end

% ------------------------------------------------------------

err_trap = abs(exact-Lhtrap);
err_gau2 = abs(exact-Lhgau2);
err_simp = abs(exact-Lhsimp);
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Fonction f a integrer
function f=f(x)
 f= x*sin(10*pi*x);
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

