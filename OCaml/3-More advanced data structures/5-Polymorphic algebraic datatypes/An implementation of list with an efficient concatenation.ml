let to_list l = 
  let rec transf l liste = match l with
    | CEmpty -> []
    | CSingle a -> a :: liste
    | CApp (a, b) -> (transf a liste) @ (transf b liste)
  in
  transf l []
;;

let rec of_list l =
  let rec transf l liste = match liste with
    | [] -> CEmpty
    | [a] ->  CSingle a
    | a :: xs -> CApp (CSingle a, transf (CSingle a) xs)
  in
  transf CEmpty l
;;

let append l1 l2 = match l1, l2 with
  | CEmpty, l -> l
  | l, CEmpty -> l
  | l1, l2 -> CApp (l1, l2)
;;


let hd l = 
  let liste = to_list l in 
  match liste with
  | [] -> None
  | x :: xs -> Some x
;;

let tl l =
  let liste = to_list l in 
  match liste with
  | [] -> None
  | x :: xs -> Some (of_list xs)
;;
