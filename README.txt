APOSTOL RUXANDRA ECATERINA
GRUPA 324 CD

				TEMA 1-POO

		1. PACHETUL PLAYERS
	In pachetul players am implementat cate o clasa pentru fiecare tip de
jucator si o clasa Bag in care creez o lista cu bunurile ce ajung in sacul 
fiecarui jucator, care va ajunge la inspectie. Aceasta clasa are campurile: 
cardsBag (o lista cu bunurile din sac), id si bribe, si metodele de tip get
aferente acestora. 
	O alta clasa din acest pachet este Player care contine
campurile id, budget, bag (lista cu cartile ce vor ajunge in sac), vectorul de
aparitie apparence in care verific de cate ori apare fiecare bun existent 
printre cartile primite de jucator, bagCreated un obiect de tip Bag cu 
caracteristicile sacului ce va ajuge la inspectie si nrRound(necesar doar
pentru Greedy). Ca metode intalnim in aceasta clasa doar "getter-uri"si 
"setter-uri".
	Clasa BasicPlayer extinde clasa Player. In aceasta clasa implementez
metoda frequentCard. Aceasta verifica folosindu-se de vectorul de aparitie 
apparence pentru a gasi cel mai frecvent obiect legale si returnez indicele
acestuia. Daca in vectorul de aparitie toti termenii cu indicii mai mici decat
20 (bunurile legale) au valoarea zero, atunci returnez cel mai scump bun ilegal
(parcurgand vectorul de aparitie de la 20 la 25 -bunurile ilegale- si comparand
profitul cu o variabila illegalMax). O alta metoda din aceasta clasa este
bagBasic, in care creez o lista cu bunul cel mai frecvent returnat de metoda
anterior mentionata (daca exista), pe care il adaug de atatea ori cat indica
valoarea vectorului de aparitie la pozitia cu acelasi index. Altfel, adauga 
bunul ilegal, returnat de aceeasi functie, o singura data.
	Clasa Greedy extinde clasa Basic, aducand in plus metoda greedyBag.
Daca jocul se afla intr-o runda para si printre cartile primite de jucator se
afla un bun ilegal, atunci pe langa bunurile din sacul creat prin apelarea 
functiei basicBag cel mai scump bun ilegal detinut. Acesta este ales parcurgand
intreaga lista de bunuri din posesia jucatorului si comparandule, ca profit, cu
o variabila profitMax, daca in acea lista exista si un bun legal. Altfel, 
indexul bunului ilegal este dat de functia illegalCardGreedy (pentru a evita 
dublarea bunului ilegal deja adaugat in sac de strategia basic).
	Clasa Bribe extinde, de asemenea, clasa Basic. Daca printre cartile 
jucatorului nu se afla nicio carte ilegala, atunci va juca strategia basic, 
apeland functia basicBag. Altfel, va seta mita in mod corespunzator pentru
viitorul lui sac in care va adauga cele mai valoroase bunuri. Acestea au fost
sortate dupa profit prin functia sortList, care are grija si ca suma 
penaltiului adunat de la toate bunurile sa nu aduca jucatorul pe minus.

		2. Sheriff pachet
	Clasa Sheriff contine doar campul budget, constructorul si metodele
de tip getter si setter aferente acestuia.
	Clasa BasicSheriff implementeaza metoda inspectBasic in care mita este
returnata jucatorului, daca exista. Se verifica daca bunurile din sac sunt 
legale si au id-ul declarat, daca nu atunci valoarea penaltiului lor se adauga
la suma penaltyPay, altfel bunurile sunt adaugata intr-o lista noua. Daca totul
a fost in regula se calculeaza suma penaltiului aferent acestora, pe care 
seriful va trebui sa o plateasca. Se creeaza un nou sac cu lista noua, mita 0
si id-ul vechi. bunurile din el vor ajunge pe taraba jucatorului. Se returneaza
suma de penalti dupa caz jucatorului sau serifului. Tot in aceasta clasa se 
gasesc si metodele cu modul de joc al serifilor Basic si Bribe. In primul caz 
sunt inspectati toti jucatorii si actualizandu-se caracteristicile 
corespunzatoare (prin clasa Result). In cazul jocului serifului Bribe, acestuia
i se calculeaza intai vecinii, acestia sunt inspectati, iar de la restul 
jucatorilor se incearca doar obtinerea mitei, daca exista. In final se 
actualizeaza caracteristicile (de asemenea, Result). Metoda playBribeBankrupt
face acelasi lucru cu exceptia inspectiei vecinilor.
	Clasa GreedySheriff extinde clasa BasicSheriff. Metoda inspectGreedy,
verifica daca jucatorul a incercat sa dea mita. In caz afirmativ banii trec in
bugetul serifului, iar in caz contrar sacul este inspectat apelandu-se metoda
inspectBasic. Metoda playGreedy simuleaza jocul serifului Greedy acesta 
verificand toti jucatorii apeland metoda anterior precizata. Caracteristicile 
jucatorilor sunt actualizte prin clasa Result.

		3. Result pachet
	Folosesc clasa Result pentru a retine mai usor id-ul jucatorilor, tipul
lor, bugetul si bunurile ajunse pe taraba. Aceasta clasa contine metode getter
si setter, iar in plus o metoda care afiseaza id-ul jucatorului, tipul si 
bugetul acestuia, dupa modelul cerut in enunt.
	O alta clasa din acest pachet este KingQueenBonus care contine metodele
setKingBonus si setQueenBonus prin care se vor adauga bonusurile regale. Pentru
implementarea lor ma folosesc de 4 vectori de aparitie care retin de cate ori
apare un bun de cele mai multe ori pe tarabele jucatorilor, respectiv a doua de
cele mai multe ori, si indicele jucatorului pe taraba caruia apre acel bun de
cele mai multe ori, respectiv a doua de cele mai multe ori. Determinarea 
acestor valori maxime se face prin parcurgerea listelor cu bunurile jucatorilor
si compararea frcventei aparitiei fiecarui tip cu maximul retinut in vector 
(pentru bonusul reginei trebuie sa tinem cont si de a nu fi acordat aceluias
jucator ca bonusul regelui).


		4. Main
	Detaliile despre jucatori sunt retinute intr-o lista de Result. Pentru 
fiecare subrunda din fiecare meci intai creez o lista cu jucatorii apeland in
functie de tipul lor metoda clasei derivate din Player si formandu-le sacul.
Apoi creez serifii conform tipului lor si are loc inspectia. Bunurile care 
ajung in cele din urma pe taraba sunt retinute in componenta listei Result cu 
acelasi id ca jucatorul. Bugetul acestei componente este actualizat. La sfarsit
apelez metodele clasei KingQueenBonus si sortez lista de Result.     


 