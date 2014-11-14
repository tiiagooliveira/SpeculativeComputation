% Case-study 1

% ------ Operators ------

:- op(400, xfx, @).

% ------ Answer Conversion ------

%Predicate used to convert the answer from the information sources into literals.

answer_conversion(t,pis,in_set,Value):-
										assert(answer(t,constraint(t(T)@pis,T,expression(in_set([Value]),list)))).
answer_conversion(n,pis,in_set,Value) :- 
										assert(answer(n,constraint(n(N)@pis,N,expression(in_set([Value]),list)))).
answer_conversion(m,pis,in_set,Value) :- 
										assert(answer(m,constraint(m(M)@pis,M,expression(in_set([Value]),list)))).
answer_conversion('ex(a01)',cp,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer('ex(a01)',constraint(ex(a01,F)@cp,F,expression(F#=Value2,regular)))).
answer_conversion('ex(a02)',cp,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer('ex(a02)',constraint(ex(a02,F)@cp,F,expression(F#=Value2,regular)))).
answer_conversion('ex(a03)',cp,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer('ex(a03)',constraint(ex(a03,F)@cp,F,expression(F#=Value2,regular)))).	
answer_conversion('ex(a04)',cp,equal,Value) :- 
										atom_number(Value,Value2),
										assert(answer('ex(a04)',constraint(ex(a04,F)@cp,F,expression(F#=Value,regular)))).										
										

% ------ External Predicates ------

askable(t(_)@pis).
askable(n(_)@pis).
askable(m(_)@pis).
askable(ex(_,_)@cp).

ask_format(t(_)@pis,t,pis).
ask_format(n(_)@pis,n,pis).
ask_format(m(_)@pis,m,pis).
ask_format(ex(a01,_)@cp,ex(a01),cp).
ask_format(ex(a02,_)@cp,ex(a02),cp).
ask_format(ex(a03,_)@cp,ex(a03),cp).
ask_format(ex(a04,_)@cp,ex(a04),cp).

% ------ Default Set ------

delta(t(T)@pis,T,expression(in_set([t1]),list)).
delta(n(N)@pis,N,expression(in_set([n0]),list)).
delta(m(M)@pis,M,expression(in_set([m0]),list)).
delta(ex(a01,D)@cp,D,expression(D#=1,regular)).
delta(ex(a02,D)@cp,D,expression(D#=1,regular)).
delta(ex(a03,D)@cp,D,expression(D#=0,regular)).
delta(ex(a04,D)@cp,D,expression(D#=0,regular)).

% ------ Program P ------

rule(nt(X,F),[alt(X,F),tcv(F),gtt(X,F)]).

rule(tcv(a01),
			[constraint(t(T)@pis,T,[expression(in_set([tis]),list)])]).
rule(tcv(a01),
			[constraint(t(T)@pis,T, [expression(in_set([t1,t2]),list)]),
			constraint(n(N)@pis,N,[expression(in_set([n0]),list)]),
			constraint(m(M)@pis,M,[expression(in_set([m0]),list)])]).
rule(tcv(a02),
			[constraint(t(T)@pis,T,[expression(in_set([t3]),list)]),
			constraint(n(N)@pis,N,[expression(in_set([n0]),list)]),
			constraint(m(M)@pis,M,[expression(in_set([m0]),list)])]).
rule(tcv(a03),
			[constraint(t(T)@pis,T,[expression(in_set([t4]),list)]),
			constraint(n(N)@pis,N,[expression(in_set([n0]),list)]),
			constraint(m(M)@pis,M,[expression(in_set([m0]),list)])]).
rule(tcv(a04),
			[constraint(t(T)@pis,T,[expression(in_set([t1,t2,t3,t4]),list)]),
			constraint(n(N)@pis,N,[expression(in_set([n1,n2]),list)]),
			constraint(m(M)@pis,M,[expression(in_set([m0]),list)])]).

fact(alt(q01,a01)).
fact(alt(q01,a02)).
fact(alt(q01,a03)).
fact(alt(q01,a04)).
