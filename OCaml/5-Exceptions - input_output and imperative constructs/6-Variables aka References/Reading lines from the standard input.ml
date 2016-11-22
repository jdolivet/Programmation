let read_lines () =
  let sl = ref [] in
  let rec aux () =
    try
      sl := read_line () :: !sl ;
      aux ()
    with
      End_of_file -> () in
  aux ();
  List.rev !sl
;;
