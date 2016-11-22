let find a w =
  let rec find_rec a w = function
    | idx -> if idx = Array.length a then None else
        if a.(idx) = w then Some idx else find_rec a w (idx + 1) 
  in 
  find_rec a w 0
;;

let default_int = function
  | None -> 0
  | Some x -> x
;;

let merge a b = match a,b with
  | (None, None) -> None
  | (None, Some x) -> Some x
  | (Some x, None) -> Some x
  | (Some x, Some y) -> Some (x + y)
;;
