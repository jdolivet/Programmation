let rotate a = 
  try
    let n = Array.length a in 
    let v = a.(0) in
    for i = 0 to n-2 do
      a.(i) <- a.(i+1)
    done;
    a.(n-1)<-v 
  with _ -> ()
;;

let rotate_by a n =
  if n >= 0 then
    for i = 1 to n do
      rotate a
    done
  else
    let stop = Array.length a in
    for i = 1 to (n + stop) do
      rotate a
    done
;;
