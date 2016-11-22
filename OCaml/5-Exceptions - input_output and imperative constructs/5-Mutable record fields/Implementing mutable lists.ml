let head l = 
  match l.pointer with
  | Nil -> raise Empty_xlist
  | List (a, list) -> a
;;

let tail l =
  match l.pointer with
  | Nil -> raise Empty_xlist
  | List (a, list) -> list
;;

let add a l = 
  let rec ajout elt liste = 
    match liste.pointer with
    | Nil -> { pointer = List (elt, nil ())}
    | List (e, tail) ->  { pointer = List (elt, (ajout e tail))}
  in l.pointer <- (ajout a l).pointer 
;;

let chop l =
  match l.pointer with
  | Nil -> raise Empty_xlist
  | List (a, list) -> l.pointer <- list.pointer
;;

let append l l' = 
  let rec ajout liste1 liste2= 
    match liste1.pointer with
    | Nil -> { pointer = liste2.pointer}
    | List (a, tail) ->  { pointer = List (a, (ajout tail liste2))}
  in l.pointer <- (ajout l l').pointer
;;

let filter p l = 
  let rec filtre pred liste =
    match liste.pointer with
    | Nil -> nil ()
    | List (elt, tail) ->  
        if pred elt then { pointer = List (elt, (filtre p tail))} else 
          { pointer = ( filtre p tail).pointer}
  in l.pointer <- (filtre p l).pointer 
;;
