% ------ Dynamic Predicates ------
:- dynamic cbs/4.
:- dynamic ask/2.
:- dynamic dont_ask/2.
:- dynamic already_asked_question/1.
:- dynamic aux/2.
:- dynamic aux2/3.
:- dynamic aux3/2.
:- dynamic aux4/2.
:- dynamic aux5/2.
:- dynamic process/7.
:- dynamic active/1.
:- dynamic suspended/2.
:- dynamic answer/2.
:- dynamic terminated/1.
:- dynamic terminated/2.
:- dynamic unchanged/1.
:- dynamic default_opposite/2.
:- dynamic opposite_default_process/2.
:- dynamic check_opposite/3.
:- dynamic here/1.
:- dynamic constraint_result/2.
:- dynamic active_process/2.

% ------ Used Built-in Modules ------
:- use_module(library(clpfd)). 
:- use_module(library(random)). 
:- use_module(library(lists)). 


% ------ Operators ------
:- op(400, xfx, @).

% ------ Auxiliary Predicates ------
not( Question ) :-
    Question, !, fail.
not( Question ).

add(X,[],[X]).
add(X,[A|L],[A|L1]):-
 add(X,L,L1).
 
empty_list([]).

% ------ Constraint Processing Predicates ------

results_active :-
					forall(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))),constraint_results(ID,VerifiedCL,Result)).
					
constraint_results(ID,VerifiedCL,Result):-
										results(ID,VerifiedCL,Result),
										assert(active_process(ID,Result)).
					
results(ID,[constraint(Q@S,V,ExpressionList,R)|T],Result) :-
															results(ID,T,IntermediateResult),
															revise(ExpressionList,V),
															add(constraint_result(Q@S,V),IntermediateResult,Result).

results(ID,[],[]).													
 
