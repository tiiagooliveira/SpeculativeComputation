% Case-study 2

% ------ Operators ------

:- op(400, xfx, @).

% ------ Answer Conversion ------

%Predicate used to convert the answer from the information sources into literals.

answer_conversion(gdr,pis,in_set,Value):-
								     assert(answer(gdr,constraint(gdr(G)@pis,G,expression(in_set([Value]),list)))).

answer_conversion(aob,pis,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer(aob,constraint(aob(A)@pis,A,expression(A#=Value2,regular)))).
answer_conversion(trig,pis,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer(trig,constraint(trig(T)@pis,T,expression(T#=Value2,regular)))).
answer_conversion(hdl,pis,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer(hdl,constraint(hdl(L)@pis,L,expression(L#=Value2,regular)))).

answer_conversion(sbp,pis,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer(sbp,constraint(sbp(SP)@pis,SP,expression(SP#=Value2,regular)))).
answer_conversion(dbp,pis,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer(dbp,constraint(dbp(DP)@pis,DP,expression(DP#=Value2,regular)))).
answer_conversion(fg,pis,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer(fg,constraint(fg(FG)@pis,FG,expression(FG#=Value2,regular)))).
										
answer_conversion('ex(a05)',cp,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer('ex(a05)',constraint(ex(a05,F)@cp,F,expression(F#=Value2,regular)))).
answer_conversion('ex(a06)',cp,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer('ex(a06)',constraint(ex(a06,F)@cp,F,expression(F#=Value2,regular)))).

% ------ External Predicates ------

askable(gdr(_)@pis).
askable(aob(_)@pis).
askable(trig(_)@pis).
askable(hdl(_)@pis).
askable(sbp(_)@pis).
askable(dbp(_)@pis).
askable(fg(_)@pis).
askable(ex(_,_)@cp).

ask_format(gdr(_)@pis,gdr,pis).
ask_format(aob(_)@pis,aob,pis).
ask_format(trig(_)@pis,trig,pis).
ask_format(hdl(_)@pis,hdl,pis).
ask_format(sbp(_)@pis,sbp,pis).
ask_format(dbp(_)@pis,dbp,pis).
ask_format(fg(_)@pis,fg,pis).

ask_format(ex(a05,_)@cp,ex(a05),cp).
ask_format(ex(a06,_)@cp,ex(a06),cp). 

% ------ Default Set ------

delta(gdr(G)@pis,G,expression(in_set([male]),list)).
delta(aob(A)@pis,A,expression(A#>105,regular)).
delta(trig(T)@pis,T,expression(T#=152,regular)).
delta(hdl(L)@pis,L,expression(L#<35,regular)).
delta(sbp(SP)@pis,SP,expression(SP#>=130,regular)).
delta(dbp(DP)@pis,DP,expression(DP#>=85,regular)).
delta(fg(FG)@pis,FG,expression(FG#>112,regular)).

delta(ex(a05,D)@cp,D,expression(D#=1,regular)).
delta(ex(a06,D)@cp,D,expression(D#=1,regular)).

% ------ Program P ------

rule(nt(X,F),[alt(X,F),tcv(F),gtt(X,F)]).

rule(tcv(a05),
			[constraint(gdr(G)@pis,G,[expression(in_set([male]),list)]),
			constraint(aob(A)@pis,A,[expression(A#>102,regular)]),
			constraint(trig(T)@pis,T,[expression(T#>=150,regular)]),
			constraint(hdl(L)@pis,L,[expression(L#<40,regular)]),
			constraint(sbp(SP)@pis,SP,[expression(SP#>=130,regular)]),
			constraint(dbp(DP)@pis,DP,[expression(DP#>=85,regular)]),
			constraint(fg(FG)@pis,FG,[expression(FG#>=110,regular)])
			]).

rule(tcv(a05),
			[constraint(gdr(G)@pis,G,[expression(in_set([female]),list)]),
			constraint(aob(A)@pis,A,[expression(A#>88,regular)]),
			constraint(trig(T)@pis,T,[expression(T#>=150,regular)]),
			constraint(hdl(L)@pis,L,[expression(L#<50,regular)]),
			constraint(sbp(SP)@pis,SP,[expression(SP#>=130,regular)]),
			constraint(dbp(DP)@pis,DP,[expression(DP#>=85,regular)]),
			constraint(fg(FG)@pis,FG,[expression(FG#>=110,regular)])
			]).

rule(tcv(a06),
			[constraint(gdr(G)@pis,G,[expression(in_set([male]),list)]),
			constraint(aob(A)@pis,A,[expression(A#=<102,regular)])
			]).

rule(tcv(a06),
			[constraint(gdr(G)@pis,G,[expression(in_set([female]),list)]),
			constraint(aob(A)@pis,A,[expression(A#=<88,regular)])
			]).
			
rule(tcv(a06),
			[constraint(trig(T)@pis,T,[expression(T#<150,regular)])
			]).

rule(tcv(a06),
			[constraint(gdr(G)@pis,G,[expression(in_set([male]),list)]),
			constraint(hdl(L)@pis,L,[expression(L#>=40,regular)])
			]).

rule(tcv(a06),
			[constraint(gdr(G)@pis,G,[expression(in_set([female]),list)]),
			constraint(hdl(L)@pis,L,[expression(L#>=50,regular)])
			]).

rule(tcv(a06),
			[constraint(sbp(SP)@pis,SP,[expression(SP#<130,regular)])
			]).
			
rule(tcv(a06),
			[constraint(dbp(DP)@pis,DP,[expression(DP#<85,regular)])
			]).
			
rule(tcv(a06),
			[constraint(fg(FG)@pis,FG,[expression(FG#<110,regular)])
			]).
			
fact(alt(q02,a05)).
fact(alt(q02,a06)).
