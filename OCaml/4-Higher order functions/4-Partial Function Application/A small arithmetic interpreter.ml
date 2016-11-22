let rec lookup_function n = function 
  | [] -> invalid_arg "lookup_function"
  | l :: ls -> match l with
    | (a, b) -> if a = n then b else lookup_function n ls 
;;
  
let add_function name op env = 
  (name, op) :: env 
;;

let my_env = 
  add_function "min" (fun a b -> if a < b then a else b) initial_env
;;


let rec compute env op = match op with
  | Value e -> e
  | Op (name, op1, op2) ->  (lookup_function name env) (compute env op1) (compute env op2) 
;;

let rec compute_eff env = function
  | Value e -> e
  | Op (name, op1, op2) ->  (lookup_function name env) (compute env op1) (compute env op2) 
;;
