-module(quicksort).
-export([quicksort/1,lc_quicksort/1]).

quicksort([]) -> [];
quicksort([Pivot|Rest]) ->
    {Left,Right} = partition(Pivot,Rest,[],[]),
    quicksort(Left) ++ [Pivot] ++ quicksort(Right).

partition(_,[],Smaller,Larger) ->
    {Smaller,Larger};
partition(Pivot,[H|T],Smaller,Larger) ->
    if H =< Pivot ->
	    partition(Pivot,T,[H|Smaller],Larger); % grow Smaller
       H > Pivot ->
	    partition(Pivot,T,Smaller,[H|Larger]) % grow Larger
    end.

lc_quicksort([]) ->
    [];
lc_quicksort([Pivot|Rest]) ->
    lc_quicksort([Smaller || Smaller <- Rest,
			     Smaller =< Pivot])
    ++ Pivot ++
	lc_quicksort([Larger || Larger <- Rest,
				Larger > Pivot]).
