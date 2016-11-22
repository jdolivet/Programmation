let rec last_element = function 
    [] -> (invalid_arg "last_element")
  | [e] -> e
  | x :: xs -> last_element xs
;;

let rec is_sorted = function
    [] -> true 
  | [_] -> true 
  | x :: (y :: xs) -> if x < y then true && is_sorted (y :: xs) else false
;;
