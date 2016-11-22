let output_multiples x n m =
  for i = n to m do 
    if (mod) i x = 0 then 
      begin
        print_int i ; 
        print_string ","
      end
  done
;;

exception Zero;;

let display_sign_until_zero f m =
  try
    for i = 0 to m do
      if f i = 0 then 
        raise Zero 
      else
      if f i < 0 then 
        begin
          print_string "negative"; 
          print_string "\n"
        end
      else 
        begin
          print_string "positive"; 
          print_string "\n"
        end
    done;
  with
    Zero -> print_string "zero"
;;
