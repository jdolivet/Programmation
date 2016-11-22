let bfs t =
  let rec aux results = function
    | [] ->
        List.rev results
    | l :: ls ->
        let results = (Tree.Iterator.focus l) :: results in
        try
          aux results (ls @ [Tree.Iterator.go_first l; Tree.Iterator.go_second l])
        with Tree.Iterator.Fail ->
          aux results ls
  in
  aux [] [Loc (t, Top)]
;;
