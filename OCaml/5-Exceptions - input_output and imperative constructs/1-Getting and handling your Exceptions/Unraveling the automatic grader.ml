let exec f x = 
  try 
    Ok (f x)
  with
    e -> (Error e)
;;

let compare user reference to_string = match user, reference with
  | Ok a, Ok b -> if a = b then ("got correct value " ^ to_string a,Successful) 
      else ("got unexpected value " ^ to_string a, Failed)
  | Error a, Error b -> if a=b then ("got correct exception " ^ exn_to_string a,Successful) 
      else ("got unexpected exception " ^ exn_to_string a, Failed)
  | Error a, Ok _ -> ("got unexpected exception " ^ exn_to_string a, Failed)
  | Ok a, Error _ -> ("got unexpected value " ^ to_string a, Failed)                                                    
;;

let test user reference sample to_string = 
  let rec append resultat size =
    if size = 0 then resultat else
      let s = sample () in 
      append (resultat @ [compare (exec user s) (exec reference s) to_string]) (size -1)
  in append [] 10
;;
