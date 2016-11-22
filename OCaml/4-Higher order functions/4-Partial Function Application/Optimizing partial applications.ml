let ccr = fun a -> fun b -> fun c -> fun s ->
  s /. (2. *. cos(a /. 2.) *. 2. *. cos(b /. 2.) *. 2. *. cos(c /. 2.))
;;

let ccr = fun a -> 
  let a = 8. *. cos(a /. 2.) in fun b -> 
    let b = a *. cos(b /. 2.) in fun c ->  
      let c = b *. cos(c /. 2.) in fun s -> 
        s /. c
;;
