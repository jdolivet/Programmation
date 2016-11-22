let wrap l = 
  List.map (function x -> [x]) l
;;

let rec tree_map f = function 
  | Leaf a -> Leaf (f(a))
  | Node (l, a, r) -> Node (tree_map f l, (f(a)), tree_map f r)
;;
