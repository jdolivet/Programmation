let rec print_int_list l = match l with
  | [] -> ()
  | hd::tl -> print_int hd; print_string "\n"; print_int_list tl
;;

let print_every_other k l = 
  let liste = List.fold_left
      (fun (list, pos) element -> if (mod) pos k = 0 then ((list @ [element]), pos + 1) 
        else (list, pos + 1))
      ([], 0)
      l
  in match liste with
    (l,_) -> print_int_list l
;;

let rec print_list print l = match l with
  | [] -> ()
  | hd::tl -> print hd; print_string "\n"; print_list print tl
;;
