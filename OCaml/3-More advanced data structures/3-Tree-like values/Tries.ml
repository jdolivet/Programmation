let rec children_from_char m c = match m with
  | [] -> None
  | y :: ys -> match y with
    | (a, b) ->  if a = c then Some b else children_from_char ys c
;;

let rec update_children m c t = match m with
  | [] -> (c, t) :: []
  | x :: xs -> match x with  
    | (a, t') -> if a = c then (c, t) :: xs else x :: update_children xs c t
;;

let rec lookup trie w =
  match w, trie with
  | "", Trie (e, _) -> e 
  | str, Trie (_, liste) -> 
      let result = children_from_char liste str.[0] in
      match result with
      | None -> None
      | Some b -> lookup b (String.sub str 1 (String.length str - 1)) 
;;

let rec insert trie w k = 
  match w, trie with
  | "", Trie (e, liste) -> Trie (Some k, liste) 
  | str, Trie (e, liste) -> 
      let wbis = (String.sub str 1 (String.length str - 1)) in
      let result = children_from_char liste str.[0] in
      match result with 
      | None -> Trie (e, update_children liste str.[0] (insert empty wbis k))
      | Some b -> Trie (e, update_children liste str.[0] (insert b wbis k))
;; 
