let read a index = match index with
  | Index k -> a.(k)
;;

let inside a index = match index with
  | Index k -> if k >= 0 && k < Array.length a then true else false
;;

let next index = match index with
  | Index k -> Index (k + 1)
;;

let min_index a =
  let rec min_index_rec a idx_compt idx_min=
    if inside a (next idx_compt) = false then idx_min else
    if read a idx_compt < read a idx_min then
      min_index_rec a (next idx_compt) idx_compt else
      min_index_rec a (next idx_compt) idx_min
  in min_index_rec a (Index 0) (Index 0)
;;