generate_opposite_constraint(A,expression(A #= B,regular),expression(A #\= B,regular)).
generate_opposite_constraint(A,expression(A #> B,regular),expression(A #=< B,regular)).
generate_opposite_constraint(A,expression(A #< B,regular),expression(A #>= B,regular)).
generate_opposite_constraint(A,expression(A #>= B,regular),expression(A #< B,regular)).
generate_opposite_constraint(A,expression(A #=< B,regular),expression(A #> B,regular)).
generate_opposite_constraint(A,expression(A in B,regular),expression(no(A in B),negation)).
generate_opposite_constraint(A,expression(in_set(L),list),expression(not_in_set(L),list)).
generate_opposite_constraint(A,expression(not_in_set(L),list),expression(in_set(L),list)).
				

check_opposite(ID):- 
					aux3(ID,constraint(Q@Is,T,[expression(D,regular)])),
					default_opposite(ID,T,expression(E,regular)),
					assert(aux2(ID,Q@Is,constraint(Q@Is,T,[expression(D,regular),expression(E,regular)]))),
					D #/\ E.

check_opposite(ID):- 
					aux3(ID,constraint(Q@Is,T,[expression(D,regular)])),
					default_opposite(ID,T,expression(no(E),negation)),
					assert(aux2(ID,Q@Is,constraint(Q@Is,T,[expression(D,regular),expression(no(E),negation)]))),
					D,
					#\ E.

check_opposite(ID):- 
					aux3(ID,constraint(Q@Is,T,[expression(in_set(D),list)])),
					default_opposite(ID,T,expression(not_in_set(E),list)),
					intersection(D,E,Lresult),
					subtract(D,Lresult,LNewresult),
					not(empty_list(LNewresult)),
					assert(aux2(ID,Q@Is,constraint(Q@Is,T,[expression(in_set(LNewresult),list)]))).
					 
check(constraint(P@Is,T,Expression),ID) :- 
										 cbs(P@Is,T,expression(A,regular),R),
										 assert(aux5(ID,R)),
										 add(expression(A,regular),Expression,NewExpression),
										 assert(aux(ID,constraint(P@Is,T,NewExpression,R))),
										 generate_opposite_constraint(T,expression(A,regular),Op),
										 assert(default_opposite(ID,T,Op)),
										 revise(Expression,D),
										 D #/\ A.
													
check(constraint(P@Is,T,[expression(in_set(Lrule),list)]),ID) :- 
															cbs(P@Is,T,expression(in_set(Lcbs),list),R),
															assert(aux5(ID,R)),
															intersection(Lrule,Lcbs,Lresult), 
															not(empty_list(Lresult)),
															assert(aux(ID,constraint(P@Is,T,[expression(in_set(Lresult),list)],R))),
															generate_opposite_constraint(T,expression(in_set(Lcbs),list),Op),
															assert(default_opposite(ID,T,Op)).
														
check(constraint(P@Is,T,[expression(not_in_set(Lrule),list)]),ID) :- 
																cbs(P@Is,T,expression(in_set(Lcbs),list),R),
																assert(aux5(ID,R)),
																intersection(Lrule,Lcbs,Lresult),
																subtract(Lcbs,Lresult,LNewresult),
																not(empty_list(LNewResult)),
																assert(aux(ID,constraint(P@Is,T,[expression(in_set(LNewresult),list)],R))),
																generate_opposite_constraint(T,expression(in_set(Lcbs),list),Op),
																assert(default_opposite(ID,T,Op)).

check(constraint(P@Is,T,[expression(in_set(Lrule),list)]),ID) :- 
															cbs(P@Is,T,expression(not_in_set(Lcbs),list),R),
															assert(aux5(ID,R)),
															intersection(Lrule,Lcbs,Lresult),
															subtract(Lrule,Lresult,LNewresult),
															not(empty_list(LNewResult)),
															assert(aux(ID,constraint(P@Is,T,[expression(in_set(LNewresult),list)],R))),
															generate_opposite_constraint(T,expression(not_in_set(Lcbs),list),Op),
														    assert(default_opposite(ID,T,Op)).

revise([expression(Const,regular)|T],V):- 
									revise(T,C),
									C #/\ Const,
									assert(here(V)).

revise([expression(no(Const),negation)|T],V):- 
									revise(T,C),
									#\ Const.
									
revise([expression(in_set(Const),list)],Const).								
																		
revise([],V).				

															
extract(constraint(Pred,Variable,Exp),Pred).
											

% ------ Global Variables -----

:- nb_setval(process_identifier,0).

% ------ System Component Identifier ------

sigma(pis). % patient information sources

sigma(cp). % context provider 

% ------ Abducible Predicates A ------

abducible(gtt(A,B),yes).

% ------ Integrity Constraints I ------

contradiction(gtt(PREVIOUS,TASK),constraint(ex(TASK,V)@cp,V,expression(V #= 0,regular))).

% ------ Process reduction------

query(next(X,List)) :- 
				findall(F,query2(nt(X,F)),List).
				%computation_state(APL,SPL).

query2(nt(X,F)) :-
					fact(alt(X,F)),
					process_generation_initialization(rule(tcv(F),CL),nt(X,F)).
					

computation_state(APL,SPL) :- 
							findall(active(Process),active(Process),APL),
							findall(suspended(SP,Process),suspended(SP,Process),SPL).
							
process_generation_initialization(rule(tcv(F),CL),nt(X,F)) :-
																rule(tcv(F),CL),
																add(gtt(X,F),CL,NewCL),
																nb_getval(process_identifier,ID),
																NewID is ID+1,
																nb_setval(process_identifier,NewID),
																assert(process(NewID,[],NewCL,[],[],[],nt(X,F))),
																assert(active(process(NewID,[],NewCL,[],[],[],nt(X,F)))),
																process_reduction(process(NewID,[],NewCL,[],[],[],nt(X,F))).
											
process_reduction(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F))) :-
																					nb_getval(process_identifier,ID),
																					NewID is ID+1,
																					assert(aux3(NewID,C)),
																					nb_setval(process_identifier,NewID),
																					
																			
																(abducible(C,yes) -> contradiction(C,constraint(ex(F,V)@cp,V,ContradictionExp)),
																					generate_opposite_constraint(V,ContradictionExp,ContradictionOpposite),
																					add(constraint(ex(F,V)@cp,V,[ContradictionOpposite]),CL,NewCL),
																					add(OldID,ParentProcessID,NewParentList),
																					retract(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																					
																					add(C,IA,NewIA),
																					
																					retract(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F))),
																					assert(active(process(NewID,NewParentList,NewCL,OD,NewIA,VerifiedCL,nt(X,F)))),
																					process_reduction(process(NewID,NewParentList,NewCL,OD,NewIA,VerifiedCL,nt(X,F)))
																					;
																					
																					( check(C,NewID) ->   aux(NewID,CO),
																										
																										(aux(NewID,constraint(ODToAdd,T,ConstraintList,default)) -> extract(C,Pred),																																							
																																									ask_format(Pred,Question,Recipient),
																																									
																																								   (not(already_asked_question(asked(Question,Recipient))) ->  assert(ask(Question,Recipient)),
																																																						       assert(already_asked_question(asked(Question,Recipient)))
																																																						   ;
																																																							   assert(dont_ask(Question,Recipient))),
																																									add(CO,VerifiedCL,NewVerifiedCL),
																																									add(OldID,ParentProcessID,NewParentList),
																																									add(ODToAdd,OD,NewOD),
																																									assert(process(NewID,NewParentList,CL,NewOD,IA,NewVerifiedCL,nt(X,F))),
																																									assert(active(process(NewID,NewParentList,CL,NewOD,IA,NewVerifiedCL,nt(X,F)))),
																																									retract(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																																									
																																									(check_opposite(NewID) -> aux2(NewID,SP,NC),
																																															  add(NC,CL,NewCL),
																																															  nb_getval(process_identifier,CurrentID),
																																															  NewCurrentID is CurrentID+1,
																																															  nb_setval(process_identifier,NewCurrentID),
																																															  assert(process(NewCurrentID,NewParentList,NewCL,OD,IA,VerifiedCL,nt(X,F))),
																																															  assert(suspended(SP,process(NewCurrentID,NewParentList,NewCL,OD,IA,VerifiedCL,nt(X,F))))
																																															;
																																															  assert(opposite_default_process(NewID,no))),
																																									
																																									process_reduction(process(NewID,NewParentList,CL,NewOD,IA,NewVerifiedCL,nt(X,F)))
																																									
																																								;
																																									add(CO,VerifiedCL,NewVerifiedCL),
																																									add(OldID,ParentProcessID,NewParentList),
																																									assert(process(NewID,NewParentList,CL,OD,IA,NewVerifiedCL,nt(X,F))),
																																									retract(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																																									assert(active(process(NewID,NewParentList,CL,OD,IA,NewVerifiedCL,nt(X,F)))),
																																									process_reduction(process(NewID,NewParentList,CL,OD,IA,NewVerifiedCL,nt(X,F))))
																										; 
																										 
																																		  (aux5(NewID,default) ->   extract(C,SP),
																																									retract(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																																									assert(suspended(SP,process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																																									ask_format(SP,Question,Recipient),
																																									
																																									(not(already_asked_question(asked(Question,Recipient))) ->  assert(ask(Question,Recipient)),
																																																						        assert(already_asked_question(asked(Question,Recipient)))
																																																						    ;
																																																							    assert(dont_ask(Question,Recipient)))
																																								;
																																									retract(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																																									assert(terminated(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F))))))).
																																									
process_reduction(process(OldID,ParentProcessID,[],OD,IA,VerifiedCL,nt(X,F))).														

% ------ Answer arrival ------


answer_arrival(Parameter,Source,Operator,Value) :- 
														    answer_conversion(Parameter,Source,Operator,Value),
															answer(Parameter,constraint(Q@Is,V,expression(Const,Type2))),
															cbs(Q@Is,V,expression(ConstOld,Type),default),
															retract(cbs(Q@Is,V,expression(ConstOld,Type),default)),
															assert(cbs(Q@Is,V,expression(Const,Type2),revised)),
															belief_revision(constraint(Q@Is,V,expression(Const,Type2))).

belief_revision(constraint(Q@Is,V,expression(Const,Type))) :- 
															forall(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))),revise_active(constraint(Q@Is,V,expression(Const,Type)),process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
															forall(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))),revise_suspended(constraint(Q@Is,V,expression(Const,Type)),SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))).
															

revise_active(constraint(Q@Is,V,expression(Const,regular)),process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))) :- 
																											(member(Q@Is,OD) -> nb_getval(process_identifier,CurrentID),
																																NewID is CurrentID+1,
																																nb_setval(process_identifier,NewID),
																																member(constraint(Q@Is,V,ExpressionList,Modality),VerifiedCL),
																																add(expression(Const,regular),ExpressionList,NewExpressionList),
																																assert(aux4(NewID,constraint(Q@Is,V,NewExpressionList,revised))),

																																(revise(NewExpressionList,V) -> retract(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																								select(Q@Is,OD,NewOD),
																																								select(constraint(Q@Is,V,ExpressionList,Modality),VerifiedCL,IntermediateVerifiedCL),
																																								aux4(NewID,RevisedConstraint),
																																								add(RevisedConstraint,IntermediateVerifiedCL,NewVerifiedCL),
																																								add(ID,ParentList,NewParentList),
																																								assert(active(process(NewID,NewParentList,CL,NewOD,IA,NewVerifiedCL,nt(X,F))))
																																							;																	
																																								retract(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																								assert(terminated(answer(constraint(Q@Is,V,expression(Const,regular))),process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))))
																																;
																																assert(unchanged(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))))).

revise_active(constraint(Q@Is,V,expression(in_set(Const),list)),process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))) :- 
																											(member(Q@Is,OD) -> nb_getval(process_identifier,CurrentID),
																																NewID is CurrentID+1,
																																nb_setval(process_identifier,NewID),
																																member(constraint(Q@Is,V,[expression(in_set(Lverified),list)],Modality),VerifiedCL),
																																intersection(Const,Lverified,Lresult),
																																
																																(not(empty_list(Lresult)) ->    retract(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																								select(Q@Is,OD,NewOD),
																																								select(constraint(Q@Is,V,[expression(in_set(Lverified),list)],Modality),VerifiedCL,IntermediateVerifiedCL),
																																								add(constraint(Q@Is,V,[expression(in_set(Lresult),list)],revised),IntermediateVerifiedCL,NewVerifiedCL),
																																								add(ID,ParentList,NewParentList),
																																								assert(active(process(NewID,NewParentList,CL,NewOD,IA,NewVerifiedCL,nt(X,F))))
																																							;																	
																																								retract(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																								assert(terminated(answer(constraint(Q@Is,V,expression(expression(in_set(Const),list)))),process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))))
																																;
																																assert(unchanged(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))))).																																
																																
revise_suspended(constraint(Q@Is,V,expression(Const,regular)),SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))) :-
																											
																											( SP=Q@Is ->    nb_getval(process_identifier,CurrentID),
																															NewID is CurrentID+1,
																															nb_setval(process_identifier,NewID),
																															member(constraint(Q@Is,V,ExpressionList),CL),
																															select(constraint(Q@Is,V,ExpressionList),CL,NewCL),
																															add(expression(Const,regular),ExpressionList,NewExpressionList),
																															assert(aux4(NewID,constraint(Q@Is,V,NewExpressionList,revised))),
																															
																															(revise(NewExpressionList,V) ->  retract(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																							 aux4(NewID,RevisedConstraint),
																																							 add(RevisedConstraint,VerifiedCL,NewVerifiedCL),
																																							 add(ID,ParentList,NewParentList),
																																							 assert(active(process(NewID,NewParentList,NewCL,OD,IA,NewVerifiedCL,nt(X,F)))),
																																							 process_reduction(process(NewID,NewParentList,NewCL,OD,IA,NewVerifiedCL,nt(X,F)))
																																						    ;																	
																																							  retract(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																							  assert(terminated(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))))
																														;
																														
																														
																														(member(Q@Is,OD) -> nb_getval(process_identifier,CurrentID),
																																			NewID is CurrentID+1,
																																			nb_setval(process_identifier,NewID),
																																			member(constraint(Q@Is,V,ExpressionList,Modality),VerifiedCL),
																																			add(expression(Const,regular),ExpressionList,NewExpressionList),
																																			assert(aux4(NewID,constraint(Q@Is,V,NewExpressionList,revised))),
																																			
																																			(revise(NewExpressionList,V) -> retract(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																											select(Q@Is,OD,NewOD),
																																											select(constraint(Q@Is,V,ExpressionList,Modality),VerifiedCL,IntermediateVerifiedCL),
																																											aux4(NewID,RevisedConstraint),
																																										    add(RevisedConstraint,IntermediateVerifiedCL,NewVerifiedCL),
																																											add(ID,ParentList,NewParentList),
																																											assert(suspended(SP,process(NewID,NewParentList,CL,NewOD,IA,NewVerifiedCL,nt(X,F))))
																																										;																	
																																								          retract(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																								          assert(terminated(answer(constraint(Q@Is,V,expression(expression(in_set(Const),list)))),process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))))
																																
																																         ;
																														assert(unchanged(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))))))).
				
