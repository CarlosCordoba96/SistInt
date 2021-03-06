programa(X):-downcase_atom(X, D), tokenizer(D,B),phrase(oracion,B).
tokenizer(A,B):-split_string(A," ",'',B).
%% Reglas gramaticales

oracion --> s_nominal(G, N), s_verbal(G, N).
oracion --> s_verbal(_, _).

s_nominal(Genero, Numero) --> determinante(Genero, Numero), s_nominal2(Genero, Numero).
s_nominal(Genero, Numero) --> s_nominal2(Genero, Numero).

%s_nominal2(G, N) --> s_nominal2(G, N), s_preposicional(_, _).
%s_nominal2(G, N) --> sustantivo(G, N).
s_nominal2(G, N) --> s_adjetivo(G, N),  s_nominal2(G, N).
%s_nominal2(G, N) --> sustantivo(G, N), s_adjetivo(G, N).

s_nominal2(G, N) --> sustantivo(G, N), s_nominal3(G, N) |
		     sustantivo(G, N), s_adjetivo(G, N), s_nominal3(G, N).
s_nominal3(G, N) --> [] | s_preposicional(_, _), s_nominal3(G, N) |
		     s_nominal(G, N), s_nominal3(G, N).

s_preposicional(G,N) --> preposicion,s_nominal(G,N).

s_adjetivo(G, N) --> s_adverbial, s_adjetivo(G, N).
%s_adjetivo(G, N) --> adjetivo(G, N).
%s_adjetivo(G, N) --> s_adjetivo(G, N), s_preposicional(_, _).
s_adjetivo(G, N) --> adjetivo(G, N), s_adjetivo2(G, N).
s_adjetivo2(G, N) --> [] | s_preposicional(_, _), s_adjetivo2(G, N).

%s_adverbial --> adverbio.
%s_adverbial --> s_adverbial, s_preposicional.
s_adverbial --> adverbio, s_adverbial2.
s_adverbial2 --> [] | s_preposicional(_, _), s_adverbial2.

% REVISAR PERSONA
s_verbal(G, N) --> verbo(normal, _, G, N, __), complementos.


s_verbal(G,N) --> verbo(copulativo, _, G, N, _), s_adjetivo(G, N).
s_verbal(G,N) -->verbo(copulativo,_,G,N,_),s_preposicional(_,_).
s_verbal(G,N) -->verbo(copulativo,_,G,N,_),s_nominal(_,_).
complementos --> [] | complemento, complementos.
complemento --> s_nominal(_, _) | s_adjetivo(_,_) |
		s_adverbial | s_adjetivo(_, _).
%% Vocabulario

determinante(masculino, singular) --> ["ese"].
determinante(masculino, plural) --> ["esos"].
determinante(femenino, singular) --> ["esa"].
determinante(femenino, plural) --> ["esas"].
determinante(masculino, singular) --> ["este"].
determinante(masculino, plural) --> ["estos"].
determinante(femenino, singular) --> ["esta"].
determinante(femenino, plural) --> ["estas"].
determinante(masculino, singular) --> ["aquel"].
determinante(masculino, plural) --> ["aquellos"].
determinante(femenino, singular) --> ["aquella"].
determinante(femenino, plural) --> ["aquellas"].
determinante(_, singular) --> ["mi"].
determinante(masculino, plural) --> ["unos"].
determinante(femenino, singular) --> ["la"].
determinante(_, plural) --> ["sus"].

sustantivo(masculino,singular) --> ["arbol"].
sustantivo(masculino,plural) --> ["arboles"].
sustantivo(femenino,singluar) -->["chica"].
sustantivo(femenino,plural) -->["chicas"].
sustantivo(masculino,singluar) -->["chico"].
sustantivo(masculino,plural) -->["chicos"].
sustantivo(masculino, singular) --> ["él"].
sustantivo(femenino, singular) --> ["ella"].
sustantivo(femenino, singular) --> ["casa"].
sustantivo(femenino, plural) --> ["casas"].
sustantivo(masculino, singular) --> ["enrique"].
sustantivo(masculino, singular) --> ["amigo"].
sustantivo(masculino, singular) --> ["pueblo"].
sustantivo(_, plural) --> ["turistas"].
sustantivo(masculino, plural) --> ["abuelos"].

preposicion --> ["de"].
preposicion --> ["a"].

adverbio --> ["muy"].
adverbio --> ["tan"].
adverbio --> ["pronto"].

verbo(copulativo, indicativo, _, singular, tercera) --> ["es"].
verbo(copulativo, indicativo, _, plural, tercera) --> ["son"].

verbo(copulativo, indicativo, _, singular, tercera) --> ["parece"].
verbo(copulativo, indicativo, _, plural, tercera) --> ["parecen"].

verbo(copulativo, indicativo, _, singular, tercera) --> ["está"].
verbo(copulativo, indicativo, _, plural, tercera) --> ["están"].

verbo(normal, indicativo, _, singular, tercera) --> ["venderá"].
verbo(normal, indicativo, _, singular, tercera) --> ["muerde"].

adjetivo(masculino, singular) --> ["bonito"].
adjetivo(femenino, singular) --> ["bonita"].
adjetivo(masculino, plural) --> ["bonitos"].
adjetivo(femenino, plural) --> ["bonitas"].
adjetivo(masculino, singular) --> ["guapo"].
adjetivo(femenino, singular) --> ["guapa"].
adjetivo(masculino, plural) --> ["guapos"].
adjetivo(femenino, plural) --> ["guapas"].
adjetivo(masculino, singular) --> ["simpático"].
