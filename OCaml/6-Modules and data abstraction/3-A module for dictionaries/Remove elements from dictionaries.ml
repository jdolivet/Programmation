module Dict : DictSig = struct
  type ('key, 'value) t =
    | Empty
    | Node of ('key, 'value) t * 'key * 'value * ('key, 'value) t

  let empty = Empty

  let rec add d k v =
    match d with
    | Empty -> Node (Empty, k, v, Empty)
    | Node (l, k', v', r) ->
        if k = k' then Node (l, k, v, r)
        else if k < k' then Node (add l k v, k', v', r)
        else Node (l, k', v', add r k v)

  exception NotFound

  let rec lookup d k =
    match d with
    | Empty ->
        raise NotFound
    | Node (l, k', v', r) ->
        if k = k' then v'
        else if k < k' then lookup l k
        else lookup r k
            
  let rec find_max = function
    | Empty -> assert false
    | Node (_, k, v, Empty) -> (k, v)
    | Node (_, k, v, r) -> find_max r;;
            
  let rec remove d k = match d with
    | Empty ->
        Empty
    | Node (l, k', v', r) ->
        if k = k' then join l r
        else if k < k' then Node (remove l k, k', v', r)
        else Node (l, k', v', remove r k)
  and join l r =
    match l, r with
    | Empty, r -> r
    | l, Empty -> l
    | l, r -> let m = find_max l in 
        match m with
        | a, b -> 
            Node (remove l a, a, b, r);;
            
end 
;;
