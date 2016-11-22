let display_image width height f_image =
  for y = 0 to height do
    for x = 0 to width do
      if f_image x y = true then print_string "#" else print_string " "
    done
            ; print_string "\n"
  done
;;

let rec render blend x y = match blend, x, y with
  | Image im, x, y -> im x y
  | And (b1,b2), x, y -> (render b1 x y) && (render b2 x y)
  | Or (b1,b2), x, y -> (render b1 x y) || (render b2 x y)
  | Rem (b1,b2), x, y -> (render b1 x y) && not (render b2 x y)
;;

let display_blend width height blend =
  display_image width height (render blend)
;;
