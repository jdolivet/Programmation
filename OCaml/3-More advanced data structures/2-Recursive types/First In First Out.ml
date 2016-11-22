let is_empty ((front, back) :queue) =
  if front @ List.rev back = [] then true else false
;;

let enqueue x ((front, back) : queue) :queue =
  (front, List.rev (List.rev back @ [x]))
;;

let split l = 
  let rec split_rec l back = match l with
    | [] -> (l, back) 
    | x :: xs -> 
        if List.length xs = List.length back || List.length xs + 1 = List.length back 
        then (List.rev l, back) else
          split_rec xs (back @ [x])
  in
  split_rec l []
;;

let dequeue ((front, back) : queue) = match (front, List.rev back) with 
  | ([], (x :: xs)) -> x, (([], List.rev xs) : queue)
  | ((y :: ys), l) -> y, ((ys, List.rev l) : queue) 
;;

