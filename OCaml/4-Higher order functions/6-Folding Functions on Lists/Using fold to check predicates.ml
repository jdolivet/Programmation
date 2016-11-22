let for_all p l = 
  let liste = List.fold_left
      (fun liste element -> if p element then element::liste else liste)
      []
      l
  in
  if List.length liste == List.length l then true else false
;; 

let exists p l =
  let liste = List.fold_left
      (fun liste element -> if p element then element::liste else liste)
      []
      l
  in
  if List.length liste >= 1 then true else false
;; 

let sorted cmp l = match l with
  | [] -> true
  | _::lr -> 
      let resultat = List.fold_left 
          (fun (compteur, pos) element -> 
             if (cmp (List.nth l pos) element <= 0) then (compteur + 1, pos + 1) else 
               (compteur, pos + 1))
          (0,0)
          lr
      in 
      match resultat with
      | (corrects,_) -> if corrects + 1 = List.length l then true else false
;; 
