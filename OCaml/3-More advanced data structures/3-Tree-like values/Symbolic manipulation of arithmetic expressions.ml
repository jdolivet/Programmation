let my_example =
  EAdd (EMul (EInt 2, EInt 2), EMul (EInt 3, EInt 3))
;;

let rec eval e = match e with 
  | EInt n -> n
  | EAdd (a, b) -> eval a + eval b
  | EMul (a, b) -> eval a * eval b
;;

let factorize e = match e with
  | EAdd (EMul (a, b), EMul (a', c)) ->
      if a = a' then
        EMul (a, EAdd (b, c)) else
        EAdd (EMul (a, b), EMul (a', c))
  | e -> e 
;;

let expand e = match e with
  | EMul (a, EAdd (b, c)) -> EAdd (EMul (a, b), EMul (a, c)) 
  | e -> e 
;;

let simplify e = match e with
  | EMul (a, EInt 0) -> EInt 0
  | EMul (EInt 0, a) -> EInt 0
  | EMul (a, EInt 1) -> a
  | EMul (EInt 1, a) -> a
  | EAdd (a, EInt 0) -> a
  | EAdd (EInt 0, a) -> a
  | e -> e
      
;;