revise_suspended(constraint(Q@Is,V,expression(in_set(Const),list)),SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))) :-
																											
																											( SP=Q@Is ->    nb_getval(process_identifier,CurrentID),
																															NewID is CurrentID+1,
																															nb_setval(process_identifier,NewID),
																															member(constraint(Q@Is,V,[expression(in_set(Lcl),list)]),CL),
																															intersection(Const,Lcl,Lresult),
																															
																															(not(empty_list(Lresult)) ->     retract(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																							 select(constraint(Q@Is,V,[expression(in_set(Lcl),list)]),CL,NewCL),
																																							 add(constraint(Q@Is,V,[expression(in_set(Lresult),list)],revised),VerifiedCL,NewVerifiedCL),
																																							 add(ID,ParentList,NewParentList),
																																							 assert(active(process(NewID,NewParentList,NewCL,OD,IA,NewVerifiedCL,nt(X,F)))),
																																							 assert(process(NewID,NewParentList,NewCL,OD,IA,NewVerifiedCL,nt(X,F)))
																																							
																																						    ;																	
																																							 retract(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																							 assert(terminated(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))))
																														; 
																													    
																														(member(Q@Is,OD) -> nb_getval(process_identifier,CurrentID),
																																			
																																			NewID is CurrentID+1,
																																			nb_setval(process_identifier,NewID),
																																			member(constraint(Q@Is,V,[expression(in_set(Lverified),list)],Modality),VerifiedCL),
																																			
																																			intersection(Const,Lverified,Lresult),
																																			
																																			(not(empty_list(Lresult)) ->  retract(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																										  select(Q@Is,OD,NewOD),
																																										  select(constraint(Q@Is,V,[expression(in_set(Lverified),list)],Modality),VerifiedCL,IntermediateVerifiedCL),
																																										  add(constraint(Q@Is,V,[expression(in_set(Lresult),list)],revised),IntermediateVerifiedCL,NewVerifiedCL),
																																										  add(ID,ParentList,NewParentList),
																																								          assert(suspended(SP,process(NewID,NewParentList,CL,NewOD,IA,NewVerifiedCL,nt(X,F))))
																																										;																	
																																								          retract(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																																								          assert(terminated(answer(constraint(Q@Is,V,expression(expression(in_set(Const),list)))),process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))))
																																
																																         ;
																																		assert(unchanged(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))))))).				
																														
% ------ Initialization of the Current Belief Set -----
init :-
		[casestudy1],
	    forall(delta(A,B,C),assert(cbs(A,B,C,default))).

% ------ Search for questions -----

check_ask(Value,Receiver) :-
							ask(Value,Receiver),
							retract(ask(Value,Receiver)).