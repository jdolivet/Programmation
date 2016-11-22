let create size =
  Array.init (size + 1) (fun i -> 0)
;;

let push buf elt =
  let long = Array.length buf in
  let pos = buf.(0) in
  if pos + 1 < long then buf.(pos + 1) <- elt else raise Full;
  buf.(0) <- pos + 1
;; 

let append buf arr = 
  let long = Array.length arr in
  for i = long - 1 downto 0 do
    push buf arr.(i)
  done
;;

let pop buf = 
  let pos = buf.(0) in
  if pos = 0 then raise Empty else 
    let result = buf.(pos) in
    buf.(0) <- pos - 1; 
    buf.(pos) <- 0;
    result
;; 
