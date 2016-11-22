let filter p l = List.fold_left
    (fun liste element -> if p element then element::liste else liste)
    []
    l
;; 

let partition p l = List.fold_right
    (fun element (l1,l2) -> if p element then ((element::l1), l2) else (l1, (element::l2))) 
    l
    ([], []) 
;;

let rec sort = function 
  | [] -> []
  | h::r -> let (l1,l2) = partition (function x -> x < h) r in
      sort(l1)@(h::sort(l2))
;;
