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
:- dynamic constraint_result/3.
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
				forall(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))),constraint_results(ID,VerifiedCL)).
					
constraint_results(ID,VerifiedCL):-
									results(ID,VerifiedCL).
					
results(ID,[constraint(Q@S,V,ExpressionList,R)|T]) :-
															revise(ExpressionList,V),
															assert(constraint_result(ID,Q@S,V)),
															results(ID,T).
						
results(ID,[]).	

all_constraint_results(ID,Result):-
							findall(constraint(Q@S,V),constraint_result(ID,Q@S,V),Result).
 

%Predicate that generates the negation of a given constraint.
%Works for equality and inequality constrains, and sets.

generate_opposite_constraint(A,expression(A #= B,regular),expression(A #\= B,regular)).
generate_opposite_constraint(A,expression(A #> B,regular),expression(A #=< B,regular)).
generate_opposite_constraint(A,expression(A #< B,regular),expression(A #>= B,regular)).
generate_opposite_constraint(A,expression(A #>= B,regular),expression(A #< B,regular)).
generate_opposite_constraint(A,expression(A #=< B,regular),expression(A #> B,regular)).
generate_opposite_constraint(A,expression(A in B,regular),expression(no(A in B),negation)).
generate_opposite_constraint(A,expression(in_set(L),list),expression(not_in_set(L),list)).
generate_opposite_constraint(A,expression(not_in_set(L),list),expression(in_set(L),list)).
				

%Predicate that checks, for a given default, if a certain constraint is consistent with its opposite.

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

%Predicate that checks, for a given default, if a certain constraint is consistent.					

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

%Having a list of constraints for a certain parameter, this predicate checks if all of them are consistent.

revise([expression(Const,regular)|T],V):- 
									revise(T,C),
									C #/\ Const.

revise([expression(no(Const),negation)|T],V):- 
									revise(T,C),
									#\ Const.
									
revise([expression(in_set(Const),list)],in_set(Const)).								
																		
revise([],V).				

															
%Having the expression of a constraint associated with a predicate, this predicate extracts the predicate of the constraint.
															
extract(constraint(Pred,Variable,Exp),Pred).
											

% ------ Global Variables -----

%Variable used to identify processes.

:- nb_setval(process_identifier,0).

% ------ System Component Identifier ------

sigma(pis). % patient information sources

sigma(cp). % context provider 

% ------ Abducible Predicates A ------

abducible(gtt(A,B),yes).

% ------ Integrity Constraints I ------

contradiction(gtt(PREVIOUS,TASK),constraint(ex(TASK,V)@cp,V,expression(V #= 0,regular))).

% ------ Process reduction------

%Predicate used to perform a query.

query(next(X)) :- process_generation_initialization(nt(X,F)).

%Predicate that handles the top level goal.
						
process_generation_initialization(nt(X,F)) :-    
											nb_getval(process_identifier,ID),
											NewID is ID+1,
											nb_setval(process_identifier,NewID),
											assert(process(NewID,[],[nt(X,F)],[],[],[],nt(X,F))),
											assert(active(process(NewID,[],[nt(X,F)],[],[],[],nt(X,F)))),
											process_reduction(process(NewID,[],[nt(X,F)],[],[],[],nt(X,F))).
											
%Predicate that performs process reduction.

process_reduction(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F))) :-
																					nb_getval(process_identifier,ID),
																					NewID is ID+1,
																					assert(aux3(NewID,C)),
																
																(abducible(C,yes) -> nb_setval(process_identifier,NewID),
																					contradiction(C,constraint(ex(F,V)@cp,V,ContradictionExp)),
																					generate_opposite_constraint(V,ContradictionExp,ContradictionOpposite),
																					add(constraint(ex(F,V)@cp,V,[ContradictionOpposite]),CL,NewCL),
																					add(OldID,ParentProcessID,NewParentList),
																					retract(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																					add(C,IA,NewIA),
																					assert(process(NewID,NewParentList,NewCL,OD,NewIA,VerifiedCL,nt(X,F))),
																					assert(active(process(NewID,NewParentList,NewCL,OD,NewIA,VerifiedCL,nt(X,F)))),
																					process_reduction(process(NewID,NewParentList,NewCL,OD,NewIA,VerifiedCL,nt(X,F)))
																					
																					;
																							
																							( check(C,NewID) -> nb_setval(process_identifier,NewID), 
																												aux(NewID,CO),
																										
																										(aux(NewID,constraint(ODToAdd,T,ConstraintList,default)) -> extract(C,Pred),																																							
																																									askable(Pred),
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
																																		  (aux5(NewID,default) ->   %nb_setval(process_identifier,NewID),
																																									extract(C,SP),
																																									retract(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																																									assert(suspended(SP,process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																																									askable(SP),
																																									ask_format(SP,Question,Recipient),
																																									
																																									(not(already_asked_question(asked(Question,Recipient))) ->  assert(ask(Question,Recipient)),
																																																						        assert(already_asked_question(asked(Question,Recipient)))
																																																						    ;
																																																								assert(dont_ask(Question,Recipient)))
																																								;
																																								
																																								(not(fact(C)) -> forall(rule(C,Constraints),process_reduction_rule(rule(C,Constraints),process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F))))
																																												;
																																												forall(fact(C),process_reduction_fact(C,process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F))))
																																								
																																								)))).
							
process_reduction(process(OldID,ParentProcessID,[],OD,IA,VerifiedCL,nt(X,F))).						
																						 
%Process reduction when the goal is a fact.
																						 
process_reduction_fact(C,process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F))) :- 
																						 nb_getval(process_identifier,ID),
																						 NewID is ID+1,
																						 nb_setval(process_identifier,NewID),
																						 add(OldID,ParentProcessID,NewParentList),
																						 assert(process(NewID,NewParentList,CL,OD,IA,VerifiedCL,nt(X,F))),
																						 assert(active(process(NewID,NewParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
																						 
																						 (active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))-> retract(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																																								   process_reduction(process(NewID,NewParentList,CL,OD,IA,VerifiedCL,nt(X,F)))
																																									;
																																								   process_reduction(process(NewID,NewParentList,CL,OD,IA,VerifiedCL,nt(X,F)))).

%Process reduction when the goal is a rule.

process_reduction_rule(rule(C,Constraints),process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F))):- 			
																												nb_getval(process_identifier,ID),
																												NewID is ID+1,
																												nb_setval(process_identifier,NewID),
																												add(OldID,ParentProcessID,NewParentList),
																												append(Constraints,CL,NewCL),
																												assert(active(process(NewID,NewParentList,NewCL,OD,IA,VerifiedCL,nt(X,F)))),
																												assert(process(NewID,NewParentList,NewCL,OD,IA,VerifiedCL,nt(X,F))),
																												
																												(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))-> retract(active(process(OldID,ParentProcessID,[C|CL],OD,IA,VerifiedCL,nt(X,F)))),
																																														  process_reduction(process(NewID,NewParentList,NewCL,OD,IA,VerifiedCL,nt(X,F)))
																																														;
																																														  process_reduction(process(NewID,NewParentList,NewCL,OD,IA,VerifiedCL,nt(X,F)))).
																									 
% ------ Answer arrival ------

%This predicate processes the arrival of answers from the information sources.

answer_arrival(Parameter,Source,Operator,Value) :- 
														    answer_conversion(Parameter,Source,Operator,Value),
															answer(Parameter,constraint(Q@Is,V,expression(Const,Type2))),
															cbs(Q@Is,V,expression(ConstOld,Type),default),
															retract(cbs(Q@Is,V,expression(ConstOld,Type),default)),
															assert(cbs(Q@Is,V,expression(Const,Type2),revised)),
															belief_revision(constraint(Q@Is,V,expression(Const,Type2))).

% For all active and suspended processes, belief revision is performed.

belief_revision(constraint(Q@Is,V,expression(Const,Type))) :- 
															forall(active(process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))),revise_active(constraint(Q@Is,V,expression(Const,Type)),process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))),
															forall(suspended(SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F))),revise_suspended(constraint(Q@Is,V,expression(Const,Type)),SP,process(ID,ParentList,CL,OD,IA,VerifiedCL,nt(X,F)))).
															

% Predicate that performs belief revision for an active process.

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
% Predicate that performs belief revision for a suspended process.
																																
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

% Predicate that initializes the current belief set and consults the case-study file.

init :-
		[casestudy1v2],
	    forall(delta(A,B,C),assert(cbs(A,B,C,default))).

% ------ Search for questions -----

check_ask(Value,Receiver) :-
							ask(Value,Receiver),
							retract(ask(Value,Receiver)).