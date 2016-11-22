module MultiSet = struct
  (* A multi-set of type ['a t] is a collection of values of
     type ['a] that may occur several times. *)
  type 'a t = 'a list

  (* [occurrences s x] return the number of time [x] occurs
     in [s]. *)
  let rec occurrences l elt = match l with
    | [] -> 0
    | hd::tl ->  if hd = elt then  1 + occurrences tl elt else occurrences tl elt 
    

  (* The empty set has no element. There is only one unique
     representation of the empty set. *)
  let empty = []

  (* [insert s x] returns a new multi-set that contains all
     elements of [s] and a new occurrence of [x]. Typically,
     [occurrences s x = occurrences (insert s x) x + 1]. *)
  let insert l elt = elt::l

  (* [remove s x] returns a new multi-set that contains all elements
     of [s] minus an occurrence of [x] (if [x] actually occurs in
     [s]). Typically, [occurrences s x = occurrences (remove s x) x -
     1] if [occurrences s x > 0]. *)
  let rec remove l elt = 
    let rec moins liste e res count = match liste with
      | [] -> res
      | hd::tl -> if e = hd && count = 0 then moins tl e res (count + 1) else 
            moins tl e (hd::res) count
    in moins l elt [] 0
              
end 
;;

let rec letters word = 
  if String.length word = 0 then MultiSet.empty else 
    let long = String.length word in
    MultiSet.insert (letters (String.sub word 1 (long - 1)))(String.get word 0) 
;;

let anagram word1 word2 =
  List.sort compare (letters word1) = List.sort compare (letters word2)
;;
