let rec loop p f x =
  if p x = true then  x  else loop p f (f x)
;;

let rec exists p l = match l with
  | [] -> false
  | hd::tl -> if (p hd) then true else exists p tl
;;

let rec find p l = match l with
  | [] -> raise NotFound
  | hd::tl -> if (p hd) then hd else find p tl
;;

(* --- Part A: A Generic Problem Solver --- *)

let near x =
  let rec construct nb ref l = 
    if abs((nb) - ref) <= 2 then construct (nb + 1) ref ((nb)::l) else l
  in
  construct (x - 2) x []
;; 

let rec flat_map r = function 
  | [] -> []
  | x::xs -> ((r x)@(flat_map r xs))
;; 

let rec iter_rel rel n =  function
  | x ->  if n <= 1 then rel x else flat_map rel ((iter_rel rel (n - 1)) x)
;; 

let solve r p x = 
  if p x then x else
    let rec aux n liste = 
      try 
        find p liste
      with
        _ -> aux (n + 1) (iter_rel r (n + 1) x)
    in
    aux 1 (iter_rel r 1 x)
;;

let solve_path r p x = 
  if p x then [x] else
    let solution = solve r p x in 
    let rec aux liste result start =
      if exists (fun y -> y = solution) liste then result@[solution]
      else
        let next = if solution >= x then List.hd (List.rev liste) else List.hd liste
        in
        aux (r next) (result@[next]) next
    in
    aux (r x) [x] x
;;

let archive_map opset r (s, l) =
  let liste = flat_map r l in
  let rec construct l result set = match l with
    | [] -> (set, result)
    | hd::tl -> if opset.mem hd set then construct tl result set else 
          construct tl (hd::result) (opset.add hd set)
  in
  construct liste [] s
;;

let solve' opset r p x = 
  let rec aux (set, liste) = 
    try 
      find p liste
    with
      _ -> aux (archive_map opset r (set, liste))
  in
  aux (opset.empty, [x])
;;

let solve_path' opset r p x =
  let r' = function 
    | [] -> [] 
    | hd::tl -> List.map (fun y -> y::hd::tl) (r hd) in
  let p' = function 
    | [] -> false 
    | hd::tl -> p hd in
  let rec aux (set, liste) =
    try 
      find p' liste
    with
      _ -> aux (archive_map opset r' (set, liste))
  in List.rev (aux (opset.empty, [[x]]))
;;

let solve_puzzle p opset c = 
  let rel = function config -> List.map (p.move config) (p.possible_moves config) in
  solve_path' opset rel p.final c
;;


(* --- Part B: A Solver for Klotski --- *)

let final board =
  if board.(3).(1) = s && 
     board.(4).(2) = s
  then true else false
;;

let move_piece board piece { drow; dcol } = 
  let transform board piece {drow; dcol} = 
    let rec modif_ligne l i j ligne =
      if j = 4 then ligne else 
      if l.(j) = piece then
        try
          if board.(i+drow).(j+dcol) = piece || board.(i+drow).(j+dcol) = x then 
            try
              if board.(i-drow).(j-dcol) = piece then
                modif_ligne l i (j + 1) (ligne@[piece]) 
              else modif_ligne l  i (j + 1) (ligne@[x])
            with _ -> modif_ligne l  i (j + 1) (ligne@[x]) 
          else raise NotFound 
        with _ -> raise NotFound 
      else 
      if l.(j) = x then
        try
          if board.(i-drow).(j-dcol) = piece then 
            modif_ligne l i (j + 1) (ligne@[piece]) else
            modif_ligne l i (j + 1) (ligne@[x])
        with _ -> modif_ligne l i (j + 1) (ligne@[x])
      else
        modif_ligne l i (j + 1) (ligne@[l.(j)])
    in
    let rec modif_colonne i liste =
      if i = 5 then liste else 
        modif_colonne (i + 1) (liste@[Array.of_list (modif_ligne board.(i) i 0 [])])
    in
    Array.of_list (modif_colonne 0 []) 
  in try Some (transform board piece {drow; dcol}) with _ -> None
;; 

let possible_moves board =
  let list_dir = [{dcol = 1; drow = 0};{dcol = -1; drow = 0};{dcol = 0; drow = 1};{dcol = 0; drow = -1}] in
  let rec recup elt_list list_move = match elt_list with
    | [] -> list_move
    | hd::tl -> 
        let possible = List.map (move_piece board hd) list_dir in
        let rec recup_piece list listmov i = match list with
          | [] -> recup tl listmov
          | hd1::tl1 -> match hd1 with
            | Some bd -> recup_piece tl1 ((Move (hd, List.nth list_dir i, bd))::listmov) (i+1) 
            | None -> recup_piece tl1 listmov (i+1)
        in
        recup_piece possible list_move 0
  in recup all_pieces []
;;

module BoardSet = Set.Make (struct
    type t = board
    let compare b1 b2 =
      let ordre = [X;V;C;H;S] in
      let rec comp_ligne l1 l2 i j rep =
        let find_index x lst =
          let rec aux x lst c = match lst with
            | [] -> -1
            | hd::tl -> if (hd=x) then c else aux x tl (c+1)
          in aux x lst 0
        in
        if j = 4 then rep else 
          let e1 = l1.(j) in
          let e2 = l2.(j) in
          match e1, e2 with
          | (t1,n1), (t2, n2) ->
              let r1 = find_index t1 ordre in
              let r2 = find_index t2 ordre in
              if r1 < r2 then -1 else 
              if r1 > r2 then 1 else
              if n1 < n2 then -1 else 
              if n1 > n2 then 1 else
                comp_ligne l1 l2 i (j + 1) rep
      in
      let rec comp_colonne i resp =
        if i = 5 then resp else 
        if resp = -1 then -1 else
        if resp = 1 then 1 else
          comp_colonne (i + 1) (comp_ligne b1.(i) b2.(i) i 0 0)
      in
      comp_colonne 0 0
  end)
;;

let klotski  = {move;  possible_moves; final};;

let solve_klotski board =
  let opset = {
    empty = BoardSet.empty;
    mem = (fun l -> BoardSet.mem (List.hd l));
    add = (fun l -> BoardSet.add (List.hd l))}
  in
  solve_puzzle klotski opset board
;;
