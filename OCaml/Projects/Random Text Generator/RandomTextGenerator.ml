(* -- Part A -------------------------------------------------------------- *)

let words str =
  let rec rec_split str liste mot = 
    if str = "" then liste@[mot] else
    if str.[0] = ' ' then 
      rec_split (String.sub str 1 (String.length str - 1)) (liste@[mot]) "" else
      rec_split (String.sub str 1 (String.length str - 1)) liste (mot^(String.make 1 str.[0]))
  in 
  rec_split str [] ""
;; 

let build_ltable words = 
  let first = List.hd words in 
  (*first make a table with each word and his successor*)
  let rec build_rec liste ltab = match liste with
    | [] -> []
    | [w] -> ltab@[(w,["STOP"])]
    | hd1::hd2::tl -> 
        build_rec (hd2::tl) (ltab)@[(hd1,[hd2])]
  in 
  let liste_all = ("START", [first]) :: (List.rev (build_rec words []))
  in
  (* compact the list with same keys*)
  let rec compact liste_init liste_fin = match liste_init with
    | [] -> liste_fin
    | hd::tl -> match hd with
      | (e, l) -> let result = try List.assoc e tl with _ -> [] in
          if result = [] then compact tl (hd::liste_fin) else 
            compact ((e, result@l)::(List.remove_assoc e tl)) liste_fin
  in
  compact liste_all []
;; 

let next_in_ltable table word =
  let liste = List.assoc word table in
  let idx = Random.int (List.length liste) in
  List.nth liste idx
;;

let walk_ltable table =
  let first = next_in_ltable table "START" in
  let rec parcours tab list word = 
    let next = next_in_ltable tab word in
    if next = "STOP" then list else 
      parcours tab (list@[next]) next
  in parcours table [first] first
;;

(* -- Part B -------------------------------------------------------------- *)

let compute_distribution l =
  let rec construct list distr = match list with
    | [] -> distr
    | hd::tl -> 
        let cpt = try List.assoc hd distr.amounts with _ -> 0 in
        let old_tot = distr.total in
        let old_list = distr.amounts in
        construct tl {total = old_tot + 1 ; amounts = (hd, cpt + 1)::(List.remove_assoc hd old_list)}
  in
  construct l {total = 0; amounts = []}
;;

let build_htable words =
  let first = List.hd words in 
  (*first make a htable with each word and his successor*)
  let rec build_rec liste htab = match liste with
    | [] -> Hashtbl.create 0
    | [w] -> 
        let liste = try Hashtbl.find htab w with _ -> []
        in
        Hashtbl.replace htab w (["STOP"]@liste); 
        htab
    | hd1::hd2::tl -> 
        let liste = try Hashtbl.find htab hd1 with _ -> []
        in
        Hashtbl.replace htab hd1 ([hd2]@liste); 
        build_rec (hd2::tl) htab
  in 
  let htab_start = Hashtbl.create 0 in
  Hashtbl.add htab_start "START" [first];
  let htab_first = build_rec words htab_start in
  let htab_fin = Hashtbl.create 0 in
  Hashtbl.iter (fun a b -> Hashtbl.replace htab_fin a (compute_distribution b)) htab_first;
  htab_fin
;; 

let next_in_htable table word =
  let distr = Hashtbl.find table word in
  let nb = Random.int (distr.total) in
  let liste = distr.amounts in
  let rec alea list nomb = match list with
    | [] -> ""
    | hd::tl -> match hd with
      | (a,b) -> if b > nomb then a else alea tl (nomb - b)
  in 
  alea liste nb
;;

let walk_htable table =
  let first = next_in_htable table "START" in
  let rec parcours tab list word = 
    let next = next_in_htable tab word in
    if next = "STOP" then list else 
      parcours tab (list@[next]) next
  in parcours table [first] first
;;


(* -- Part C -------------------------------------------------------------- *)

