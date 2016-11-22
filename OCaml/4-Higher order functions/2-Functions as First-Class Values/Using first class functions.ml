let rec compose = function 
  | [] -> (function x-> x)
  | f :: fs -> (function x-> f(compose fs(x)))
;;

let rec fixedpoint f start delta =
  if ((f(start) -. start) < delta && (f(start) -. start) >= 0.) || 
     ((start -. f(start)) < delta && (start -. f(start)) >= 0.) then start else 
    fixedpoint f (f(start)) delta
;;
