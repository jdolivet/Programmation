let swap ra rb =
  let rc = ref !rb in
  rb := !ra;
  ra := !rc
;;

let update r f =
  let res = ref !r in
  r := f !r;
  !res
;;

let move l1 l2 =
  match !l1 with
  | [] -> raise Empty
  | hd::tl -> 
      l2 := hd :: !l2;
      l1 := tl
;;

let reverse l =
  let rev = ref [] in
  let liste = ref l in
  let doing =
    try
      while true do 
        move liste rev
      done
    with _ -> ()
  in doing;
  !rev
;;