let sentences str = 
  let ajout list str = if str = "" then list else list@[str] in
  let rec rec_split str liste sentence mot = 
    if str = "" then  
      if sentence = [] then 
        liste else
        liste@[ajout sentence mot] 
    else
    if str.[0] = '?' || str.[0] = '!' ||  str.[0] = '.' then 
      rec_split (String.sub str 1 (String.length str - 1)) (liste@[(ajout sentence mot)@[String.make 1 str.[0]]]) [] ""
    else
    if str.[0] = ';' || str.[0] = ',' || str.[0] = ':' || str.[0] = '-' || str.[0] = '\'' || str.[0] = '"' then 
      rec_split (String.sub str 1 (String.length str - 1)) liste ((ajout sentence mot)@[String.make 1 str.[0]]) ""
    else
    if (Char.code str.[0] >= 128) && (Char.code str.[0] <= 255) ||
       (Char.code str.[0] >= 97) && (Char.code str.[0] <= 122) ||
       (Char.code str.[0] >= 65) && (Char.code str.[0] <= 90) ||
       (Char.code str.[0] >= 48) && (Char.code str.[0] <= 57) 
    then 
      rec_split (String.sub str 1 (String.length str - 1)) liste sentence (mot^(String.make 1 str.[0]))
    else 
      rec_split (String.sub str 1 (String.length str - 1)) liste (ajout sentence mot) "" 
  in 
  rec_split str [] [] ""
;; 

let start pl = 
  let rec build nb liste = 
    if nb = 0 then liste else build (nb-1) ("START"::liste)
  in build pl []
;;

let shift l x = match l with
  |[] -> [x]
  | hd::tl -> tl@[x]
;; 

let build_ptable words pl =
  let first = List.hd words in 
  (*first make a htable with each word and his successor*)
  let rec build_rec liste htab buffer= match liste with
    | [] -> Hashtbl.create 0
    | [w] -> 
        let liste = try Hashtbl.find htab (shift buffer w) with _ -> []
        in
        Hashtbl.replace htab (shift buffer w) (["STOP"]@liste); 
        htab
    | hd1::hd2::tl -> 
        let liste = try Hashtbl.find htab (shift buffer hd1) with _ -> []
        in
        Hashtbl.replace htab (shift buffer hd1) ([hd2]@liste); 
        build_rec (hd2::tl) htab (shift buffer hd1)
  in 
  let htab_start = Hashtbl.create 0 in
  Hashtbl.add htab_start (start pl) [first];
  let htab_first = build_rec words htab_start (start pl) in
  let htab_fin = Hashtbl.create 0 in
  Hashtbl.iter (fun a b -> Hashtbl.replace htab_fin a (compute_distribution b)) htab_first; 
  {prefix_length = pl; table = htab_fin}
;;

let next_in_ptable table liste =
  let distr = Hashtbl.find table liste in
  let nb = Random.int (distr.total) in
  let liste = distr.amounts in
  let rec alea list nomb = match list with
    | [] -> ""
    | hd::tl -> match hd with
      | (a,b) -> if b > nomb then a else alea tl (nomb - b)
  in 
  alea liste nb
;;


let walk_ptable { table ; prefix_length = pl } =
  let first = next_in_ptable table (start pl) in
  let rec parcours tab list buffer = 
    let next = next_in_ptable tab buffer in
    if next = "STOP" then list else 
      parcours tab (list@[next]) (shift buffer next)
  in parcours table [first]  (shift (start pl) first)
;;

let merge_ptables tl =
  let pt_zero =  { prefix_length = 0 ; table =Hashtbl.create 0} in
  let distr_zero =  { total = 0 ; amounts = []} in
  let rec append pt1 pt2 =
    if pt1.prefix_length <> pt2.prefix_length then
      raise (Invalid_argument "Longueur incoherente")
    else
      let t1 = pt1.table in
      let t2 = pt2.table in 
      (* on cherche ce qu il y a en commun et on passe dans t1*)
      Hashtbl.iter (
        fun a b -> 
          let distr = try Hashtbl.find t2 a with _ -> distr_zero in
          if distr <> distr_zero then 
            (Hashtbl.replace t1 a {total = b.total + distr.total; amounts = b.amounts@distr.amounts}; 
             Hashtbl.remove t2 a)
      ) 
        t1; 
      (* on ajoute tout le reste de t2 dans t1*)
      Hashtbl.iter (
        fun a b -> 
          Hashtbl.add t1 a b; 
      ) 
        t2; 
      pt1
  in
  let rec merge liste =
    match liste with
    | [] -> pt_zero
    | [t] -> t
    | t1::t2::tl -> merge ((append t1 t2)::tl)
  in
  merge tl
;;
