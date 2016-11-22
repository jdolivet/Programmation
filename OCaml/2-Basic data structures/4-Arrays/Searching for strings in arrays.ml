let is_sorted a =
  let rec is_sorted_rec array inf sup=
    if sup <= inf then true else
    if array.(inf) >= array.(inf + 1) then false else 
      true && is_sorted_rec array (inf + 1) sup
  in 
  is_sorted_rec a 0 (Array.length a - 1)
;;

let find dict word =
  let rec bin_search dict word min max = 
    if min > max then -1 else
    if min = max then 
      if dict.(min)= word then min else -1 
    else
      let med = (min + max) / 2 in 
      if dict.(med) >= word then bin_search dict word min med else 
        bin_search dict word (med + 1) max
  in
  bin_search dict word 0 (Array.length dict - 1)
;;
