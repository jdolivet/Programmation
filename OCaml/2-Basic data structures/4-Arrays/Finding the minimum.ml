let min a = 
  let rec min_rec array inf sup = 
    if sup = inf then array.(inf) else
    if array.(sup) > array.(inf) then 
      min_rec array inf (sup - 1) else
      min_rec array (inf + 1) sup
  in 
  min_rec a 0 (Array.length a - 1)
;;

let min_index a =
  let rec min_index_rec array sup=
    if a.(sup) = min array then sup else
      min_index_rec array (sup - 1)
  in 
  min_index_rec a (Array.length a - 1)
;;

let it_scales =
  "no" 
;;
