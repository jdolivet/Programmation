let rec height t = match t with
  | Empty -> 0
  | Node (l, _, r) -> 
      let l1 = height l in
      let l2 = height r in
      if l1 < l2 then l2 + 1 else l1 + 1
;;

let rec balanced t  = match t with
  | Empty -> true
  | Node (l, _, r) -> 
      if height l = height r then 
        true && balanced l && balanced r 
      else false
;;
