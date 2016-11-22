let rec mem x l = match l with
  | [] -> false
  | y ::  ys -> if x = y then true else mem x ys
;;

let rec append l1 l2 = match l2 with
  | [] -> l1
  | x :: xs -> append (l1 @ [x]) xs
;;

let rec combine l1 l2 = match l1, l2 with
  | [], [] -> []
  | (x :: xs), (y :: ys) -> [(x, y)] @ (combine xs ys)
;;

let rec assoc l k = match l with
  | [] -> None
  | y :: ys -> match y  with
    | (a, b) ->  if a = k then Some b else assoc ys k
;;
