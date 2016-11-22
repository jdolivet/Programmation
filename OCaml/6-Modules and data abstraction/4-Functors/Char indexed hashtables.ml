module CharHashedType = struct
  type t = char 
  let equal k1 k2 = (k1 = k2)
  let hash k = Hashtbl.hash k
end

module CharHashtbl = Hashtbl.Make (CharHashedType)

module Trie : GenericTrie
  with type 'a char_table = 'a CharHashtbl.t =
struct
  type 'a char_table = 'a CharHashtbl.t
  type 'a trie =  Trie of 'a option * 'a trie char_table

  let empty () = Trie (None, CharHashtbl.create 0)

  let rec lookup trie w =
    match w, trie with
    | "", Trie (e, _) -> e 
    | str, Trie (_, t) -> 
        let wbis = (String.sub str 1 (String.length str - 1)) in
        let result = 
          try 
            CharHashtbl.find t str.[0]
          with
            _ -> empty ()
        in
        lookup result wbis 
  
  let rec insert trie w k = 
    match w, trie with
    | "", Trie (e, t) -> Trie (Some k, t) 
    | str, Trie (e, t) -> 
        let wbis = (String.sub str 1 (String.length str - 1)) in
        let result = 
          try 
            CharHashtbl.find t str.[0]
          with
            _ ->  empty ()
        in
        CharHashtbl.add t str.[0] (insert result wbis k);
        Trie (e, t)
                      
end
