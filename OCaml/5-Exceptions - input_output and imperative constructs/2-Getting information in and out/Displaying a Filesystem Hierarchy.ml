let rec print_path path = match path with
  | [] -> ()
  | [e] -> print_string e
  | hd::tl -> print_string hd ; print_string "/" ; print_path tl
;;

let rec print_file lvl name = match lvl with
  | 0 -> print_string name
  | _ -> print_string "|" ; print_file (lvl - 1) name 
;;

let print_symlink lvl name path =
  print_file lvl name ; print_string " -> " ; print_path path
;;

let rec print_dir lvl name = match lvl with
  | 0 -> print_string "/" ; print_string name
  | _ -> print_string "|" ; print_dir (lvl - 1) name 
;;

let print_filesystem root = 
  let rec print_filesysteme lvl items = match items with
    | [] -> ()
    | hd::tl -> match hd with 
      | (name, file) -> match file with
        | File -> print_file lvl name ; print_string "\n" ;
            print_filesysteme lvl tl 
        | Symlink path -> print_symlink lvl name path ; print_string "\n" ;
            print_filesysteme lvl tl 
        | Dir system -> print_dir lvl name ; print_string "\n" ;
            print_filesysteme (lvl + 1) system ;
            print_filesysteme lvl tl 
  in
  print_filesysteme 0 root 
;;

let rec resolve sym path = 
  let rec resoudre name liste = match name with
    | [] -> liste 
    | hd::tl -> 
        let listeSur = 
          try
            (List.rev (List.tl (List.rev liste)))
          with
            Failure(tl) -> []
        in
        if hd = ".." then 
          resoudre tl listeSur else resoudre tl (liste @ [hd])
  in 
  resoudre ((List.rev (List.tl (List.rev sym))) @ path) [] 
;;

let rec file_exists root path = match root with
  | [] -> false
  | hd::tl -> match hd with 
    | (name, file) -> match file with
      | File -> if name = List.hd path then true else file_exists tl path
      | Symlink _ -> file_exists tl path
      | Dir system -> 
          if name = List.hd path then 
            file_exists system (List.tl path) else file_exists tl path
;;

let print_filesystem root = 
  let rec print_filesysteme lvl items chemin = match items with
    | [] -> ()
    | hd::tl -> match hd with 
      | (name, file) -> match file with
        | File -> print_file lvl name ; print_string "\n" ;
            print_filesysteme lvl tl chemin
        | Symlink path -> 
            if file_exists root (resolve (chemin @ [name]) path) = false 
            then 
              (print_file lvl name ; print_string " -> INVALID" ; print_string "\n") 
            else
              (print_symlink lvl name path ; print_string "\n")
            ;
            print_filesysteme lvl tl chemin
        | Dir system -> print_dir lvl name ; print_string "\n" ;
            print_filesysteme (lvl + 1) system (chemin @ [name]);
            print_filesysteme lvl tl chemin
  in
  print_filesysteme 0 root [] 
;;
