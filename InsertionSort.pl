% rule
desc_insertion_sort(List, Sorted) :-
  desc_in_sort(List, [], Sorted).

desc_in_sort([], Acc, Acc).
desc_in_sort([H|T], Acc, Sorted) :-
  desc_insert(H, Acc, NAcc), desc_in_sort(T, NAcc, Sorted).

desc_insert((XKey, XValue), [(YKey, YValue)|T], [(YKey, YValue)|NT]) :-
  YValue > XValue, desc_insert((XKey, XValue), T, NT).
desc_insert((XKey, XValue), [(YKey, YValue)|T], [(XKey, XValue), (YKey, YValue)|T]):-
  YValue =< XValue.
desc_insert(X, [], [X]).